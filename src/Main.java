import java.util.*;

public class Main {
  private static final Scanner scanner = new Scanner(System.in);

  private static void printRoleMenu() {
    System.out.println("Options for role:\n" +
        "\t1 - Shop Manager\n" +
        "\t2 - Customer\n" +
        "\t3 - Quit application"
    );
  }

  private static void printManagerMenu() {
    System.out.println("Your options as a manager:\n" +
        "\t1 - Add Stock\n" +
        "\t2 - Modify Stock\n" +
        "\t3 - Remove Stock\n" +
        "\t4 - View Stock\n" +
        "\t5 - View Locations\n" +
        "\t6 - Return to Main Menu"
    );
  }

  private static void printCustomerMenu() {
    System.out.println("Your options as a customer:\n" +
        "\t1 - Add to basket\n" +
        "\t2 - Modify basket\n" +
        "\t3 - Remove from basket\n" +
        "\t4 - View basket items\n" +
        "\t5 - Proceed to checkout\n" +
        "\t6 - View Locations of the shop\n" +
        "\t7 - Return to Main Menu"
    );
  }

  private static void printShopDetails(Shop shop) {
    System.out.println("Shop has been set and here are the details:\n" +
        "\tName: " + shop.getName() + "\n" +
        "\tPhone Number: " + shop.getPhoneNumber() + "\n" +
        "\tLocations: " + shop.getLocations()
    );
  }

  private static void addItemsToStock(Shop shop) {
    System.out.print("How many items do you want to add to stock?: ");
    int count = scanner.nextInt();
    scanner.nextLine();
    for(int i = 1; i <= count; i++) {
      System.out.println("Enter details for item # " + i);
      System.out.print("Name: ");
      String name = scanner.nextLine();
      System.out.print("Price per unit: ");
      float cost = scanner.nextFloat();
      System.out.print("Number of units: ");
      int quantity = scanner.nextInt();
      scanner.nextLine();
      if(shop.addStock(name, cost, quantity)) {
        System.out.println(quantity + " units of " + name + " @ $" + cost + " per unit have been added to stock");
      } else {
        System.out.println(name + " already exists in stock. Please modify the existing stock.");
      }
      System.out.println();
    }
  }

  private static void modifyExistingStock(Shop shop) {
    System.out.println("Enter the details of the item to be modified: ");
    scanner.nextLine();
    System.out.print("Name: ");
    String name = scanner.nextLine();
    System.out.print("New Quantity: ");
    int newQuantity = scanner.nextInt();
    if(shop.modifyStock(name, newQuantity)) {
      System.out.println("Modified existing stock:\n" +
          "\tName: " + name + "\n" +
          "\tNew Quantity: " + newQuantity
      );
    } else {
      System.out.println(name + " does not exist. Please add new stock.");
    }
    System.out.println();
  }

  private static void removeStock(Shop shop) {
    System.out.println("Enter details of the item to be removed from stock: ");
    scanner.nextLine();
    System.out.print("Name: ");
    String name = scanner.nextLine();
    if(shop.removeStock(name)) {
      System.out.println(name + " has been removed from stock.");
    } else {
      System.out.println(name + " is not present in the stock.");
    }
    System.out.println();
  }

  private static void addItemsToBasket(Customer customer, Shop shop) {
    System.out.print("How many items do you want to buy?: ");
    int count = scanner.nextInt();
    scanner.nextLine();
    for(int i = 0; i < count; i++) {
      System.out.print("Enter item name: ");
      String name = scanner.nextLine();
      System.out.print("Enter quantity of item: ");
      int quantity = scanner.nextInt();
      scanner.nextLine();
      Item item = shop.findItem(name);
      if(item != null && shop.findItemQuantity(name) >= quantity) {
        if(customer.addToBasket(item, quantity)) {
          System.out.println(quantity + " units of " + name + " have been added to basket.");
        } else {
          System.out.println(name + " already present in basket. Please modify your basket.");
          i--;
        }
      } else {
        System.out.println("Item not present or stock not available.");
        i--;
      }
    }
  }

  private static void modifyItemsInBasket(Customer customer, Shop shop) {
    System.out.print("Enter the name of the item you would like to modify: ");
    scanner.nextLine();
    String name = scanner.nextLine();
    System.out.print("Enter the quantity of items required: ");
    int quantity = scanner.nextInt();
    Item item = shop.findItem(name);
    if(item != null && shop.findItemQuantity(name) >= quantity) {
      int previousQuantity = customer.findItemQuantity(name);
      if(customer.modifyBasketItem(name, quantity)) {
        System.out.println("Modified Basket:\n" +
            "\tItem name: " + name + "\n" +
            "\tPrevious quantity: " + previousQuantity + "\n" +
            "\tNew Quantity: " + quantity
        );
        System.out.println();
      } else {
        System.out.println(name + " is not present in the basket. Please add this item.");
      }
    } else {
      System.out.println(name + " is not present in the basket or stock is not available.");
    }
  }

  private static void removeItemsFromBasket(Customer customer) {
    System.out.print("Enter the name of the item you want to remove: ");
    scanner.nextLine();
    String name = scanner.nextLine();
    Item item = customer.findItem(name);
    if(item != null) {
      if(customer.removeFromBasket(item)) {
        System.out.println(name + " has been successfully removed from basket.");
      } else {
        System.out.println(name + " not present in the basket.");
      }
    } else {
      System.out.println(name + " not present in the basket.");
    }
  }

