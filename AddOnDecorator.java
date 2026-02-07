// ==============================
// 2) DECORATOR (Add-ons) 
// ==============================
public abstract class AddOnDecorator implements IMenuComponent {
    protected IMenuComponent base;
    public AddOnDecorator(IMenuComponent base) {
        if (base instanceof MenuComposite) {
            throw new IllegalArgumentException("Decorators must wrap leaf MenuItem only.");
        }
        this.base = base;
    }
    @Override public String getName() { return base.getName() + " + " + getAddOnName(); }
    @Override public double getPrice() { return base.getPrice() + getAddOnPrice(); }
    public abstract String getAddOnName();
    public abstract double getAddOnPrice();
    @Override public void print(String indent) {
        System.out.println(indent + "- " + getName() + " ($" + getPrice() + ")");
    }
}

class ExtraCheese extends AddOnDecorator {
    public ExtraCheese(IMenuComponent base) { super(base); }
    public String getAddOnName() { return "Extra Cheese"; }
    public double getAddOnPrice() { return 10; }
}

class ExtraSauce extends AddOnDecorator {
    public ExtraSauce(IMenuComponent base) { super(base); }
    public String getAddOnName() { return "Extra Sauce"; }
    public double getAddOnPrice() { return 5; }
}

class ExtraTopping extends AddOnDecorator {
    public ExtraTopping(IMenuComponent base) { super(base); }
    public String getAddOnName() { return "Extra Topping"; }
    public double getAddOnPrice() { return 15; }
}