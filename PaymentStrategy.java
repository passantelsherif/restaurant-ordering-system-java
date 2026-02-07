// ==============================
// 5) STRATEGY: Payment
// ==============================
public interface PaymentStrategy {
    void pay(double amount);
    String name();
}

class CashPayment implements PaymentStrategy {
    public void pay(double amount) { System.out.println("[Payment][Cash] Received cash: $" + amount); }
    public String name() { return "Cash"; }
}

class CardPayment implements PaymentStrategy {
    public void pay(double amount) { System.out.println("[Payment][Card] Processed card payment: $" + amount); }
    public String name() { return "Card"; }
}

class MobileWalletPayment implements PaymentStrategy {
    public void pay(double amount) { System.out.println("[Payment][MobileWallet] Wallet charged: $" + amount); }
    public String name() { return "MobileWallet"; }
}