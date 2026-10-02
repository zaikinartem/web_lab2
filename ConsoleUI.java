package com.lab.ui;

import com.lab.model.MyGuid;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Отвечает за ввод данных и вывод результатов в консоль.
 *
 * @author User
 * @version 1.0
 */
@Component
public class ConsoleUI {

    /**
     * Сканер для чтения данных из консоли.
     */
    private final Scanner scanner;

    /**
     * Создаёт консольный интерфейс.
     */
    public ConsoleUI() {
        System.setOut(new PrintStream(
                System.out,
                true,
                StandardCharsets.UTF_8
        ));

        this.scanner = new Scanner(
                System.in,
                StandardCharsets.UTF_8
        );
    }

    /**
     * Запускает программу.
     */
    public void start() {
        printHeader();

        String input = readInput();

        MyGuid.validateAll(input);

        printResults();

        close();
    }

    /**
     * Выводит название задания и пример ввода.
     */
    private void printHeader() {
        println("\nЗАДАНИЕ 2: Проверка GUID");
        println("Формат: 8-4-4-4-12 шестнадцатеричных цифр через тире");
        println("Пример: 090Add98-ca30-0d00-a003-8ba0e02fd0e4");
        println("Можно ввести несколько GUID через запятую.");
    }

    /**
     * Читает строку с консоли.
     *
     * @return введённая строка или null, если ввод закрыт
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
     */
    private void printResults() {
        println("\nРезультат:");

        List<MyGuid> guids = MyGuid.getGuids();

        if (guids.isEmpty()) {
            println("Нет данных для проверки.");
            return;
        }

        int index = 1;

        for (MyGuid myGuid : guids) {
            String status = myGuid.getIsCorrect()
                    ? "КОРРЕКТНЫЙ"
                    : "НЕКОРРЕКТНЫЙ";

            println(
                    index + ". "
                            + myGuid.getGuid()
                            + " — "
                            + status
            );

            index++;
        }
    }

    /**
     * Выводит текст без перевода строки.
     *
     * @param text текст для вывода
     */
    public void print(String text) {
        System.out.print(text);
    }

    /**
     * Выводит текст с переводом строки.
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
