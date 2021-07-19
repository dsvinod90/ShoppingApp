import java.util.HashMap;
import java.util.Map;

public final class Customer {
  private final String name;
  private final long mobileNumber;
  private final Map<Item, Integer> basket;

  public Customer(String name, long mobileNumber) {
    this.name = name;
    this.mobileNumber = mobileNumber;
    this.basket = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public long getMobileNumber() {
    return mobileNumber;
  }

  public Map<Item, Integer> getBasket() {
    return basket;
  }

  public boolean modifyBasketItem(String name, int quantity) {
    Map.Entry<Item, Integer> entry = findEntry(name);
    if(entry == null) return false;
    return basket.replace(entry.getKey(), entry.getValue(), quantity);
  }

  public boolean addToBasket(Item item, int quantity) {
    Map.Entry<Item, Integer> entry = findEntry(item.getName());
    if(entry == null) {
      basket.put(item, quantity);
      return true;
    } else {
      return false;
    }
  }

  public boolean removeFromBasket(Item item) {
    Map.Entry<Item, Integer> entry = findEntry(item.getName());
    if(entry != null) {
      basket.remove(item);
      return true;
    } else {
      return false;
    }
  }

  public void viewBasketItems() {
    for(Map.Entry<Item, Integer> entry : basket.entrySet()) {
      Item item = entry .getKey();
      int quantity = entry.getValue();
      System.out.println("\tItem Name: " + item.getName() + "\n" +
          "\tPrice per unit: $" + item.getPricePerUnit() + "\n" +
          "\tQuantity of items: " + quantity + "\n" +
          "\tSubtotal: $" + String.format("%.2f", item.getPricePerUnit() * quantity)
      );
      System.out.println();
    }

  }

  public Item findItem(String name) {
    Map.Entry<Item, Integer> entry = findEntry(name);
    if(entry != null) {
      return entry.getKey();
    } else {
      return null;
    }
  }

  public int findItemQuantity(String name) {
    Map.Entry<Item, Integer> entry = findEntry(name);
    if(entry != null) {
      return entry.getValue();
    } else {
      return -1;
    }
  }

  private Map.Entry<Item, Integer> findEntry(String name) {
    for(Map.Entry<Item, Integer> entry : basket.entrySet()) {
      if (entry.getKey().getName().equalsIgnoreCase(name)) {
        return entry;
      }
    }
    return null;
  }
}
