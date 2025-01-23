package org.project1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordDictionaryTest {
    private WordDictionary wordDictionary;

    @BeforeEach
    public void setUp() {
        wordDictionary = new WordDictionary();
    }

    @Test
    public void testLoadCategories() {
        List<String> categories = wordDictionary.getCategories();
        assertEquals(10, categories.size(), "Должно быть 10 категорий");
        assertTrue(categories.contains("Животные"), "Список категорий должен содержать 'Животные'");
        assertTrue(categories.contains("Фрукты"), "Список категорий должен содержать 'Фрукты'");
        assertTrue(categories.contains("Овощи"), "Список категорий должен содержать 'Овощи'");
    }

    @Test
    public void testGetWordsForCategory() {
        List<String> words = wordDictionary.getWordsForCategory("Фрукты");
        assertNotNull(words, "Список слов для категории 'Фрукты' не должен быть null");
        assertEquals(10, words.size(), "Категория 'Фрукты' должна содержать 10 слов");
        assertTrue(words.contains("яблоко"), "Список слов для категории 'Фрукты' должен содержать 'яблоко'");
    }

    @Test
    public void testGetRandomValidWordFromList() {
        List<String> words = wordDictionary.getWordsForCategory("Животные");
        String randomWord = wordDictionary.getRandomValidWordFromList(words);
        assertNotNull(randomWord, "Случайное слово не должно быть null");
        assertTrue(words.contains(randomWord), "Случайное слово должно быть из категории 'Животные'");
    }

    @Test
    public void testGetRandomValidWordFromEmptyList() {
        List<String> emptyList = List.of();
        assertThrows(IllegalArgumentException.class, () -> {
            wordDictionary.getRandomValidWordFromList(emptyList);
        }, "Ожидается исключение при попытке получить случайное слово из пустого списка");
    }

    @Test
    public void testGetRandomValidWordFromListWithNoValidWords() {
        List<String> invalidWords = List.of("!", "@@", "12");
        assertThrows(IllegalStateException.class, () -> {
            wordDictionary.getRandomValidWordFromList(invalidWords);
        }, "Ожидается исключение при отсутствии валидных слов в списке");
    }
}