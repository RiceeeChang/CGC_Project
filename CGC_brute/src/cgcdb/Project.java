package cgcdb;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Project.
 * 
 * Project project = new Project("Project Code");
 * project.set2GInformation( ... );
 * project.set3GInformation( ... );
 * project.setLTEInformation( ... );
 * project.setPic( ... );
 * project.setCondition( ... );
 */
public class Project {
	
	/** The project_code. */
	private String project_code;
	
	/** The _2 g. 
	 * */
	private _2GInfo _2G;
	
	/** The _3 g. */
	private _3GInfo _3G;
	
	/** The lte. */
	private LTEInfo LTE;
	
	/** The pics. */
	protected HashMap<String, HashMap<String, Pic>> PICS;// HashMap<"Table Spec", HashMap<"Table Id + Item Id", Pic>>
	
	/** The conditions. */
	protected HashMap<String, HashMap<String, Condition>> CONDITIONS;	
	
	/** The error list. */
	private ArrayList<ConditionError> ERROR_LIST;
	
	/**
	 * Instantiates a new project.
	 *
	 * @param project_code the project_code
	 */
	public Project(String project_code){
		this.project_code = project_code;
		
		CONDITIONS = new HashMap<String, HashMap<String, Condition>>();
		PICS = new HashMap<String, HashMap<String, Pic>>();
		
		ERROR_LIST = new ArrayList<ConditionError>();
	}
	
	/**
	 * Gets the project_ code.
	 *
	 * @return the project_ code
	 */
	public String getProject_Code(){
		return project_code;
	}
	
	/**
	 * Set2 g information.
	 *
	 * @param gcf_version the gcf_version
	 * @param ptcrb_version the ptcrb_version
	 * @param gsm the gsm
	 * @param gprs the gprs
	 * @param egprs the egprs
	 * @param amr the amr
	 * @param satk the satk
	 * @param dtm the dtm
	 */
	public void set2GInformation(
			String gcf_version,
			String ptcrb_version,
			String gsm,
			String gprs,
			String egprs,
			String amr,
			String satk,
			String dtm
	){
		
		_2G = new _2GInfo(
				gcf_version,
				ptcrb_version,
				gsm,
				gprs,
				egprs,
				amr,
				satk,
				dtm
		);
	}
	
	/**
	 * Set3 g information.
	 *
	 * @param gcf_version the gcf_version
	 * @param ptcrb_version the ptcrb_version
	 * @param category the category
	 * @param hsdpa_category the hsdpa_category
	 * @param primary_spec the primary_spec
	 * @param audio_category the audio_category
	 */
	public void set3GInformation(
			String gcf_version,
			String ptcrb_version,
			String category,
			String hsdpa_category,
			String primary_spec,
			String audio_category){
		
		_3G = new _3GInfo(
				gcf_version,
				ptcrb_version,
				category,
				hsdpa_category,
				primary_spec,
				audio_category
		);
	}
	
	/**
	 * Sets the lte information.
	 *
	 * @param gcf_version the gcf_version
	 * @param ptcrb_version the ptcrb_version
	 * @param category the category
	 * @param hsdpa_category the hsdpa_category
	 * @param primary_spec the primary_spec
	 * @param band the band
	 */
	public void setLTEInformation(
			String gcf_version,
			String ptcrb_version,
			String category,
			String hsdpa_category,
			String primary_spec,
			String band){
		
		LTE = new LTEInfo(
				gcf_version,
				ptcrb_version,
				category,
				hsdpa_category,
				primary_spec,
				band
		);
	}
	
	/**
	 * Sets the conditions.
	 *
	 * @param CONDITIONS the conditions
	 */
	public void setConditions(HashMap<String, HashMap<String, Condition>> CONDITIONS){
		this.CONDITIONS = CONDITIONS;
	}
	
	/**
	 * Sets the pics.
	 *
	 * @param PICS the pics
	 */
	public void setPICS(HashMap<String, HashMap<String, Pic>> PICS){
		this.PICS = PICS;
	}
	
	/**
	 * Gets the pic.
	 *
	 * @param picname the picname
	 * @param table_spec the table_spec
	 * @return the pic
	 */
	public Pic getPic(String picname, String table_spec){
		HashMap<String, Pic> table;
		
		if( (table = PICS.get(table_spec)) == null)
			return null;
		return table.get(picname);
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
	 * Gets the 2 g info.
	 *
	 * @return the 2 g info
	 */
	public _2GInfo get2GInfo(){
		return _2G;
		
	}
	
	/**
	 * Gets the 3 g info.
	 *
	 * @return the 3 g info
	 */
	public _3GInfo get3GInfo(){
		return _3G;
		
	}
	
	/**
	 * Gets the LTE info.
	 *
	 * @return the LTE info
	 */
	public LTEInfo getLTEInfo(){
		return LTE;
	}
	
	/**
	 * Gets the error list.
	 *
	 * @return the error list
	 */
	public ArrayList<ConditionError> getERROR_LIST() {
		return ERROR_LIST;
	}

	/**
	 * Adds the error.
	 *
	 * @param error the error
	 */
	public void addError(ConditionError error){
		ERROR_LIST.add(error);
	}
}
