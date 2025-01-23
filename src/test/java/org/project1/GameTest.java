package org.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {
    private Game game;
    private WordDictionary wordDictionary;
    private UserInterface userInterface;
    private GameState gameState;

    @BeforeEach
    public void setUp() {
        wordDictionary = mock(WordDictionary.class);
        userInterface = mock(UserInterface.class);
        game = new Game(wordDictionary, userInterface);
        gameState = new GameState(10);
        gameState.setChosenWord("яблоко");
    }

    @Test
    public void testWordSelection() {
        List<String> mockWords = List.of("яблоко", "папайя", "виноград", "манго", "абрикос");

        WordDictionary wordDictionary = mock(WordDictionary.class);
        when(wordDictionary.getWordsForCategory("Фрукты")).thenReturn(mockWords);

        String word = new WordDictionary().getRandomValidWordFromList(mockWords);

        assertTrue(mockWords.contains(word), "Выбранное слово должно быть из списка предоставленных слов.");
    }

    @Test
    public void testLetterInputCaseInsensitivity() {
        game.setGameState(gameState);
        when(userInterface.getUserInput()).thenReturn("А");

        char letter = game.getValidLetterFromUser();
        assertEquals('а', letter);
    }

    @Test
    public void testGameDoesNotStartWithInvalidWordLength() {
        String invalidWord = "ab";
        gameState = new GameState(10);
        gameState.setChosenWord(invalidWord);
        game.setGameState(gameState);

        assertThrows(NullPointerException.class, () -> {
            game.startGame();
        });
    }

    @Test
    public void testGameStateChangesWhenLetterGuessedCorrectly() {
        gameState.updateGuessedLetters('а');
        game.setGameState(gameState);
        assertTrue(gameState.getAllGuessedLetters().contains('а'));
    }

    @Test
    public void testChooseCategoryWithValidInput() {
        when(userInterface.getUserInput()).thenReturn("1");
        when(wordDictionary.getCategories()).thenReturn(List.of("Фрукты", "Овощи", "Животные"));

        String chosenCategory = game.chooseCategory();
        assertEquals("Фрукты", chosenCategory);
    }

    @Test
    public void testChooseCategoryWithRandomSelection() {
        when(userInterface.getUserInput()).thenReturn("");
        when(wordDictionary.getCategories()).thenReturn(List.of("Фрукты", "Овощи", "Животные"));
        when(wordDictionary.getCategories()).thenReturn(List.of("Фрукты"));

        String chosenCategory = game.chooseCategory();
        assertTrue(List.of("Фрукты", "Овощи", "Животные").contains(chosenCategory));
    }

    @Test
    public void testChooseDifficultyWithValidInput() {
        when(userInterface.getUserInput()).thenReturn("2");

        int maxAttempts = game.chooseDifficulty();
        assertEquals(8, maxAttempts);
    }

    @Test
    public void testGetValidLetterFromUser() {
        gameState = new GameState(10);
        game.setGameState(gameState);
        when(userInterface.getUserInput()).thenReturn("А", "а", "Б");

        char letter = game.getValidLetterFromUser();
        assertEquals('а', letter);
    }

    @Test
    public void testClearScreen() {
        assertDoesNotThrow(() -> game.clearScreen());
    }

    @Test
    public void testIsGameOverWhenWordIsGuessed() {
        gameState.setChosenWord("яблоко");

        char[] lettersToGuess = {'я', 'б', 'л', 'о', 'к', 'о'};
        for (char letter : lettersToGuess) {
            gameState.updateGuessedLetters(letter);
            if (gameState.isWordGuessed()) {
                break;
            }
        }

        game.setGameState(gameState);
        assertTrue(game.isGameOver(), "Игра должна закончиться, если слово отгадано полностью.");
    }

    @Test
    public void testIsGameOverWhenAttemptsExhausted() {
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        gameState.getHangmanState().incrementMistakes();
        game.setGameState(gameState);
        assertTrue(game.isGameOver(), "Игра должна завершиться, если все попытки исчерпаны.");
    }

    @Test
    public void testGetGuessedLettersStringWithGuessedLetters() {
        gameState.updateGuessedLetters('а');
        gameState.updateGuessedLetters('б');
        game.setGameState(gameState);

        String result = game.getGuessedLettersString();
        assertEquals("а, б", result, "Ожидается строка с угаданными буквами, разделенными запятой.");
    }

    @Test
    public void testGetGuessedLettersStringWithNoGuessedLetters() {
        game.setGameState(gameState);

        String result = game.getGuessedLettersString();
        assertEquals("", result, "Ожидается пустая строка, так как нет угаданных букв.");
    }
}