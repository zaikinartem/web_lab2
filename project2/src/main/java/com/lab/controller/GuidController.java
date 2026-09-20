package com.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lab.model.GuidResponse;
import com.lab.service.GuidService;
import com.lab.ui.ConsoleUI;

/**
 * Контроллер для управления выполнением лабораторной работы. Обрабатывает ввод
 * данных и взаимодействие с сервисным слоем.
 *
 * @author User
 * @version 1.0
 */
@Controller
public class GuidController {

    /**
     * Сервис для проверки GUID.
     */
    @Autowired
    private GuidService guidService;

    /**
     * UI для взаимодействия с консолью.
     */
    @Autowired
    private ConsoleUI consoleUI;

    /**
     * Выполняет задание лабораторной работы.
     */
    public void execute() {
        printHeader();
        String input = readInput();
        GuidResponse response = guidService.validateGuid(input);
        printResult(response);
        consoleUI.close();
    }

    /**
     * Выводит заголовок задания.
     */
    private void printHeader() {
        consoleUI.println("\nЗАДАНИЕ 2: Проверка GUID");
        consoleUI.println("Формат: 8-4-4-4-12 шестнадцатеричных цифр через тире");
        consoleUI.println("Пример: 090Add98-ca30-0d00-a003-8ba0e02fd0e4");
    }

    /**
     * Читает строку для проверки.
     *
     * @return введённая строка
     */
    private String readInput() {
        return consoleUI.readLine("\nВведите строку для проверки: ");
    }

    /**
     * Выводит результат проверки.
     *
     * @param response результат проверки GUID
     */
    private void printResult(GuidResponse response) {
        consoleUI.println("\nРезультат:");
        consoleUI.println(response.getMessage());
        if (response.isValid()) {
            consoleUI.println("Нормализованный GUID: " + response.getNormalizedGuid());
        }
    }
}
