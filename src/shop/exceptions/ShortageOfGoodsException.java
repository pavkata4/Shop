package shop.exceptions;

import shop.Good;

import java.util.List;

public class ShortageOfGoodsException extends Exception{
 private int shortage;
 private List<Good> availableGoods;

 public ShortageOfGoodsException(int shortage, List<Good> availableGoods){
     this.shortage = shortage;
     this.availableGoods = availableGoods;
 }

    public int getShortage() {
        return shortage;
    }

    public void setShortage(int shortage) {
        this.shortage = shortage;
    }

    public List<Good> getAvailableGoods() {
        return availableGoods;
    }
}
