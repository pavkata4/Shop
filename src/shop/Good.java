package shop;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Good {
    private int id;
    private String name;
    private BigDecimal single_delivery_price;
    private Good_Category category;
    private LocalDate expiry_date;
    private Map<String, Integer> goods_id = new HashMap<>();
    private BigDecimal sellingPrice;
    private boolean havePromotion;
    public static int increment = 0;

public Good(){

}


    public Good(String name, BigDecimal single_delivery_price, Good_Category category, LocalDate expiry_date) {
    if (!goods_id.containsKey(name)){
        this.id = ++increment;
        goods_id.put(name,id);
    }else {
     this.id = goods_id.get(name);
    }
        this.name = name;
        this.single_delivery_price = single_delivery_price;
        this.category = category;
        this.expiry_date = expiry_date;


        if (category.equals(Good_Category.FOOD)) {
           this.sellingPrice = single_delivery_price.add(single_delivery_price.multiply(new BigDecimal(Good_Category.FOOD.percentage)).divide(new BigDecimal(100)));
        } else {
            this.sellingPrice = single_delivery_price.add(single_delivery_price.multiply(new BigDecimal(Good_Category.NON_FOOD.percentage)).divide(new BigDecimal(100)));
        }
      this.havePromotion = false;
    }


    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
    if (!havePromotion) {
        this.sellingPrice = sellingPrice;
    }
    }

    public void setHavePromotion(boolean havePromotion) {
        this.havePromotion = havePromotion;
    }

    public boolean compareGoods(Good good){
        return this.name.equals(good.name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSingle_delivery_price() {
        return single_delivery_price;
    }

    public void setSingle_delivery_price (BigDecimal single_delivery_price) {
        this.single_delivery_price = single_delivery_price;
        if (category.equals(Good_Category.FOOD)) {
            this.sellingPrice = single_delivery_price.add(single_delivery_price.multiply(new BigDecimal(Good_Category.FOOD.percentage)).divide(new BigDecimal(100)));
        } else {
            this.sellingPrice = single_delivery_price.add(single_delivery_price.multiply(new BigDecimal(Good_Category.NON_FOOD.percentage)).divide(new BigDecimal(100)));
        }
    }

    public Good_Category getCategory() {
        return category;
    }

    public void setCategory(Good_Category category) {
        this.category = category;
    }

    public LocalDate getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(LocalDate expiry_date) {
        this.expiry_date = expiry_date;
    }

    @Override
    public String toString() {
        return name + "    " + sellingPrice ;
    }
}
