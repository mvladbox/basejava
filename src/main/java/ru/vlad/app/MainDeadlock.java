package ru.vlad.app;

import static ru.vlad.app.MainDeadlock.Sequence.A_B;
import static ru.vlad.app.MainDeadlock.Sequence.B_A;

public class MainDeadlock {
    enum Sequence {A_B, B_A}

    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(() -> {
            processChain(A_B);
        });

        Thread threadTwo = new Thread(() -> {
            processChain(B_A);
        });

        threadOne.setName("First Process");
        threadTwo.setName("Second Process");
        threadOne.start();
//        Thread.sleep(600);
        threadTwo.start();
    }

    private static void processChain(Sequence sequence) {
        System.out.println(Thread.currentThread().getName() + " started");
        try {
            if (sequence == A_B) {
                System.out.println(Thread.currentThread().getName() + ": Try consume resource A...");
                synchronized (resourceA) {
                    consumeResourceA();
                    System.out.println(Thread.currentThread().getName() + ": Try consume resource B...");
                    synchronized (resourceB) {
                        consumeResourceB();
                    }
                }
            } else {
                System.out.println(Thread.currentThread().getName() + ": Try consume resource B...");
                synchronized (resourceB) {
                    consumeResourceA();
                    System.out.println(Thread.currentThread().getName() + ": Try consume resource A...");
                    synchronized (resourceA) {
                        consumeResourceB();
                    }
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
