// Leaf item
public class MenuItem implements IMenuComponent {
    private String name;
    private double price;
    public MenuItem(String name, double price) { this.name = name; this.price = price; }
    @Override public String getName() { return name; }
    @Override public double getPrice() { return price; }
    @Override public void print(String indent) { System.out.println(indent + "- " + name + " ($" + price + ")"); }
}