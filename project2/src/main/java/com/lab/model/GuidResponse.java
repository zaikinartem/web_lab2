package com.lab.model;

/**
 * Класс-ответ для передачи результата проверки GUID.
 * Содержит флаг валидности, сообщение и нормализованный GUID.
 *
 * @author User
 * @version 1.0
 */
public class GuidResponse {

    /**
     * Флаг: является ли строка корректным GUID.
     */
    private boolean valid;

    /**
     * Сообщение о результате проверки.
     */
    private String message;

    /**
     * Нормализованный GUID (приведённый к нижнему регистру без скобок).
     * Может быть null, если строка невалидна.
     */
    private String normalizedGuid;

    /**
     * Конструктор для создания объекта ответа.
     *
     * @param valid флаг валидности GUID
     * @param message сообщение о результате
     * @param normalizedGuid нормализованный GUID или null
     */
    public GuidResponse(boolean valid, String message, String normalizedGuid) {
        this.valid = valid;
        this.message = message;
        this.normalizedGuid = normalizedGuid;
    }

    /**
     * Возвращает флаг валидности.
     *
     * @return true, если GUID корректен
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Возвращает сообщение о результате.
     *
     * @return сообщение
     */
    public String getMessage() {
        return message;
    }

    /**
     * Возвращает нормализованный GUID.
     *
     * @return нормализованный GUID или null
     */
    public String getNormalizedGuid() {
        return normalizedGuid;
    }
}
