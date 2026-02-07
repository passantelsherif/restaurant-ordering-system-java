public class DineInOrderProcess extends OrderProcessTemplate {
    public DineInOrderProcess(OrderRepository repo, OrderNotifier notifier) {
        super(repo, notifier);
    }

    @Override
    protected void prepareOrderTypeSpecifics(Order order) {
        System.out.println("[Dine-In] Preparing table service...");
        System.out.println("[Dine-In] Order will be served at table");
    }

    @Override
    protected void handlePostPayment(Order order) {
        System.out.println("[Dine-In] Payment processed at table via " +
                order.getPaymentStrategy().name());
        System.out.println("[Dine-In] Thank you for dining with us!");
    }
}