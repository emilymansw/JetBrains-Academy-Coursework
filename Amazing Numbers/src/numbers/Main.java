package numbers;
import java.util.*;

class Main {
    public static void main(String[] args) {
//        write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Amazing Numbers!");
        System.out.print("Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
        while (true){
            long num = -1;
            long consecutiveNum = -1;
            ArrayList<Long> numArr = new ArrayList<>();
            NumberProperties numPropsArr[] = NumberProperties.values();
            ArrayList<String> numProp = new ArrayList<>();
            ArrayList<String> excludedNumProp = new ArrayList<>();
            String numPropsArrStr = Arrays.toString(numPropsArr);
            while (num < 1){
                System.out.print("Enter a request:");
                String[] input = scanner.nextLine().split(" ");
                ArrayList<String> wrongNumProps = new ArrayList<>();
                Boolean wrongInput = false;
                if (input.length > 2) {
                    for (int i = 2; i < input.length; i++) {
                        if(input[i].charAt(0)=='-'){
                            excludedNumProp.add(input[i].substring(1).toUpperCase());
                        } else {
                            numProp.add(input[i].toUpperCase());
                        }
                    }
                    for (int i = 0; i < numProp.size(); i++) {
                        if (!numPropsArrStr.contains(numProp.get(i))) {
                            wrongNumProps.add(numProp.get(i));
                        }
                    }
                    for (int i = 0; i < excludedNumProp.size(); i++) {
                        if (!numPropsArrStr.contains(excludedNumProp.get(i))) {
                            wrongNumProps.add(excludedNumProp.get(i));
                        }
                    }
                    if (wrongNumProps.size() > 0) {
                        if (wrongNumProps.size() == 1) {
                            System.out.printf("The property [%s] is wrong.\n", wrongNumProps.get(0));
                        } else {
                            System.out.print("The properties [");
                            System.out.printf("%s,", wrongNumProps.get(1));
                            for (int i = 1; i < wrongNumProps.size() - 1; i++) {
                                System.out.printf(" %s,", wrongNumProps.get(i));
                            }
                            System.out.printf("%s] are wrong.\n", wrongNumProps.get(wrongNumProps.size() - 1));
                        }
                        System.out.printf("Available properties: %s", numPropsArrStr);
                        wrongInput = true;
                    }

                    if (numProp.contains("SUNNY") && numProp.contains("SQUARE")) {
                        System.out.print("The request contains mutually exclusive properties: [SQUARE, SUNNY]\n" +
                                "There are no numbers with these properties.\n");
                        wrongInput = true;

                    }
                    if (numProp.contains("ODD") && numProp.contains("EVEN") || excludedNumProp.contains("ODD") && excludedNumProp.contains("EVEN")) {
                        System.out.print("The request contains mutually exclusive properties: [SQUARE, SUNNY]\n" +
                                "There are no numbers with these properties.\n");
                        wrongInput = true;

                    }
                    if (numProp.contains("SPY") && numProp.contains("DUCK")) {
                        System.out.print("The request contains mutually exclusive properties: [SPY, DUCK]\n" +
                                "There are no numbers with these properties.\n");
                        wrongInput = true;

                    }
                    if (numProp.contains("HAPPY") && numProp.contains("SAD") || excludedNumProp.contains("HAPPY") && excludedNumProp.contains("SAD")) {
                        System.out.print("The request contains mutually exclusive properties: [HAPPY, SAD]\n" +
                                "There are no numbers with these properties.\n");
                        wrongInput = true;

                    }
                    for(int i = 0; i < numProp.size(); i++){
                        if (excludedNumProp.contains(numProp.get(i))){
                            System.out.printf("The request contains mutually exclusive properties: [%s, -%s]\n" +
                                    "There are no numbers with these properties.\n", numProp.get(i),numProp.get(i));
                            wrongInput = true;
                        }
                    }
                }

                    if(!wrongInput && input.length > 1) {
                        consecutiveNum = Long.parseLong(input[1]);
                        if (consecutiveNum < 1) {
                            System.out.println("The second parameter should be a natural number.");
                            wrongInput = true;
                        }
                    }
                    if (!wrongInput){
                        num = Long.parseLong(input[0]);
                            if(num < 1){
                                System.out.println("The first parameter should be a natural number or zero.");
                            }
                   }

                if(num == 0){
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
            }

            numArr.add(num);
            if(consecutiveNum > 1){
                for (int i = 1; i < consecutiveNum; i++){
                    numArr.add(num + i);
                }
            }

            if(consecutiveNum < 1){
                long number = numArr.get(0);
                System.out.printf("Properties of %d\n",number);
                System.out.printf("\tbuzz: %b\n",NumberProperties.BUZZ.checkIfTrue(number));
                System.out.printf("\tduck: %b\n",NumberProperties.DUCK.checkIfTrue(number));
                System.out.printf("\tpalindromic: %b\n",NumberProperties.PALINDROMIC.checkIfTrue(number));
                System.out.printf("\tgapful: %b\n",NumberProperties.GAPFUL.checkIfTrue(number));
                System.out.printf("\teven: %b\n", NumberProperties.EVEN.checkIfTrue(number));
                System.out.printf("\todd: %b\n",NumberProperties.ODD.checkIfTrue(number));
                System.out.printf("\tspy: %b\n",NumberProperties.SPY.checkIfTrue(number));
                System.out.printf("\tsunny: %b\n",NumberProperties.SUNNY.checkIfTrue(number));
                System.out.printf("\tsquare: %b\n",NumberProperties.SQUARE.checkIfTrue(number));
                System.out.printf("\tjumping: %b\n",NumberProperties.JUMPING.checkIfTrue(number));
                System.out.printf("\thappy: %b\n",NumberProperties.HAPPY.checkIfTrue(number));
                System.out.printf("\tsad: %b\n",NumberProperties.SAD.checkIfTrue(number));

            } else {
                if(numProp.size() > 0 || excludedNumProp.size() > 0) {
                    int counter = 0;
                    while (counter < consecutiveNum) {
                        //covert string ArrayList to enum ArrayList
                        ArrayList<NumberProperties> inputIncludingNumProps = new ArrayList<>();
                        ArrayList<NumberProperties> inputExcludingNumProps = new ArrayList<>();

                        for (int i = 0; i < numProp.size(); i++){
                            inputIncludingNumProps.add(NumberProperties.valueOf(numProp.get(i)));
                        }
                        for (int i = 0; i < excludedNumProp.size(); i++){
                            inputExcludingNumProps.add(NumberProperties.valueOf(excludedNumProp.get(i)));
                        }
                        if(checkNumProperties(num,inputIncludingNumProps, inputExcludingNumProps)){
                            System.out.println("\t" + genNumberProperties(num));
                            counter++;
                        }
                        num++;
                    }
                }
                else {
                    for (Long aLong : numArr) {
                        System.out.println("\t" + genNumberProperties(aLong));
                    }
                }
            }
        }
    }
    public static boolean checkNumProperties(long num, ArrayList<NumberProperties> numPropsArrayList, ArrayList<NumberProperties> excludedNumPropsArrayList){
        for (NumberProperties property : numPropsArrayList){
             if(!property.checkIfTrue(num)){
                 return false;
             }
        }
        for (NumberProperties property: excludedNumPropsArrayList) {

            if (property.checkIfTrue(num)) {
                return false;
            }
        }
        return true;
    }

    protected static String genNumberProperties(final long num){
        StringBuilder numberProperties = new StringBuilder();
        numberProperties.append(num).append(" is");
        if(NumberProperties.BUZZ.checkIfTrue(num)) {numberProperties.append(" buzz,");}
        if(NumberProperties.DUCK.checkIfTrue(num)) {numberProperties.append(" duck,");}
        if(NumberProperties.PALINDROMIC.checkIfTrue(num)) {numberProperties.append(" palindromic,");}
        if(NumberProperties.GAPFUL.checkIfTrue(num)) {numberProperties.append(" gapful,");}
        if(NumberProperties.EVEN.checkIfTrue(num)) {numberProperties.append(" even,");}
        if(NumberProperties.SPY.checkIfTrue(num)) {numberProperties.append(" spy,");}
        if(NumberProperties.ODD.checkIfTrue(num)) {numberProperties.append(" odd,");}
        if(NumberProperties.SQUARE.checkIfTrue(num)) {numberProperties.append(" square,");}
        if(NumberProperties.SUNNY.checkIfTrue(num)) {numberProperties.append(" sunny,");}
        if(NumberProperties.JUMPING.checkIfTrue(num)) {numberProperties.append(" jumping,");}
        if(NumberProperties.HAPPY.checkIfTrue(num)) {numberProperties.append(" happy,");}
        if(NumberProperties.SAD.checkIfTrue(num)) {numberProperties.append(" sad,");}


        numberProperties.deleteCharAt(numberProperties.length()-1);
        return numberProperties.toString();
    }
}