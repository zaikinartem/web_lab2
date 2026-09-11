package com.lab.controller;

import com.lab.model.GuidResponse;
import com.lab.service.GuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class GuidController {

    @Autowired
    private GuidService guidService;

    private Scanner scanner;

    public GuidController() {
        this.scanner = new Scanner(System.in);
    }

    public void execute() {
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
        System.out.println("\n ЗАДАНИЕ 2: Проверка GUID");
        System.out.println("Формат: 8-4-4-4-12 шестнадцатеричных цифр через тире");
        System.out.println("Пример: 090Add98-ca30-0d00-a003-8ba0e02fd0e4");

        System.out.print("\nВведите строку для проверки: ");
        String input = scanner.nextLine();

        GuidResponse response = guidService.validateGuid(input);

        System.out.println("\nРезультат:");
        System.out.println(response.getMessage());
        if (response.isValid()) {
            System.out.println("Нормализованный GUID: " + response.getNormalizedGuid());
        }

        scanner.close();
    }
}