package FinalAssignment;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Cafe {

    /*   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   
    *    Scenario 1         -          Scenario 2        -        Scenario 3        * 
    *    num   - 200       -          num   - 100       -       num   - 50        * 
    *    num2 - 400       -          num2 - 200       -     num2  - 100        * 
    *    num3 - 500       -          num3 - 300       -     num3  - 150        * 
    *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   */
    int num = 200; //order and take order
    int num2 = 400; //process order
    int num3 = 500; //time taken to finish drink
    int statistics = 0; //counter for total customers being served after each time.
    List<Customer> customerList; //linkedlist for customer, will store all customers waiting to be served.
    public static ReentrantLock takingorder = new ReentrantLock(); // order lock for customer order, taking their order and starting their order.
    public static ReentrantLock cupboard = new ReentrantLock(); //cupboard access lock - can only be accessed once other user has put back the ingredients or served juicetap.
    public static ReentrantLock ingredients = new ReentrantLock(); //ingredientlock
    public static ReentrantLock juicetap = new ReentrantLock(); //juicetaplock
    Random orders = new Random();  //random generator order, 1 = cappuccino while 2 = juicetap.
    int cafeseats = 10; //number of customers allowed at one time, if 10 chairs full, customer will exit.

    public Cafe() {
        customerList = new LinkedList<Customer>(); //list accessed by the functions below
    }

    public void newCustomer(Customer customer) {
        System.out.println("**********" + customer.getName() + " is entering the cafe at " + customer.getInTime());
        synchronized (customerList) { //synchronized block 
            if (customerList.size() == cafeseats) {
                System.out.println("xxxxx No seats available for  " + customer.getName());
                System.out.println("xxxxx " + customer.getName() + " exits cafe.");
                return;
            }
            ((LinkedList<Customer>) customerList).offer(customer);
            System.out.println("----------" + customer.getName() + " got the seat.");
            if (customerList.size() == 1) {
                customerList.notify();
            }

        }
    }

    public void WorkerFunction(User worker) {
        int Order = orders.nextInt(2) + 1; //this is used to generate either 1 or 2 for order. 1 - cappuccino and 2- juice.
        Customer customer;
        synchronized (customerList) {
            while (customerList.size() == 0) {
                try {
                    customerList.wait();  //will make thread wait until notify is invoked,after customer has gotten the chair.
                } catch (InterruptedException iex) {
                    iex.printStackTrace();
                }
            }
            customer = (Customer) ((LinkedList<?>) customerList).poll();
        }
        //long duration = 0;
        takingorder.lock(); //when one user is taking order, the other user can't take order until after this lock is released.
        try {
            if (Order == 1) {
                System.out.println("-----" + worker.name + " takes order for: " + customer.getName());
                try {
                    Thread.sleep(num);
                } catch (Exception e) {
                }
                System.out.println("@" + customer.getName() + " orders cappuccino.");
            } else {
                System.out.println("-----" + worker.name + " takes order for: " + customer.getName());
                try {
                    Thread.sleep(num);
                } catch (Exception e) {
                }
                System.out.println("@" + customer.getName() + " orders juice.");
            }
            try {
                Thread.sleep(num2);
            } catch (Exception e) {
            }
        } finally {
            takingorder.unlock();
            System.out.println("&&&&&" + worker.name + "  has finished taking the order for " + customer.getName());
            try {
                Thread.sleep(num);
            } catch (Exception e) {
            }
        }

        if (Order == 1) {
            cupboard.lock();
            try {

                worker.makecappuccino(); //method for getting ingredients and making drink 
                // other thread can't access this until cupboard is unlocked.
            } finally {
                cupboard.unlock();
            }

            ingredients.lock();
            try {

                System.out.println("#####" + worker.name + " is putting ingredient back .");
                //now next user can access this lock after ingredients have been put back
                try {
                    Thread.sleep(num2);
                } catch (Exception e) {
                }
            } finally {

                ingredients.unlock();
            }
            System.out.println("-----" + worker.name + " has finished serving cappuccino to " + customer.getName());

        } else {

            cupboard.lock();
            try {

                worker.makejuice(); //takes glass from cupboard

            } finally {
                cupboard.unlock();

            }
            juicetap.lock();
            try {
                System.out.println("#####" + worker.name + " filling glass with juice from juice tap for  " + customer.getName());
                try {
                    Thread.sleep(num2);
                } catch (Exception e) {
                }

            } finally {
                juicetap.unlock();
            }
            System.out.println("-----" + worker.name + " has finished serving juice to " + customer.getName());
        }
        try {
            Thread.sleep(num3);
        } catch (Exception e) {
        }
        System.out.println("!!!!!" + customer.getName() + " has finished their drink in " + num3 + " ms");
        System.out.println("!!!!!" + customer.getName() + " has left the cafe. ");
        statistics++; //counter number of customers served
        System.out.println(statistics + "- customers have been served as of " + customer.getInTime());

    }
}
