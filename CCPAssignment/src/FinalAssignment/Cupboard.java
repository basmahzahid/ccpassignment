package FinalAssignment;

import java.util.concurrent.locks.ReentrantLock;

class Cupboard {

    //locks for each resource
    int num = 300;
    int num2 = 500;

    /*   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   
    *  Scenario 1         -          Scenario 2        -        Scenario 3          * 
    *  num   - 300       -          num   - 100       -       num   - 50           * 
    *  num2 - 500       -          num2 - 100       -       num2  - 50          * 
    *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   */
    Cafe cafe;
    ReentrantLock glass = new ReentrantLock();
    ReentrantLock cup = new ReentrantLock();
    ReentrantLock milk = new ReentrantLock();
    ReentrantLock coffee = new ReentrantLock();
    ReentrantLock mix = new ReentrantLock();

    @SuppressWarnings("finally")
    public void getcup(User worker) {
        cup.lock();
        try {
            System.out.println(worker.name + " is getting the cup from the cupboard.");
            try {
                Thread.sleep(num);
            } catch (Exception e) {
            }
        } finally {
            cup.unlock();
        }
    }

    @SuppressWarnings("finally")
    public void getglass(User worker) {
        glass.lock();
        try {
            System.out.println(worker.name + " is getting the glass from the cupboard.");
            try {
                Thread.sleep(num);
            } catch (Exception e) {
            }
        } finally {
            glass.unlock();
        }

    }

    @SuppressWarnings("finally")
    public void getmilk(User worker) {
        milk.lock();
        try {
            System.out.println(worker.name + " is getting the milk from the cupboard.");
            try {
                Thread.sleep(num);
            } catch (Exception e) {
            }
        } finally {
            milk.unlock();
        }
    }

    @SuppressWarnings("finally")
    public void getcoffee(User worker) {
        coffee.lock();
        try {
            System.out.println(worker.name + " is getting the coffee from the cupboard.");
            try {
                Thread.sleep(num);
            } catch (Exception e) {
            }
        } finally {
            coffee.unlock();
        }

    }

    public void mixing(User worker) {
        mix.lock();
        try {
            System.out.println(worker.name + " is making cappuccino for customer");
            try {
                Thread.sleep(num2);
            } catch (Exception e) {
            }
        } finally {
            mix.unlock();
        }
    }

}
