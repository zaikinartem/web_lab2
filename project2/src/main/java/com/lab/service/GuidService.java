package com.lab.service;

import com.lab.model.GuidResponse;

/**
 * Интерфейс сервиса для проверки GUID.
 * Определяет контракт для валидации строк на соответствие формату GUID.
 *
 * @author User
 * @version 1.0
 */
public interface GuidService {

    /**
     * Проверяет строку на соответствие формату GUID.
     *
     * @param input строка для проверки
     * @return объект GuidResponse с результатом проверки
     */
    GuidResponse validateGuid(String input);
}
