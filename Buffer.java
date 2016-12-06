import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Buffer extends Thread{

    static Random rand = new Random();

    static int size;
    static int numProducers;
    static int numConsumers;
    static int prodSleep;
    static int conSleep;
    static int numOfProdMessages;
    static ArrayList<LocalDateTime> buffer = new ArrayList(100);// Makes ArrayList of type LocalDateTime
    static ArrayList<LocalDateTime> buffer2 = new ArrayList(size);




    //Constructor that makes variables for sleep, size and num of threads
    public Buffer(int size, int numProducers, int numConsumers, int prodSleep, int conSleep, int numOfProdMessages){
        Buffer.size = size;
        Buffer.numProducers = numProducers;
        Buffer.numConsumers = numConsumers;
        Buffer.prodSleep = prodSleep;
        Buffer.conSleep = conSleep;
        Buffer.numOfProdMessages = numOfProdMessages;
    }



    public void runSimulation(){
        ArrayList <Producer> producers = new ArrayList <Producer>();
        ArrayList <Consumer> consumers = new ArrayList <Consumer>();
        for(int i = 1 ; i < numProducers + 1; i++){
            producers.add(new Producer("P" + i, prodSleep, numOfProdMessages));// Creates a Producer and puts it in Producer list
        }
        for(int i = 1 ; i < numConsumers + 1; i++){
            consumers.add(new Consumer("C" + i, conSleep, numOfProdMessages));// Creates a consumer and puts it in Consumer list
        }
        for(Producer p : producers){//Start ever Producer inside the Arraylist
            p.start();
        }
        for (Consumer c : consumers){//Start ever Consumer inside the Arraylist
            c.start();
        }
    }

    public void runSimulation2(){
        ArrayList <Producer2> producers = new ArrayList <Producer2>();
        ArrayList <Consumer2> consumers = new ArrayList <Consumer2>();
        for(int i = 1 ; i < numProducers + 1; i++){
            producers.add(new Producer2("P" + i, prodSleep, numOfProdMessages));// Creates a Producer and puts it in Producer list
        }
        for(int i = 1 ; i < numConsumers + 1; i++){
            consumers.add(new Consumer2("C" + i, conSleep, numOfProdMessages));// Creates a consumer and puts it in Consumer list
        }
        for(Producer2 p : producers){//Start ever Producer inside the Arraylist
            p.start();
        }
        for (Consumer2 c : consumers){//Start ever Consumer inside the Arraylist
            c.start();
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            long a = System.nanoTime();
            Buffer b = new Buffer(5, 2, 2, 20, 20, 500);
            b.runSimulation();
            System.out.println(System.nanoTime() - a);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        for(int i = 0; i < 10; i++) {
            long d = System.nanoTime();
            Buffer c = new Buffer(5, 2, 2, 20, 20, 500);
            c.runSimulation2();
            System.out.println(System.nanoTime() - d);
        }
    }
}
