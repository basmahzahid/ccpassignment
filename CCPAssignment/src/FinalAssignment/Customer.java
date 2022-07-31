package FinalAssignment;

import java.util.Date;

class Customer implements Runnable {

    String name;
    Date inTime;
    Cafe cafe;

    public Customer(Cafe shop) {
        this.cafe = shop;
    }

    public String getName() {
        return name;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public void run() {
        cafeStart();
    }

    private synchronized void cafeStart() {
        cafe.newCustomer(this);
    }

}
