package cgcdb;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class Standard.
 */
public class Standard {
	
	/** The project code list. */
	protected ArrayList<String> PROJECT_CODE_LIST;
	
	/** The projects. */
	protected HashMap<String, Project> PROJECTS;
	
	/** The ref spec. */
	protected HashMap<String, String> REF_SPEC;
	
	/** The release list. */
	protected ArrayList<String> RELEASE_LIST;
	
	/** The release. */
	protected HashMap<String, Integer> RELEASE;
	
	
	/** The condition list. */
	protected ArrayList<Condition> CONDITION_LIST;
	
	/** The conditions. */
	protected HashMap<String, HashMap<String, Condition>> CONDITIONS;	
	
	/** The testcases. */
	protected ArrayList<Testcase> TESTCASES;
	
	

	/**
	 * Instantiates a new standard.
	 */
	public Standard(){
		CONDITION_LIST = new ArrayList<Condition>();
		CONDITIONS = new HashMap<String, HashMap<String, Condition>>();
		TESTCASES = new ArrayList<Testcase>();
	}
	
	/**
	 * Sets the condition list.
	 * 
	 *
	 * @param condition_list the new condition list
	 */
	public void setCONDITION_LIST(ArrayList<Condition> condition_list){
		this.CONDITION_LIST = condition_list;
	}
	
	/**
	 * Sets the conditions.
	 *
	 * @param CONDITIONS the conditions
	 */
	public void setCONDITIONS(HashMap<String, HashMap<String, Condition>> CONDITIONS){
		this.CONDITIONS = CONDITIONS;
	}
	
	/**
	 * Sets the testcases.
	 *
	 * @param TESTCASES the new testcases
	 */
	public void setTESTCASES(ArrayList<Testcase> TESTCASES){
		this.TESTCASES = TESTCASES;
	}
	
	/**
	 * Gets the condition list.
	 *
	 * @return the condition list
	 */
	public ArrayList<Condition> getCONDITION_LIST() {
		return CONDITION_LIST;
	}
	
	/**
	 * Gets the condition.
	 *
	 * @param condition_id the condition_id
	 * @param table_spec the table_spec
	 * @return the condition
	 */
	public Condition getCondition(String condition_id, String table_spec){
		HashMap<String, Condition> table;
		if( (table = CONDITIONS.get(table_spec)) == null)
			return null;
		return table.get(condition_id);
	}
	
	/**
	 * Gets the ref spec.
	 *
	 * @param ref the ref
	 * @return the ref spec
	 */
	public String getRefSpec(String ref){
		return REF_SPEC.get(ref);
	}
}
