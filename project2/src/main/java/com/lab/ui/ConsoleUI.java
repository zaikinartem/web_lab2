package com.lab.ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * Класс для взаимодействия с консолью.
 * Содержит методы ввода/вывода и обработку
 * технических исключений.
 *
 * @author User
 * @version 1.0
 */
@Component
public class ConsoleUI {

    /**
     * Сканер для чтения ввода с консоли.
     */
    private final Scanner scanner;

    /**
     * конструктор класса ConsoleUI.
     * Настраивает вывод в UTF-8 и инициализирует сканер для чтения ввода.
     */
    public ConsoleUI() {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    /**
     * Выводит текст в консоль без перевода строки.
     *
     * @param text текст для вывода
     */
    public void print(String text) {
        System.out.print(text);
    }

    /**
     * Выводит текст в консоль с переводом строки.
     *
     * @param text текст для вывода
     */
    public void println(String text) {
        System.out.println(text);
    }

    /**
     * Читает строку с валидацией.
     *
     * @param prompt приглашение к вводу
     * @return прочитанная строка
     */
    public String readLine(String prompt) {
        while (true) {
            try {
                print(prompt);
                String input = scanner.nextLine();
                if (isValid(input)) {
                    return input.trim();
                }
                println("Ошибка: строка не может быть пустой!");
            } catch (NoSuchElementException e) {
                println("Ошибка: поток ввода закрыт.");
                return null;
            }
        }
    }

    /**
     * Проверяет, что строка не пустая.
     *
     * @param input проверяемая строка
     * @return true, если строка не пустая
     */
    private boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Закрывает сканер.
     */
    public void close() {
        scanner.close();
    }

}
