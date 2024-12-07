abstract class Toy {
  public final String name;
  public final int ageGroup;
  public double price;

  /**
   * Constructor
   *
   * @param name
   * @param ageGroup
   * @param price
   */
  public Toy(String name, int ageGroup, double price) {
    this.name = name;
    this.ageGroup = ageGroup;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Toy{ name: " + this.name + ", age group: " + this.ageGroup + ", price: " + Double.toString(this.price;
  }
}
