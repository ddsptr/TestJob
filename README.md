Задание:

Реализовать отображение нескольких сложных списков на одном экране.

Основное задание

Экран реализован на основе фрагмента. Пропорции всех элементов должны сохраняться на всех разрешениях экранов (минимальная поддерживаемая плотность - mdpi). Все списки отображают данные из трех коллекций (изображения, основные элементы, комментарии).


1. Горизонтальный слайдер изображений

В верхней части экрана постраничный горизонтальный слайдер изображений с индикатором текущей страницы.


2. Сгруппированный по секциям список

Сложный список, состоящий из двух секций, расположен под слайдером изображений и имеет заголовок (статичный текст: название и краткое описание). При прокрутке вверх этот список перекрывает слайдер изображений, а текст заголовка начинает отображаться в Action Bar. При прокрутке вниз до исходного состояния - текст заголовка исчезает из Action Bar.

Первая секция списка содержит элементы с изображением, заголовком и описанием, как показано на картинке.
Вторая секция - отображает комментарии. Каждый комментарий содержит имя, текст, дату и аватар пользователя.


3. Нижняя панель

При прокрутке списка до секции с комментариями в нижней части экрана появляется панель отправки комментария. При прокрутке вверх - панель исчезает.


Дополнительно, но необязательно

Комментарии могут иметь ответы (1 уровень вложенности). Если комментарий является ответом - он сдвинут на экране вправо на величину, равную половине ширины аватарки пользователя, относительно основного комментария.
Предусмотреть структуру хранения ответов на комментарии в коллекции.
Анимированное исчезание (fade) слайдера изображений при перекрытии его основным списком
Асинхронная загрузка содержимого коллекций с сервера