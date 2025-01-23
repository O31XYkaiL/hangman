package org.project1;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс, представляющий игру "Виселица".
 * Управляет логикой игры, включая выбор категории, уровня сложности и взаимодействие с пользователем.
 */
public class Game {
    private GameState gameState;
    private final WordDictionary wordDictionary;
    private final UserInterface userInterface;
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final int NUMBER_OF_LEVELS = 3;
    private static final int EASY_LVL_MISTAKES = 10;
    private static final int MEDIUM_LVL_MISTAKES = 8;
    private static final int HARD_LVL_MISTAKES = 6;
    private final Random random = new SecureRandom();

    /**
     * Конструктор для создания экземпляра игры.
     *
     * @param wordDictionary Словарь слов, используемый в игре.
     * @param userInterface Интерфейс для взаимодействия с пользователем.
     */
    public Game(WordDictionary wordDictionary, UserInterface userInterface) {
        this.wordDictionary = wordDictionary;
        this.userInterface = userInterface;
    }

    /**
     * Запускает игру "Виселица".
     * Выбирает категорию и слово, устанавливает уровень сложности и управляет процессом игры.
     */
    public void startGame() {
        LOGGER.info("Добро пожаловать в игру Виселица!");

        String chosenCategory = chooseCategory();
        List<String> wordsInCategory = wordDictionary.getWordsForCategory(chosenCategory);
        String word = wordDictionary.getRandomValidWordFromList(wordsInCategory);

        int maxAttempts = chooseDifficulty();
        gameState = new GameState(maxAttempts);
        gameState.setChosenWord(word);

        while (!isGameOver()) {
            clearScreen();
            LOGGER.info("Категория: {}", chosenCategory);
            displayGameState();

            char letter = getValidLetterFromUser();
            gameState.updateGuessedLetters(letter);
        }

        clearScreen();

        if (gameState.isWordGuessed()) {
            LOGGER.info("Поздравляем! Вы угадали слово: {}", word);
        } else {
            gameState.getHangmanState().drawHangman();
            LOGGER.info("Игра окончена. Слово было: {}", word);
        }

        playAgainPrompt();
    }

    /**
     * Очищает экран консоли.
     */
    public void clearScreen() {
        LOGGER.info("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * Возвращает строку с буквами, которые уже были введены пользователем.
     *
     * @return Строка с введенными буквами, разделенными запятыми.
     */
    public String getGuessedLettersString() {
        Set<Character> guessedLetters = gameState.getAllGuessedLetters();
        if (!guessedLetters.isEmpty()) {
            return String.join(", ", guessedLetters.stream()
                    .map(String::valueOf)
                    .toArray(String[]::new));
        } else {
            return "";
        }
    }

    /**
     * Отображает буквы, которые уже были введены пользователем.
     */
    public void displayGuessedLetters() {
        String guessedLettersStr = getGuessedLettersString();
        LOGGER.info("Введенные ранее буквы: {}", guessedLettersStr);
    }

    /**
     * Получает корректную букву от пользователя.
     *
     * @return Введенная пользователем буква.
     */
    public char getValidLetterFromUser() {
        displayGuessedLetters();

        LOGGER.info("Введите букву:");

        String input;
        while (true) {
            input = userInterface.getUserInput().trim();

            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                char letter = Character.toLowerCase(input.charAt(0));

                if (gameState.getAllGuessedLetters().contains(letter)) {
                    userInterface.displayMessage("Буква была введена ранее! Повторите ввод.");
                } else {
                    return letter;
                }
            } else {
                userInterface.displayMessage("Неверный ввод! Введите одну букву.");
            }
        }
    }

    /**
     * Позволяет пользователю выбрать категорию слов.
     *
     * @return Выбранная категория.
     */
    public String chooseCategory() {
        LOGGER.info("Выберите номер категории или нажмите Enter для случайного выбора категории:");
        List<String> categories = wordDictionary.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            LOGGER.info("Категория {}: {}", i + 1, categories.get(i));
        }

        String input;
        while (true) {
            input = userInterface.getUserInput().trim();
            if (input.isEmpty()) {
                String randomCategory = categories.get(random.nextInt(categories.size()));
                LOGGER.info("Выбрана случайная категория: {}", randomCategory);
                return randomCategory;
            } else {
                try {
                    int categoryIndex = Integer.parseInt(input) - 1;
                    if (categoryIndex >= 0 && categoryIndex < categories.size()) {
                        return categories.get(categoryIndex);
                    }
                } catch (NumberFormatException e) {
                }
                userInterface.displayMessage(
                        "Ошибка ввода! Введите число от 1 до %d".formatted(categories.size()));
            }
        }
    }

