# Rotating Connect Four Game

Добро пожаловать в **Rotating Connect Four Game** – игру, которая перевернёт ваше представление о классической **"Четыре в ряд"**!

## Описание
Этот проект представляет собой расширенную версию классической игры **"Четыре в ряд"** с дополнительной механикой вращения игрового поля. После каждого хода доска может изменять свое положение, что добавляет элемент неожиданности, усложняя стратегию и создавая новые возможности для победы. Игра поддерживает до 4 игроков с разными цветами фишек.

## Основные возможности
✔ **Поддержка от 2 до 4 игроков**  
✔ **Выбор размера поля**
✔ **Случайное вращение доски** влево, вправо или вверх дном, возможное после каждого хода  
✔ **Анализ победных комбинаций** в реальном времени  
✔ **Консольный интерфейс с цветным отображением**  
✔ **Разные режимы запуска** (для IntelliJ IDEA и JAR-файла)  
✔ **Оптимизированный алгоритм проверки победы**  

## Как играть
1. Запустите игру.
2. Выберите количество игроков (от **2** до **4**).
3. Введите имена игроков и выберите цвета фишек.
4. Выберите размер игрового поля.
5. Игроки по очереди делают ходы:
    - Введите номер колонки (от 1 до ширины доски), в которую хотите поместить фишку.
    - Фишка падает вниз доски, как в классической игре "Четыре в ряд".
6. Поворот доски: После хода доска может случайным образом повернуться, что изменит расположение фишек.
7. Игра продолжается, пока один из игроков не соберёт 4 фишки в ряд (горизонтально, вертикально или по диагонали), или доска не заполнится.


## Пример игрового процесса
### Два игрока
![](images/Two_players_example_01.png)
![](images/Two_players_example_02.png)
![](images/Two_players_example_03.png)
![](images/Two_players_example_04.png)
![](images/Two_players_example_05.png)
![](images/Two_players_example_06.png)
![](images/Two_players_example_08.png)
![](images/Two_players_example_09.png)
![](images/Two_players_example_10.png)
![](images/Two_players_example_11.png)
![](images/Two_players_example_12.png)
![](images/Two_players_example_13.png)
![](images/Two_players_example_14.png)
![](images/Two_players_example_15.png)
![](images/Two_players_example_16.png)
![](images/Two_players_example_17.png)
![](images/Two_players_example_18.png)
![](images/Two_players_example_19.png)
![](images/Two_players_example_20.png)
![](images/Two_players_example_21.png)
![](images/Two_players_example_22.png)
![](images/Two_players_example_23.png)
![](images/Two_players_example_24.png)
![](images/Two_players_example_25.png)
![](images/Two_players_example_26.png)
![](images/Two_players_example_27.png)
![](images/Two_players_example_28.png)

### Четыре игрока
![](images/Four_players_example_01.png)
![](images/Four_players_example_02.png)
![](images/Four_players_example_03.png)
![](images/Four_players_example_04.png)
![](images/Four_players_example_05.png)
![](images/Four_players_example_06.png)
![](images/Four_players_example_07.png)
![](images/Four_players_example_08.png)
![](images/Four_players_example_09.png)
![](images/Four_players_example_10.png)
![](images/Four_players_example_11.png)
![](images/Four_players_example_12.png)
![](images/Four_players_example_13.png)
![](images/Four_players_example_14.png)
![](images/Four_players_example_15.png)
![](images/Four_players_example_16.png)
![](images/Four_players_example_17.png)
![](images/Four_players_example_18.png)
![](images/Four_players_example_19.png)
![](images/Four_players_example_20.png)
![](images/Four_players_example_21.png)
![](images/Four_players_example_22.png)
![](images/Four_players_example_23.png)
![](images/Four_players_example_24.png)
![](images/Four_players_example_25.png)
![](images/Four_players_example_26.png)
![](images/Four_players_example_27.png)
![](images/Four_players_example_28.png)
![](images/Four_players_example_29.png)
![](images/Four_players_example_30.png)
![](images/Four_players_example_31.png)
![](images/Four_players_example_32.png)
![](images/Four_players_example_33.png)
![](images/Four_players_example_34.png)
![](images/Four_players_example_35.png)
![](images/Four_players_example_36.png)
![](images/Four_players_example_37.png)
![](images/Four_players_example_38.png)
![](images/Four_players_example_39.png)
![](images/Four_players_example_40.png)
![](images/Four_players_example_41.png)
![](images/Four_players_example_42.png)
![](images/Four_players_example_43.png)
![](images/Four_players_example_44.png)
![](images/Four_players_example_45.png)
![](images/Four_players_example_46.png)
![](images/Four_players_example_47.png)
![](images/Four_players_example_48.png)
![](images/Four_players_example_49.png)



## Установка и запуск
### Требования к окружению
- **Java 17+**
- Терминал или консоль с поддержкой ANSI-цветов


### Запуск в IntelliJ IDEA
1. Импортируйте проект в вашу **IntelliJ IDEA**.
2. Запустите класс `MainForIdea.java`.

### Запуск с JAR-файлом
1. Скомпилируйте проект и соберите JAR-файл:
   ```bash
   gradlew build
   ```
2. Запустите игру командой:
   ```bash
   java -jar rotating_connect_four.jar
   ```

## Будущие улучшения и идеи
- **Графический интерфейс (GUI):** Перенос игры на Swing или JavaFX.
- **Онлайн-мультиплеер:** Возможность играть с друзьями через Интернет.
- **Поддержка мобильных платформ:** Разработка версии игры для iOS и Android.
- **Дополнительные режимы игры:** Введение новых правил и игровых режимов, например, турнирного режима со статистикой.


                        