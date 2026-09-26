package com.lab.ui;

import com.lab.model.MyGuid;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс для взаимодействия с консолью.
 * Отвечает только за ввод строки, вывод результатов и обработку
 * ошибок ввода. Логика проверки GUID вынесена в класс
 * {@link com.lab.model.MyGuid}.
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
     * Конструктор класса {@code ConsoleUI}.
     * Настраивает вывод в UTF-8 и инициализирует сканер для чтения ввода.
     */
    public ConsoleUI() {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    /**
     * Запускает основной сценарий работы приложения:
     * вывод заголовка, чтение строки, проверку GUID и вывод результата.
     */
    public void start() {
        printHeader();
        String input = readInput();
        MyGuid container = MyGuid.validateAll(input);
        printResults(container);
        close();
    }

    /**
     * Выводит заголовок задания и подсказку по формату ввода.
     */
    private void printHeader() {
        println("\nЗАДАНИЕ 2: Проверка GUID");
        println("Формат: 8-4-4-4-12 шестнадцатеричных цифр через тире");
        println("Пример: 090Add98-ca30-0d00-a003-8ba0e02fd0e4");
        println("Можно ввести несколько GUID через запятую.");
    }

    /**
     * Читает строку с консоли с защитой от пустого ввода
     * и закрытия потока ввода.
     *
     * @return введённая строка или {@code null}, если поток ввода закрыт
     */
    private String readInput() {
        while (true) {
            try {
                print("\nВведите строку для проверки: ");
                String input = scanner.nextLine();
                if (input != null && !input.trim().isEmpty()) {
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
     * Выводит результаты проверки всех GUID.
     *
     * @param container объект {@link MyGuid} с коллекцией результатов
     */
    private void printResults(MyGuid container) {
        println("\nРезультат:");

        if (container.getGuids().isEmpty()) {
            println("Нет данных для проверки.");
            return;
        }

        int index = 1;
        for (MyGuid myGuid : container.getGuids()) {
            String status = myGuid.isCorrect() ? "КОРРЕКТНЫЙ" : "НЕКОРРЕКТНЫЙ";

            println(index + ". " + myGuid.getGuid() + " — " + status);

            index++;
        }
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
     * Закрывает сканер.
     */
    public void close() {
        scanner.close();
    }
}