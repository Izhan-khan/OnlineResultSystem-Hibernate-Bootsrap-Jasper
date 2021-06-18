package exception;

/**
 * Database Exception thrown when a Database Error occurred
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class DatabaseException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @param msg
     *            error message
     */
    public DatabaseException(String msg) {
        super(msg);
    }

}
