package sepm.ss13.e0828.gui.exception;

import sepm.ss13.e0828.gui.MessagesConfig;

public class UnknownTherapytypeException extends Exception {

	private static final String ERRMSG = MessagesConfig.ERRUNKNOWNTHERAPYTYPE;
	
	public UnknownTherapytypeException(){
		super(ERRMSG);
	}
	
}
