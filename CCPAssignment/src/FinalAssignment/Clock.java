package FinalAssignment;

public class Clock implements Runnable {

    Cafe cafe;
    CustomerGenerator custgen;
    User owner;
    User waiter;

    Clock(CustomerGenerator cg, User owner, User waiter, Cafe shop) {
        this.custgen = cg;
        this.owner = owner;
        this.waiter = waiter;
        this.cafe = shop;
    }

    public void run() {

        try {
            Thread.sleep(30000); //timing of program, duration of program will be around 30 seconds approximately. 
            notifyClosing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void notifyClosing() { //notify each thread when closing time is happening.
        custgen.setcafeclose();
        waiter.setclosingTime();
        owner.cafeclose = true;

    }

}
