package entity;

public class Stationery {
    private Integer id;
    private String name;
    private String type;
    private Double price;
    private Integer num_in_box;

    public Stationery() {}

    public Stationery(Integer id, String name, String type, Double mass, Integer num_in_box) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = mass;
        this.num_in_box = num_in_box;
    }

    public Stationery(String name, String type, Double price, Integer num_in_box) {
        this.id = 0;
        this.name = name;
        this.type = type;
        this.price = price;
        this.num_in_box = num_in_box;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getNum_in_box() {
        return num_in_box;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNum_in_box(Integer num_in_box) {
        this.num_in_box = num_in_box;
    }

    @Override
    public String toString() {
        return "Stationery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", num_in_box=" + num_in_box +
                '}';
    }
}
