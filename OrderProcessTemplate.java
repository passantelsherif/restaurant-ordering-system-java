// ==============================
// 8) TEMPLATE for different order types
// ==============================
public abstract class OrderProcessTemplate {
    protected OrderRepository repo;
    protected OrderNotifier notifier;

    public OrderProcessTemplate(OrderRepository repo, OrderNotifier notifier) {
        this.repo = repo;
        this.notifier = notifier;
    }

    // Template method - defines the order processing sequence (FINAL)
    public final void processOrder(Order order) {
        validateOrder(order);
        prepareOrderTypeSpecifics(order);
        calculateTotals(order);
        processPayment(order);
        saveOrder(order);
        handlePostPayment(order);
        notifyStaff(order);
        generateReceipt(order);
    }

    // Common steps
    protected void validateOrder(Order order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalStateException("Order must have at least one item");
        }
        System.out.println("[Validation] Order validated.");
    }

    protected void calculateTotals(Order order) {
        double subtotal = order.itemsTotal();
        double discount = order.discountAmount();
        double total = order.total();
        System.out.println("[OrderProcess] Subtotal=$" + subtotal +
                " Discount=$" + String.format("%.2f", discount) +
                " Total=$" + String.format("%.2f", total));
    }

    protected void processPayment(Order order) {
        PaymentStrategy p = order.getPaymentStrategy();
        if (p == null) throw new IllegalStateException("No payment method selected");
        p.pay(order.total());
    }

    protected void saveOrder(Order order) {
        repo.saveOrder(order);
    }

    protected void notifyStaff(Order order) {
        if (notifier != null) notifier.notifyAll(order);
    }

    protected void generateReceipt(Order order) {
        System.out.println("===== RECEIPT =====");
        System.out.println("Order Type: " + order.getOrderType());
        for (IMenuComponent it : order.getItems()) {
            System.out.println("- " + it.getName() + " : $" + it.getPrice());
        }
        double subtotal = order.itemsTotal();
        double discount = order.discountAmount();
        double after = subtotal - discount;
        double tax = order.taxAmount(after);
        System.out.println("Subtotal: $" + String.format("%.2f", subtotal));
        System.out.println("Discount: -$" + String.format("%.2f", discount));
        System.out.println("Tax: $" + String.format("%.2f", tax));
        System.out.println("Total: $" + String.format("%.2f", order.total()));
        System.out.println("===================");
    }

    // Steps that vary by order type (abstract methods)
    protected abstract void prepareOrderTypeSpecifics(Order order);
    protected abstract void handlePostPayment(Order order);
}