package FinalAssignment;

class User implements Runnable {

    Cafe cafe;
    Cupboard cupboard;
    User user;
    String name;
    public boolean cafeclose = false;

    public User(String name, Cafe cafe, Cupboard cupboard) {
        this.name = name;
        this.cafe = cafe;
        this.cupboard = cupboard;
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }
        System.out.println(this.name + " has started their shift.");
        while (!cafeclose) {

            cafe.WorkerFunction(this);
        }

        if (cafeclose) { //closing time, 100 ms gap
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }

        }
    }

    public void makejuice() {
        cupboard.getglass(this);

    }

    public void makecappuccino() {
        cupboard.getcup(this);
        cupboard.getmilk(this);
        cupboard.getcoffee(this);
        cupboard.mixing(this);

    }

    public synchronized void setclosingTime() { //announces if waiter has remaining order, they complete and leaving at last order call
        // while owner will also finish his order and leave after last customer has left cafe.
        cafeclose = true;
        System.out.println("\t\t**********IF WAITER HAS REMAINING ORDER, SERVE AND LEAVE CAFE. ");
        System.out.println("\t\t**********IF OWNER HAS REMAINING ORDER, SERVE AND LEAVE CAFE AFTER CLOSING THE SHOP. ");
    }

}
