package ru.job4j.gc.cache.menu;

import ru.job4j.gc.cache.DirFileCache;

import java.util.Scanner;

public class Emulator {

    public static final int ADD_DIRECTORY = 1;
    public static final int ADD_CACHE_FILE = 2;
    public static final int GET_CACHE_FILE = 3;
    public static DirFileCache dirFileCache;

    public static final String MENU = """
                Введите 1, для выбора директории.
                Введите 2, загрузить содержимое файла в кэш.
                Введите 3, получить содержимое файла из кэша.
                Введите любое другое число для выхода.
            """;

    public static final String DIRECTORY = "Введите путь к кешируемой дериктории";
    public static final String PUT_CACHE = "Введите имя кешируемого файла";
    public static final String GET_CACHE = "Введите имя извлекаемого файла";
    public static final String EXIT = "Конец работы";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        start(scanner);
    }

    private static void start(Scanner scanner) {
        boolean run = true;
        while (run) {
            System.out.println(MENU);
            int userChoice = Integer.parseInt(scanner.nextLine());
            if (ADD_DIRECTORY == userChoice) {
                System.out.println(DIRECTORY);
                String text = scanner.nextLine();
                dirFileCache = new DirFileCache(text);
            } else if (ADD_CACHE_FILE == userChoice) {
                System.out.println(PUT_CACHE);
                String text = scanner.nextLine();
                dirFileCache.put(text, dirFileCache.get(text));
            } else if (GET_CACHE_FILE == userChoice) {
                System.out.println(GET_CACHE);
                String text = scanner.nextLine();
                System.out.println(dirFileCache.get(text));
            } else {
                run = false;
                System.out.println(EXIT);
            }
        }
    }
}
