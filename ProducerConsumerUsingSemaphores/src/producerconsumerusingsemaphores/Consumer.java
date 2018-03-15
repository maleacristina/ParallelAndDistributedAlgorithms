package producerconsumerusingsemaphores;
import static producerconsumerusingsemaphores.Application.list;
//import java.util.Queue;
//import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;



public class Consumer extends Thread {
    Lock lock;
    Semaphore semFull;
    Semaphore semFree;

    Consumer(Semaphore semFree, Semaphore semFull,Lock lock) {
        this.semFree = semFree;
        this.semFull = semFull;
        this.lock = lock;
    }

    @Override
    public void run() {
        int counter = 0;

        while (true) {
            try {
                synchronized (lock) {
                    semFull.acquire();
                    counter = list.remove();
                    semFree.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The consumer consums: " + counter);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    }
    

