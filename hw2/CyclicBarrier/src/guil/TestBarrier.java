package guil;

import java.util.Random;


public class TestBarrier {

    public static Random r = new Random();

    static class TestRunner implements Runnable {

        CyclicBarrier barrier;

        public TestRunner(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        public void run() {

            try {
            for(;;) {
                System.out.printf("%d wait before entering\n", Thread.currentThread().getId());
                Thread.sleep(r.nextInt(50));
                int ticket = barrier.await();
                System.out.printf("%d waited at the barrier with ticket: %d\n", Thread.currentThread().getId(), ticket);
            }
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {

        int barrierSize = 4;
        int numberOfThreads = 4;

        if (args.length == 2) {
            barrierSize = Integer.parseInt(args[0]);
            numberOfThreads = Integer.parseInt(args[1]);
        }

        CyclicBarrier barrier = new CyclicBarrier(barrierSize);


        for(int i = 0; i < numberOfThreads; i++) {
            new Thread(new TestRunner(barrier)).start();
        }

    }
}