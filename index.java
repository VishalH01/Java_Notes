import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class index {
    public static void main(String[] args) {
        try {
            int a = 5;
            char[] b ={'c','s'};
            System.out.println(Arrays.toString(b));
            System.out.println("----------------");
            System.out.println(a/0);
        } catch (ArithmeticException e) {
            // TODO: handle exception
            System.out.println("You can't divide by 0 " + e.toString());
        } finally {
            System.out.println("Finally block executed!");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("README.md"))) {
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }





        String s1 = new String("hello");
        String s2 = new String("hello");
        String s3 = "hello";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        // --- Real Example of throw and throws ---
        System.out.println("\n--- Real Example of throw and throws ---");
        double myBalance = 100.0;
        
        // 1. Calling a method that has "throws" in its signature requires a try-catch block!
        try {
            System.out.println("Attempting to withdraw $50...");
            withdrawMoney(myBalance, 50.0); // This will succeed
            
            System.out.println("\nAttempting to withdraw $150...");
            withdrawMoney(myBalance, 150.0); // This will fail and throw an exception!
            
        } catch (InsufficientFundsException e) {
            // 2. We catch and handle the exception that was "thrown" from the method
            System.out.println("Transaction Failed: " + e.getMessage());
        }
    }

    /**
     * This method uses the 'throws' keyword in its signature.
     * It declares: "I might throw an InsufficientFundsException. The caller MUST handle it!"
     */
    public static void withdrawMoney(double balance, double amount) throws InsufficientFundsException {
        if (amount > balance) {
            // The 'throw' keyword is used to actively throw a new exception object in the code execution path.
            throw new InsufficientFundsException("Cannot withdraw $" + amount + ". You only have $" + balance);
        }
        System.out.println("Success! You withdrew $" + amount + ". Remaining balance: $" + (balance - amount));
    }
}

// Custom Checked Exception
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
