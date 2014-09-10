/**
 * Example:</br>
 * 
 */
package conditiontool;

import java.sql.*;
import java.util.*;

import cgcdb.*;
import javacccode.*;

public class TCList {
	
	private CGCDB db;
	
	private Project project;
	private Standard standard;
	/**
	 * Instantiates a new TC list. Input a Connection to SQL Server to initialize a Database to further use.</br>
	 * --------------------------------------------------------</br>
	 * Ex:</br>
	 * <pre>
	 * String conUrl = "jdbc:sqlserver://123.12.12.12\\SQLEXPRESS:1433;user=;password=;";
	 * Connection con = null;
	 * try {
	 * 	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	 * 	con = DriverManager.getConnection(conUrl);
	 * } catch (ClassNotFoundException e) {
	 * 	e.printStackTrace();
	 * } catch (SQLException e) {
	 * 	e.printStackTrace();
	 * }
	 * 
	 * TCList tcList = new TCList(con, "NTUTC");
	 * </pre>
	 *
	 * @param con java.sql.Connection
	 * @param database_name the database_name
	 */
	public TCList(Connection con, String database_name){
		standard = new Standard();
		db = new CGCDB(con, database_name);

		db.loadRef_Spec(standard);
		db.loadRelease(standard);
		
	}
	
	/**
	 * Show Project Code List in Database.
	 */
	public void printProject_List(){
		ArrayList<String> projectcodelist = db.getProjectCodeList();
		System.out.println("Please input which project you want to run.");
		for(String str : projectcodelist){
		  	System.out.println(str);
		}
	}
	
	public void loadProject(String project_code){
		db.loadProject();
		project = db.getProject(project_code);
	}
	
	public void loadPics(){
		db.loadPics(project);
	}
	
	public void loadConditions(){
		db.loadConditions(standard);
	}
	
	public void loadTestcases(){
		db.loadTestcases(standard, project);
	}
	
	/*public void uploadResult(){
		db.uploadTestCase(standard, project);
		db.uploadError(project);
	}*/
	
	/**
	 * Execution the inputed Project</br>
	 * ----------------------------------</br>
	 * Ex:</br>
	 * <pre>tclist.run("LYW200");</pre>
	 *
	 * @param project_code String 
	 */
	/*public void run(String project_code){
		project = db.getProject(project_code);
		db.loadPics(project);
		db.loadConditions(standard);
		db.loadTestcases(standard, project);
		//-----------Solve Conditions----------
		run();
		//-------------------------------------
		
		System.out.println("Solving Conditions is done.");
		
		db.uploadTestCase(standard, project);
		db.uploadError(project);
		System.out.println("Solving Conditon is Done.");
		// --------------------------------------
	}*/
	
	
	private void run(){
	  	for(Condition con : standard.getCONDITION_LIST()){	
	  		ConditionSolver.solveCondition(standard, project, con, project.getERROR_LIST());
	  	}
	  	System.out.println("Solving Conditions is done.");
		
		db.uploadTestCase(standard, project);
		db.uploadError(project);
		System.out.println("Solving Conditon is Done.");
	}
	
	
	// Code Demo
	public static void main(String[] args){
		String conUrl = "jdbc:sqlserver://140.112.42.144\\SQLEXPRESS:1433;user=sa;password=bl618;";
		Connection con = null;
		
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(conUrl);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TCList tcList = new TCList(con, "NTUTC");
		
		// Load Project Metadata
		tcList.loadProject("LYW200");
		// Load Pics of Project
		tcList.loadPics();
		// Load Conditions
		tcList.loadConditions();
		// Load Conditions
		tcList.loadTestcases();
		
		// Solve Condition and Upload Result to Database
		tcList.run();
	}
	
	
}
