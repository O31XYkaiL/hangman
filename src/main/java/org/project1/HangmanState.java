package org.project1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс, представляющий состояние виселицы в игре "Виселица".
 * Управляет количеством ошибок и отображением состояния виселицы.
 */
public class HangmanState {
    private int mistakes;
    private final int maxAttempts;
    private static final Logger LOGGER = LoggerFactory.getLogger(HangmanState.class);
    private static final int EASY_LVL_MISTAKES = 10;
    private static final int MEDIUM_LVL_MISTAKES = 8;
    private static final int HARD_LVL_MISTAKES = 6;

    private static final String EMPTY_HANGMAN_STAGE = "\n\n\n\n\n\n\n";
    private static final String FIRST_HANGMAN_STAGE = "\n\n\n\n\n\n‾‾‾";
    private static final String SECOND_HANGMAN_STAGE = """
            
             |
             |
             |
             |
             |
            ‾‾‾""";

    private static final String THIRTEEN_HANGMAN_STAGE = """
            
             |‾‾‾‾‾‾‾‾
             |
             |
             |
             |
            ‾‾‾""";

    private static final String FOURTEEN_HANGMAN_STAGE = """
            
             |‾‾‾‾‾‾‾‾|
             |
             |
             |
             |
            ‾‾‾""";

    private static final String FIFTEEN_HANGMAN_STAGE = """
            
             |‾‾‾‾‾‾‾‾|
             |        ◯
             |
             |
             |
            ‾‾‾""";

    private static final String SIXTH_HANGMAN_STAGE = """
            
             |‾‾‾‾‾‾‾‾|
             |        ◯
             |        |
             |
             |
             |
            ‾‾‾""";

    private static final String SEVENTH_HANGMAN_STAGE = """
            
             |‾‾‾‾‾‾‾‾|
             |        ◯
             |       /|
             |
             |
            ‾‾‾""";

    private static final String EIGHTH_HANGMAN_STAGE = """
            
             |‾‾‾‾‾‾‾‾|
             |        ◯
             |       /|\\
             |
             |
            ‾‾‾""";

    private static final String NINTH_HANGMAN_STAGE = """
            
             |‾‾‾‾‾‾‾‾|
             |        ◯
             |       /|\\
             |       / \s
             |
            ‾‾‾""";

    private static final String TENTH_HANGMAN_STAGE = """
            
             |‾‾‾‾‾‾‾‾|
             |        ◯
             |       /|\\
             |       / \\\s
             |
            ‾‾‾""";

    /**
     * Конструктор для создания экземпляра состояния виселицы.
     *
     * @param maxAttempts Максимальное количество попыток для угадывания слова.
     */
    public HangmanState(int maxAttempts) {
        this.mistakes = 0;
        this.maxAttempts = maxAttempts;
    }

    /**
     * Возвращает количество сделанных ошибок.
     *
     * @return Количество ошибок.
     */
    public int getMistakes() {
        return mistakes;
    }

    /**
     * Возвращает максимальное количество попыток.
     *
     * @return Максимальное количество попыток.
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Увеличивает количество ошибок на единицу, если это возможно.
     */
    public void incrementMistakes() {
        if (mistakes < maxAttempts) {
            mistakes++;
        }
    }

    /**
     * Проверяет, завершена ли игра (количество ошибок достигло максимума).
     *
     * @return true, если игра завершена, иначе false.
     */
    public boolean isGameOver() {
        return mistakes == maxAttempts;
    }

    /**
     * Отображает текущее состояние виселицы в зависимости от количества ошибок.
     */
    public void drawHangman() {
        String[] hangmanStages;

        if (maxAttempts == EASY_LVL_MISTAKES) {
            hangmanStages = getEasyHangmanStages();
        } else if (maxAttempts == MEDIUM_LVL_MISTAKES) {
            hangmanStages = getMediumHangmanStages();
        } else if (maxAttempts == HARD_LVL_MISTAKES) {
            hangmanStages = getHardHangmanStages();
        } else {
            throw new IllegalArgumentException("Unsupported difficulty level");
        }

        LOGGER.info("Виселица: {}", hangmanStages[mistakes]);
    }

    /**
     * Возвращает массив строк, представляющих этапы виселицы для легкого уровня сложности.
     *
     * @return Массив строк с этапами виселицы.
     */
    private String[] getEasyHangmanStages() {
        return new String[] {
                EMPTY_HANGMAN_STAGE,
                FIRST_HANGMAN_STAGE,
                SECOND_HANGMAN_STAGE,
                THIRTEEN_HANGMAN_STAGE,
                FOURTEEN_HANGMAN_STAGE,
                FIFTEEN_HANGMAN_STAGE,
                SIXTH_HANGMAN_STAGE,
                SEVENTH_HANGMAN_STAGE,
                EIGHTH_HANGMAN_STAGE,
                NINTH_HANGMAN_STAGE,
                TENTH_HANGMAN_STAGE
        };
    }

    /**
     * Возвращает массив строк, представляющих этапы виселицы для среднего уровня сложности.
     *
     * @return Массив строк с этапами виселицы.
     */
    private String[] getMediumHangmanStages() {
        return new String[] {
                EMPTY_HANGMAN_STAGE,
                FIRST_HANGMAN_STAGE,
                SECOND_HANGMAN_STAGE,
                THIRTEEN_HANGMAN_STAGE,
                FOURTEEN_HANGMAN_STAGE,
                FIFTEEN_HANGMAN_STAGE,
                SIXTH_HANGMAN_STAGE,
                EIGHTH_HANGMAN_STAGE,
                TENTH_HANGMAN_STAGE
        };
    }

    /**
     * Возвращает массив строк, представляющих этапы виселицы для сложного уровня сложности.
     *
     * @return Массив строк с этапами виселицы.
     */
    private String[] getHardHangmanStages() {
        return new String[] {
                EMPTY_HANGMAN_STAGE,
                THIRTEEN_HANGMAN_STAGE,
                FOURTEEN_HANGMAN_STAGE,
                FIFTEEN_HANGMAN_STAGE,
                SIXTH_HANGMAN_STAGE,
                EIGHTH_HANGMAN_STAGE,
                TENTH_HANGMAN_STAGE
        };
    }
}