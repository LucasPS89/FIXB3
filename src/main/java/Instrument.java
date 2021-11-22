import java.util.HashMap;
import java.util.Random;

/**
 * Class to Generate Instruments codes
 */

public class Instrument {
    //Map of ISIN codes
    HashMap<String, String> isinCodes = new HashMap<String, String>();

    Instrument(){
        this.isinCodes.put("ITUB", "BRITUBACNPR6");
        this.isinCodes.put("BBDC", "BRBBDCACNPR6");
        this.isinCodes.put("VALE", "BRVALEACNPR6");
        this.isinCodes.put("PETR", "BRPETRACNPR6");
        this.isinCodes.put("GOLL", "BRGOLLACNPR6");
        this.isinCodes.put("ABEV", "BRABEVACNPR6");
        this.isinCodes.put("BBAS", "BRBBASACNPR6");
        this.isinCodes.put("B3SA", "BRB3SAACNPR6");
        this.isinCodes.put("MGLU", "BRMGLUACNPR6");
        this.isinCodes.put("EQTL", "BREQTLACNPR6");
    }

    /**
     * Get a Random Instrument C(4)
     * @return
     */
    public String GetRandom(){
        Random rand = new Random();
        Object[] values = this.isinCodes.keySet().toArray();
        String randomValue = values[rand.nextInt(values.length)].toString();

        return randomValue;
    }

    /**
     * Returns the ISIN code of a given instrument
     * @param instrument
     * @return
     */
    public String GetISINCode(String instrument){
        return this.isinCodes.get(instrument);
    }
}
