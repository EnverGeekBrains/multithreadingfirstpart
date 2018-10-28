package ru.eskendarov.ea;

/**
 * 1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
 * Используйте wait/notify/notifyAll.
 * 2. На серверной стороне сетевого чата реализовать управление потоками через ExecutorService.
 */

public class App {
  private static volatile char aChar = 'A';
  private final static Object monitor = new Object();

  public static void main(String[] args) {
    new Thread(new WaitNotifyClass('A', 'B')).start();
    new Thread(new WaitNotifyClass('B', 'C')).start();
    new Thread(new WaitNotifyClass('C', 'A')).start();
  }

  static class WaitNotifyClass implements Runnable {
    private final char currentLetter;
    private final char nextLetter;

    WaitNotifyClass(char currentLetter, char nextLetter) {
      this.currentLetter = currentLetter;
      this.nextLetter = nextLetter;
    }

    @Override
    public void run() {
      for (int i = 0; i < 5; i++) {
        synchronized (monitor) {
          try {
            while (aChar != currentLetter)
              monitor.wait();
            System.out.print(currentLetter);
            aChar = nextLetter;
            monitor.notifyAll();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}