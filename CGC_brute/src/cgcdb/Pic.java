package cgcdb;

// TODO: Auto-generated Javadoc
/**
 * The Class Pic.
 */
public class Pic {
	
	/** The project_code. */
	String project_code;
	
	/** The table_spec. */
	String table_spec;
	
	/** The table_id. */
	String table_id;
	
	/** The item_id. */
	String item_id;
	
	/** The support. */
	Boolean support;

	/**
	 * Instantiates a new pic.
	 *
	 * @param project_code the project_code
	 * @param table_spec the table_spec
	 * @param table_id the table_id
	 * @param item_id the item_id
	 * @param support the support
	 */
	public Pic(String project_code,
			String table_spec,
			String table_id,
			String item_id,
			String support){
		
		this.project_code = project_code.trim();
		this.table_spec = table_spec.trim();
		this.table_id = table_id.trim();
		this.item_id = item_id.trim();
		
		if(support.contains("Y"))
			this.support = true;
		else
			this.support = false;
	}
	
	/**
	 * Gets the table_spec.
	 *
	 * @return the table_spec
	 */
	public String getTable_spec(){
		return table_spec;
	}
	
	/**
	 * Gets the table_ id.
	 *
	 * @return the table_ id
	 */
	public String getTable_ID(){
		return table_id;
	}
	
	/**
	 * Gets the item_ id.
	 *
	 * @return the item_ id
	 */
	public String getItem_ID(){
		return item_id;
	}
	
	/**
	 * Gets the support.
	 *
	 * @return the support
	 */
	public boolean getSupport(){
		return support;
	}
}
