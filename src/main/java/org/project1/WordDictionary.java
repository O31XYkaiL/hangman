package org.project1;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Класс, представляющий словарь слов для игры "Виселица".
 * Содержит слова, разделенные по категориям, и предоставляет методы для работы с ними.
 */
public class WordDictionary {
    private final Map<String, List<String>> categoryMap;
    private final Random random = new SecureRandom();
    private static final int MIN_WORD_LENGTH = 3;

    /**
     * Конструктор для создания экземпляра словаря.
     * Инициализирует словарь с предопределенными категориями и словами.
     */
    public WordDictionary() {
        this.categoryMap = new HashMap<>() {{
            put("Животные", Arrays.asList("пантера", "гепард", "медведь", "пингвин", "лошадь", "заяц",
                    "обезьяна", "капибара", "свинья", "хомяк"));
            put("Фрукты", Arrays.asList("апельсин", "папайя", "виноград", "манго", "абрикос", "нектарин",
                    "инжир", "помело", "яблоко", "грейпфрут"));
            put("Овощи", Arrays.asList("батат", "помидор", "топинамбур", "брокколи", "баклажан", "сельдерей",
                    "огурец", "морковка", "капуста", "фасоль"));
            put("Хобби", Arrays.asList("фотография", "путешествия", "моделирование", "рукоделие", "кулинария",
                    "садоводство", "книгопечатание", "велоспорт", "гончарство", "акварель"));
            put("Спорт", Arrays.asList("футбол", "баскетбол", "теннис", "хоккей", "бокс", "мотоспорт",
                    "волейбол", "плавание", "гимнастика", "гольф"));
            put("Цветы", Arrays.asList("роза", "лилия", "тюльпан", "гвоздика", "ромашка", "орхидея",
                    "василек", "пион", "георгин", "нарцисс"));
            put("Профессии", Arrays.asList("врач", "учитель", "инженер", "повар", "полицейский", "программист",
                    "строитель", "адвокат", "пилот", "художник"));
            put("Музыкальные инструменты", Arrays.asList("гитара", "пианино", "скрипка", "флейта", "барабан",
                    "труба", "саксофон", "арфа", "контрабас", "аккордеон"));
            put("Транспорт", Arrays.asList("машина", "самолет", "поезд", "велосипед", "мотоцикл", "автобус",
                    "корабль", "трамвай", "метро", "катер"));
            put("Школьные принадлежности", Arrays.asList("пенал", "карандаш", "маркер", "циркуль", "линейка",
                    "портфель", "рюкзак", "транспортир", "ластик", "корректор"));
        }};
    }

    /**
     * Возвращает список доступных категорий.
     *
     * @return Список категорий.
     */
    public List<String> getCategories() {
        return new ArrayList<>(categoryMap.keySet());
    }

    /**
     * Возвращает список слов для указанной категории.
     *
     * @param category Категория, для которой нужно получить слова.
     * @return Список слов в указанной категории. Если категория не найдена, возвращает пустой список.
     */
    public List<String> getWordsForCategory(String category) {
        return categoryMap.getOrDefault(category, Collections.emptyList());
    }

    /**
     * Возвращает случайное слово из предоставленного списка, которое соответствует критериям:
     * длина слова не меньше {@link #MIN_WORD_LENGTH} и слово состоит только из букв.
     *
     * @param words Список слов, из которых нужно выбрать случайное слово.
     * @return Случайное слово, соответствующее критериям.
     * @throws IllegalArgumentException Если список слов пуст или равен null.
     * @throws IllegalStateException Если в списке нет подходящих слов.
     */
    public String getRandomValidWordFromList(List<String> words) {
        if (words == null || words.isEmpty()) {
            throw new IllegalArgumentException("Список слов пуст или равен null");
        }

        List<String> validWords = words.stream()
                .filter(word -> word.length() >= MIN_WORD_LENGTH && word.matches("[А-Яа-яA-Za-z]+"))
                .toList();

        if (validWords.isEmpty()) {
            throw new IllegalStateException("Нет подходящих слов в предоставленном списке");
        }

        return validWords.get(random.nextInt(validWords.size()));
    }
}