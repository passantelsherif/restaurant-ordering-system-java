import java.util.*;

// ==============================
// 6) OBSERVER (Notify kitchen & waiter)
// ==============================
public interface OrderObserver { void update(Order order); }

class Kitchen implements OrderObserver {
    public void update(Order order) {
        System.out.println("[Kitchen] New order received: " + order.summary());
    }
}

class Waiter implements OrderObserver {
    public void update(Order order) {
        System.out.println("[Waiter] Please serve order: " + order.summary());
    }
}

class OrderNotifier {
    private List<OrderObserver> observers = new ArrayList<>();
    public void addObserver(OrderObserver o) { observers.add(o); }
    public void removeObserver(OrderObserver o) { observers.remove(o); }
    public void notifyAll(Order order) {
        for (OrderObserver o : observers) o.update(order);
    }
}