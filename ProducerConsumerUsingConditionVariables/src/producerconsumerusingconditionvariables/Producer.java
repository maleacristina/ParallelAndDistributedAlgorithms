package producerconsumerusingconditionvariables;
import static java.time.Instant.MAX;
import static producerconsumerusingconditionvariables.Application.list;

public class Producer extends Thread{
    private final Object condProd;
    private final Object condCons;

    public Producer(Object condProd, Object condCons) {
        this.condProd = condProd;
        this.condCons = condCons;
    }

    @Override
        public void run() {
        int item = 0;

        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            item++;
            synchronized (condProd) {
                if (Application.list.size() == Application.MAX) {
                    try {
                        condProd.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("The producer produces = " + list.add(item));
            }
            synchronized (condCons) {
                condCons.notify();
            }
        }
    }
}
