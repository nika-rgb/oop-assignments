package Product;


public class Product implements Comparable{
    private String productId;
    private String name;
    private String imageFile;
    private int price;

    public Product(String productId, String name, String imageFile, int price){
        this.productId = productId;
        this.name = name;
        this.imageFile = imageFile;
        this.price = price;
    }

    public Product(Product other){
        this(other.productId, other.name, other.imageFile, other.price);
    }

    public String getProductId(){ return productId; }

    public String getItemName(){ return name; }

    public String getImageFile() { return imageFile; }

    public int getPrice() { return price; }

    @Override
    public boolean equals(Object other){
        if(other == null || !(other instanceof Product)) return false;
        return getProductId().equals(((Product) other).getProductId());
    }



    @Override
    public int hashCode() {
        return getProductId().hashCode();
    }

    @Override
    public int compareTo(Object o) {
        return getProductId().compareTo(o.toString());
    }
}
