package com.lab;

import com.lab.ui.ConsoleUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Главный класс приложения.
 * Запускает Spring Boot контекст и инициирует выполнение лабораторной работы.
 *
 * @author User
 * @version 1.0
 */
@SpringBootApplication
public class Application {

    /**
     * Точка входа в приложение.
     * Запускает Spring Boot, получает бин {@link ConsoleUI}
     * и вызывает метод {@link ConsoleUI#start()}.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        ConsoleUI consoleUI = context.getBean(ConsoleUI.class);
        consoleUI.start();
    }
}