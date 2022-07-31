//this is main class
//consists of 4 threads
//owner,waiter,generator and clock
package FinalAssignment;

public class Main {

    public static void main(String[] args) {

        Cafe cafe = new Cafe();
        Cupboard cupboard = new Cupboard();
        User owner = new User("Owner", cafe, cupboard);
        User waiter = new User("Waiter", cafe, cupboard);
        CustomerGenerator custgen = new CustomerGenerator(cafe); //cust gen object
        Clock clock = new Clock(custgen, owner, waiter, cafe); //clock for alerting used in custgen, owner, waiter and cafe

        //declaration of threads
        Thread thcg = new Thread(custgen);
        Thread thowner = new Thread(owner);//owner thread
        Thread thwaiter = new Thread(waiter); //waiter thread
        Thread thclock = new Thread(clock);

        //start threads
        thowner.start();
       thwaiter.start();
        thcg.start();
        thclock.start();
    }
}
