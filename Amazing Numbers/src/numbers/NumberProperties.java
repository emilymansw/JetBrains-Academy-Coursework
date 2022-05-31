package numbers;

import java.util.ArrayList;

enum NumberProperties {
    EVEN{
        @Override
        public Boolean checkIfTrue(long num){
            if(num % 2 == 0){
                return true;
            }
            return false;
        }
    }, ODD{
        @Override
        public Boolean checkIfTrue(long num){
            if(num % 2 == 1){
                return true;
            }
            return false;
        }
    }, BUZZ{
        @Override
        public Boolean checkIfTrue(long num){
            long initialLastDigit = num % 10L;
            long remaining = num;
            while (remaining > 10L){
                long lastDigit = remaining % 10L;
                remaining = (remaining / 10L - lastDigit * 2L);
            }
            if (initialLastDigit == 7L){
                return true;
            }
            if (remaining % 7 == 0 || remaining == 0){
                return true;
            }
            return false;
        }
    }, DUCK{
        @Override
        public Boolean checkIfTrue(long num){
            while (num >= 10) {
                if (num % 10 == 0) {
                    return true;
                }
                num /= 10;
            }
            return false;
        }
    }, PALINDROMIC{
        @Override
        public Boolean checkIfTrue(long num){
            StringBuilder numStr = new StringBuilder();
            numStr.append(num);
            StringBuilder reversedNumStr = new StringBuilder();
            reversedNumStr.append(num).reverse();
            if(numStr.toString().equals(reversedNumStr.toString())){
                return true;
            }
            return false;
        }
    }, GAPFUL{
        @Override
        public Boolean checkIfTrue(long num){
            if(num > 99){
                StringBuilder numStr = new StringBuilder();
                numStr.append(num);
                int divisor = Character.getNumericValue(numStr.charAt(0)) * 10 + Character.getNumericValue(numStr.charAt(numStr.length()-1));
                if(num % divisor == 0){
                    return true;
                }
            }
            return false;
        }
    }, SPY{
        @Override
        public Boolean checkIfTrue(long num){
            long productOfAllDigits = 1;
            long sumOfAllDigits = 0;
            while (num >= 10){
                sumOfAllDigits = sumOfAllDigits + num % 10;
                productOfAllDigits = productOfAllDigits * (num % 10);
                num /= 10;
            }
            sumOfAllDigits = sumOfAllDigits + num % 10;
            productOfAllDigits = productOfAllDigits * (num % 10);

            if(productOfAllDigits == sumOfAllDigits){
                return true;
            }
            return false;
        }
    }, SQUARE{
        @Override
        public Boolean checkIfTrue(long num){
            double squareRoot = Math.sqrt(num);
            if(squareRoot % 1 > 0){
                return false;
            }
            if(squareRoot * squareRoot == num){
                return true;
            }
            return false;
        }
    }, SUNNY{
        @Override
        public Boolean checkIfTrue(long num){
            return SQUARE.checkIfTrue(num + 1);
        }
    }, JUMPING{
        @Override
        public Boolean checkIfTrue(long num){
            if (num < 10) {
                return true;
            }
            long previous = num % 10;
            num /= 10;
            while (num >= 10) {
                if (Math.abs(previous - num % 10) != 1){
                    return false;
                }
                previous = num % 10;
                num /= 10;
            }
            if (Math.abs(previous - num % 10) != 1){
                return false;
            }
            return true;
        }
    }, HAPPY{
        @Override
        public Boolean checkIfTrue(long num) {
            if(num < 10){
                num = (long)Math.pow(num,2);
            }
            while(num >= 10){
                ArrayList<Long> digits = new ArrayList<>();
                while (num >= 10){
                    digits.add(num % 10);
                    num = num / 10;
                }
                digits.add(num % 10);
                num = 0;
                for (Long digit : digits){
                    num += Math.pow(digit, 2);
                }
            }
            if(num == 1){
                return true;
            }
            return false;
        }
    }, SAD{
        @Override
        public Boolean checkIfTrue(long num) {
            return !HAPPY.checkIfTrue(num);
        }
    };

    public abstract Boolean checkIfTrue(long num);

    }
