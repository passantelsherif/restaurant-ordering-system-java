import java.util.*;

// ==============================
// 7) BUILDER (Order)
// ==============================
public class Order {
    private List<IMenuComponent> items = new ArrayList<>();
    private String orderType;
    private PaymentStrategy paymentStrategy;
    private DiscountStrategy discountStrategy;
    private double taxPercent = 0.14;

    public List<IMenuComponent> getItems() { return items; }
    public void addItem(IMenuComponent it) { items.add(it); }
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public void setPaymentStrategy(PaymentStrategy p) { this.paymentStrategy = p; }
    public void setDiscountStrategy(DiscountStrategy d) { this.discountStrategy = d; }

    public String getOrderType() { return orderType; }
    public PaymentStrategy getPaymentStrategy() { return paymentStrategy; }
    public DiscountStrategy getDiscountStrategy() { return discountStrategy; }

    public double itemsTotal() {
        return items.stream().mapToDouble(IMenuComponent::getPrice).sum();
    }

    public double discountAmount() {
        return (discountStrategy != null) ? discountStrategy.applyDiscount(this) : 0;
    }

    public double taxAmount(double subtotalAfterDiscount) {
        return subtotalAfterDiscount * taxPercent;
    }

    public double total() {
        double subtotal = itemsTotal();
        double disc = discountAmount();
        double after = subtotal - disc;
        double tax = taxAmount(after);
        return after + tax;
    }

    public String summary() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderType=").append(orderType).append(", Items=[");
        for (IMenuComponent it : items) {
            sb.append(it.getName()).append("($").append(it.getPrice()).append("), ");
        }
        sb.append("] Total=$").append(String.format("%.2f", total()));
        return sb.toString();
    }
}

class OrderBuilder {
    private Order order = new Order();
    public OrderBuilder addItem(IMenuComponent item) { order.addItem(item); return this; }
    public OrderBuilder orderType(String type) { order.setOrderType(type); return this; }
    public OrderBuilder payment(PaymentStrategy p) { order.setPaymentStrategy(p); return this; }
    public OrderBuilder discount(DiscountStrategy d) { order.setDiscountStrategy(d); return this; }
    public Order build() { return order; }
}