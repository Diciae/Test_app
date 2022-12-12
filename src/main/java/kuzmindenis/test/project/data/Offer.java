package kuzmindenis.test.project.data;

public class Offer {

    public final long id;
    public final  String name;
    public final  String model;
    public final  double price;
    public final  String url;

    public Offer(long id, String name, String model, double price, String url) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = price;
        this.url = url;
    }
}
