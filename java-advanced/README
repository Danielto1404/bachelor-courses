## [Домашнее задание 1. Обход файлов](HW1)

+ Разработайте класс Walk, осуществляющий подсчет хеш-сумм файлов.

    + Формат запуска

       `java Walk <входной файл> <выходной файл>`

    + Входной файл содержит список файлов, которые требуется обойти.
    + Выходной файл должен содержать по одной строке для каждого файла. Формат строки:

        `<шестнадцатеричная хеш-сумма> <путь к файлу>`

    + Для подсчета хеш-суммы используйте алгоритм FNV.
    + Если при чтении файла возникают ошибки, укажите в качестве его хеш-суммы 00000000.
    + Кодировка входного и выходного файлов — UTF-8.
    + Если родительская директория выходного файла не существует, то соответствующий путь надо создать.
    + Размеры файлов могут превышать размер оперативной памяти.
    + Пример

        + Входной файл

            ```
            java/info/kgeorgiy/java/advanced/walk/samples/1
            java/info/kgeorgiy/java/advanced/walk/samples/12
            java/info/kgeorgiy/java/advanced/walk/samples/123
            java/info/kgeorgiy/java/advanced/walk/samples/1234
            java/info/kgeorgiy/java/advanced/walk/samples/1
            java/info/kgeorgiy/java/advanced/walk/samples/binary
            java/info/kgeorgiy/java/advanced/walk/samples/no-such-file
            ```

        + Выходной файл

            ```
            050c5d2e java/info/kgeorgiy/java/advanced/walk/samples/1
            2076af58 java/info/kgeorgiy/java/advanced/walk/samples/12
            72d607bb java/info/kgeorgiy/java/advanced/walk/samples/123
            81ee2b55 java/info/kgeorgiy/java/advanced/walk/samples/1234
            050c5d2e java/info/kgeorgiy/java/advanced/walk/samples/1
            8e8881c5 java/info/kgeorgiy/java/advanced/walk/samples/binary
            00000000 java/info/kgeorgiy/java/advanced/walk/samples/no-such-file
            ```


+ Усложненная версия:

    + Разработайте класс RecursiveWalk, осуществляющий подсчет хеш-сумм файлов в директориях

    + Входной файл содержит список файлов и директорий, которые требуется обойти. Обход директорий осуществляется рекурсивно.

    + Пример

        + Входной файл

        ```
        java/info/kgeorgiy/java/advanced/walk/samples/binary
        java/info/kgeorgiy/java/advanced/walk/samples
        ```

        + Выходной файл

        ```
        8e8881c5 java/info/kgeorgiy/java/advanced/walk/samples/binary
        050c5d2e java/info/kgeorgiy/java/advanced/walk/samples/1
        2076af58 java/info/kgeorgiy/java/advanced/walk/samples/12
        72d607bb java/info/kgeorgiy/java/advanced/walk/samples/123
        81ee2b55 java/info/kgeorgiy/java/advanced/walk/samples/1234
        8e8881c5 java/info/kgeorgiy/java/advanced/walk/samples/binary
        ```


+ При выполнении задания следует обратить внимание на:

    + Дизайн и обработку исключений, диагностику ошибок.
    + Программа должна корректно завершаться даже в случае ошибки.
    + Корректная работа с вводом-выводом.
    + Отсутствие утечки ресурсов.

+ Требования к оформлению задания.

    + Проверяется исходный код задания.
    + Весь код должен находиться в пакете ru.ifmo.rain.фамилия.walk.

## [Домашнее задание 2. Множество на массиве](HW2)

+ Разработайте класс ArraySet, реализующие неизменяемое упорядоченное множество.
    + Класс ArraySet должен реализовывать интерфейс SortedSet (упрощенная версия) или NavigableSet (усложненная версия).
    + Все операции над множествами должны производиться с максимально возможной асимптотической эффективностью.

+ При выполнении задания следует обратить внимание на:
    + Применение стандартных коллекций.
    + Избавление от повторяющегося кода.
