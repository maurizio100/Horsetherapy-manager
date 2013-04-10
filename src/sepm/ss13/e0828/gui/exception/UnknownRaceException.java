package sepm.ss13.e0828.gui.exception;

import sepm.ss13.e0828.gui.MessagesConfig;

public class UnknownRaceException extends Exception {

private static final String ERRMSG = MessagesConfig.ERRUNKNOWNHORSERACE;
	
	public UnknownRaceException(){
		super(ERRMSG);
	}
}
