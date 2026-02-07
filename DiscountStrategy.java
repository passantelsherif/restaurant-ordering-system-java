public interface DiscountStrategy {
    double applyDiscount(Order order);
    String name();
}

class PizzaDiscount implements DiscountStrategy {
    public double applyDiscount(Order order) {
        double pizzasTotal = 0;
        for (IMenuComponent it : order.getItems()) {
            if (isPizza(it)) {
                pizzasTotal += getBasePrice(it);
            }
        }
        return pizzasTotal * 0.10;
    }

    private boolean isPizza(IMenuComponent component) {
        if (component instanceof Pizza) {
            return true;
        }
        if (component instanceof AddOnDecorator) {
            AddOnDecorator decorator = (AddOnDecorator) component;
            return isPizza(decorator.base);
        }
        return false;
    }

    private double getBasePrice(IMenuComponent component) {
        if (component instanceof AddOnDecorator) {
            AddOnDecorator decorator = (AddOnDecorator) component;
            return getBasePrice(decorator.base);
        }
        return component.getPrice();
    }

    public String name() { return "PizzaDiscount(10%)"; }
}

class MeatDiscount implements DiscountStrategy {
    public double applyDiscount(Order order) {
        double meatTotal = 0;
        for (IMenuComponent it : order.getItems()) {
            if (isMeatBurger(it)) {
                meatTotal += getBasePrice(it);
            }
        }
        return meatTotal * 0.05;
    }

    private boolean isMeatBurger(IMenuComponent component) {
        if (component instanceof Burger) {
            String name = getBaseName(component).toLowerCase();
            return name.contains("beef") || name.contains("chicken");
        }
        if (component instanceof AddOnDecorator) {
            AddOnDecorator decorator = (AddOnDecorator) component;
            return isMeatBurger(decorator.base);
        }
        return false;
    }

    private String getBaseName(IMenuComponent component) {
        if (component instanceof AddOnDecorator) {
            AddOnDecorator decorator = (AddOnDecorator) component;
            return getBaseName(decorator.base);
        }
        return component.getName();
    }

    private double getBasePrice(IMenuComponent component) {
        if (component instanceof AddOnDecorator) {
            AddOnDecorator decorator = (AddOnDecorator) component;
            return getBasePrice(decorator.base);
        }
        return component.getPrice();
    }

    public String name() { return "MeatDiscount(5%)"; }
}

class NoDiscount implements DiscountStrategy {
    public double applyDiscount(Order order) { return 0; }
    public String name() { return "NoDiscount"; }
}
