import java.time.LocalDateTime;

public class Consumer2 extends Thread{
    String name;
    int sleepTime;
    int numMessages;

    public Consumer2(String name, int sleepTime, int numMessages){ //Consructor that'll make a consumer thread
        this.name = name;
        this.sleepTime = sleepTime;
        this.numMessages = numMessages;
    }

    public void run(){
        for (int i = 0; i < numMessages; i++){//Runs consumer thread for amount of numMessages
            try {
                consumeItem();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consumeItem() throws InterruptedException {
        if(name.equals("C1")) {
            synchronized (Buffer.buffer) {//Only one thread can access the buffer
                while (Buffer.buffer.isEmpty()) {//Condition where if buffer is empty thread has to wait
                    Buffer.buffer.wait();
                }
            }
            LocalDateTime time = Buffer.buffer.get(0); //the removed object gets put into time
            synchronized (Buffer.buffer) {
                Buffer.buffer.remove(0);//Removes object
                //System.out.println(name + " got the  time stamp " + time + " Queue1 has " + Buffer.buffer.size() + " elements");
            }
            int sleepTime = Buffer.rand.nextInt(Buffer.conSleep + 1);//Sets sleep time for consumers
            Thread.sleep(sleepTime);
            synchronized (Buffer.buffer) {
                Buffer.buffer.notifyAll();
            }
        }
        else if(name.equals("C2")){
            synchronized (Buffer.buffer2) {//Only one thread can access the buffer
                while (Buffer.buffer2.isEmpty()) {//Condition where if buffer is empty thread has to wait
                    Buffer.buffer2.wait();
                }
            }
            LocalDateTime time = Buffer.buffer2.get(0); //the removed object gets put into time
            synchronized (Buffer.buffer2) {
                Buffer.buffer2.remove(0);//Removes object
                //System.out.println(name + " got the  time stamp " + time + " Queue2 has " + Buffer.buffer2.size() + " elements");
            }
            int sleepTime = Buffer.rand.nextInt(Buffer.conSleep + 1);//Sets sleep time for consumers
            Thread.sleep(sleepTime);
            synchronized (Buffer.buffer2) {
                Buffer.buffer2.notifyAll();
            }
        }
        }
    }
