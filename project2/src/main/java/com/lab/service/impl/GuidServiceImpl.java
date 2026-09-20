package com.lab.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.lab.model.GuidResponse;
import com.lab.service.GuidService;

/**
 * Реализация сервиса для проверки GUID.
 * Использует регулярное выражение для валидации формата 8-4-4-4-12.
 *
 * @author User
 * @version 1.0
 */
@Service
public class GuidServiceImpl implements GuidService {

    /**
     * Регулярное выражение для проверки GUID без скобок.
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
     * Проверяет строку на соответствие формату GUID.
     *
     * @param input строка для проверки
     * @return объект GuidResponse с результатом проверки
     */
    @Override
    public GuidResponse validateGuid(String input) {
        if (isBlank(input)) {
            return buildInvalidResponse("Строка пустая или null");
        }
        String cleaned = stripBrackets(input.trim());
        Matcher matcher = GUID_PATTERN.matcher(cleaned);
        if (matcher.matches()) {
            return buildValidResponse(matcher);
        }
        return buildInvalidResponse("Строка НЕ является корректным GUID");
    }

    /**
     * Проверяет, что строка пустая или null.
     *
     * @param input проверяемая строка
     * @return true, если строка пустая или null
     */
    private boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Убирает парные скобки из строки, если они есть.
     * Поддерживает круглые () и фигурные {} скобки.
     *
     * @param input строка с возможными скобками
     * @return строка без парных скобок
     */
    private String stripBrackets(String input) {
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
     * @return true, если строка начинается с open и заканчивается close
     */
    private boolean isWrapped(String input, String open, String close) {
        return input.startsWith(open) && input.endsWith(close);
    }

    /**
     * Создаёт успешный ответ с нормализованным GUID.
     *
     * @param matcher сопоставление с шаблоном
     * @return объект GuidResponse с валидным GUID
     */
    private GuidResponse buildValidResponse(Matcher matcher) {
        String normalized = normalize(matcher);
        return new GuidResponse(true, "Строка является корректным GUID", normalized);
    }

    /**
     * Создаёт неуспешный ответ.
     *
     * @param message сообщение об ошибке
     * @return объект GuidResponse с флагом false
     */
    private GuidResponse buildInvalidResponse(String message) {
        return new GuidResponse(false, message, null);
    }

    /**
     * Собирает нормализованный GUID из групп сопоставления.
     *
     * @param matcher сопоставление с шаблоном
     * @return нормализованный GUID
     */
    private String normalize(Matcher matcher) {
        return matcher.group(1) + "-"
                + matcher.group(2) + "-"
                + matcher.group(3) + "-"
                + matcher.group(4) + "-"
                + matcher.group(5);
    }
}