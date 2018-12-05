package ru.vlad.app;

public class MainDeadlock {

    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

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

    private static void processChain(Object resource1, Object resource2) {
        System.out.println(Thread.currentThread().getName() + " started");
        try {
            synchronized (resource1) {
                consumeResourceA();
                synchronized (resource2) {
                    consumeResourceB();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " stopped");
    }

    private static void consumeResourceA() throws InterruptedException {
        someWork("A");
    }

    private static void consumeResourceB() throws InterruptedException {
        someWork("B");
    }

    private static void someWork(String resource) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": Processing resource " + resource + "...");
        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + ": Resource " + resource + " released");
    }
}
