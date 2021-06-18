package exception;

/**
 * Application Exception thrown when a Application Exception occurs
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class ApplicationException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @param msg
     *  error message
     */
    public ApplicationException(String msg) {
        super(msg);
    }

}