    /**
     * Позволяет пользователю выбрать уровень сложности.
     *
     * @return Количество попыток, соответствующее выбранному уровню сложности.
     */
    public int chooseDifficulty() {
        LOGGER.info(
                """
                    Выберите номер с уровнем сложности или нажмите Enter для выбора случайного уровня:
                    1. Легкий (10 попыток)
                    2. Средний (8 попыток)
                    3. Сложный (6 попыток)""");

        String input;
        String invalidInputMessage = "Ошибка ввода! Введите число от 1 до 3.";
        while (true) {
            input = userInterface.getUserInput().trim();
            if (input.isEmpty()) {
                int randomDifficulty = random.nextInt(NUMBER_OF_LEVELS) + 1;
                return getAttemptsForDifficulty(randomDifficulty);
            } else {
                try {
                    int difficulty = Integer.parseInt(input);
                    if (difficulty >= 1 && difficulty <= NUMBER_OF_LEVELS) {
                        return getAttemptsForDifficulty(difficulty - 1);
                    }
                } catch (NumberFormatException e) {
                }
                userInterface.displayMessage(invalidInputMessage);
            }
        }
    }

    /**
     * Возвращает количество попыток для заданного уровня сложности.
     *
     * @param difficulty Уровень сложности (0 - легкий, 1 - средний, 2 - сложный).
     * @return Количество попыток.
     * @throws IllegalArgumentException Если уровень сложности некорректен.
     */
    private int getAttemptsForDifficulty(int difficulty) {
        return switch (difficulty) {
            case 0 -> EASY_LVL_MISTAKES;
            case 1 -> MEDIUM_LVL_MISTAKES;
            case 2 -> HARD_LVL_MISTAKES;
            default -> throw new IllegalArgumentException("Некорректный уровень сложности: " + difficulty);
        };
    }

    /**
     * Проверяет, завершена ли игра.
     *
     * @return true, если игра завершена (слово угадано или закончились попытки), иначе false.
     */
    public boolean isGameOver() {
        return gameState.isWordGuessed() || gameState.getHangmanState().isGameOver();
    }

    /**
     * Отображает текущее состояние игры, включая текущее состояние слова, оставшиеся попытки и виселицу.
     */
    public void displayGameState() {
        LOGGER.info("Текущее состояние слова: {}", gameState.getCurrentWordState());
        LOGGER.info("Оставшиеся попытки: {}", gameState.getRemainingAttempts());
        gameState.getHangmanState().drawHangman();
    }

    /**
     * Предлагает пользователю сыграть еще раз.
     */
    public void playAgainPrompt() {
        LOGGER.info(
                """
                    Хотите продолжить игру? (введите цифру):
                    1. Да
                    2. Нет""");

        String choice;
        while (true) {
            choice = userInterface.getUserInput().trim();
            if ("1".equals(choice)) {
                clearScreen();
                startGame();
                break;
            } else if ("2".equals(choice)) {
                LOGGER.info("Спасибо за игру! До встречи!");
                break;
            } else {
                userInterface.displayMessage("Неверный выбор! Введите 1 для \"Да\" или 2 для \"Нет\".");
            }
        }
    }

    /**
     * Устанавливает текущее состояние игры.
     *
     * @param gameState Новое состояние игры.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}