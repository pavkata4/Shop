package shop;

public enum Good_Category {
    FOOD(0), NON_FOOD(0);
    double percentage;
    Good_Category(double percentage){
        this.percentage = percentage;
    }
}
