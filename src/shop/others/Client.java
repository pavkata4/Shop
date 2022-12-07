package shop.others;

import shop.Good;
import shop.Shop;
import shop.exceptions.ShortageOfGoodsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Client {
    private int id;
    private String clientFile;
    private double cashAvailable;
    private ArrayDeque<Good> basket;
    private int cashRegNum;
    private static int incID = 0;


    public Client(String clientFile) throws FileNotFoundException {
       this.clientFile = clientFile;
       this.id = ++incID;
        File f = new File(this.clientFile);
        Scanner scanner = new Scanner(f);
        this.cashAvailable = Integer.parseInt(scanner.nextLine());
         this.basket = new ArrayDeque<>();
        this.cashRegNum = Integer.parseInt(scanner.nextLine());
    }

    public int getCashRegNum() {
        return cashRegNum;
    }

    public double getCashAvailable() {
        return cashAvailable;
    }

    public int getId() {
        return id;
    }

    public ArrayDeque<Good> getBasket() {
        return basket;
    }

    public void setCashAvailable(double cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public String getClientFile() {
        return clientFile;
    }

    public void setClientFile(String clientFile) {
        this.clientFile = clientFile;
    }
    @Override
    public String toString() {
        return "Client{" +
                "basket=" + basket +
                '}';
    }

    public ArrayList<Good> returnGoods() {
        ArrayList<Good> goodsToReturn = new ArrayList<>();
        while (!this.basket.isEmpty()) {
            goodsToReturn.add(basket.pop());

        }
        return goodsToReturn;
    }
}
