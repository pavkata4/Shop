package shop;

import shop.exceptions.ShortageOfGoodsException;
import shop.exceptions.cashRegisterNotFoundException;
import shop.exceptions.cashierNotFoundException;
import shop.others.Client;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Shop {
    private List<Cashier> cashiers;
    private Map<String, List<Good>> goods;
    private Map<String, List<Good>> soldGoods;
    private List<cashRegister> cashRegisters;
    private  BigDecimal costs;
    private List<Client> clients;
    private  int daysBeforeTheExpirationDate;
    private  double discountPercentage;

    public Shop() {
        this.cashiers = new ArrayList<>();
        goods = new HashMap<>();
        this.soldGoods = new HashMap<>();
        cashRegisters = new ArrayList<>();
        costs = new BigDecimal(0);
        this.clients = new ArrayList<>();
    }


    public void addClient(Client client) {
        this.clients.add(client);
    }

    public Map<String, List<Good>> getGoods() {
        return goods;
    }

    public List<cashRegister> getCashRegisters() {
        return cashRegisters;
    }

    public void addCashRegister(cashRegister cashRegister) {
        cashRegister.setDaysBeforeTheExpirationDate(this.daysBeforeTheExpirationDate);
        cashRegister.setDiscountPercentage(this.discountPercentage);
        cashRegisters.add(cashRegister);
    }

    public void setDaysBeforeTheExpirationDate(int daysBeforeTheExpirationDate) {
        this.daysBeforeTheExpirationDate = daysBeforeTheExpirationDate;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void addGood(Good good) {
        if (!isThereAlreadySuchAProduct(good.getName())) {
            this.goods.put(good.getName(), new ArrayList<>());
        } else {
            this.goods.get(good.getName()).add(good);
        }
        costs = costs.add(good.getSingle_delivery_price());
    }

    public void addCashier(Cashier cashier, int cashRegisterNum) {
        this.cashiers.add(cashier);
        if (cashRegisters.size() >= cashRegisterNum - 1 && cashRegisterNum > 0) {
            cashRegisters.get(cashRegisterNum - 1).setCashier(cashier);
        } else {
            System.out.println("Invalid CashRegister num");
        }
    }

    public synchronized List<Good> takeProducts(String name, int quantity) throws ShortageOfGoodsException {
        List<Good> g = new ArrayList<>();
        if (!goods.containsKey(name)) {
            throw new ShortageOfGoodsException(quantity, g);
        } else if (goods.get(name).size() < quantity) {
            int diff = quantity - goods.get(name).size();
            g.addAll(goods.get(name));
            goods.get(name).clear();
            throw new ShortageOfGoodsException(diff, g);
        } else {
            for (int i = 0; i < quantity; i++) {
                g.add(goods.get(name).get(0));
                goods.get(name).remove(0);
            }
        }
        return g;
    }

    public void changeCashRegisterCashier(int cashierId, int cashRegisterId) throws cashierNotFoundException, cashRegisterNotFoundException {
        if (cashierId > cashiers.size() || cashierId <= 0) {
            throw new cashierNotFoundException();
        } else if (cashRegisterId > cashRegisters.size() || cashRegisterId <= 0) {
            throw new cashRegisterNotFoundException();
        }else {
            Cashier cashier = cashRegisters.get(cashRegisterId-1).getCashier();

            cashRegisters.get(cashRegisterId-1).setCashier(cashiers.get(cashierId-1));
            int cashRegNum = cashiers.get(cashierId-1).getCashRegisterNum();
            cashiers.get(cashierId-1).setCashRegisterNum(cashRegisterId-1);
            cashRegisters.get(cashRegNum-1).setCashier(cashier);
            cashier.setCashRegisterNum(cashRegNum-1);


        }
    }



   public void startGettingProducts(Client client) {

           int i = 2;
           String line = "";
           int quantity = 0;
           while (!line.equals(".")) {
               String productName = "";
               try {
                   line = Files.readAllLines(Paths.get(client.getClientFile())).get(i);
                   String[] tokens = line.split("\\s+");
                   for (int j = 0; j <tokens.length-1 ; j++) {
                   productName += tokens[j] + " ";
                   }

                       productName = productName.substring(0, productName.length() - 1);

                   quantity = Integer.parseInt(tokens[tokens.length-1]);
                   client.getBasket().addAll(this.takeProducts(productName, quantity));

               } catch (ShortageOfGoodsException e) {
                   System.out.println("Клиент: " + client.getId() + " -> не достигат " + e.getShortage() + " бр. " + productName);
                   client.getBasket().addAll(e.getAvailableGoods());
               } catch (IOException o) {
                   o.printStackTrace();
               }
               i++;
               try {
                   line = Files.readAllLines(Paths.get(client.getClientFile())).get(i);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

           System.out.println("Клиент " + client.getId() + " е готов");

           this.getCashRegisters().get(client.getCashRegNum() - 1).addClient(client);
    }

    public void s() throws InterruptedException {
        for (int i = 0; i < clients.size(); i++) {
            final int iClient = i;
        Runnable runnable = () -> startGettingProducts(clients.get(iClient));
        Thread thread = new Thread(runnable);
        thread.start();
        }
      Thread.sleep(5000);
        for (int i = 0; i < this.cashRegisters.size(); i++) {
            final int iCashRegister = i;
            Runnable runnable = () ->cashRegisters.get(iCashRegister).startCashRegister();
            Thread thread = new Thread(runnable);
            thread.start();
        }
        Thread.sleep(5000);
        for (cashRegister cashRegister : cashRegisters) {
            if (!cashRegister.getReturnedGoods().isEmpty()) {
                while (!cashRegister.getReturnedGoods().isEmpty()) {
                        goods.get(cashRegister.getReturnedGoods().get(0).getName()).add(cashRegister.getReturnedGoods().remove(0));
                }
                System.out.println("Покупките от каса " + cashRegister.getId() + " са върнати в магазина");
            }
        }
        this.clients.clear();
    }
    public long numberOfReceipts(){
        long n = 0;
        for (cashRegister cashRegister : cashRegisters) {
            n+=cashRegister.getReceipts().size();
        }
        return n;
    }

    public boolean isThereAlreadySuchAProduct(String name){
       return goods.containsKey(name);
    }
    public BigDecimal income() {
        BigDecimal sum = new BigDecimal(0);
        for (shop.cashRegister cashRegister : cashRegisters) {
            sum = sum.add(cashRegister.getCashRegisterIncome());
        }
        return sum;
    }
public BigDecimal allCosts() {
    for (Cashier cashier : cashiers) {
        costs = costs.add(BigDecimal.valueOf(cashier.getMonth_salary()));
    }
    return costs;
}
public BigDecimal profit() {
        return income().subtract(costs);
}
}