  private static void proceedToCheckout(Customer customer, Shop shop) {
    float sum = 0.00f;
    System.out.println("======================================================================================");
    System.out.println();
    System.out.println("******************** " + shop.getName().toUpperCase() + " ********************");
    System.out.println("  This is an electronically generated bill  ");
    System.out.println();
    System.out.println("Customer Details:\n" +
        "\t Name: " + customer.getName() + "\n" +
        "\t Mobile Number: " + customer.getMobileNumber()
    );
    System.out.println();
    System.out.println("List of items purchased: ");
    System.out.println();
    for(Map.Entry<Item, Integer> entry : customer.getBasket().entrySet()) {
      Item item = entry.getKey();
      int quantity = entry.getValue();
      sum += item.getPricePerUnit() * quantity;
      if(shop.sellStock(item.getName(), quantity)) {
        System.out.println("\tItem name: " + item.getName() + "\n" +
            "\tCost per unit item: " + item.getPricePerUnit() + "\n" +
            "\tQuantity of items: " + quantity + "\n" +
            "\tSubtotal: $" + String.format("%.2f", item.getPricePerUnit() * quantity)
        );
        System.out.println();
      }
    }
    System.out.println("Total amount payable: $" + String.format("%.2f", sum));
    System.out.println();
    System.out.println(
        "******** " +
        "Call us @ " + shop.getPhoneNumber() + "\t" + "|" + "\t" +
        "We are now in " + shop.getLocations().toString() +
        " ********"
    );
    System.out.println("======================================================================================");
    System.out.println();
  }

  public static void main(String[] args) {
    System.out.println("Welcome to shopping in CLI");
    System.out.println("Here you are the customer and the shop manager. Before we begin, you have to name your shop and give some details.");
    System.out.println();
    System.out.print("Enter the name of the shop: ");
    Set<String> locations = new HashSet<>();
    String shopName = scanner.nextLine();
    System.out.print("Enter the shop's phone number: ");
    long shopNumber = scanner.nextLong();
    System.out.print("How many locations of this branch exist?: ");
    int locationCount = scanner.nextInt();
    scanner.nextLine();
    int count = 1;
    while(count <= locationCount) {
      System.out.print("Enter location #" + count + ": ");
      if(scanner.hasNextLine()) {
        locations.add(scanner.nextLine());
        count++;
      }
    }
    Shop shop = new Shop(shopName, shopNumber, locations);
    Customer customer = null;
    System.out.println();
    printShopDetails(shop);
    System.out.println();
    printRoleMenu();
    int option;
    boolean exit = false;
    while(!exit) {
      System.out.print("Enter an option: ");
      option = scanner.nextInt();
      System.out.println();
      switch(option) {
        case 1:
          System.out.println("You are the Shop Manager now");
          int managerChoice;
          boolean backToMainMenuForManager = false;
          printManagerMenu();
          while(!backToMainMenuForManager) {
            System.out.print("Enter your choice: ");
            managerChoice = scanner.nextInt();
            switch(managerChoice) {
              case 1:
                System.out.println("Adding stock: ");
                addItemsToStock(shop);
                printManagerMenu();
                break;
              case 2:
                System.out.println("Modifying existing stock: ");
                modifyExistingStock(shop);
                printManagerMenu();
                break;
              case 3:
                System.out.println("Removing stock: ");
                removeStock(shop);
                printManagerMenu();
                break;
              case 4:
                System.out.println("Viewing stock: ");
                shop.viewStock();
                printManagerMenu();
                break;
              case 5:
                System.out.println("Viewing other shop locations: ");
                System.out.println(shop.getLocations());
                System.out.println("-----------------------------------");
                printManagerMenu();
                break;
              case 6:
                backToMainMenuForManager = true;
                break;
            }
          }
          printRoleMenu();
          break;
        case 2:
          System.out.println("You are the Customer now");
          scanner.nextLine();
          if(customer == null) {
            System.out.println("Capturing customer details: ");
            System.out.print("\tEnter Name: ");
            String customerName = scanner.nextLine();
            System.out.print("\tEnter mobile number: ");
            long customerMobile = scanner.nextLong();
            customer = new Customer(customerName, customerMobile);
          }
          int customerChoice;
          boolean backToMainMenuForCustomer = false;
          printCustomerMenu();
          while(!backToMainMenuForCustomer) {
            System.out.print("Enter your choice: ");
            customerChoice = scanner.nextInt();
            switch(customerChoice) {
              case 1:
                System.out.println("Adding to your basket: ");
                addItemsToBasket(customer, shop);
                printCustomerMenu();
                break;
              case 2:
                System.out.println("Modifying items in your basket: ");
                modifyItemsInBasket(customer, shop);
                printCustomerMenu();
                break;
              case 3:
                System.out.println("Removing items from your basket: ");
                removeItemsFromBasket(customer);
                printCustomerMenu();
                break;
              case 4:
                System.out.println("Viewing items in your basket: ");
                customer.viewBasketItems();
                printCustomerMenu();
                break;
              case 5:
                System.out.println("Checking out items: ");
                proceedToCheckout(customer, shop);
                printCustomerMenu();
              case 6:
                System.out.println("Viewing other shop locations: ");
                System.out.println(shop.getLocations());
                System.out.println("-----------------------------------");
                printCustomerMenu();
                break;
              case 7:
                backToMainMenuForCustomer = true;
                break;
            }
          }
          printRoleMenu();
          break;
        case 3:
          exit = true;
      }
    }
    scanner.close();
  }
}
