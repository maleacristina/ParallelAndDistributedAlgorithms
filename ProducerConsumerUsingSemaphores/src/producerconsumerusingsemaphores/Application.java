package producerconsumerusingsemaphores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Application {

    public static LinkedList<Integer> list = new LinkedList<>();
    
    public static void main(String[] args){
        List<Thread> threadList = new ArrayList<>();
        Semaphore semFull = new Semaphore(1);
        Semaphore semFree = new Semaphore(0);
        Lock lock = new ReentrantLock();
        Producer producer = new Producer(semFree, semFull, lock);
        Consumer consumer = new Consumer(semFree, semFull, lock);
        threadList.add(producer);
        threadList.add(consumer);
        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }      
   }
    


