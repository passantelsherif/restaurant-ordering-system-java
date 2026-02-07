import java.util.*;

public class UserInputHandler {
    private Scanner scanner;
    private RestaurantFacade facade;
    private MenuComposite menu;
    private MenuItemFactory vegFactory;
    private MenuItemFactory nonVegFactory;
    private MenuItemFactory kidsFactory;

    public UserInputHandler(RestaurantFacade facade, MenuComposite menu, 
                          MenuItemFactory veg, MenuItemFactory nonVeg, MenuItemFactory kids) {
        // Use a new Scanner instance to avoid issues with System.in closing in main
        this.scanner = new Scanner(System.in); 
        this.facade = facade;
        this.menu = menu;
        this.vegFactory = veg;
        this.nonVegFactory = nonVeg;
        this.kidsFactory = kids;
    }

    public void startInteractiveMode() {
        System.out.println("\n=== INTERACTIVE MODE ===");
        
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Menu");
            System.out.println("2. Create Custom Order");
            System.out.println("3. Run Predefined Demos");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice = getIntInput(1, 4);
            
            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    createCustomOrder();
                    break;
                case 3:
                    runPredefinedDemos();
                    break;
                case 4:
                    System.out.println("Thank you for using RestaurantApp!");
                    return;
            }
        }
    }

    private void viewMenu() {
        System.out.println("\n----- FULL MENU -----");
        menu.print("");
    }

    private void createCustomOrder() {
        System.out.println("\n----- CREATE CUSTOM ORDER -----");
        
        OrderBuilder builder = new OrderBuilder();
        
        // Select order type
        String orderType = selectOrderType();
        builder.orderType(orderType);
        
        // Add items
        addItemsToOrder(builder);
        
        // Select payment method
        PaymentStrategy payment = selectPaymentMethod();
        builder.payment(payment);
        
        // Select discount
        DiscountStrategy discount = selectDiscount();
        builder.discount(discount);
        
        // Build and process order
        Order order = builder.build();
        System.out.println("\nProcessing your order...");
        facade.placeOrder(order);
    }

    private String selectOrderType() {
        System.out.println("\nSelect Order Type:");
        System.out.println("1. Dine-in");
        System.out.println("2. Delivery");
        System.out.println("3. Takeaway");
        System.out.print("Enter choice (1-3): ");
        
        int choice = getIntInput(1, 3);
        switch (choice) {
            case 1: return "dine-in";
            case 2: return "delivery";
            case 3: return "takeaway";
            default: return "dine-in";
        }
    }

    private void addItemsToOrder(OrderBuilder builder) {
        System.out.println("\nAdding items to order...");
        
        while (true) {
            System.out.println("\nSelect Menu Category:");
            System.out.println("1. Vegetarian");
            System.out.println("2. Non-Vegetarian");
            System.out.println("3. Kids Menu");
            System.out.println("4. Finish Adding Items");
            System.out.print("Enter choice (1-4): ");
            
            int categoryChoice = getIntInput(1, 4);
            if (categoryChoice == 4) break;
            
            MenuItemFactory factory = getFactoryForCategory(categoryChoice);
            if (factory != null) {
                addItemsFromFactory(builder, factory, categoryChoice);
            }
        }
    }

    private MenuItemFactory getFactoryForCategory(int category) {
        switch (category) {
            case 1: return vegFactory;
            case 2: return nonVegFactory;
            case 3: return kidsFactory;
            default: return null;
        }
    }

    private void addItemsFromFactory(OrderBuilder builder, MenuItemFactory factory, int category) {
        System.out.println("\nAvailable Items:");
        
        // Show available items based on factory type
        if (factory instanceof VegMenuFactory) {
            System.out.println("1. Veg Italian Pizza ($80)");
            System.out.println("2. Veg Eastern Pizza ($75)");
            System.out.println("3. Veg Beef Burger ($65)");
        } else if (factory instanceof NonVegMenuFactory) {
            System.out.println("1. NonVeg Italian Pizza ($100)");
            System.out.println("2. NonVeg Eastern Pizza ($95)");
            System.out.println("3. Beef Burger ($70)");
            System.out.println("4. Chicken Burger ($60)");
        } else if (factory instanceof KidsMenuFactory) {
            System.out.println("1. Kids Mini Pizza ($45)");
            System.out.println("2. Kids Burger ($30)");
        }
        
        System.out.print("Select item to add (0 to go back): ");
        int maxChoice = (factory instanceof NonVegMenuFactory) ? 4 : 3;
        int itemChoice = getIntInput(0, maxChoice);
        if (itemChoice == 0) return;
        
        IMenuComponent item = createMenuItem(factory, category, itemChoice);
        if (item != null) {
            // Ask for add-ons
            item = applyAddOns(item);
            builder.addItem(item);
            System.out.println("Added: " + item.getName() + " ($" + item.getPrice() + ")");
        }
    }

    private IMenuComponent createMenuItem(MenuItemFactory factory, int category, int itemChoice) {
        try {
            if (category == 1) { // Vegetarian
                switch (itemChoice) {
                    case 1: return factory.createPizza(RestaurantApp.PizzaType.ITALIAN);
                    case 2: return factory.createPizza(RestaurantApp.PizzaType.EASTERN);
                    case 3: return factory.createBurger(RestaurantApp.BurgerType.VEG_BEEF);
                }
            } else if (category == 2) { // Non-Vegetarian
                switch (itemChoice) {
                    case 1: return factory.createPizza(RestaurantApp.PizzaType.ITALIAN);
                    case 2: return factory.createPizza(RestaurantApp.PizzaType.EASTERN);
                    case 3: return factory.createBurger(RestaurantApp.BurgerType.BEEF);
                    case 4: return factory.createBurger(RestaurantApp.BurgerType.CHICKEN);
                }
            } else if (category == 3) { // Kids
                switch (itemChoice) {
                    case 1: return factory.createPizza(RestaurantApp.PizzaType.MINI);
                    case 2: return factory.createBurger(RestaurantApp.BurgerType.KID);
                }
            }
        } catch (Exception e) {
            System.out.println("Error creating menu item: " + e.getMessage());
        }
        return null;
    }

    private IMenuComponent applyAddOns(IMenuComponent baseItem) {
        System.out.println("\nWould you like to add extras?");
        System.out.println("1. Extra Cheese (+$10)");
        System.out.println("2. Extra Sauce (+$5)");
        System.out.println("3. Extra Topping (+$15)");
        System.out.println("4. No extras");
        System.out.print("Enter choice (1-4): ");
        
        int addOnChoice = getIntInput(1, 4);
        
        switch (addOnChoice) {
            case 1: return new ExtraCheese(baseItem);
            case 2: return new ExtraSauce(baseItem);
            case 3: return new ExtraTopping(baseItem);
            default: return baseItem;
        }
    }

    private PaymentStrategy selectPaymentMethod() {
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. Mobile Wallet");
        System.out.print("Enter choice (1-3): ");
        
        int choice = getIntInput(1, 3);
        switch (choice) {
            case 1: return new CashPayment();
            case 2: return new CardPayment();
            case 3: return new MobileWalletPayment();
            default: return new CashPayment();
        }
    }

    private DiscountStrategy selectDiscount() {
        System.out.println("\nSelect Discount (if applicable):");
        System.out.println("1. Pizza Discount (10% on pizzas)");
        System.out.println("2. Meat Discount (5% on meat burgers)");
        System.out.println("3. No Discount");
        System.out.print("Enter choice (1-3): ");
        
        int choice = getIntInput(1, 3);
        switch (choice) {
            case 1: return new PizzaDiscount();
            case 2: return new MeatDiscount();
            case 3: return new NoDiscount();
            default: return new NoDiscount();
        }
    }

    private void runPredefinedDemos() {
        System.out.println("\n----- PREDEFINED DEMOS -----");
        System.out.println("1. Dine-in Order (Non-Veg)");
        System.out.println("2. Delivery Order (Kids + Non-Veg)");
        System.out.println("3. Takeaway Order (Vegetarian)");
        System.out.print("Select demo (1-3): ");
        
        int choice = getIntInput(1, 3);
        
        // Re-implementing demo logic locally or calling into the main class methods
        // Since we cannot call static methods in RestaurantApp directly from an instance method,
        // we'll just inform the user to run the main Demo Mode or keep the simplified logic here.
        System.out.println("\n[INFO] Running demo logic now (Simplified for interactive mode)...");

        // The interactive demo logic will need to be written out here or in a separate DemoRunner class
        // For simplicity in this structure, we'll just show the steps:
        
        Order demoOrder = null;
        if (choice == 1) {
            Pizza nonvegItalian = nonVegFactory.createPizza(RestaurantApp.PizzaType.ITALIAN);
            IMenuComponent nonvegItalianFull = new ExtraTopping(nonvegItalian);
            Burger beefBurger = nonVegFactory.createBurger(RestaurantApp.BurgerType.BEEF);
            IMenuComponent beefBurgerWithCheese = new ExtraCheese(beefBurger);
            demoOrder = new OrderBuilder()
                    .addItem(nonvegItalianFull)
                    .addItem(beefBurgerWithCheese)
                    .orderType("dine-in")
                    .payment(new CashPayment())
                    .discount(new PizzaDiscount())
                    .build();
        } else if (choice == 2) {
            Pizza kidsPizza = kidsFactory.createPizza(RestaurantApp.PizzaType.MINI);
            IMenuComponent kidsPizzaWithExtras = new ExtraSauce(new ExtraCheese(kidsPizza));
            Burger chickenBurger = nonVegFactory.createBurger(RestaurantApp.BurgerType.CHICKEN);
            IMenuComponent chickenBurgerWithSauce = new ExtraSauce(chickenBurger);
            demoOrder = new OrderBuilder()
                    .addItem(kidsPizzaWithExtras)
                    .addItem(chickenBurgerWithSauce)
                    .orderType("delivery")
                    .payment(new CardPayment())
                    .discount(new MeatDiscount())
                    .build();
        } else if (choice == 3) {
            Pizza vegItalian = vegFactory.createPizza(RestaurantApp.PizzaType.ITALIAN);
            IMenuComponent vegItalianWithExtras = new ExtraCheese(vegItalian); 
            Burger vegBeefBurger = vegFactory.createBurger(RestaurantApp.BurgerType.VEG_BEEF);
            demoOrder = new OrderBuilder()
                    .addItem(vegItalianWithExtras)
                    .addItem(vegBeefBurger)
                    .orderType("takeaway")
                    .payment(new MobileWalletPayment())
                    .discount(new NoDiscount())
                    .build();
        }
        
        if (demoOrder != null) {
            System.out.println("\nProcessing selected demo order...");
            facade.placeOrder(demoOrder);
        }
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                // Check if scanner has another token before trying to read it as int
                if (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.nextLine(); // clear invalid input
                    continue;
                }
                
                int input = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (InputMismatchException e) {
                // This catch block is mostly redundant due to the hasNextInt check,
                // but kept for robustness.
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
}