package producerconsumerusingthreads;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Queue;
import java.util.Random;

public class Producer implements Runnable {
    ReentrantLock lock;
    Queue queue;
    int size;
    //private static int counter = 0;

    Producer(ReentrantLock lock,Queue queue, int size) {
        this.lock = lock;
        this.queue = queue;
        this.size = size;
    }

    @Override
    public void run() {

        while(true) {

            if (queue.size() < size) {
                lock.lock();
                System.out.println("The producer products a product:" + Random());
                queue.add(Random());
                lock.unlock();
            }
             else{    
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private int Random()
    {
        Random random=new Random();
        int producer=random.nextInt(size);
        return producer;
    }
}
