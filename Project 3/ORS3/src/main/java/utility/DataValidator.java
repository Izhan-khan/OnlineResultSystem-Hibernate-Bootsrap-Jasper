package utility;
 
import java.util.Date;
 
/**
 * This class validates input data
 *  
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 */
 
public class DataValidator {
 
    /**
     * Checks if value is Null
     *  
     * @param val
     * @return
     */
    public static boolean isNull(String val) {
        if (val == null || val.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
 
    /**
     * Checks if value is NOT Null
     *  
     * @param val
     * @return
     */
    public static boolean isNotNull(String val) {
        return !isNull(val);
    }
 
    /**
     * Checks if value is an Integer
     *  
     * @param val
     * @return
     */
 
    public static boolean isInteger(String val) {
 
        if (isNotNull(val)) {
            try {
                Integer.parseInt(val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
 
        } else {
            return false;
        }
    }
 
    /**
     * Checks if value is Long
     *  
     * @param val
     * @return
     */
    public static boolean isLong(String val) {
        if (isNotNull(val)) {
            try {
                Long.parseLong(val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
 
        } else {
            return false;
        }
    }
 
    /**
     * Checks if value is valid Email ID
     *  
     * @param val
     * @return
     */
    public static boolean isEmail(String val) {
 
        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
        if (isNotNull(val)) {
            try {
                return val.matches(emailreg);
            } catch (NumberFormatException e) {
                return false;
            }
 
        } else {
            return false;
        }
    }
 
    /**
     * Checks if value is Date
     *  
     * @param val
     * @return
     */
    public static boolean isDate(String val) {
        Date d = null;
        if (isNotNull(val)) {
            d = DataUtility.getDate(val);
        }
        return d != null;
    }
 
    
    /**
     * Test above methods
     *  
     * @param args
     */
    public static void main(String[] args) {
 
        System.out.println(isDate("12/35/2014"));
      
 
    }
 
}