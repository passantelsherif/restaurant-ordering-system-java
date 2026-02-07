import java.util.*;

public class RestaurantApp {

    // ==============================
    // ENUM Definitions (Must remain here or in a separate file if needed globally)
    // ==============================
    public enum PizzaType { ITALIAN, EASTERN, MINI }
    public enum BurgerType { BEEF, CHICKEN, KID, VEG_BEEF }

    // ==============================
    // MAIN
    // ==============================
    public static void main(String[] args) {
        System.out.println("=== RestaurantApp ===");

        // Setup
        MenuItemFactory vegFactory = new VegMenuFactory();
        MenuItemFactory nonVegFactory = new NonVegMenuFactory();
        MenuItemFactory kidsFactory = new KidsMenuFactory();

        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        OrderNotifier notifier = new OrderNotifier();
        notifier.addObserver(new Kitchen());
        notifier.addObserver(new Waiter());
        RestaurantFacade facade = new RestaurantFacade(repo, notifier);

        // Build menu
        MenuComposite menu = facade.buildMainMenu(vegFactory, nonVegFactory, kidsFactory);

        // Ask user for mode
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose mode:");
        System.out.println("1. Interactive Mode (User Input)");
        System.out.println("2. Demo Mode (Predefined Examples)");
        System.out.print("Enter choice (1 or 2): ");
        
        int mode = 0;
        try {
            mode = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Defaulting to Demo Mode.");
            mode = 2;
        }
        scanner.nextLine(); // consume newline
        
        if (mode == 1) {
            // Interactive mode
            UserInputHandler inputHandler = new UserInputHandler(facade, menu, vegFactory, nonVegFactory, kidsFactory);
            inputHandler.startInteractiveMode();
        } else {
            // Demo mode (original functionality)
            runDemoMode(facade, menu, vegFactory, nonVegFactory, kidsFactory);
        }
        
        scanner.close();
    }

    // ==============================
    // DEMO HELPER METHODS (Kept in main class)
    // ==============================

    private static void runDemoMode(RestaurantFacade facade, MenuComposite menu, 
                                  MenuItemFactory vegFactory, MenuItemFactory nonVegFactory, 
                                  MenuItemFactory kidsFactory) {
        System.out.println("\n=== DEMO MODE ===");

        // Build and print menu
        System.out.println("\n----- MENU -----");
        menu.print("");

        // Demo different order types
        System.out.println("\n----- DEMO 1: DINE-IN ORDER -----");
        demoDineInOrder(facade, nonVegFactory);

        System.out.println("\n----- DEMO 2: DELIVERY ORDER -----");
        demoDeliveryOrder(facade, kidsFactory, nonVegFactory);

        System.out.println("\n----- DEMO 3: TAKEAWAY ORDER -----");
        demoTakeawayOrder(facade, vegFactory);

        System.out.println("\n===== All Demos Completed =====");
    }

    private static void demoDineInOrder(RestaurantFacade facade, MenuItemFactory nonVegFactory) {
        OrderBuilder builder = new OrderBuilder();

        Pizza nonvegItalian = nonVegFactory.createPizza(PizzaType.ITALIAN);
        IMenuComponent nonvegItalianFull = new ExtraTopping(nonvegItalian);

        Burger beefBurger = nonVegFactory.createBurger(BurgerType.BEEF);
        IMenuComponent beefBurgerWithCheese = new ExtraCheese(beefBurger);

        Order order = builder
                .addItem(nonvegItalianFull)
                .addItem(beefBurgerWithCheese)
                .orderType("dine-in")
                .payment(new CashPayment())
                .discount(new PizzaDiscount())
                .build();

        facade.placeOrder(order);
    }

    private static void demoDeliveryOrder(RestaurantFacade facade, MenuItemFactory kidsFactory, MenuItemFactory nonVegFactory) {
        OrderBuilder builder = new OrderBuilder();

        Pizza kidsPizza = kidsFactory.createPizza(PizzaType.MINI);
        IMenuComponent kidsPizzaWithExtras = new ExtraSauce(new ExtraCheese(kidsPizza));

        Burger chickenBurger = nonVegFactory.createBurger(BurgerType.CHICKEN);
        IMenuComponent chickenBurgerWithSauce = new ExtraSauce(chickenBurger);

        Order order = builder
                .addItem(kidsPizzaWithExtras)
                .addItem(chickenBurgerWithSauce)
                .orderType("delivery")
                .payment(new CardPayment())
                .discount(new MeatDiscount())
                .build();

        facade.placeOrder(order);
    }
    
    private static void demoTakeawayOrder(RestaurantFacade facade, MenuItemFactory vegFactory) {
        OrderBuilder builder = new OrderBuilder();

        Pizza vegItalian = vegFactory.createPizza(PizzaType.ITALIAN);
        IMenuComponent vegItalianWithExtras = new ExtraCheese(vegItalian); 

        Burger vegBeefBurger = vegFactory.createBurger(BurgerType.VEG_BEEF);

        Order order = builder
                .addItem(vegItalianWithExtras)
                .addItem(vegBeefBurger)
                .orderType("takeaway")
                .payment(new MobileWalletPayment())
                .discount(new NoDiscount())
                .build();

        facade.placeOrder(order);
    }
}