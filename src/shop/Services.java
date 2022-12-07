package shop;

import shop.exceptions.ShortageOfGoodsException;
import shop.others.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

import static shop.exceptions.inputData.inputDouble;
import static shop.exceptions.inputData.inputInt;

public class Services {

    private Scanner scanner;
    private Shop shop;

    Services(Shop shop){
        scanner = new Scanner(System.in);
        this.shop = shop;
    }
    public void cashRegisters(){
        Cashier cashier1 = new Cashier("Петър", 1200, 1);
        Cashier cashier2 = new Cashier("Иван", 1200, 2);
        Cashier cashier3 = new Cashier("Огнян", 1200, 3);
        Cashier cashier4 = new Cashier("Жоро", 1200, 4);
        System.out.print("Моля въведете колко дена преди изтичането на срок на годност се прави отстъпка: ");
        int daysBeforeTheExpirationDate = inputInt(scanner);
        System.out.print("\nПроцент отстъпка: ");
        double discountPercentage = inputDouble(scanner);
        shop.setDaysBeforeTheExpirationDate(daysBeforeTheExpirationDate);
        shop.setDiscountPercentage(discountPercentage);

        for (int i = 0; i < 4; i++) {
            shop.addCashRegister(new cashRegister());
        }

        shop.addCashier(cashier1, 1);
        shop.addCashier(cashier2, 2);
        shop.addCashier(cashier3,3);
        shop.addCashier(cashier4, 4);

    }
    public void storedGoods(){

        for (int i = 0; i < 100; i++) {
            this.shop.addGood(new Good("дезодорант", new BigDecimal("3.10"), Good_Category.NON_FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("кафе", new BigDecimal("3.50"), Good_Category.FOOD, LocalDate.of(2022,8,29)));
            this.shop.addGood(new Good("банани", new BigDecimal("2.50"), Good_Category.FOOD, LocalDate.of(2022,8,29)));
            this.shop.addGood(new Good("вафла", new BigDecimal("0.50"), Good_Category.FOOD, LocalDate.of(2022,6,5)));
            this.shop.addGood(new Good("спагети", new BigDecimal("3.50"), Good_Category.FOOD, LocalDate.of(2022,6,8)));
            this.shop.addGood(new Good("чай", new BigDecimal("1.50"), Good_Category.FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("крем", new BigDecimal("4.20"), Good_Category.NON_FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("ориз", new BigDecimal("1.10"), Good_Category.FOOD, LocalDate.of(2022,  8,11)));
            this.shop.addGood(new Good("лак",new BigDecimal("3.80") ,Good_Category.NON_FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("олио",new BigDecimal("2.80") ,Good_Category.FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("шунка",new BigDecimal("1.80") ,Good_Category.FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("риба",new BigDecimal("13.80") ,Good_Category.FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("пиле на грил",new BigDecimal("6.80") ,Good_Category.FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("унисос",new BigDecimal("0.80") ,Good_Category.FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("ябълки",new BigDecimal("1.80") ,Good_Category.FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("круши",new BigDecimal("2.40") ,Good_Category.FOOD, LocalDate.of(2022,12,12)));
            this.shop.addGood(new Good("чипс",new BigDecimal("1.90") ,Good_Category.FOOD, LocalDate.of(2022,12,12)));
        }
    }
    public void storeGood() {
        Good good = new Good();
        double deliveryPrice = 0.00;
        Good_Category good_category = Good_Category.FOOD;
        int year = 0;
        int month = 0;
        int day = 0;
        System.out.println("Моля въведете данни за продукта");
        System.out.print("Име на продукта: ");
        String name = scanner.nextLine().toLowerCase();
        good.setName(name);
        if (this.shop.isThereAlreadySuchAProduct(name) && !shop.getGoods().get(name).isEmpty()) {
            good.setCategory(shop.getGoods().get(name).get(0).getCategory());
            good.setSingle_delivery_price(shop.getGoods().get(name).get(0).getSingle_delivery_price());
        } else {
            System.out.println();
            boolean valid = false;
            while (!valid) {
                System.out.print("Категория: 1 -> Хранителен продукт\n          2 -> Нехранителен продукт\n");
                int category = inputInt(scanner);
                if (category == 1) {
                    valid = true;
                } else if (category == 2) {
                    good_category = Good_Category.NON_FOOD;
                    valid = true;
                } else {
                    System.out.println("Невалидна категория!");
                }
            }
            good.setCategory(good_category);
            System.out.print("Доставна цена: ");
            deliveryPrice = inputDouble(scanner);
            BigDecimal dp = new BigDecimal(deliveryPrice);
           good.setSingle_delivery_price(dp);
            System.out.println();
        }
        System.out.println();
        System.out.println("Срок на годност (ДАТА): ");
        System.out.print("Година: ");
        year = inputInt(scanner);
        System.out.print("Месец: ");
        month = inputInt(scanner);
        while (month<1 || month>12){
            System.out.printf("Въведохте грешен месец!%nМесец: ");
            month = inputInt(scanner);
        }
        System.out.print("Ден: ");
        day = inputInt(scanner);
        while (day<1 || day>31){
            System.out.printf("Въведохте грешен ден!%nДен: ");
            day = inputInt(scanner);
        }

        good.setExpiry_date(LocalDate.of(year, month, day));
        if (!shop.getGoods().containsKey(good.getName())) {
            shop.getGoods().put(good.getName(), new ArrayList<>());
        }
        System.out.println("Моля въведете количество: ");
        int quantity = inputInt(scanner);
        for (int i = 0; i < quantity; i++) {
           this.shop.addGood(good);
        }
        System.out.println("\nУспешно добавена стока!");
    }


    /*public void sellGoods(Client client, cashRegister cashRegister) {
        Receipt receipt = new Receipt();
        while (!client.getBasket().isEmpty()) {
            if (client.getBasket().peek().getExpiry_date().compareTo(LocalDate.now()) < 0) {
                System.out.println("Стоката " + client.getBasket().peek().getName() + " е с изтекъл срок на годност");
                shop.getGoods().get(client.getBasket().peek().getName()).remove(client.getBasket().peek());
                for (int j = 0; j < shop.getGoods().get(client.getBasket().peek().getName()).size(); j++) {
                    if (client.getBasket().peek().getExpiry_date().compareTo(LocalDate.now()) < 0) {
                        shop.getGoods().get(client.getBasket().peek().getName()).remove(j);
                        j--;
                    }
                }
                System.out.println("Желаете ли да замените " + client.getBasket().peek().getName() + " с друго");
                int choice = 0;
                while (choice != 2 && choice != 1) {
                    System.out.println("1 -> Да\n2 -> Не");
                    choice = inputInt(scanner);
                }
                if (choice == 1) {
                    Good good = client.getBasket().peek();
                    client.getBasket().pop();
                    try {
                        client.addToBasket(good.getName(), 1);
                        receipt.addGood(client.getBasket().pop());
                    } catch (ShortageOfGoodsException e) {
                        System.out.println("Няма достатъчно количество " + good.getName());
                    }
                } else {
                    client.getBasket().pop();
                }
            } else {
                receipt.addGood(client.getBasket().pop());
            }
        }
        if (client.getCashAvailable() < receipt.total(shop)) {
            double sum = receipts.get(receipts.size() - 1).total(shop) - client.getCashAvailable();
            System.out.println("Не достигат " + sum + "лв за да се извърши покупката");
            receipts.get(receipts.size() - 1).getGoods().forEach((key, value) -> value.forEach(shop::addGood));
            receipts.get(receipts.size() - 1).getGoods().clear();
        } else {

            receipts.get(receipts.size() - 1).getGoods().forEach((key, value) -> value.forEach(shop::setGoodsSold));
            shop.receipts.add(receipt);
            shop.incNumberOfReceipts();
        }
    }
     */
}
