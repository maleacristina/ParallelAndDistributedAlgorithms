package producerconsumerusingconditionvariables;
import static producerconsumerusingconditionvariables.Application.list;
 import java.util.LinkedList;


public class Consumer extends Thread{
    private final Object condProd;
    private final Object condCons;


    public Consumer(Object condProd, Object condCons) {
        this.condProd = condProd;
        this.condCons = condCons;
    }

    @Override
    public void run() {
        int item = 0;

        while (true) {
            synchronized (condCons) {
                if (list.isEmpty()){
                    try {
                        condCons.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (condProd){
                item = list.removeFirst();
                condProd.notify();
            }
            System.out.println("Consumer consumed " + item);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
