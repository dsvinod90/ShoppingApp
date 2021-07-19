import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Shop {
  private final String name;
  private final long phoneNumber;
  private final HashMap<Item, Integer> stock;
  private final Set<String> locations;

  public Shop(String name, long phoneNumber, Set<String> locations) {
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.stock = new HashMap<>();
    this.locations = new HashSet<>(locations);
  }

  public String getName() {
    return name;
  }

  public long getPhoneNumber() {
    return phoneNumber;
  }

  public HashMap<Item, Integer> getStock() {
    return new HashMap<>(stock);
  }

  public Set<String> getLocations() {
    return new HashSet<>(locations);
  }

  public boolean modifyStock(String name, int quantity) {
    Item item = findItem(name);
    int existingQuantity = findItemQuantity(name);
    if(item != null) {
      return(stock.replace(item, existingQuantity, quantity));
    }
    return false;
  }

  public boolean addStock(String name, float pricePerUnit, int quantity) {
    if(findItem(name) != null) {
      return false;
    } else {
      stock.put(new Item(name, pricePerUnit), quantity);
      return true;
    }
  }

  public boolean removeStock(String name) {
    Item item = findItem(name);
    if(item != null) {
      stock.remove(item);
      return true;
    }
    return false;
  }

  public boolean sellStock(String name, int quantitySold) {
    Item item = findItem(name);
    int existingQuantity = findItemQuantity(name);
    int remainingQuantity = existingQuantity - quantitySold;
    return stock.replace(item, existingQuantity, remainingQuantity);
  }

  public void viewStock() {
    float sum = 0.00f;
    for(Map.Entry<Item, Integer> entry : stock.entrySet()) {
      Item item = entry.getKey();
      int quantity = entry.getValue();
      sum += (item.getPricePerUnit() * quantity);
      System.out.println("\tItem Name: " + item.getName() + "\n" +
          "\tPrice per unit of item: $" + item.getPricePerUnit() + "\n" +
          "\tQuantity of items in stock: " + quantity + "\n" +
          "\t\tSubtotal cost: $" + String.format("%.2f", item.getPricePerUnit() * quantity)
      );
      System.out.println();
    }
    System.out.println("Total inventory cost of present items: $" + String.format("%.2f", sum));
  }

  public Item findItem(String name) {
    Map.Entry<Item, Integer> entry = findEntry(name);
    if(entry != null) {
      return entry.getKey();
    }
    return null;
  }

  public int findItemQuantity(String name) {
    Map.Entry<Item, Integer> entry = findEntry(name);
    if(entry != null) {
      return entry.getValue();
    }
    return -1;
  }


  private Map.Entry<Item, Integer> findEntry(String name) {
    for(Map.Entry<Item, Integer> entry : stock.entrySet()) {
      if(entry.getKey().getName().equalsIgnoreCase(name)) {
        return entry;
      }
    }
    return null;
  }
}
