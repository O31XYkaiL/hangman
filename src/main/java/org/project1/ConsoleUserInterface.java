package org.project1;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Реализация интерфейса UserInterface для консольного взаимодействия с пользователем.
 * Этот класс предоставляет методы для вывода сообщений и получения ввода от пользователя через консоль.
 */
public class ConsoleUserInterface implements UserInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleUserInterface.class.getName());
    private final Scanner scanner;

    /**
     * Конструктор по умолчанию, инициализирующий сканер для чтения ввода пользователя.
     * Используется кодировка UTF-8 для корректного чтения ввода.
     */
    public ConsoleUserInterface() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    /**
     * Выводит сообщение пользователю через логгер.
     *
     * @param message Сообщение, которое необходимо вывести.
     */
    @Override
    public void displayMessage(String message) {
        LOGGER.info(message);
    }

    /**
     * Получает ввод от пользователя через консоль.
     *
     * @return Строка, введенная пользователем.
     */
    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }
}