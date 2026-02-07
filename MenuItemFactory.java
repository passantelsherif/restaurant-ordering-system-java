import java.util.*;
import java.util.function.Supplier;

// ==============================
// 3) FACTORY & ABSTRACT FACTORY
// ==============================

public interface MenuItemFactory {
    Pizza createPizza(RestaurantApp.PizzaType type);
    Burger createBurger(RestaurantApp.BurgerType type);
    String factoryName();
    
    // Registry methods
    void registerPizza(RestaurantApp.PizzaType type, Supplier<Pizza> supplier);
    void registerBurger(RestaurantApp.BurgerType type, Supplier<Burger> supplier);
}

abstract class AbstractMenuItemFactory implements MenuItemFactory {
    protected Map<RestaurantApp.PizzaType, Supplier<Pizza>> pizzaRegistry = new HashMap<>();
    protected Map<RestaurantApp.BurgerType, Supplier<Burger>> burgerRegistry = new HashMap<>();
    
    @Override
    public Pizza createPizza(RestaurantApp.PizzaType type) {
        Supplier<Pizza> supplier = pizzaRegistry.get(type);
        if (supplier == null) {
            throw new IllegalArgumentException("Pizza type " + type + " not supported in " + factoryName());
        }
        return supplier.get();
    }
    
    @Override
    public Burger createBurger(RestaurantApp.BurgerType type) {
        Supplier<Burger> supplier = burgerRegistry.get(type);
        if (supplier == null) {
            throw new IllegalArgumentException("Burger type " + type + " not supported in " + factoryName());
        }
        return supplier.get();
    }
    
    @Override
    public void registerPizza(RestaurantApp.PizzaType type, Supplier<Pizza> supplier) {
        pizzaRegistry.put(type, supplier);
    }
    
    @Override
    public void registerBurger(RestaurantApp.BurgerType type, Supplier<Burger> supplier) {
        burgerRegistry.put(type, supplier);
    }
}