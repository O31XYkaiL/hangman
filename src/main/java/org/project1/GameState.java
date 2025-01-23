package org.project1;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий состояние игры "Виселица".
 * Управляет состоянием угаданных букв, количеством оставшихся попыток и текущим состоянием слова.
 */
public class GameState {
    private String chosenWord;
    private final Set<Character> guessedLetters;
    private final HangmanState hangmanState;
    private final Set<Character> allGuessedLetters;

    /**
     * Конструктор для создания экземпляра состояния игры.
     *
     * @param maxAttempts Максимальное количество попыток для угадывания слова.
     */
    public GameState(int maxAttempts) {
        this.guessedLetters = new HashSet<>();
        this.hangmanState = new HangmanState(maxAttempts);
        this.allGuessedLetters = new HashSet<>();
    }

    /**
     * Устанавливает слово, которое нужно угадать.
     *
     * @param word Слово для угадывания.
     */
    public void setChosenWord(String word) {
        this.chosenWord = word.toLowerCase();
    }

    /**
     * Возвращает набор букв, которые были угаданы.
     *
     * @return Набор угаданных букв.
     */
    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    /**
     * Возвращает набор всех букв, которые были введены пользователем.
     *
     * @return Набор всех введенных букв.
     */
    public Set<Character> getAllGuessedLetters() {
        return allGuessedLetters;
    }

    /**
     * Обновляет состояние угаданных букв на основе введенной пользователем буквы.
     *
     * @param letter Буква, введенная пользователем.
     * @throws IllegalArgumentException Если буква уже была введена ранее.
     */
    public void updateGuessedLetters(char letter) {
        char normalizedLetter = Character.toLowerCase(letter);
        if (allGuessedLetters.contains(normalizedLetter)) {
            throw new IllegalArgumentException("Эта буква уже вводилась ранее!");
        }

        if (chosenWord.contains(String.valueOf(normalizedLetter))) {
            guessedLetters.add(normalizedLetter);
        } else {
            hangmanState.incrementMistakes();
        }

        allGuessedLetters.add(normalizedLetter);
    }

    /**
     * Проверяет, было ли слово полностью угадано.
     *
     * @return true, если слово угадано, иначе false.
     */
    public boolean isWordGuessed() {
        for (char c : chosenWord.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает текущее состояние слова с угаданными и неугаданными буквами.
     *
     * @return Строка, представляющая текущее состояние слова.
     */
    public String getCurrentWordState() {
        StringBuilder currentState = new StringBuilder();
        for (char c : chosenWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                currentState.append(c);
            } else {
                currentState.append('-');
            }
            currentState.append(' ');
        }
        return currentState.toString().trim();
    }

    /**
     * Возвращает количество оставшихся попыток.
     *
     * @return Количество оставшихся попыток.
     */
    public int getRemainingAttempts() {
        return hangmanState.getMaxAttempts() - hangmanState.getMistakes();
    }

    /**
     * Возвращает состояние виселицы.
     *
     * @return Состояние виселицы.
     */
    public HangmanState getHangmanState() {
        return hangmanState;
    }
}