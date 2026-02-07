# 🍽️ Restaurant Ordering System

A Java-based restaurant management system built with modular, design-pattern-heavy architecture.  
Handles menu creation, order customization, discounts, payment processing, and staff notifications.

---

## 📁 Project Structure

The project follows a clean, pattern-oriented architecture with the following key patterns:

| Pattern              | Purpose                                            |
|----------------------|----------------------------------------------------|
| **Composite**        | Menu hierarchy management                          |
| **Decorator**        | Add-ons system (extra cheese, sauce, etc.)         |
| **Abstract Factory** | Creates families of related menu items             |
| **Strategy**         | Implements payment and discount strategies         |
| **Template Method**  | Defines order processing workflows                 |
| **Builder**          | Constructs complex order objects                   |
| **Observer**         | Notifies kitchen and waiter when orders are placed |
| **Facade**           | Simplifies client interaction with the system      |

---

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or later

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/passantelsherif/restaurant-ordering-system-java.git
   cd restaurant-ordering-system-java
2. **Compile all Java files:**
     ```bash
   javac *.java
3. **Run the application:**
    ```bash
   java RestaurantApp
4. **Choose mode:**
   **1: Interactive Mode** – Build custom orders
   **2: Demo Mode** – Run automated test scenarios

## Authors
**.** Passant Shaaban
**.** Sandy Emad
