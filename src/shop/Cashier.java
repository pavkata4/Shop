package shop;

public class Cashier{
    private String name;
    private int id;
    private double month_salary;
    public static int incId = 0;
    private int cashRegisterNum;
    public Cashier(){

    }
    public Cashier(String name, double month_salary, int cashRegisterNum) {
        this.name = name;
        this.month_salary = month_salary;
        this.id = ++incId;
        this.cashRegisterNum = cashRegisterNum;
    }

    public void setCashRegisterNum(int cashRegisterNum) {
        this.cashRegisterNum = cashRegisterNum;
    }

    public int getCashRegisterNum() {
        return cashRegisterNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonth_salary() {
        return month_salary;
    }

    public void setMonth_salary(double month_salary) {
        this.month_salary = month_salary;
    }

    @Override
    public String toString() {
        return "\nИме: " + name +
                "\nидентификационен номер: " + id;

    }


}
