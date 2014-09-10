package cgcdb;

// TODO: Auto-generated Javadoc
/**
 * The Class Condition.
 */
public class Condition {
	
	/** The protocol. */
	private Protocol protocol;
	
	/** The table_spec. */
	private String table_spec;
	
	/** The condition_id. */
	private String condition_id;
	
	/** The condition_desc. */
	private String condition_desc;
	
	/** The result. */
	private Result result;
	
	/** The done. */
	private boolean done;
	
	/**
	 * Instantiates a new condition.
	 *
	 * @param protocol the protocol
	 * @param table_spec the table_spec
	 * @param condition_id the condition_id
	 * @param condition_desc the condition_desc
	 */
	public Condition(
			Protocol protocol,
			String table_spec,
			String condition_id,
			String condition_desc){
		
		this.protocol = protocol;
		this.table_spec = table_spec.trim();
		this.condition_id = condition_id.trim();
		this.condition_desc = condition_desc.trim();
		result = Result.VOID;	
		done = false;
	}
	
	/**
	 * Gets the protocol.
	 *
	 * @return the protocol
	 */
	public Protocol getProtocol(){
		return protocol;
	}
	
	/**
	 * Gets the table_ spec.
	 *
	 * @return the table_ spec
	 */
	public String getTable_Spec(){
		return table_spec;
	}
	
	/**
	 * Gets the condition_ id.
	 *
	 * @return the condition_ id
	 */
	public String getCondition_ID(){
		return condition_id;
	}
	
	/**
	 * Gets the condition_desc.
	 *
	 * @return the condition_desc
	 */
	public String getCondition_desc(){
		return condition_desc;
	}
	
	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(Result result){
		this.result = result;
	}
	
	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public Result getResult(){
		return result;
	}
	
	/**
	 * Checks if is done.
	 *
	 * @return true, if is done
	 */
	public boolean isDone(){
		return done;
	}
	
	/**
	 * Done.
	 */
	public void Done(){
		done = true;
	}
}
