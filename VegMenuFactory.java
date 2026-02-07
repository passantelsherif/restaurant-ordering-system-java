public class VegMenuFactory extends AbstractMenuItemFactory {
    public VegMenuFactory() {
        // Register supported types
        registerPizza(RestaurantApp.PizzaType.ITALIAN, VegItalianPizza::new);
        registerPizza(RestaurantApp.PizzaType.EASTERN, VegEasternPizza::new);
        registerBurger(RestaurantApp.BurgerType.VEG_BEEF, VegBeefBurger::new);
    }
    public String factoryName() { return "VegMenuFactory"; }
}