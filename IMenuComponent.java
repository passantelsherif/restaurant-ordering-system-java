// ==============================
// 1) COMPOSITE (Menu + MenuItem)
// ==============================
public interface IMenuComponent {
    String getName();
    double getPrice();
    void print(String indent);
}

// Marker interfaces for product categories (for clarity)
interface Pizza extends IMenuComponent {}
interface Burger extends IMenuComponent {}