package producerconsumerusingthreads;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Application {

    private static Queue<Integer> queue = new LinkedList<Integer>();
    
    public static void main(String[] args) throws InterruptedException{
        int size = 10;
        ReentrantLock lock = new ReentrantLock();
        Producer producer = new Producer(lock,queue, size);
        Consumer consumer = new Consumer(queue,lock);
        new Thread(producer).start();
        new Thread(consumer).start();     
       
        new Thread(producer).join();
        new Thread(consumer).join(); 
   }
    
}

