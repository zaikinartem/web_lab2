package com.lab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Модель для хранения результата проверки GUID. Хранит один GUID и флаг его
 * корректности. Статическая коллекция — общий контейнер для всех
 * проверенных значений.
 *
 * @author User
 * @version 1.0
 */
public class MyGuid {


    /**
     * Строка GUID. Для корректного — нормализованное значение. Для
     * некорректного — исходная строка.
     */
    private String guid;

    /**
     * Флаг корректности GUID. {@code true} — соответствует формату,
     * {@code false} — нет.
     */
    private boolean isCorrect;


    /**
     * Регулярное выражение для проверки формата GUID.
     */
    private static final String GUID_REGEX
            = "^([0-9A-Fa-f]{8})"
            + "-([0-9A-Fa-f]{4})"
            + "-([0-9A-Fa-f]{4})"
            + "-([0-9A-Fa-f]{4})"
            + "-([0-9A-Fa-f]{12})$";

    /**
     * Скомпилированный шаблон GUID.
     */
    private static final Pattern GUID_PATTERN = Pattern.compile(GUID_REGEX);

    /**
     * Общая коллекция всех проверенных GUID. Одна на весь класс, а не у каждого
     * объекта.
     */
    private static final List<MyGuid> guids = new ArrayList<>();


    /**
     * Конструктор для создания результата проверки. Принимает строку GUID и
     * выполняет проверку.
     *
     * @param guid строка GUID
     */
    public MyGuid(String _guid) {
        guid = _guid;
        isCorrect = validateOne();
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
     * Возвращает флаг корректности.
     *
     * @return {@code true}, если GUID корректен
     */
    public boolean getIsCorrect() {
        return isCorrect;
    }

    /**
     * Возвращает общую коллекцию проверенных GUID.
     *
     * @return список объектов {@link MyGuid}
     */
    public static List<MyGuid> getGuids() {
        return guids;
    }


    /**
     * Разбивает строку по запятой и проверяет каждую часть.
     *
     * @param input введённая строка
     * @return объект {@link MyGuid} — контейнер результатов
     */
    public static MyGuid validateAll(String input) {

        MyGuid container = new MyGuid(null);

        if (input == null) {
            container.addGuid(new MyGuid(null));
            return container;
        }

        String[] parts = input.split(",");

        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                container.addGuid(new MyGuid(trimmed));
            }
        }

        return container;
    }

    /**
     * Проверяет одну строку на соответствие формату GUID и записывает результат
     * в переданный объект. Вся логика проверки находится здесь: снятие скобок,
     * сопоставление с шаблоном и нормализация.
     *
     * @param input строка для проверки
     * @param target объект {@link MyGuid}, в который записывается результат
     */
    private boolean validateOne() {
        if (guid == null) {
            isCorrect = false;
            return isCorrect;
        }

        String cleaned = stripBrackets(guid);
        Matcher matcher = GUID_PATTERN.matcher(cleaned);

        if (matcher.matches()) {
            guid = normalize(matcher);
            isCorrect = true;
        } else {
            isCorrect = false;
        }
        return isCorrect;
    }

    /**
     * Добавляет результат проверки в коллекцию.
     *
     * @param myGuid объект {@link MyGuid}
     */
    public void addGuid(MyGuid myGuid) {
        guids.add(myGuid);
    }


    /**
     * Убирает парные скобки из строки.
     *
     * @param input строка с возможными скобками
     * @return строка без парных скобок
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
     * Проверяет, обрамлена ли строка парными символами.
     *
     * @param input строка
     * @param open открывающий символ
     * @param close закрывающий символ
     * @return {@code true}, если строка начинается с open и заканчивается close
     */
    private static boolean isWrapped(String input, String open, String close) {
        return input.startsWith(open) && input.endsWith(close);
    }

    /**
     * Собирает нормализованный GUID из групп.
     *
     * @param matcher сопоставление с шаблоном
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