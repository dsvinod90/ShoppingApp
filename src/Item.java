public final class Item {
  private final String name;
  private float pricePerUnit;

  public Item(String name, float pricePerUnit) {
    this.name = name;
    this.pricePerUnit = pricePerUnit;
  }

  public String getName() {
    return name;
  }

  public float getPricePerUnit() {
    return pricePerUnit;
  }
}
