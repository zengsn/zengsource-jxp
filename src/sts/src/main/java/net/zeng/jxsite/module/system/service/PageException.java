/**
 * 
 */
package net.zeng.jxsite.module.system.service;

/**
 * @author snzeng
 *
 */
public class PageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PageException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public PageException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PageException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PageException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
