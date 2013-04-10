package sepm.ss13.e0828.service.exception;

public class DataSourceAccessException extends Exception {

	private static final String ERRMSG = "There was a problem accessing the datasource!";

	public DataSourceAccessException(){
		super(ERRMSG);
	}
	
}
