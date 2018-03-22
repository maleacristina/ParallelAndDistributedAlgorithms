package producerconsumerusingconditionvariables;
import java.util.ArrayList;
 import java.util.LinkedList;
import java.util.List;

public class Application {
    public static LinkedList<Integer> list = new LinkedList<>();
    public static final int MAX = 30;

    public static void main(String[] args) {
        List<Thread> threadList = new ArrayList<>();
        final Object condProd = new Object();
        final Object condCons = new Object();
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
