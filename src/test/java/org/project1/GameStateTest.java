package org.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {
    private GameState gameState;
    private final int maxAttempts = 6;

    @BeforeEach
    public void setUp() {
        gameState = new GameState(maxAttempts);
        gameState.setChosenWord("hangman");
    }

    @Test
    public void testSetChosenWord() {
        gameState.setChosenWord("hangman");
        String expectedState = "- - - - - - -";
        assertEquals(expectedState, gameState.getCurrentWordState(), "Текущее состояние слова должно отражать установленное слово.");
    }


    @Test
    public void testGetGuessedLetters_InitiallyEmpty() {
        assertTrue(gameState.getGuessedLetters().isEmpty(), "Изначально множество угаданных букв должно быть пустым.");
    }

    @Test
    public void testUpdateGuessedLetters_CorrectLetter() {
        gameState.updateGuessedLetters('h');
        assertTrue(gameState.getGuessedLetters().contains('h'), "Буква 'h' должна быть добавлена в угаданные буквы.");
        assertEquals(6, gameState.getRemainingAttempts(), "Количество попыток не должно уменьшиться при правильной букве.");
    }

    @Test
    public void testUpdateGuessedLetters_IncorrectLetter() {
        gameState.updateGuessedLetters('z');
        assertFalse(gameState.getGuessedLetters().contains('z'), "Буква 'z' не должна быть добавлена в угаданные буквы.");
        assertEquals(5, gameState.getRemainingAttempts(), "Количество попыток должно уменьшиться при неверной букве.");
    }

    @Test
    public void testUpdateGuessedLetters_RepeatLetter() {
        gameState.updateGuessedLetters('h');
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameState.updateGuessedLetters('h');
        });
        assertEquals("Эта буква уже вводилась ранее!", exception.getMessage(), "При повторном вводе буквы должно выбрасываться исключение.");
    }

    @Test
    public void testUpdateGuessedLetters_AllGuessedLettersTracking() {
        gameState.updateGuessedLetters('z');
        gameState.updateGuessedLetters('h');
        gameState.updateGuessedLetters('a');

        assertEquals(5, gameState.getRemainingAttempts(), "Попытки должны уменьшаться только при неверных буквах.");
    }

    @Test
    public void testIsWordGuessed_FullyGuessed() {
        gameState.updateGuessedLetters('a');
        gameState.updateGuessedLetters('h');
        gameState.updateGuessedLetters('n');
        gameState.updateGuessedLetters('g');
        gameState.updateGuessedLetters('m');

        assertTrue(gameState.isWordGuessed(), "Слово считается угаданным, только если угаданы все буквы.");
    }

    @Test
    public void testIsWordGuessed_NotFullyGuessed() {
        gameState.updateGuessedLetters('h');
        gameState.updateGuessedLetters('a');
        gameState.updateGuessedLetters('n');
        gameState.updateGuessedLetters('g');

        assertFalse(gameState.isWordGuessed(), "Слово не считается угаданным, если угаданы не все буквы.");
    }

    @Test
    public void testGetCurrentWordState_Initially() {
        String expectedState = "- - - - - - -";
        assertEquals(expectedState, gameState.getCurrentWordState(), "Изначально на месте всех букв должны быть прочерки.");
    }

    @Test
    public void testGetCurrentWordState_AfterSomeGuesses() {
        gameState.updateGuessedLetters('h');
        gameState.updateGuessedLetters('a');
        gameState.updateGuessedLetters('n');

        String expectedState = "h a n - - a n";
        assertEquals(expectedState, gameState.getCurrentWordState(), "Состояние слова должно обновляться в зависимости от угаданных букв.");
    }

    @Test
    public void testGetRemainingAttempts() {
        gameState.updateGuessedLetters('z');
        gameState.updateGuessedLetters('y');
        gameState.updateGuessedLetters('x');

        assertEquals(3, gameState.getRemainingAttempts(), "Количество оставшихся попыток должно уменьшаться при неверных буквах.");
    }
}