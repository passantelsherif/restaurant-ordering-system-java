public class NonVegMenuFactory extends AbstractMenuItemFactory {
    public NonVegMenuFactory() {
        registerPizza(RestaurantApp.PizzaType.ITALIAN, NonVegItalianPizza::new);
        registerPizza(RestaurantApp.PizzaType.EASTERN, NonVegEasternPizza::new);
        registerBurger(RestaurantApp.BurgerType.BEEF, BeefBurger::new);
        registerBurger(RestaurantApp.BurgerType.CHICKEN, ChickenBurger::new);
    }
    public String factoryName() { return "NonVegMenuFactory"; }
}