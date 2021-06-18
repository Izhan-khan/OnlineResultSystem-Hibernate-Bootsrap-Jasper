package exception;

/**
 * Duplicate Record Exception thrown when a duplicate record occurred
 *
 * @author SUNRAYS Technologies
 * @version 1.0
 * 
 *
 */

public class DuplicateRecordException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @param msg
     *            error message
     */
    public DuplicateRecordException(String msg) {
        super(msg);
    }

}
