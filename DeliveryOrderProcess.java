public class DeliveryOrderProcess extends OrderProcessTemplate {
    public DeliveryOrderProcess(OrderRepository repo, OrderNotifier notifier) {
        super(repo, notifier);
    }

    @Override
    protected void prepareOrderTypeSpecifics(Order order) {
        System.out.println("[Delivery] Preparing for delivery...");
        System.out.println("[Delivery] Estimating delivery time: 30-45 minutes");
    }

    @Override
    protected void handlePostPayment(Order order) {
        System.out.println("[Delivery] Payment processed online via " +
                order.getPaymentStrategy().name());
        System.out.println("[Delivery] Order dispatched for delivery");
    }
}