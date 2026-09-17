export default {
  // Общий заголовок
  name: "Сортировка сбоев в Allure 3",
  // Путь к папке с отчётом
  output: "./allure-report",
  // Путь к файлу с историей
  historyPath: "./test-history/history.jsonl",
  // Плагины. В Allure 3 можно добавить несколько плагинов, и для каждого
  // будет создан отдельный отчёт. Здесь мне нужен только один. 
  plugins: {
    // Базовый вариант отчёта в Allure 3 - 'awesome'
    "awesome": {
      import: "@allurereport/plugin-awesome",
      options: {
        // Имя отчёта
        reportName: "Главный отчёт",
        // Язык отчёта
        reportLanguage: "ru",
      },
    },
  },

  categories: {
    rules: [
      {
        // Отображаемое имя
        name: "Несовпадение версии WebDriver",
        // Постоянный идентификатор, благодаря которому категорию
        // можно отслеживать по нескольким запускам
        id: "webdriver-version-mismatch",
        // Правила, по которым тест попадает в категорию
        matchers: {
          statuses: ["failed", "broken"],
          message: /SessionNotCreatedException/,
        },

        // Правила, по которым найденные тесты отображаются внутри категории
        groupBy: ["layer", "owner", "status"],
        groupByMessage: true,
        groupEnvironments: true,
        expand: true,
        hide: false,
      },
    ],
  },
};