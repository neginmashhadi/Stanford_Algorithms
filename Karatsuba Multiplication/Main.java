import java.math.BigInteger;

class KaratsubaMultiplication
{
    public static long karatsuba(long x, long y)
    {
        //base case
        //the base case is when we are handling small numbers which we use the third grade algorithm that we learned.
        if(x < 10 && y < 10)
            return x * y;

        int noOneLength = getSize(x);
        int noTwoLength = getSize(y);

        // Finding maximum length from both numbers
        // using math library max function
        int maxNum
                = Math.max(noOneLength, noTwoLength);

        // Rounding up the divided Max length
        Integer middle
                = (maxNum / 2) + (maxNum % 2);

        // Multiplier
        long maxNumLengthTen
                = (long)Math.pow(10, middle);

        // Compute the expressions
        long a = x / maxNumLengthTen;
        long b = x % maxNumLengthTen;
        long c = y / maxNumLengthTen;
        long d = y % maxNumLengthTen;


        // Compute all mutilpying variables
        // needed to get the multiplication
        long step0 = karatsuba(a, c);
        long step1 = karatsuba(a + b, c + d);
        long step2 = karatsuba(b, d);

        long ans = (step0 * (long)Math.pow(10, middle * 2) +
                ((step1 - step0 - step2) * (long)Math.pow(10, middle) + step2));

        return ans;
    }

//UPENN design
    public static BigInteger karatsuba2(BigInteger x, BigInteger y)
    {
        //base case
        int N = Math.max(x.bitLength(),y.bitLength());
        if( N <= 2000 )
            return x.multiply(y);

        int middle = (N / 2) + (N % 2); //we do this to account for odd and even integers.
        // x = a + 2^N b,   y = c + 2^N d
        BigInteger b = x.shiftRight(middle);
        BigInteger a = x.subtract(b.shiftLeft(middle));
        BigInteger d = y.shiftRight(middle);
        BigInteger c = y.subtract(d.shiftLeft(middle));

        BigInteger ac = karatsuba2(a ,c );
        BigInteger abcd = karatsuba2(a.add(b), c.add(d));
        BigInteger bd = karatsuba2(b , d);
        //Gauss' Trick
        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(middle).add(bd.shiftLeft(N)));
    }

    public static int getSize(long n)
    {
        int len = 0;
        while(n > 0)
        {
            len++;
            n /= 10;
        }
        return len;
    }

    public static void main(String[] args) {
        long num1 = 1234;
        long num2 = 5678;

        BigInteger x = new BigInteger("1234");
        BigInteger y = new BigInteger("5678");

        long expectedProduct = num1 * num2;
        long actualProduct = karatsuba(num1, num2);
        BigInteger secondProduct = karatsuba2(x, y);

        // Printing the expected and corresponding actual product
        System.out.println("Expected 1 : " + expectedProduct);
        System.out.println("karatsuba 1 : " + actualProduct);
        System.out.println("karatsuba 2 : " + secondProduct + "\n\n");
    }
}
