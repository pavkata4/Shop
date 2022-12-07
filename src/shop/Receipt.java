package shop;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Receipt {
    private int id;
    private Cashier cashier;
    private LocalDateTime date_and_time_of_issuance_of_the_receipt;
    private Map<String, List<Good>> goods;


    public static int increment_id = 0;
    public Receipt(){
        ++increment_id;
        this.id = increment_id;
        this.date_and_time_of_issuance_of_the_receipt = LocalDateTime.now();
        this.goods = new LinkedHashMap<>();
    }
    public Receipt(Cashier cashier) {
        this.cashier = cashier ;
        ++increment_id;
        this.id = increment_id;
        this.date_and_time_of_issuance_of_the_receipt = LocalDateTime.now();
        this.goods = new LinkedHashMap<>();
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public void addGood(Good good) {
        if (!goods.containsKey(good.getName())){
            goods.put(good.getName(), new ArrayList<Good>());
        }
        goods.get(good.getName()).add(good);
    }

    public int getId() {
        return id;
    }

    public BigDecimal getTotalSum() {
        BigDecimal sum = new BigDecimal(0);

            for (List<Good> value : goods.values()) {
                for (Good good : value) {
                    sum = sum.add(good.getSellingPrice());
                }
            }

        return sum;
    }

    public Map<String, List<Good>> getGoods() {
        return goods;
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder("КАСОВА БЕЛЕЖКА\n" +
                "Пореден номер: " + id +
                "\nКасиер: " + cashier.getName() +
                "\nДата и час: " + date_and_time_of_issuance_of_the_receipt.getYear() + "-"
                +date_and_time_of_issuance_of_the_receipt.getMonth() + "-"
                + date_and_time_of_issuance_of_the_receipt.getDayOfMonth() + " "+ date_and_time_of_issuance_of_the_receipt.getHour() + ":"
                +date_and_time_of_issuance_of_the_receipt.getMinute() + ":" + date_and_time_of_issuance_of_the_receipt.getSecond() +
                "\nСтоки:\n");
                goods.forEach((key, value) -> txt.append(key).append("--->").append("единична цена: ").append(value.get(0).getSellingPrice()).append(" ---> количество: ").append(value.size()).append("\n"));
               txt.append("\nОбща сума: ").append(getTotalSum());
        return txt.toString();
    }
}
