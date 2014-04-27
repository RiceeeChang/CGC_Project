package datastructure;

public class Condition {
	String table_spec;
	String spec_version;
	String condition_id;
	String condition_desc;
	Result result;
	boolean done;
	
	public Condition(String table_spec,
			String spec_version,
			String condition_id,
			String condition_desc){
		this.table_spec = table_spec;
		this.spec_version = spec_version;
		this.condition_id = condition_id;
		this.condition_desc = condition_desc;
		result = Result.VOID;
		
		done = false;
	}
	
	public String getCondition_ID(){
		return condition_id;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public void Done(){
		done = true;
	}
	
	public String getCondition_desc(){
		return condition_desc;
	}
	public void setResult(Result result){
		this.result = result;
	}
	public Result getResult(){
		return result;
	}
	
	public String getTable_spec(){
		return table_spec;
	}
	
	public String getSpec_version(){
		return spec_version;
	}
}
