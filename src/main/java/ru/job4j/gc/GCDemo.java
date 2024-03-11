package ru.job4j.gc;

public class GCDemo {

    private static final long KB = 1000;
    private static final long MB = KB * KB;
    private static final Runtime ENVIRONMENT = Runtime.getRuntime();

    public static long info() {
        final long freeMemory = ENVIRONMENT.freeMemory();
        final long totalMemory = ENVIRONMENT.totalMemory();
        final long maxMemory = ENVIRONMENT.maxMemory();
        System.out.println("=== Environment state ===");
        System.out.printf("Free: %d%n", freeMemory / MB);
        System.out.printf("Total: %d%n", totalMemory / MB);
        System.out.printf("Max: %d%n", maxMemory / MB);
        return freeMemory;
    }

    public static void personInfo(int i, long mem) {
        System.out.printf("Person:\t\t%d\t\t%d%n", i, mem);
    }

    public static void main(String[] args) {
        info();
        for (int i = 0; i < 1000; i++) {
            new Person(i, "N" + i);
            personInfo(i, ENVIRONMENT.freeMemory());
        }
        System.gc();
        info();
    }

}