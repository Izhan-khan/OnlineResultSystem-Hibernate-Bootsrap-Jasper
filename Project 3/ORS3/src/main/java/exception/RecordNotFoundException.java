package exception;

/**
 * Record Not Found Exception thrown when Record is not found
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class RecordNotFoundException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @param msg
     *            error message
     */
    public RecordNotFoundException(String msg) {
        super(msg);
    }

}
