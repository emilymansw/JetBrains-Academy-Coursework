package budget;

public class Purchase implements Comparable<Purchase>{
    float price;
    String itemName;
    PurchaseTypes purchaseType;

    Purchase(String itemName, float price, PurchaseTypes purchaseType){
        this.price = price;
        this.itemName = itemName;
        this.purchaseType = purchaseType;
    }

    @Override
    public int compareTo(Purchase anotherPurchase) {
        return compare(this.price, anotherPurchase.price);
    }

    public static int compare (float x, float y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Purchase purchase = (Purchase) object;
        return itemName.equals(purchase.itemName);
    }


}
