package ru.vlad.app;

public class MainDeadlock {

    private static final String resourceA = "A";
    private static final String resourceB = "B";

    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(() -> {
            processChain(resourceA, resourceB);
        });

        Thread threadTwo = new Thread(() -> {
            processChain(resourceB, resourceA);
        });

        threadOne.setName("First Process");
        threadTwo.setName("Second Process");
        threadOne.start();
//        Thread.sleep(1000);
        threadTwo.start();
    }

    private static void processChain(String resource1, String resource2) {
        System.out.println(Thread.currentThread().getName() + " started");
        try {
            synchronized (resource1) {
                someWork(resource1);
                synchronized (resource2) {
                    someWork(resource2);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " stopped");
    }

    private static void someWork(String resource) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": Processing resource " + resource + "...");
        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + ": Resource " + resource + " released");
    }
}
