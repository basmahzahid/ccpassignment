package FinalAssignment;

import java.util.Date;
import java.util.concurrent.TimeUnit;

class CustomerGenerator implements Runnable {

    Cafe cafe;
    public volatile boolean cafeclose = false;
    int n = 3;
    int sleep = 500;

    public CustomerGenerator(Cafe shop) {
        this.cafe = shop;
    }

    /*   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   
    *  Scenario 1         -          Scenario 2        -        Scenario 3          * 
    *  sleep  - 500       -          sleep   - 200       -       sleep   - 100        * 
    *  n - 3       -                    n   - 2       -                 n- 2                   * 
    *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *    */
    public void run() {
        try {
            Thread.sleep(sleep); //time before customer start enter the cafe.

        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }
        while (!cafeclose) {
            Customer customer = new Customer(cafe);
            customer.setInTime(new Date());
            Thread thcustomer = new Thread(customer);
            customer.setName("Customer " + thcustomer.getId());
            thcustomer.start();
            try {
                TimeUnit.SECONDS.sleep((long) (Math.random() * n)); //random time between each customer entering the cafe.
            } catch (InterruptedException iex) {
                iex.printStackTrace();
            }
        }
        if (cafeclose) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    public synchronized void setcafeclose() { //used to announce last orders and allow other customers to leave.
        cafeclose = true;
        System.out.println("\t\t**********LAST ORDER CALL.");
        System.out.println("\t\t**********NO MORE ORDERS. OTHER CUSTOMERS ASKED TO LEAVE.");

    }

}
