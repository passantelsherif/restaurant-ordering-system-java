public class TakeawayOrderProcess extends OrderProcessTemplate {
    public TakeawayOrderProcess(OrderRepository repo, OrderNotifier notifier) {
        super(repo, notifier);
    }

    @Override
    protected void prepareOrderTypeSpecifics(Order order) {
        System.out.println("[Takeaway] Preparing for pickup...");
        System.out.println("[Takeaway] Order will be ready in 15 minutes");
    }

    @Override
    protected void handlePostPayment(Order order) {
        System.out.println("[Takeaway] Payment processed at counter via " +
                order.getPaymentStrategy().name());
        System.out.println("[Takeaway] Ready for pickup at counter");
    }
}
