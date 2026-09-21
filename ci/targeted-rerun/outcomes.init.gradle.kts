// CI-only build-events recorder for ci/targeted-rerun/run_tests.py.
// Passed with --init-script only from the wrapper; must not change build behavior.
// Writes one JSON file (path from -DtargetedRerun.outcomesFile=<path>) describing,
// for every task in this invocation's task graph: whether it is a Test task, its
// JUnit XML report directory (read from task configuration), its outcome, and
// (for failed tasks) whether the failure was caused by failing tests.

import org.gradle.api.Plugin
import org.gradle.api.invocation.Gradle
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import org.gradle.api.tasks.testing.Test
import org.gradle.build.event.BuildEventsListenerRegistry
import org.gradle.tooling.Failure
import org.gradle.tooling.events.FinishEvent
import org.gradle.tooling.events.OperationCompletionListener
import org.gradle.tooling.events.task.TaskFailureResult
import org.gradle.tooling.events.task.TaskFinishEvent
import org.gradle.tooling.events.task.TaskSkippedResult
import org.gradle.tooling.events.task.TaskSuccessResult
import java.io.File
import javax.inject.Inject

abstract class TargetedRerunOutcomesService :
    BuildService<BuildServiceParameters.None>, OperationCompletionListener, AutoCloseable {

    private class TaskRecord {
        var isTest: Boolean = false
        var reportsDir: String? = null
        var outcome: String = "NOT_RUN"
        var testFailure: Boolean = false
    }

    private val records = LinkedHashMap<String, TaskRecord>()

    @Synchronized
    fun registerTestTask(path: String, reportsDir: String) {
        records.getOrPut(path) { TaskRecord() }.also {
            it.isTest = true
            it.reportsDir = reportsDir
        }
    }

    @Synchronized
    fun registerKnownTask(path: String) {
        records.getOrPut(path) { TaskRecord() }
    }

    @Synchronized
    override fun onFinish(event: FinishEvent) {
        if (event !is TaskFinishEvent) return
        val record = records.getOrPut(event.descriptor.taskPath) { TaskRecord() }
        when (val result = event.result) {
            is TaskSuccessResult -> record.outcome = when {
                result.isUpToDate -> "UP_TO_DATE"
                result.isFromCache -> "FROM_CACHE"
                else -> "SUCCESS"
            }
            is TaskSkippedResult -> record.outcome =
                if (result.skipMessage == "NO-SOURCE") "NO_SOURCE" else "SKIPPED"
            is TaskFailureResult -> {
                record.outcome = "FAILED"
                val messages = mutableListOf<String>()
                fun collect(failure: Failure) {
                    failure.message?.let { messages.add(it) }
                    failure.causes.forEach(::collect)
                }
                result.failures.forEach(::collect)
                record.testFailure = messages.any { it.startsWith("There were failing tests") }
            }
            else -> record.outcome = "OTHER"
        }
    }

    private fun escape(value: String): String = value
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "")

    @Synchronized
    override fun close() {
        val outFile = System.getProperty("targetedRerun.outcomesFile") ?: return
        val json = StringBuilder("{\"tasks\":{")
        records.entries.forEachIndexed { index, (path, record) ->
            if (index > 0) json.append(",")
            json.append("\"").append(escape(path)).append("\":{")
            json.append("\"isTest\":").append(record.isTest).append(",")
            json.append("\"outcome\":\"").append(escape(record.outcome)).append("\",")
            json.append("\"testFailure\":").append(record.testFailure).append(",")
            json.append("\"reportsDir\":")
            json.append(record.reportsDir?.let { "\"${escape(it)}\"" } ?: "null")
            json.append("}")
        }
        json.append("}}")
        val file = File(outFile)
        file.parentFile?.mkdirs()
        file.writeText(json.toString())
    }
}

abstract class TargetedRerunOutcomesPlugin @Inject constructor(
    private val registry: BuildEventsListenerRegistry
) : Plugin<Gradle> {
    override fun apply(gradle: Gradle) {
        val serviceProvider = gradle.sharedServices.registerIfAbsent(
            "targetedRerunOutcomes", TargetedRerunOutcomesService::class.java
        ) {}
        registry.onTaskCompletion(serviceProvider)

        gradle.taskGraph.whenReady {
            val service = serviceProvider.get()
            allTasks.forEach { task ->
                if (task is Test) {
                    service.registerTestTask(
                        task.path,
                        task.reports.junitXml.outputLocation.get().asFile.absolutePath
                    )
                } else {
                    service.registerKnownTask(task.path)
                }
            }
        }
    }
}

apply<TargetedRerunOutcomesPlugin>()
