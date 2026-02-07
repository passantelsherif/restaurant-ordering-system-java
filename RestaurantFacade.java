public class RestaurantFacade {
    private OrderRepository repo;
    private OrderNotifier notifier;

    public RestaurantFacade(OrderRepository repo, OrderNotifier notifier) {
        this.repo = repo;
        this.notifier = notifier;
    }

    private OrderProcessTemplate getOrderProcessor(String orderType) {
        switch (orderType.toLowerCase()) {
            case "dine-in": return new DineInOrderProcess(repo, notifier);
            case "delivery": return new DeliveryOrderProcess(repo, notifier);
            case "takeaway": return new TakeawayOrderProcess(repo, notifier);
            default: throw new IllegalArgumentException("Unknown order type: " + orderType);
        }
    }

    public MenuComposite buildMainMenu(MenuItemFactory veg, MenuItemFactory nonveg, MenuItemFactory kids) {
        MenuComposite main = new MenuComposite("Main Menu");

        MenuComposite vegMenu = new MenuComposite("Vegetarian Menu");
        try {
            vegMenu.add(veg.createPizza(RestaurantApp.PizzaType.ITALIAN));
            vegMenu.add(veg.createPizza(RestaurantApp.PizzaType.EASTERN));
            vegMenu.add(veg.createBurger(RestaurantApp.BurgerType.VEG_BEEF));
        } catch (Exception e) { }

        MenuComposite nonVegMenu = new MenuComposite("Non-Vegetarian Menu");
        try {
            nonVegMenu.add(nonveg.createPizza(RestaurantApp.PizzaType.ITALIAN));
            nonVegMenu.add(nonveg.createPizza(RestaurantApp.PizzaType.EASTERN));
            nonVegMenu.add(nonveg.createBurger(RestaurantApp.BurgerType.BEEF));
            nonVegMenu.add(nonveg.createBurger(RestaurantApp.BurgerType.CHICKEN));
        } catch (Exception e) { }

        MenuComposite kidsMenu = new MenuComposite("Kids Menu");
        try {
            kidsMenu.add(kids.createPizza(RestaurantApp.PizzaType.MINI));
            kidsMenu.add(kids.createBurger(RestaurantApp.BurgerType.KID));
        } catch (Exception e) { }

        main.add(vegMenu);
        main.add(nonVegMenu);
        main.add(kidsMenu);
        return main;
    }

    public void placeOrder(Order order) {
        OrderProcessTemplate processor = getOrderProcessor(order.getOrderType());
        processor.processOrder(order);
    }
}
