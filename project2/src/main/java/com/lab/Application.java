package com.lab;

import com.lab.controller.GuidController;
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
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        GuidController controller = context.getBean(GuidController.class);
        controller.execute();
    }
}