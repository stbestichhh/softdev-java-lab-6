class Program {
  public static void main(String[] args) {
    Toy toy1 = new Toy("Car", 3, 10.99) {};
    Toy toy2 = new Toy("Doll", 5, 15.49) {};
    Toy toy3 = new Toy("Blocks", 2, 19.99) {};

    ToyList<Toy> collection = new ToyList<>();
    collection.add(toy1);
    collection.add(toy2);
    collection.add(toy3);

    System.out.println("Collection size: " + collection.size());
    for (Toy toy : collection) {
      System.out.println(toy);
    }
  }
}
