package cinema;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Write your code here
        System.out.println("Enter the number of rows:");
        Scanner scanner = new Scanner(System.in);
        final int r = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        final int c = scanner.nextInt();
        char[][] seat = new char[r][c];
        for (int m = 0; m < seat.length; m++){
            for(int t = 0; t < seat[0].length; t++){
                seat[m][t] = 'S';
            }
        }
        int[] purchasedTicketsNIncome = new int[]{0,0};

        selectMenu(seat, r, c, purchasedTicketsNIncome);
        
        
    }
    
    public static void printSeat(char[][] seat){
        System.out.println("Cinema:");
        for (int i = 0; i < seat.length +1; i++){
            for (int n = 0 ; n < seat[0].length +1; n++){
                if (i == 0){
                    if (n == 0){
                        System.out.print("  ");
                    } else {
                    System.out.print(n + " ");
                    if (n == seat[0].length){
                        System.out.println();
                    }
                }
            }
                if (i != 0){
                    if (n == 0){
                        System.out.print(i+ " ");
                    }
                    if (n == seat[0].length){
                        System.out.println();
                    } else {
                        System.out.print(seat[i-1][n] + " ");

                    }
                }
            }
        }
    }
    
    
    public static void selectMenu(char[][] seat, int r, int c, int[]purchasedTicketsNIncome){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        int choice = scanner.nextInt();
        float percentage = 0;
        int totalIncome = 0;
        
        switch (choice){
            case 1:
                printSeat(seat);
                selectMenu(seat, r, c, purchasedTicketsNIncome);
                break;
            case 2:
                buyTicket(seat, r, c, purchasedTicketsNIncome);
                selectMenu(seat, r, c, purchasedTicketsNIncome);
                break;
            case 3:
                showStatistic(purchasedTicketsNIncome, r, c, percentage, totalIncome);
                selectMenu(seat, r, c, purchasedTicketsNIncome);
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice");
                selectMenu(seat, r, c, purchasedTicketsNIncome);
                break;
        }
        
    }
    
    public static void buyTicket(char seat[][], int r, int c, int[] purchasedTicketsNIncome){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int selectedRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int selectedSeat = scanner.nextInt();
        if (selectedRow > r || selectedSeat > c){
            System.out.println("Wrong input!");
        } else {
            if(seat[selectedRow - 1][selectedSeat - 1] == 'B'){
                System.out.println("That ticket has already been purchased!");
                buyTicket(seat, r, c, purchasedTicketsNIncome);
            } else {
                seat[selectedRow - 1][selectedSeat - 1] = 'B';
                int price;
                if ( seat.length * seat[0].length < 60 ){
                    price = 10;
                } else {
                    if(selectedRow <= seat.length/2){
                        price = 10;
                    } else {
                        price = 8;
                    }
                }
                purchasedTicketsNIncome[0] = purchasedTicketsNIncome[0] + 1;
                purchasedTicketsNIncome[1] = purchasedTicketsNIncome[1] + price;
                System.out.println("Ticket price: $" + price);

            }
        }
    }           
        
    public static void showStatistic(int[] purchasedTicketsNIncome, int r, int c, float percentage, int totalIncome){
        percentage = (float)purchasedTicketsNIncome[0] * 100 /((float)r * (float)c);
        System.out.println(percentage);

        if (r * c < 60){
            totalIncome = r * c * 60;
        } else {
            totalIncome = r / 2 * c * 10 + (r - r / 2) * c * 8;
        }
        
        System.out.printf("Number of purchased tickets: %d %n", purchasedTicketsNIncome[0]);
        System.out.printf("Percentage: %.2f%% %n", percentage);
        System.out.printf("Current income: $%d %n", purchasedTicketsNIncome[1]);
        System.out.printf("Total income: $%d %n", totalIncome);
        
    }



        
    
}