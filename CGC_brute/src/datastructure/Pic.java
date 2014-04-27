package datastructure;

public class Pic {
	String project_code;
	String table_spec;
	String spec_version;
	String table_id;
	String item_id;
	Boolean support;

	
	
	

	public Pic(String project_code,
			String table_spec,
			String spec_version,
			String table_id,
			String item_id,
			String support){
		
		this.project_code = project_code;
		this.table_spec = table_spec;
		this.spec_version = spec_version;
		this.table_id = table_id;
		this.item_id = item_id;
		
		if(support.contains("Y"))
			this.support = true;
		else
			this.support = false;
	}
	
	public boolean getSupport(){
		return support;
	}
	
	public String getTable_ID(){
		return table_id;
	}
	
	public String getItem_ID(){
		return item_id;
	}
	
	public String getTable_Spec(){
		return table_spec;
	}
	
	public String getSpec_Version(){
		return spec_version;
	}
}
