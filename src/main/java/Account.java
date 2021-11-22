import java.util.Random;


public class Account {
    //List of Accounts in DB
    int[] accounts = new int[10];

    Account(){
        Random rand = new Random();
        for (int i=0; i<10; i++) {
            this.accounts[i] = rand.nextInt(1100000000);
        }
    }

    /**
     * Get a Random Client Account
     * @return
     */
    public int GetRandom(){
        Random rand = new Random();
        int[] values = this.accounts;
        int randomValue = values[rand.nextInt(values.length)];

        return randomValue;
    }
}
