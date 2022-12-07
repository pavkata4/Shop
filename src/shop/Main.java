package shop;


import shop.exceptions.cashRegisterNotFoundException;
import shop.exceptions.cashierNotFoundException;
import shop.others.Client;
import shop.others.saveReceiptInFile;

import java.io.File;
import java.io.FileNotFoundException;


import java.math.BigDecimal;
import java.util.Scanner;

import static shop.exceptions.inputData.inputDouble;
import static shop.exceptions.inputData.inputInt;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        saveReceiptInFile s;
        Shop shop = new Shop();
        Services services = new Services(shop);

        System.out.print("Моля въведете надценка на хранителни стоки: ");
        Good_Category.FOOD.percentage = inputDouble(scanner);
        System.out.println("\nМоля въведете надценка на нехранителни стоки: ");
        Good_Category.NON_FOOD.percentage = inputDouble(scanner);
        services.cashRegisters();
        services.storedGoods();

        int option = 0;
        while (option != 4) {
            Thread.sleep(1000);
                    String menu = String.format("Моля изберете опция:%n1 - Добавяне на стоки%n2 - Четене на клиенти от файлове и  Продажба на Стоки%n3- Смяна на касиер - каса%n4 - Край на месеца и изход от програма");
                    System.out.println(menu);
                    option = inputInt(scanner);
            switch (option) {

                case 1:
                    services.storeGood();
                    break;
                case 2:
                        Client client;
                    for (int i = 1; i <= 6 ; i++) {
                        File file = new File("src/shop/client_basket/client_" + i);
                        try {
                            client = new Client(file.getPath());
                            shop.addClient(client);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    shop.s();
                    break;
                case 3:{
                    System.out.println("Моля въведете ID на касиера");
                    int cashierToSwap = inputInt(scanner);
                    System.out.println("Моля въведете ID на касата");
                    int cashRegNum = inputInt(scanner);

                    try {
                        shop.changeCashRegisterCashier(cashierToSwap,cashRegNum);
                        System.out.println(shop.getCashRegisters().get(cashierToSwap - 1));
                        System.out.println(shop.getCashRegisters().get(cashRegNum-1));
                    } catch (cashierNotFoundException e) {
                        System.out.println("Въведохте невалидно ID на касиер!");
                    } catch (cashRegisterNotFoundException e) {
                        System.out.println("Въведохте невалидно ID на каса!");
                    }
                    break;
                }
                case 4:
                    System.out.println("Общо издадени касови бележки: " + shop.numberOfReceipts());
                        System.out.print("Приходи за месец: "+shop.income() + "лв.");
                        System.out.print("\nРазходи за месец: " + shop.allCosts());
                        System.out.println(shop.profit().compareTo(new BigDecimal(0))<0? "\nМагазинът е на загуба с: " + shop.profit() + "лв.": "\nМагазинът е на печалба с: " + shop.profit()+"лв.");
                    break;

            }
        }
    }

}
