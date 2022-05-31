package budget;

public enum PurchaseTypes {
    FOOD("Food"),
    CLOTHES("Clothes"),
    ENTERTAINMENT("Entertainment"),
    OTHER("Other");

    private String name;

    private PurchaseTypes(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

}
