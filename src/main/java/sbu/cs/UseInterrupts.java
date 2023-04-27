package sbu.cs;

public class UseInterrupts {
    public static class SleepThread extends Thread {
        int sleepCounter;

        public SleepThread(int sleepCounter) {
            super();
            this.sleepCounter = sleepCounter;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");

            while (this.sleepCounter > 0)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(this.getName() + " has been interrupted");
                    return; // terminate thread
                }
                finally {
                    this.sleepCounter--;
                    System.out.println("Number of sleeps remaining: " + this.sleepCounter);
                }
            }

        }
    }

    public static class LoopThread extends Thread {
        int value;
        public LoopThread(int value) {
            super();
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");

            for (int i = 0; i < 10; i += 3)
            {
                i -= this.value;

                if (Thread.currentThread().isInterrupted()) { // check if interrupted
                    System.out.println(this.getName() + " has been interrupted");
                    return; // terminate thread
                }
            }
        }
    }

    public static void main(String[] args) {
        SleepThread sleepThread = new SleepThread(5);
        sleepThread.start();

        // Check if this thread runs for longer than 3 seconds (if it does, interrupt it)
        try {
            Thread.sleep(3000); // wait for 3 seconds
            if (sleepThread.isAlive()) { // check if still running
                sleepThread.interrupt(); // interrupt if still running
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LoopThread loopThread = new LoopThread(3);
        loopThread.start();

        // Check if this thread runs for longer than 3 seconds (if it does, interrupt it)
        try {
            Thread.sleep(3000); // wait for 3 seconds
            if (loopThread.isAlive()) { // check if still running
                loopThread.interrupt(); // interrupt if still running
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
