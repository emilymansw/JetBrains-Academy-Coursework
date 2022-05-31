package budget;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        SpendingLog spendingLog = new SpendingLog();
        ArrayList<Purchase> food = spendingLog.food;
        ArrayList<Purchase> clothes = spendingLog.clothes;
        ArrayList<Purchase> entertainment = spendingLog.entertainment;
        ArrayList<Purchase> other = spendingLog.other;
        Map<String, ArrayList<Purchase>> allPurchase = spendingLog.allPurchase;
        Balance balance = spendingLog.balance;

        int action = -1;
        while (action != 0){
            System.out.print("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit\n");
            Scanner scanner = new Scanner(System.in);
            action = Integer.parseInt(scanner.nextLine());
            System.out.println("");
            switch (action){
                case 1:
                    System.out.println("Enter income:");
                    balance.setBalanceAmount(balance.getBalanceAmount() + scanner.nextFloat());
                    System.out.println("Income was added!");
                    System.out.println("");
                    break;
                case 2:
                    action = -1;
                    while (action != 5){
                        System.out.print("Choose the type of purchase\n" +
                                "1) Food\n" +
                                "2) Clothes\n" +
                                "3) Entertainment\n" +
                                "4) Other\n" +
                                "5) Back\n");
                        try {
                            action = Integer.parseInt(scanner.nextLine());
                        } catch (Exception e){
                            System.out.println("Choose the type of purchase\n" +
                                    "1) Food\n" +
                                    "2) Clothes\n" +
                                    "3) Entertainment\n" +
                                    "4) Other\n" +
                                    "5) Back\n");
                        }
                        switch (action){
                            case 1: food.add(newPurchase(PurchaseTypes.FOOD, balance));
                                break;
                            case 2: clothes.add(newPurchase(PurchaseTypes.CLOTHES, balance));
                                break;
                            case 3: entertainment.add(newPurchase(PurchaseTypes.ENTERTAINMENT, balance));
                                break;
                            case 4: other.add(newPurchase(PurchaseTypes.OTHER, balance));
                                break;
                            default: break;
                        }
                    }



                    break;
                case 3:
                    action = -1;
                    while (action != 6){
                        System.out.println("Choose the type of purchases\n" +
                                "1) Food\n" +
                                "2) Clothes\n" +
                                "3) Entertainment\n" +
                                "4) Other\n" +
                                "5) All\n" +
                                "6) Back");
                        action = Integer.parseInt(scanner.nextLine());
                        switch (action){
                            case 1: printPurchaseList(allPurchase, PurchaseTypes.FOOD);
                                break;
                            case 2: printPurchaseList(allPurchase, PurchaseTypes.CLOTHES);
                                break;
                            case 3: printPurchaseList(allPurchase, PurchaseTypes.ENTERTAINMENT);
                                break;
                            case 4: printPurchaseList(allPurchase, PurchaseTypes.OTHER);
                                break;
                            case 5:
                                System.out.println("");
                                StringBuilder printAllPurchase = new StringBuilder();
                                float total = 0;
                                for (var entry : allPurchase.entrySet()) {
                                    if (entry.getValue().size() != 0){
                                        ArrayList<Purchase> purchases = entry.getValue();
                                        for(int i = 0; i < purchases.size(); i++){
                                            printAllPurchase.append(purchases.get(i).itemName)
                                                    .append(String.format(" $%.2f", purchases.get(i).price))
                                                    .append("\n");
                                            total += purchases.get(i).price;
                                        }
                                    }
                                }
                                if(total != 0){
                                    System.out.println("All:");
                                    System.out.print(printAllPurchase.toString());
                                    System.out.printf("Total sum: $%.2f\n", total);
                                } else {
                                    System.out.println("The purchase list is empty!");
                                }
                                System.out.println("");
                                break;
                                default: break;
                        }

                    }
                    System.out.println("");
                    break;
                case 4:
                    System.out.printf("Balance: $%.2f\n", balance.getBalanceAmount());
                    System.out.println("");
                    break;
                case 5:
                    writeToFile(spendingLog);
                    break;
                case 6:
                    spendingLog = readFile(spendingLog);
                    System.out.print("Purchases were loaded!\n");
                    System.out.println("");
                    break;
                case 7:
                    action = -1;
                    while (action != 4){
                        System.out.print("How do you want to sort?\n" +
                                "1) Sort all purchases\n" +
                                "2) Sort by type\n" +
                                "3) Sort certain type\n" +
                                "4) Back\n");
                        action = Integer.parseInt(scanner.nextLine());
                        System.out.println("");
                        switch (action) {
                            case 1:
                                ArrayList<Purchase> all = new ArrayList<>();
                                all.addAll(food);
                                all.addAll(entertainment);
                                all.addAll(clothes);
                                all.addAll(other);
                                if(all.size() == 0){
                                    System.out.println("The purchase list is empty!");
                                    System.out.println("");
                                } else {
                                    Collections.sort(all, Collections.reverseOrder());
                                    float total = 0;
                                    System.out.println("All:");
                                    for(int i = 0; i < all.size(); i++){
                                        total += all.get(i).price;
                                        System.out.printf("%s $%.2f\n",all.get(i).itemName, all.get(i).price);
                                    }
                                    System.out.printf("Total: $%.2f\n", total);
                                    System.out.println("");
                                }
                                break;
                            case 2:
                                System.out.println("Types:");
                                float foodTotal = 0;
                                float clothesTotal = 0;
                                float entertainmentTotal = 0;
                                float otherTotal = 0;
                                for(int i = 0; i < food.size(); i++){
                                    foodTotal += food.get(i).price;
                                }
                                for(int i = 0; i < clothes.size(); i++){
                                    clothesTotal += clothes.get(i).price;
                                }
                                for(int i = 0; i < entertainment.size(); i++){
                                    entertainmentTotal += entertainment.get(i).price;
                                }
                                for(int i = 0; i < other.size(); i++){
                                    otherTotal += other.get(i).price;
                                }
                                float total = foodTotal + clothesTotal + entertainmentTotal + otherTotal;
                                Map<Float,String> totalMap = new HashMap<>();
                                ArrayList<Float> totalArr = new ArrayList<>();
                                if(foodTotal != 0){
                                    totalMap.put(foodTotal, "Food");
                                    totalArr.add(foodTotal);
                                }
                                if(clothesTotal != 0){
                                    totalMap.put(clothesTotal, "Clothes");
                                    totalArr.add(clothesTotal);
                                }
                                if(otherTotal != 0){
                                    totalMap.put(otherTotal, "Other");
                                    totalArr.add(otherTotal);
                                }
                                if(entertainmentTotal != 0){
                                    totalMap.put(entertainmentTotal, "Entertainment");
                                    totalArr.add(entertainmentTotal);
                                }
                                for(int i = 0; i<totalArr.size();i++){
                                    for(int j = 0; j < totalArr.size()-i-1; j++){
                                        if(totalArr.get(j + 1) > totalArr.get(j)){
                                            float temp = totalArr.get(j + 1);
                                            totalArr.set(j + 1, totalArr.get(j));
                                            totalArr.set(j, temp);
                                        }
                                    }
                                }

                                for(int i = 0; i < totalArr.size(); i++){
                                    System.out.printf("%s - $%.2f\n",totalMap.get(totalArr.get(i)), totalArr.get(i));
                                }
                                if(foodTotal == 0){
                                    System.out.printf("%s - $%.2f\n","Food", foodTotal);
                                }
                                if(clothesTotal == 0){
                                    System.out.printf("%s - $%.2f\n","Clothes", clothesTotal);
                                }
                                if(otherTotal == 0){
                                    System.out.printf("%s - $%.2f\n","Other", otherTotal);
                                }
                                if(entertainmentTotal == 0){
                                    System.out.printf("%s - $%.2f\n","Entertainment", entertainmentTotal);
                                }
                                System.out.printf("Total sum: $%.2f\n",total);
                                System.out.println("");

                                break;
                            case 3:
                                System.out.println("Choose the type of purchase\n" +
                                        "1) Food\n" +
                                        "2) Clothes\n" +
                                        "3) Entertainment\n" +
                                        "4) Other");
                                action = Integer.parseInt(scanner.nextLine());
                                System.out.println("");
                                switch (action){
                                    case 1:
                                        Collections.sort(food, Collections.reverseOrder());
                                        if(food.size() == 0){
                                            System.out.println("The purchase list is empty!");
                                            System.out.println("");
                                        } else {
                                            System.out.println("Food :");
                                            total = 0;
                                            for(int i = 0; i < food.size(); i++){
                                                System.out.printf("%s $%.2f\n",
                                                        food.get(i).itemName,
                                                        food.get(i).price);
                                                total += food.get(i).price;
                                            }
                                            System.out.printf("Total sum: $%.2f\n", total);
                                            System.out.println("");
                                        }
                                        break;
                                    case 2:
                                        Collections.sort(clothes, Collections.reverseOrder());
                                        if(clothes.size() == 0){
                                            System.out.println("The purchase list is empty!");
                                            System.out.println("");
                                        } else {
                                            System.out.println("Clothes :");
                                            total = 0;
                                            for(int i = 0; i < clothes.size(); i++){
                                                System.out.printf("%s $%.2f\n",
                                                        clothes.get(i).itemName,
                                                        clothes.get(i).price);
                                                total += clothes.get(i).price;
                                            }
                                            System.out.printf("Total sum: $%.2f\n", total);
                                            System.out.println("");
                                        }
                                        break;
                                    case 3:
                                        Collections.sort(entertainment, Collections.reverseOrder());
                                        if(entertainment.size() == 0){
                                            System.out.println("The purchase list is empty!");
                                            System.out.println("");
                                        } else {
                                            System.out.println("Entertainment :");
                                            total = 0;
                                            for(int i = 0; i < entertainment.size(); i++){
                                                System.out.printf("%s $%.2f\n",
                                                        entertainment.get(i).itemName,
                                                        entertainment.get(i).price);
                                                total += entertainment.get(i).price;
                                            }
                                            System.out.printf("Total sum: $%.2f\n", total);
                                            System.out.println("");
                                        }
                                        break;
                                    case 4:
                                        Collections.sort(other, Collections.reverseOrder());
                                        if(other.size() == 0){
                                            System.out.println("The purchase list is empty!");
                                            System.out.println("");
                                        } else {
                                            System.out.println("Other :");
                                            total = 0;
                                            for(int i = 0; i < other.size(); i++){
                                                System.out.printf("%s $%.2f\n",
                                                        other.get(i).itemName,
                                                        other.get(i).price);
                                                total += other.get(i).price;
                                            }
                                            System.out.printf("Total sum: $%.2f\n", total);
                                            System.out.println("");
                                        }
                                        action = -1;
                                        break;
                                }
                                break;
                        }
                    }
                    break;
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                default: System.out.println("");
                        throw new IllegalArgumentException(
                        "Invalid action type: " + action);
            }


        }


    }


    public static Purchase newPurchase(PurchaseTypes purchaseType, Balance balance){
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
        String itemName = scanner.nextLine();
        System.out.println("Enter its price:");
        float itemPrice = scanner.nextFloat();
        if(itemPrice > balance.getBalanceAmount()){
            return null;
        }
        Purchase purchase = new Purchase(itemName, itemPrice, purchaseType);
        balance.setBalanceAmount(balance.getBalanceAmount() - itemPrice);
        System.out.println("Purchase was added!");
        System.out.println("");
        return purchase;
    }

    public static void printPurchaseList(Map<String, ArrayList<Purchase>> allPurchase, PurchaseTypes purchaseType){
        System.out.println("");
        String purchaseTypeStr = purchaseType.toString();
        if(allPurchase.get(purchaseTypeStr).size() == 0){
            System.out.println("The purchase list is empty!\n");
        } else {
            System.out.println(purchaseTypeStr + ":");
            float total = 0;
            for(int i = 0; i < allPurchase.get(purchaseTypeStr).size(); i++){
                System.out.printf("%s $%.2f\n",
                        allPurchase.get(purchaseTypeStr).get(i).itemName,
                        allPurchase.get(purchaseTypeStr).get(i).price);
                total += allPurchase.get(purchaseTypeStr).get(i).price;
            }
            System.out.printf("Total sum: $%.2f\n", total);
            System.out.println("");
        }
    }

    public static void writeToFile(SpendingLog spendingLog) throws IOException {
        File file = new File("./purchases.txt");
        try (PrintWriter printWriter = new PrintWriter(file)) {
            Locale.setDefault(Locale.US);
            printWriter.printf("Balance: %.2f\n", spendingLog.balance.getBalanceAmount());
            for(var entry : spendingLog.allPurchase.entrySet()){
                printWriter.printf("--%s\n", entry.getKey());
                if(entry.getValue().size() == 0){
                    printWriter.printf("Empty");
                } else {
                    for(int i = 0; i < entry.getValue().size(); i++){
                        printWriter.printf("%s\t$%.2f\n",
                                entry.getValue().get(i).itemName,
                                entry.getValue().get(i).price);
                    }
                }
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    public static SpendingLog readFile(SpendingLog spendingLog){
        File file = new File("./purchases.txt");
        try (Scanner scanner = new Scanner(file)) {
            spendingLog.balance.setBalanceAmount(Float.parseFloat(scanner.nextLine().split(" ")[1]));
            String purchasesTypes = "";
            while (scanner.hasNext()) {
                String nextLine = scanner.nextLine();
                if(nextLine.contains("--")){
                    purchasesTypes = nextLine.split("--")[1];
                } else {
                    Purchase purchaseItem = new Purchase(nextLine.split("\t\\$")[0],
                            Float.parseFloat(nextLine.split("\t\\$")[1]),
                            PurchaseTypes.valueOf(purchasesTypes.toUpperCase()));
                    spendingLog.allPurchase.get(purchasesTypes).add(purchaseItem);
                }
            }

        } catch (IOException e) {
            System.out.println("No file found: ");
        }
        return spendingLog;
    }
}


