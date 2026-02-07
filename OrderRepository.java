import java.util.*;

// ==============================
// 4) REPOSITORY
// ==============================
public interface OrderRepository {
    void saveOrder(Order order);
}

class InMemoryOrderRepository implements OrderRepository {
    List<Order> orders = new ArrayList<>();
    @Override public void saveOrder(Order order) {
        orders.add(order);
        System.out.println("[Repository] Order saved: " + order.summary());
    }
}
