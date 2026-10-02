package com.lab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Хранит один GUID и результат его проверки.
 * Все результаты текущей проверки хранятся в общей коллекции.
 *
 * @author User
 * @version 1.0
 */
public class MyGuid {

    /**
     * Строка GUID.
     * Для корректного GUID хранится нормализованное значение.
     */
    private String guid;

    /**
     * Результат проверки GUID.
     * true — корректный, false — некорректный.
     */
    private boolean isCorrect;

    /**
     * Регулярное выражение для проверки GUID.
     */
    private static final String GUID_REGEX
            = "^([0-9A-Fa-f]{8})"
            + "-([0-9A-Fa-f]{4})"
            + "-([0-9A-Fa-f]{4})"
            + "-([0-9A-Fa-f]{4})"
            + "-([0-9A-Fa-f]{12})$";

    /**
     * Шаблон для проверки GUID.
     */
    private static final Pattern GUID_PATTERN = Pattern.compile(GUID_REGEX);

    /**
     * Общая коллекция результатов проверки.
     */
    private static final List<MyGuid> guids = new ArrayList<>();

    /**
     * Создаёт объект GUID и сразу проверяет его.
     *
     * @param guid строка для проверки
     */
    public MyGuid(String guid) {
        this.guid = guid;
        this.isCorrect = validateOne();
    }

    /**
     * Возвращает строку GUID.
     *
     * @return строка GUID
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Возвращает результат проверки.
     *
     * @return true, если GUID корректный
     */
    public boolean getIsCorrect() {
        return isCorrect;
    }

    /**
     * Проверяет все GUID из введённой строки.
     * Результаты сохраняются в общей коллекции.
     *
     * @param input строка с GUID через запятую
     */
    public static void validateAll(String input) {

        if (input == null) {
            return;
        }

        String[] parts = input.split(",");

        for (String part : parts) {
            String trimmed = part.trim();

            if (!trimmed.isEmpty()) {
                guids.add(new MyGuid(trimmed));
            }
        }
    }

    /**
     * Возвращает результаты текущей проверки.
     *
     * @return список результатов
     */
    public static List<MyGuid> getGuids() {
        return List.copyOf(guids);
    }

    /**
     * Проверяет один GUID.
     *
     * @return true, если GUID корректный
     */
    private boolean validateOne() {
        if (guid == null) {
            return false;
        }

        String cleaned = stripBrackets(guid);
        Matcher matcher = GUID_PATTERN.matcher(cleaned);

        if (matcher.matches()) {
            guid = normalize(matcher);
            return true;
        }

        return false;
    }

    /**
     * Убирает внешние скобки из GUID.
     *
     * @param input строка со скобками
     * @return строка без скобок
     */
    private static String stripBrackets(String input) {
        if (isWrapped(input, "(", ")")) {
            return input.substring(1, input.length() - 1);
        }

        if (isWrapped(input, "{", "}")) {
            return input.substring(1, input.length() - 1);
        }

        return input;
    }

    /**
     * Проверяет наличие парных скобок вокруг строки.
     *
     * @param input строка для проверки
     * @param open открывающая скобка
     * @param close закрывающая скобка
     * @return true, если строка находится внутри скобок
     */
    private static boolean isWrapped(
            String input,
            String open,
            String close) {

        return input.startsWith(open) && input.endsWith(close);
    }

    /**
     * Собирает GUID из найденных групп.
     *
     * @param matcher результат проверки регулярным выражением
     * @return нормализованный GUID
     */
    private static String normalize(Matcher matcher) {
        return matcher.group(1) + "-"
                + matcher.group(2) + "-"
                + matcher.group(3) + "-"
                + matcher.group(4) + "-"
                + matcher.group(5);
    }
}
