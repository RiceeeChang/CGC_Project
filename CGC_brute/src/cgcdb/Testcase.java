package cgcdb;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Testcase.
 */
public class Testcase {
	
	// Normal Column in Test case
	/** The table_spec. */
	String table_spec;
	
	/** The clause_id. */
	String clause_id;
	
	/** The title. */
	String title;
	
	/** The release_version. */
	String release_version;
	
	/** The application. */
	String application;
	
	/** The comment. */
	String comment;
	
	// SIM
	/** The releases. */
	ArrayList<String> releases;
	
	/** The redundancy. */
	String redundancy;
	
	/** The r_condition. */
	String r_condition;

	// Normal Test case Constructor
	/**
	 * Instantiates a new testcase.
	 * ¤£¬OSIMªºTestcase
	 *
	 * @param table_spec the table_spec
	 * @param clause_id the clause_id
	 * @param title the title
	 * @param release_version the release_version
	 * @param application the application
	 * @param comment the comment
	 */
	public Testcase(String table_spec, 
			String clause_id, 
			String title,
			String release_version,
			String application, 
			String comment
			) {
		this.table_spec = table_spec;
		this.clause_id = clause_id;
		this.title = title;
		this.release_version = release_version;
		this.application = application;
		this.comment = comment;
	}
	
	// SIM Test case Constructor
	/**
	 * Instantiates a new testcase.
	 * SIM Testcase
	 *
	 * @param table_spec the table_spec
	 * @param clause_id the clause_id
	 * @param title the title
	 * @param release_version the release_version
	 * @param application the application
	 * @param comment the comment
	 * @param releases the releases
	 * @param redundancy the redundancy
	 * @param r_condition the r_condition
	 */
	public Testcase(String table_spec, 
			String clause_id, 
			String title,
			String release_version,
			String application, 
			String comment,
			ArrayList<String> releases, 
			String redundancy,
			String r_condition) {
		super();
		this.table_spec = table_spec;
		this.clause_id = clause_id;
		this.title = title;
		this.release_version = release_version;
		this.application = application;
		this.comment = comment;
		this.releases = releases;
		this.redundancy = redundancy;
		this.r_condition = r_condition;
	}



	/**
	 * Gets the release_version.
	 *
	 * @return the release_version
	 */
	public String getRelease_version() {
		return release_version;
	}

	/**
	 * Gets the table_spec.
	 *
	 * @return the table_spec
	 */
	public String getTable_spec() {
		return table_spec;
	}
	
	/**
	 * Sets the table_spec.
	 *
	 * @param table_spec the new table_spec
	 */
	public void setTable_spec(String table_spec) {
		this.table_spec = table_spec;
	}
	
	/**
	 * Gets the clause_id.
	 *
	 * @return the clause_id
	 */
	public String getClause_id() {
		return clause_id;
	}
	
	/**
	 * Sets the clause_id.
	 *
	 * @param clause_id the new clause_id
	 */
	public void setClause_id(String clause_id) {
		this.clause_id = clause_id;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the release.
	 *
	 * @return the release
	 */
	public ArrayList<String> getRelease() {
		return releases;
	}
	
	/**
	 * Sets the release.
	 *
	 * @param releases the new release
	 */
	public void setRelease(ArrayList<String> releases) {
		this.releases = releases;
	}
	
	/**
	 * Gets the application.
	 *
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}
	
	/**
	 * Sets the application.
	 *
	 * @param application the new application
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	
	/**
	 * Gets the redundancy.
	 *
	 * @return the redundancy
	 */
	public String getRedundancy() {
		return redundancy;
	}
	
	/**
	 * Sets the redundancy.
	 *
	 * @param redundancy the new redundancy
	 */
	public void setRedundancy(String redundancy) {
		this.redundancy = redundancy;
	}
	
	/**
	 * Gets the r_condition.
	 *
	 * @return the r_condition
	 */
	public String getR_condition() {
		return r_condition;
	}
	
	/**
	 * Sets the r_condition.
	 *
	 * @param r_condition the new r_condition
	 */
	public void setR_condition(String r_condition) {
		this.r_condition = r_condition;
	}
}
