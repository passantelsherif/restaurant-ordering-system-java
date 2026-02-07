// ==============================
// CONCRETE MENU ITEM CLASSES (Grouped for simplicity)
// ==============================
    
// Vegetarian Pizza classes
class VegItalianPizza extends MenuItem implements Pizza {
    public VegItalianPizza() { super("Veg Italian Pizza", 80); }
}

class VegEasternPizza extends MenuItem implements Pizza {
    public VegEasternPizza() { super("Veg Eastern Pizza", 75); }
}

// Vegetarian Burger classes
class VegBeefBurger extends MenuItem implements Burger {
    public VegBeefBurger() { super("Veg Beef Burger", 65); }
}

// Non-Vegetarian Pizza classes
class NonVegItalianPizza extends MenuItem implements Pizza {
    public NonVegItalianPizza() { super("NonVeg Italian Pizza", 100); }
}

class NonVegEasternPizza extends MenuItem implements Pizza {
    public NonVegEasternPizza() { super("NonVeg Eastern Pizza", 95); }
}

// Non-Vegetarian Burger classes
class BeefBurger extends MenuItem implements Burger {
    public BeefBurger() { super("Beef Burger", 70); }
}

class ChickenBurger extends MenuItem implements Burger {
    public ChickenBurger() { super("Chicken Burger", 60); }
}

// Kids Menu classes
class KidsMiniPizza extends MenuItem implements Pizza {
    public KidsMiniPizza() { super("Kids Mini Pizza", 45); }
}

class KidBurger extends MenuItem implements Burger {
    public KidBurger() { super("Kids Burger", 30); }
}