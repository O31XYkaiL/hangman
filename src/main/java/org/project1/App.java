package org.project1;

/**
 * Класс, который запускает игру "Виселица".
 * Создает необходимые объекты (словарь, интерфейс пользователя и саму игру) и начинает игровой процесс.
 */
public class App {
    /**
     * Точка входа в программу.
     * Создает экземпляры {@link WordDictionary}, {@link ConsoleUserInterface} и {@link Game},
     * а затем запускает игру.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();
        UserInterface userInterface = new ConsoleUserInterface();
        Game game = new Game(wordDictionary, userInterface);
        game.startGame();
    }
}