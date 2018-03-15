package producerconsumerusingsemaphores;
import static producerconsumerusingsemaphores.Application.list;
import java.util.concurrent.locks.ReentrantLock;
//import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;



public class Producer extends Thread {
    Semaphore semFull;
    Semaphore semFree;
    Lock lock;
    //private static int counter = 0;

    Producer(Semaphore semFree, Semaphore semFull, Lock lock) {
        this.semFree = semFree;
        this.semFull = semFull;
        this.lock = lock;
    }

    @Override
    public void run() {
        int counter = 0;
        while(true) {
            try{
                Thread.sleep(500);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
             try {
                synchronized (lock) {
                    semFree.acquire();
                    System.out.println("The producer produces:");
                    list.add(counter++);
                    semFull.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
  
}
