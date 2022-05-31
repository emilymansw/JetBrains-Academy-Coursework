package budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpendingLog {
    Balance balance;
    Map<String, ArrayList<Purchase>> allPurchase;
    ArrayList<Purchase> food;
    ArrayList<Purchase> clothes;
    ArrayList<Purchase> entertainment;
    ArrayList<Purchase> other;

    SpendingLog(){
        balance = new Balance(0);
        food = new ArrayList<>();
        clothes = new ArrayList<>();
        entertainment = new ArrayList<>();
        other = new ArrayList<>();
        allPurchase = new HashMap<>();
        allPurchase.put("Food", food);
        allPurchase.put("Clothes", clothes);
        allPurchase.put("Entertainment", entertainment);
        allPurchase.put("Other", other);
    }


}
