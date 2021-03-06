# Генерация автомобильного номера 

## Запуск проекта:

1. ./start.sh

Для запуска проекта необходимо, чтобы на вашей машине был Docker :)

### API

Контекст приложения: `/number`

REST-сервис реализует два GET-метода: `random` и `next`

Правильные примеры вызовов:

> Запрос: `GET http://localhost:8080/number/random`\
> Ответ: `C399BA 116 RUS`

> Запрос: `GET http://localhost:8080/number/next`\
> Ответ: `C400BA 116 RUS`

### Описание

Методы `random` и `next` должны генерировать и выводить строку формата `A111AA 116 RUS`,

где `A` - любой символ из списка `[А, Е, Т, О, Р, Н, У, К, Х, С, В, М]`, `1` - любая цифра, `116 RUS` - константа.

Реализованы два алгоритма генерации номера:
1. *Рандомный* - выдается произвольный номер, соответствующий формату при вызове метода `random`. При этом номер не должен совпадать с любым ранее выданным.
1. *Последовательный* - номера выдаются последовательно от меньшего к большему при вызове метода `next`. Сначала производится итерация цифровых символов, и только после этого итерация буквенных. Как предыдущий номер нужно использовать номер, выданный во время предыдущего вызова алгоритма. Номер не должен совпадать с любым ранее выданным.
Для определения порядка буквенных символов следует использовать русский алфавит.

### Примеры
Пример рандомный: C399BA 116 RUS

Пример последовательный 1: после номера C399BA 116 RUS должен идти номер C400BA 116 RUS

Пример последовательный 2: после номера C999BA 116 RUS должен идти номер C000BB 116 RUS
