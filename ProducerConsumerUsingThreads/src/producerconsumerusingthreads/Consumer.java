package producerconsumerusingthreads;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {
    ReentrantLock lock;
    Queue queue;
    int size;

    Consumer(Queue queue,ReentrantLock lock) {
        this.queue = queue;
        this.lock = lock;
        //this.size = size;
    }

    @Override
    public void run() {

        while(true) {

            if (queue.size() > 0) {
                lock.lock();
                System.out.println("The consumer consumes a product:" + Random());
                queue.remove();
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
        int product;
        Random random=new Random();
        product=random.nextInt(size);
        return product;
    }
}
