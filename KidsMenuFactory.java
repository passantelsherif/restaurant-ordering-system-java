public class KidsMenuFactory extends AbstractMenuItemFactory {
    public KidsMenuFactory() {
        registerPizza(RestaurantApp.PizzaType.MINI, KidsMiniPizza::new);
        registerBurger(RestaurantApp.BurgerType.KID, KidBurger::new);
    }
    public String factoryName() { return "KidsMenuFactory"; }
}