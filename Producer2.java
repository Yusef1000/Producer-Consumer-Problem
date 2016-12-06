import java.time.LocalDateTime;

public class Producer2 extends Thread {
    String name;
    int sleepTime;
    int numMessages;

    public Producer2(String name, int sleepTime, int numMessages) { //Makes a Producer thread
        this.name = name;
        this.sleepTime = sleepTime;
        this.numMessages = numMessages;
    }

    public void run() {
        for (int i = 0; i < numMessages; i++) {//Runs producers for amount of messages
            try {
                produceItem();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void produceItem() throws InterruptedException {
        if (Buffer.buffer.size() < Buffer.buffer2.size()) {
            synchronized (Buffer.buffer) {
                while (Buffer.buffer.size() == Buffer.size) {//Condition where threads cannot produce if Arraylist is full
                    Buffer.buffer.wait();
                }
            }
            LocalDateTime time = LocalDateTime.now();//time variable would be equal to the time it is now.
            synchronized (Buffer.buffer) {
                Buffer.buffer.add(time);//Adds time stamp into the arraylist
                //System.out.println(name + " produced time stamp " + time + " Queue1 has " + Buffer.buffer.size() + " elements");
            }
            int sleepTime = Buffer.rand.nextInt(Buffer.prodSleep + 1);//sets sleep for producers
            Thread.sleep(sleepTime);
            synchronized (Buffer.buffer) {
                Buffer.buffer.notifyAll();
            }
        }
        if(Buffer.buffer.size() >= Buffer.buffer2.size()) {
            synchronized (Buffer.buffer2) {
                while (Buffer.buffer2.size() == Buffer.size) {//Condition where threads cannot produce if Arraylist is full
                    Buffer.buffer2.wait();
                }
            }
            LocalDateTime time = LocalDateTime.now();//time variable would be equal to the time it is now.
            synchronized (Buffer.buffer2) {
                Buffer.buffer2.add(time);//Adds time stamp into the arraylist
                //System.out.println(name + " produced time stamp " + time + " Queue2 has " + Buffer.buffer2.size() + " elements");
            }
            int sleepTime = Buffer.rand.nextInt(Buffer.prodSleep + 1);//sets sleep for producers
            Thread.sleep(sleepTime);
            synchronized (Buffer.buffer2) {
                Buffer.buffer2.notifyAll();
            }
        }
    }
}