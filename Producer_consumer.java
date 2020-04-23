
package producer_consumer;

import java.util.*;

class Producer extends Thread
{
    Vector V;
    int size;
    
    Producer(Vector V, int size)
    {
        this.V = V;
        this.size = size;
    }

    @Override
    public void run() 
    {
         int i=1;
         while(true)
         {
            System.out.println("Producer produced : " +i);
            try 
            {
                 produce(i);
                 i++;
            } 
            catch (Exception e) 
            {
                    e.printStackTrace();
            }
        }
    }
    
     void produce(int i) throws InterruptedException 
    {
        while (V.size() == size) 
        {
            synchronized (V) 
            {
                System.out.println("Queue is full ");
                V.wait();
            }
        }

        
        synchronized (V) 
        {
            V.add(i);
            V.notifyAll();
        }
    }
    
}

class Consumer extends Thread
{

     final Vector V;
     final int size;

     Consumer(Vector V, int size)
    {
        this.V = V;
        this.size = size;
    }
     
    int consume() throws Exception 
    {
        
        while (V.isEmpty())
        {
            synchronized (V)
            {
                System.out.println("Queue is empty" );
                V.wait();
            }
        }

        synchronized (V) 
        {
            V.notifyAll();
            return (Integer) V.remove(0);
        }
    }

    @Override
    public void run()
    {
        while (true) 
        {
            try 
            {
                 System.out.println("Consumer consumed : " + consume());
                 Thread.sleep(1000);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }

   
}


public class Producer_consumer {

    
    public static void main(String[] args) 
    {
        
        Vector V=new Vector();
        int size=5;
        
        Producer p1=new Producer(V,size);
        Consumer c1=new Consumer(V,size);
        
        p1.start();
        c1.start();
  
    }
    
}
