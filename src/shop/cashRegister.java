package shop;

import shop.others.Client;
import shop.others.saveReceiptInFile;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class cashRegister{
    private int id;
    private Cashier cashier;
    private static int inc;
    private List<Receipt> receipts;
    private  ArrayDeque<Client> clients;
    private  int daysBeforeTheExpirationDate;
    private  double discountPercentage;
    private  BigDecimal cashRegisterIncome;
    private ArrayList<Good> returnedGoods;
    private List<Good> markedGoods;

    public cashRegister() {
        this. receipts = new ArrayList<>();
        this.id = ++inc;
        clients = new ArrayDeque<>();
        this.cashRegisterIncome = new BigDecimal(0);
        this.markedGoods = new ArrayList<>();
        this.returnedGoods = new ArrayList<>();
    }



    public  int getDaysBeforeTheExpirationDate() {
        return daysBeforeTheExpirationDate;
    }

    public  void setDaysBeforeTheExpirationDate(int daysBeforeTheExpirationDate) {
        this.daysBeforeTheExpirationDate = daysBeforeTheExpirationDate;
    }

    public  double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void addClient(Client client)  {
      this.clients.push(client);
    }

    public BigDecimal getCashRegisterIncome() {
        return cashRegisterIncome;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public void addReceipt(Receipt receipt){
        this.receipts.add(receipt);
    }

    public ArrayList<Good> getReturnedGoods() {
        return returnedGoods;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public  List<Receipt> getReceipts() {
        return this.receipts;
    }

    public void startCashRegister() {
        Receipt receipt;
            while (!clients.isEmpty()) {
                receipt = new Receipt(this.cashier);
                while (!clients.peekLast().getBasket().isEmpty()) {
                    if (clients.getLast().getBasket().peek().getExpiry_date().compareTo(LocalDate.now()) < 0) {
                        System.out.println("Каса " + id + ": Клиент " + clients.peekLast().getId() + ": " + "Продуктът " + clients.peekLast().getBasket().pop().getName() + " е с изтекъл срок на годност");
                    } else if (ChronoUnit.DAYS.between(LocalDate.now(), clients.getLast().getBasket().peek().getExpiry_date()) <= daysBeforeTheExpirationDate) {
                        clients.getLast().getBasket().peek().setSellingPrice(clients.getLast().getBasket().peek().getSellingPrice().subtract(clients.getLast().getBasket().peek().getSellingPrice().multiply(new BigDecimal(discountPercentage)).divide(new BigDecimal(100))));
                        clients.getLast().getBasket().peek().setHavePromotion(true);
                        markedGoods.add(clients.getLast().getBasket().peek());
                        receipt.addGood(clients.getLast().getBasket().pop());
                    } else
                        markedGoods.add(clients.getLast().getBasket().peek());
                        receipt.addGood(clients.getLast().getBasket().pop());
                }
                if (BigDecimal.valueOf(clients.getLast().getCashAvailable()).compareTo(receipt.getTotalSum()) >= 0) {
                    receipts.add(receipt);
                    this.cashRegisterIncome = this.cashRegisterIncome.add(receipt.getTotalSum());
                    try {
                        saveReceiptInFile saveReceiptInFile = new saveReceiptInFile(receipt);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    markedGoods.clear();
                } else {
                    BigDecimal diff = new BigDecimal(0);
                    diff = receipt.getTotalSum().subtract(BigDecimal.valueOf(clients.getLast().getCashAvailable()));
                    System.out.println("Каса " + id + ": Клиент " + clients.getLast().getId() + " не достигат " + diff + "лв. да се извърши покупката");
                    for (int i = 0; i < markedGoods.size(); i++) {
                        this.returnedGoods.add(markedGoods.remove(i));
                    }
                }
                clients.removeLast();

            }
        }

    @Override
    public String toString() {
        return "cashRegister{" +
                "id=" + id +
                ", cashier=" + cashier +
                '}';
    }
}


