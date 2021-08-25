package shoppingCart;

import Product.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class cart {
    private int spentMoney;
    private HashMap < Product, Integer > items;

    public cart(){
        spentMoney = 0;
        items = new HashMap<>();
    }

    public int numBought(Product product){
        return items.get(product);
    }

    public int spentMoney(){
        return spentMoney;
    }

    public int numDistinctItems(){
        return items.size();
    }


    public void addItem(Product add){
        if(items.containsKey(add)){
            items.put(add, items.get(add) + 1);
        }else{
            items.put(add, 1);
        }
        spentMoney += add.getPrice();
    }

    public Set<Product> getItemSet(){
        return new HashSet<>(items.keySet());
    }


    public void setAmount(Product product, int amount){
        Integer currentAmount = items.get(product);
        if (currentAmount != null) {
            if (amount == 0) items.remove(product);
            else items.put(product, amount);
            spentMoney -= currentAmount * product.getPrice();
        }else{
            if(amount == 0) return;
            items.put(product, amount);
        }
        spentMoney += amount * product.getPrice();
    }

    public HashMap < Product, Integer > getAllProducts(){
        HashMap <Product, Integer> tmp = new HashMap<>();
        for (Product prod : items.keySet()){
            tmp.put(new Product(prod), items.get(prod));
        }
        return tmp;
    }


}
