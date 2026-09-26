package com.lab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Модель для хранения результата проверки GUID.
 * Класс хранит один GUID, флаг его корректности, а также коллекцию
 * объектов {@link MyGuid}, которая используется как контейнер для
 * всех проверенных значений. Содержит статические методы для
 * проверки строк на соответствие формату GUID.
 *
 * @author User
 * @version 1.0
 */
public class MyGuid {

    /**
     * Регулярное выражение для проверки формата GUID.
     * Ожидает 8-4-4-4-12 шестнадцатеричных цифр, разделённых тире.
     */
    private static final String GUID_REGEX =
            "^([0-9A-Fa-f]{8})"
          + "-([0-9A-Fa-f]{4})"
          + "-([0-9A-Fa-f]{4})"
          + "-([0-9A-Fa-f]{4})"
          + "-([0-9A-Fa-f]{12})$";

    /**
     * Скомпилированный шаблон GUID.
     */
    private static final Pattern GUID_PATTERN = Pattern.compile(GUID_REGEX);

    /**
     * Строка GUID. Для корректного GUID хранится нормализованное значение
     * (без скобок, в исходном регистре). Для некорректного — исходная строка.
     */
    private String guid;

    /**
     * Флаг корректности GUID.
     * {@code true} — строка соответствует формату GUID, {@code false} — нет.
     */
    private boolean isCorrect;

    /**
     * Коллекция всех проверенных GUID.
     * Используется как контейнер для хранения результатов проверки.
     */
    private List<MyGuid> guids;

    /**
     * Конструктор по умолчанию.
     * Инициализирует коллекцию {@link #guids} пустым списком.
     */
    public MyGuid() {
        this.guids = new ArrayList<>();
    }

    /**
     * Конструктор для создания одного результата проверки GUID.
     *
     * @param guid      строка GUID (нормализованная или исходная)
     * @param isCorrect флаг корректности GUID
     */
    public MyGuid(String guid, boolean isCorrect) {
        this.guid = guid;
        this.isCorrect = isCorrect;
        this.guids = new ArrayList<>();
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
     * Устанавливает строку GUID.
     *
     * @param guid строка GUID
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * Возвращает флаг корректности GUID.
     *
     * @return {@code true}, если GUID корректен, иначе {@code false}
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * Устанавливает флаг корректности GUID.
     *
     * @param correct флаг корректности
     */
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    /**
     * Возвращает коллекцию всех проверенных GUID.
     *
     * @return список объектов {@link MyGuid}
     */
    public List<MyGuid> getGuids() {
        return guids;
    }

    /**
     * Устанавливает коллекцию GUID.
     *
     * @param guids список объектов {@link MyGuid}
     */
    public void setGuids(List<MyGuid> guids) {
        this.guids = guids;
    }

    /**
     * Добавляет результат проверки GUID в коллекцию.
     *
     * @param myGuid объект {@link MyGuid} для добавления
     */
    public void addGuid(MyGuid myGuid) {
        guids.add(myGuid);
    }

    /**
     * Разбивает введённую строку по запятой и проверяет каждую часть.
     *
     * @param input введённая строка
     * @return объект {@link MyGuid}, содержащий коллекцию проверенных GUID
     */
    public static MyGuid validateAll(String input) {
        MyGuid container = new MyGuid();

        if (input == null) {
            container.addGuid(new MyGuid(null, false));
            return container;
        }

        String[] parts = input.split(",");

        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                container.addGuid(validateOne(trimmed));
            }
        }

        return container;
    }

    /**
     * Проверяет одну строку на соответствие формату GUID.
     *
     * @param input строка для проверки
     * @return объект {@link MyGuid} с результатом проверки
     */
    public static MyGuid validateOne(String input) {
        String cleaned = stripBrackets(input);
        Matcher matcher = GUID_PATTERN.matcher(cleaned);

        if (matcher.matches()) {
            String normalized = normalize(matcher);
            return new MyGuid(normalized, true);
        } else {
            return new MyGuid(input, false);
        }
    }

    /**
     * Убирает парные скобки из строки, если они есть.
     * Поддерживает круглые {@code ()} и фигурные {@code {}} скобки.
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
     * @param input строка для проверки
     * @param open  открывающий символ
     * @param close закрывающий символ
     * @return {@code true}, если строка начинается с {@code open}
     *         и заканчивается {@code close}
     */
    private static boolean isWrapped(String input, String open, String close) {
        return input.startsWith(open) && input.endsWith(close);
    }

    /**
     * Собирает нормализованный GUID из групп сопоставления.
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