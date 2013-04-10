package sepm.ss13.e0828.dao.helper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** The class has the ability to generate a Select or Update
 * query using an Array of values that is given by the caller
 * @author Maurizio Rinder u0828852
 */

public class DBQueryGenerationHelper {

	/** the fields of the persistence unit */
	private String[] fields = null;
	/** the entity that is in use by the query */
	private String query = "";
	/** a string that is used if a groupby statement is needed */
	private String grouping = "";
	
	public enum QueryMode{
		SELECT, UPDATE
	}

	public DBQueryGenerationHelper(String entitydefinition, String[] fields, String aggregation){
		if( fields.length > 0 ){
			String queryFields = getFieldString(fields);
			query = "Select " + queryFields;
			grouping = queryFields;
			if( aggregation != null && !aggregation.isEmpty() ){
				query += "," + aggregation;

			}
			query += " from " + entitydefinition + " ";
			this.fields =fields;
		}
	}

	/** should be used if there will be a creation of an update query or a select query with filter ability */
	public DBQueryGenerationHelper(String entitydefinition, String[] fields,  QueryMode mode){
		if(fields.length > 0 ){
			switch( mode ){
			case SELECT: query = "Select " + getFieldString(fields) + " from " + entitydefinition + " "; break;
			case UPDATE: query = "Update " + entitydefinition + " set "; break;
			}
			this.fields = fields;
		}
	}

	private String getFieldString( String[] fields ){
		String fieldString = fields[0];
		for( int i = 1; i < fields.length; i++ ){
			fieldString += "," + fields[i];
		}
		return fieldString;
	}


	/** Returns a select query including the given values
	 * 
	 * @param values the values that are going to be processed into the query
	 * @return the resulting query, ready for execution
	 */
	public String prepareSelectQuery(Object values[]) {

		List<String> queryparts = getQueryparts(values) ;

		if( queryparts != null && queryparts.size() > 0 ){ 
			query += "where "; query = iterateQueryparts(queryparts, query, "AND"); 
		}

		if( grouping != null && !grouping.isEmpty()){
			query += " group by " + grouping;
		}

		query += ";";
		return query;
	}

	/** Returns an update query including the given values
	 * 
	 * @param values the values that are going to be processed into the query
	 * @param searchField the fields that determines what has to be updated
	 * @return the resulting query, ready for execution
	 * @throws EmptyFieldsException when no fields were given
	 */
	public String prepareUpdateQuery(Object values[], String searchField) throws EmptyFieldsException{
		List<String> queryparts = getQueryparts(values);

		if( queryparts == null || queryparts.size() <= 0 ){ throw new EmptyFieldsException(); }
		else{ query = iterateQueryparts(queryparts, query, ","); }	

		query += " where " + searchField + " = ?;";
		return query;
	}

	/** Returns an update query including the given values
	 * 
	 * @param values the values that are going to be processed into the query
	 * @param fields the fields that should be updated
	 * @param searchField the fields that determines what has to be updated
	 * @return the resulting query, ready for execution
	 * @throws NoFieldsException when no fields were given
	 */
	/*	public String prepareUpdateQuery(Object values[], String fields[], String searchField) throws NoFieldsException{
		this.fields = fields;
		return prepareUpdateQuery(values, searchField);
	}
	 */

	/** runs through the whole query strings and concatenates them with a given connector
	 * 
	 * @param queryparts the parts that have to be concatenated
	 * @param query the query where the queryparts should get included
	 * @param connector the connector with which the queryparts get connected
	 * @return a full concatenation of all queryparts
	 */
	private String iterateQueryparts(List<String> queryparts, String query, String connector){
		if( queryparts.size() > 0 ){

			Iterator<String> it = queryparts.iterator();
			for( int i = 0; i < (queryparts.size() -1); i++ ){
				query += it.next() + " " + connector + " ";
			}
			query += it.next();
		}

		return query;
	}

	/** extracts all values for processing the query
	 * 
	 * @param values the values given by the caller
	 * @return a list of queryparts that can be concatenated 
	 */
	private List<String> getQueryparts(Object values[]){
		LinkedList<String> queryparts = null;
		queryparts = new LinkedList<String>() ;

		for( int i = 0; i < ( values.length); i++ ){
			if( values[i] != null ){
				queryparts.add(fields[i] + " = ?");
			}
		}

		return queryparts;
	}
}
