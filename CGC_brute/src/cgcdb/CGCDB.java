package cgcdb;

import java.sql.*;
import java.util.*;

import datastructure.*;




public class CGCDB {
	
	private Statement stmt;
	
	private HashMap<String, Condition> CONDITIONS_2G;
	private HashMap<String, Condition> CONDITIONS_3G;
	private HashMap<String, Condition> CONDITIONS_LTE;
	
	public HashMap<String, HashMap<String, Pic>> PICS;
	
	public CGCDB(){
		String conUrl = "jdbc:sqlserver://140.112.42.141\\SQLEXPRESS:1433;user=sa;password=bl618;";
		Connection con = null;
		try{//註冊JODBC類
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(conUrl);
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		PICS = new HashMap<String, HashMap<String, Pic>>();
		getPics();
		getConditions();
	}
	
	public HashMap<String, Condition> getConditions(Protocol protocol){
		switch(protocol){
		case _2G:
			return CONDITIONS_2G;

		case _3G:
			return CONDITIONS_3G;

		case _LTE:
			return CONDITIONS_LTE;
		default:
			return null;
		} 
	}
	
	private void getPics(){
		
		get2GPics();
		get3GPics();
		getLTEPics();
	}
	
	private void getConditions(){
		int num = 0;
		num+=get2GCondtion();
		num+=get3GCondtion();
		num+=getLTECondtion();
		
		System.out.println("input Condition:" + num);
	}
	
	private void get2GPics(){
		HashMap<String, Pic> PICS_2G = new HashMap<String, Pic>();
		
		try {
			String SQL = "SELECT ALL "
					+ "[PROJECT_CODE],"
					+ "[TABLE_ID],"
					+ "[ITEM_ID],"
					+ "[SUPPORT]"
					+ "FROM [NTUTC].[dbo].[PSPICS]";
			ResultSet rs = stmt.executeQuery(SQL);
			
			while(rs.next()){
				Pic p = new Pic(

						rs.getString("PROJECT_CODE"),
						null,
						null,
						rs.getString("TABLE_ID"),
						rs.getString("ITEM_ID"),
						rs.getString("SUPPORT")
				);
				PICS_2G.put(p.getTable_ID() + "/" + p.getItem_ID(), p);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PICS.put("2G", PICS_2G);
	}
	
	private void get3GPics(){
		HashMap<String, Pic> PICS_3G = new HashMap<String, Pic>();
		
		String SQL = "SELECT ALL "
				+ "[IDENTIFY_INPUTPICS],"
				+ "[PROJECT_CODE],"
				+ "[IDENTIFY_PICS],"
				+ "[TABLE_SPEC],"
				+ "[SPEC_VERSION],"
				+ "[TABLE_ID],"
				+ "[ITEM_ID],"
				+ "[SUPPORT]"
				+ "FROM [NTUTC].[dbo].[PICS_3G_DATA]";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			
			while(rs.next()){
				Pic p = new Pic(
						rs.getString("PROJECT_CODE"),
						rs.getString("TABLE_SPEC"),
						rs.getString("SPEC_VERSION"),
						rs.getString("TABLE_ID"),
						rs.getString("ITEM_ID"),
						rs.getString("SUPPORT")
				);
				
				if( (PICS_3G = PICS.get(p.getTable_Spec())) == null ){;
					PICS_3G = new HashMap<String, Pic>();
					PICS.put(p.getTable_Spec(), PICS_3G);
				}
				
				PICS_3G.put(p.getTable_ID() + "/" + p.getItem_ID(), p);
			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	private void getLTEPics(){
		HashMap<String, Pic> PICS_LTE = new HashMap<String, Pic>();
		
		String SQL = "SELECT ALL "
				+ "[IDENTIFY_INPUTPICS],"
				+ "[PROJECT_CODE],"
				+ "[IDENTIFY_PICS],"
				+ "[TABLE_SPEC],"
				+ "[SPEC_VERSION],"
				+ "[TABLE_ID],"
				+ "[ITEM_ID],"
				+ "[SUPPORT]"
				+ "FROM [NTUTC].[dbo].[PICS_LTE_DATA]";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				Pic p = new Pic(
						rs.getString("PROJECT_CODE"),
						rs.getString("TABLE_SPEC"),
						rs.getString("SPEC_VERSION"),
						rs.getString("TABLE_ID"),
						rs.getString("ITEM_ID"),
						rs.getString("SUPPORT")
				);
				
				if( (PICS_LTE = PICS.get(p.getTable_Spec())) == null ){;
					PICS_LTE = new HashMap<String, Pic>();
					PICS.put(p.getTable_Spec(), PICS_LTE);
				}
				
				PICS_LTE.put(p.getTable_ID() + "/" + p.getItem_ID(), p);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int get2GCondtion(){
		CONDITIONS_2G = new HashMap<String, Condition>();
		int num = 0;
		String SQL = "SELECT ALL "
				//+ "[TABLE_SPEC],"
				//+ "[SPEC_VERSION],"
				+ "[CONDITION_ID],"
				+ "[CONDITION_DESC]"
				+ "FROM [NTUTC].[dbo].[ETSITCCONDITION]";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				Condition con = new Condition(
						"2G",
						null,
						rs.getString("CONDITION_ID"),
						rs.getString("CONDITION_DESC")
				);
				num++;
				CONDITIONS_2G.put("2G", con);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return num;
	}
	
	private int get3GCondtion(){
		CONDITIONS_3G = new HashMap<String, Condition>();
		int num = 0;
		
		String SQL = "SELECT ALL "
				+ "[TABLE_SPEC],"
				+ "[SPEC_VERSION],"
				+ "[CONDITION_ID],"
				+ "[CONDITION_DESC]"
				+ "FROM [NTUTC].[dbo].[TESTCASE_COND_3G]";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				Condition con = new Condition(
						rs.getString("TABLE_SPEC"),
						rs.getString("SPEC_VERSION"),
						rs.getString("CONDITION_ID"),
						rs.getString("CONDITION_DESC")
				);
				num++;
				CONDITIONS_3G.put(con.getCondition_ID(), con);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return num;
	}
	
	private int getLTECondtion(){
		CONDITIONS_LTE = new HashMap<String, Condition>();
		int num = 0;
		String SQL = "SELECT ALL "
				+ "[TABLE_SPEC],"
				+ "[SPEC_VERSION],"
				+ "[CONDITION_ID],"
				+ "[CONDITION_DESC]"
				+ "FROM [NTUTC].[dbo].[TESTCASE_COND_LTE]";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				Condition con = new Condition(
						rs.getString("TABLE_SPEC"),
						rs.getString("SPEC_VERSION"),
						rs.getString("CONDITION_ID"),
						rs.getString("CONDITION_DESC")
				);
				num++;
				CONDITIONS_LTE.put(con.getCondition_ID(), con);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return num;
	}
}
