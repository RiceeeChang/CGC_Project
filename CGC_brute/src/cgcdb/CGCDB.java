package cgcdb;

import java.sql.*;
import java.util.*;

public class CGCDB {
	Connection con = null;
	private Statement stmt;
	
	private ArrayList<String> PROJECT_CODE_LIST;
	private HashMap<String, Project> PROJECTS;
	
	private String DBNAME;
	
	public CGCDB(Connection con, String DBNAME){
		//String conUrl = "jdbc:sqlserver://140.112.42.141\\SQLEXPRESS:1433;user=sa;password=bl618;";
		this.DBNAME = DBNAME;
		
		try{//註冊JODBC類
			//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//con = DriverManager.getConnection(conUrl);
			this.con = con;
			stmt = con.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getProjectCodeList(){
		return PROJECT_CODE_LIST;
	}
	
	public Project getProject(String project_code){
		return PROJECTS.get(project_code);
	} 
	
	public void loadProject(){
		PROJECT_CODE_LIST = new ArrayList<String>();
		PROJECTS = new HashMap<String, Project>();
		try {
			// Load [PROJRUN] 2G
			String SQL = "SELECT ALL [PROJECT_CODE],"
					+ "[GCF_VERSION],"
					+ "[PTCRB_VERSION],"
					+ "[GSMCATEGORY],"
					+ "[GPRSCATEGORY],"
					+ "[EGPRSCATEGORY],"
					+ "[AMRCATEGORY],"
					+ "[SATKCATEGORY],"
					+ "[DTMCATEGORY]"
					+ "FROM [" + DBNAME + "].[dbo].[PROJRUN]";
			ResultSet rs = stmt.executeQuery(SQL);
			while( rs.next() ){
				Project project = new Project(rs.getString("PROJECT_CODE"));
				project.set2GInformation(
						rs.getString("GCF_VERSION"), 
						rs.getString("PTCRB_VERSION"), 
						rs.getString("GSMCATEGORY"), 
						rs.getString("GPRSCATEGORY"), 
						rs.getString("EGPRSCATEGORY"), 
						rs.getString("AMRCATEGORY"), 
						rs.getString("SATKCATEGORY"), 
						rs.getString("DTMCATEGORY"));
				PROJECT_CODE_LIST.add(rs.getString("PROJECT_CODE"));
				PROJECTS.put(project.getProject_Code(), project);
			}
			// Load [PROJRUN_3G]
			SQL = "SELECT ALL [PROJECT_CODE],"
					+ "[GCF_VERSION],"
					+ "[PTCRB_VERSION],"
					+ "[CATEGORY],"
					+ "[HSDPA_CATEGORY],"
					+ "[PRIMARY_SPEC],"
					+ "[AUDIO_CATEGORY]"
					+ "FROM [" + DBNAME + "].[dbo].[PROJRUN_3G]";
			rs = stmt.executeQuery(SQL);
			while(rs.next()){
				Project project = PROJECTS.get(rs.getString("PROJECT_CODE"));
				project.set3GInformation(
						rs.getString("GCF_VERSION"), 
						rs.getString("PTCRB_VERSION"), 
						rs.getString("CATEGORY"), 
						rs.getString("HSDPA_CATEGORY"), 
						rs.getString("PRIMARY_SPEC"), 
						rs.getString("AUDIO_CATEGORY"));
			}
			
			// Load [PROJRUN_LTE]
			SQL = "SELECT ALL [PROJECT_CODE],"
				+ "[GCF_VERSION],"
				+ "[PTCRB_VERSION],"
				+ "[CATEGORY],"
				+ "[HSDPA_CATEGORY],"
				+ "[PRIMARY_SPEC],"
				+ "[BAND]"
				+ "FROM [" + DBNAME + "].[dbo].[PROJRUN_LTE]";
			rs = stmt.executeQuery(SQL);
			while(rs.next()){
				Project project = PROJECTS.get(rs.getString("PROJECT_CODE"));
				project.setLTEInformation(
					rs.getString("GCF_VERSION"), 
					rs.getString("PTCRB_VERSION"), 
					rs.getString("CATEGORY"), 
					rs.getString("HSDPA_CATEGORY"), 
					rs.getString("PRIMARY_SPEC"), 
					rs.getString("BAND"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void loadPics(Project project){
		load2GPics(project);
		load3GPics(project);
		loadLTEPics(project);
	}
	
	public void loadConditions(Standard standard){
		load2GCondtion(standard);
		load3GCondtion(standard);
		loadLTECondtion(standard);
	}
	
	public void loadTestcases(Standard standard, Project project){
		load2GTestcase(standard, project);
		load3GTestcase(standard, project);
		loadLTETestcase(standard, project);
	}
	
	public void loadRelease(Standard standard){
		standard.RELEASE_LIST = new ArrayList<String>();
		standard.RELEASE = new HashMap<String, Integer>();
		
		String SQL = "SELECT ALL"
				+ "[c00],"
				+ "[rel]"
				+ "FROM [" + DBNAME + "].[dbo].[release]";
		
		try{
			ResultSet rs = stmt.executeQuery(SQL);
			while(rs.next()){
				standard.RELEASE_LIST.add(rs.getString("rel"));
				if(Integer.parseInt(rs.getString("c00")) >= 7)
					standard.RELEASE.put(rs.getString("rel"), new Integer(rs.getString("c00")));
			}
			rs.close();
		}catch(SQLException exception){
			exception.printStackTrace();
		}
	}
	
	public void loadRef_Spec(Standard standard){
		standard.REF_SPEC = new HashMap<String, String>();
		String SQL = "SELECT ALL [C00],"
				+ "[tech_type],"
				+ "[ref],"
				+ "[mapping_spec]"
				+ "FROM [" + DBNAME + "].[dbo].[ref_spec]";
		
		try{
			ResultSet rs = stmt.executeQuery(SQL);
			while(rs.next()){
				standard.REF_SPEC.put(rs.getString("ref"), rs.getString("mapping_spec"));
			}
			rs.close();
		}catch(SQLException exception){
			exception.printStackTrace();
		}
	}
	
	private int load2GPics(Project project){
		HashMap<String, Pic> PICS_2G = new HashMap<String, Pic>();
		int picnumber = 0;
		try {
			String SQL = "SELECT ALL "
					+ "[PROJECT_CODE],"
					+ "[TABLE_ID],"
					+ "[ITEM_ID],"
					+ "[SUPPORT]"
					+ "FROM [" + DBNAME + "].[dbo].[PSPICS]"
					+ "WHERE [PROJECT_CODE] = '" + project.getProject_Code() + "'";
			
			ResultSet rs = stmt.executeQuery(SQL);
			
			while(rs.next()){
				Pic p = new Pic(
						rs.getString("PROJECT_CODE"),
						"",
						rs.getString("TABLE_ID"),
						rs.getString("ITEM_ID"),
						rs.getString("SUPPORT")
				);
				PICS_2G.put(p.getTable_ID() + "/" + p.getItem_ID(), p);
				picnumber++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		project.PICS.put("2G", PICS_2G);
		
		return picnumber;
	}
	
	private int load3GPics(Project project){
		HashMap<String, Pic> PICS_3G = new HashMap<String, Pic>();
		int picnumber = 0;
		String SQL = "SELECT ALL "
				+ "[PROJECT_CODE],"
				+ "[TABLE_SPEC],"
				+ "[TABLE_ID],"
				+ "[ITEM_ID],"
				+ "[SUPPORT]"
				+ "FROM [" + DBNAME + "].[dbo].[PICS_3G_DATA]"
				+ "WHERE [PROJECT_CODE] = '" + project.getProject_Code() + "'";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			
			while(rs.next()){
				Pic p = new Pic(
						rs.getString("PROJECT_CODE"),
						rs.getString("TABLE_SPEC"),
						rs.getString("TABLE_ID"),
						rs.getString("ITEM_ID"),
						rs.getString("SUPPORT")
				);
				if( (PICS_3G = project.PICS.get(p.getTable_spec())) == null ){
					PICS_3G = new HashMap<String, Pic>();
					project.PICS.put(p.getTable_spec(), PICS_3G);
				}
				PICS_3G.put(p.getTable_ID() + "/" + p.getItem_ID(), p);
				picnumber++;
			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return picnumber;
	}

	private int loadLTEPics(Project project){
		HashMap<String, Pic> PICS_LTE = new HashMap<String, Pic>();
		
		int picnumber = 0;
		
		String SQL = "SELECT ALL "
				+ "[PROJECT_CODE],"
				+ "[TABLE_SPEC],"
				+ "[TABLE_ID],"
				+ "[ITEM_ID],"
				+ "[SUPPORT]"
				+ "FROM [" + DBNAME + "].[dbo].[PICS_LTE_DATA]"
				+ "WHERE [PROJECT_CODE] = '" + project.getProject_Code() + "'";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				Pic p = new Pic(
						rs.getString("PROJECT_CODE"),
						rs.getString("TABLE_SPEC"),
						rs.getString("TABLE_ID"),
						rs.getString("ITEM_ID"),
						rs.getString("SUPPORT")
				);
				
				if( (PICS_LTE = project.PICS.get(p.getTable_spec())) == null ){;
					PICS_LTE = new HashMap<String, Pic>();
					project.PICS.put(p.getTable_spec(), PICS_LTE);
				}
				PICS_LTE.put(p.getTable_ID() + "/" + p.getItem_ID(), p);
				picnumber++;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return picnumber;
	}
	
	/*
	 * Load Condition data from Database to our table in memory
	 * They are split in 3 table in database.
	 * [ETSITCCONDITION]
	 * [TESTCASE_COND_3G]
	 * [TESTCASE_COND_LTE]
	 * 
	 */
	private void load2GCondtion(Standard standard){
		HashMap<String, Condition> CONDITIONS_2G = new HashMap<String, Condition>();
		
		String SQL = "SELECT ALL "
				+ "[CONDITION_ID],"
				+ "[CONDITION_DESC]"
				+ "FROM [" + DBNAME + "].[dbo].[ETSITCCONDITION]";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			while(rs.next()){
				
				Condition con = new Condition(
						Protocol._2G,
						"2G",
						rs.getString("CONDITION_ID"),
						rs.getString("CONDITION_DESC")
				);

				standard.getCONDITION_LIST().add(con);
				CONDITIONS_2G.put(con.getCondition_ID(), con);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		standard.CONDITIONS.put("2G", CONDITIONS_2G);
	}
	
	private void load3GCondtion(Standard standard){
		HashMap<String, Condition> CONDITIONS_3G = new HashMap<String, Condition>();
		
		String SQL = "SELECT ALL "
				+ "[TABLE_SPEC],"
				+ "[CONDITION_ID],"
				+ "[CONDITION_DESC]"
				+ "FROM [" + DBNAME + "].[dbo].[TESTCASE_COND_3G]";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				Condition con = new Condition(
						Protocol._3G,
						rs.getString("TABLE_SPEC"),
						rs.getString("CONDITION_ID"),
						rs.getString("CONDITION_DESC")
				);
				
				// Add to List
				standard.CONDITION_LIST.add(con);
				
				// Add to Hash table
				if( (CONDITIONS_3G = standard.CONDITIONS.get(con.getTable_Spec())) == null ){;
					CONDITIONS_3G = new HashMap<String, Condition>();
					standard.CONDITIONS.put(con.getTable_Spec(), CONDITIONS_3G);
				}
				CONDITIONS_3G.put(con.getCondition_ID(), con);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void loadLTECondtion(Standard standard){
		HashMap<String, Condition> CONDITIONS_LTE = new HashMap<String, Condition>();
		
		String SQL = "SELECT ALL "
				+ "[TABLE_SPEC],"
				+ "[SPEC_VERSION],"
				+ "[CONDITION_ID],"
				+ "[CONDITION_DESC]"
				+ "FROM [" + DBNAME + "].[dbo].[TESTCASE_COND_LTE]";
		
		try {
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				Condition con = new Condition(
						Protocol._LTE,
						rs.getString("TABLE_SPEC"),
						rs.getString("CONDITION_ID"),
						rs.getString("CONDITION_DESC")
				);
				standard.CONDITION_LIST.add(con);
				if( (CONDITIONS_LTE = standard.CONDITIONS.get(con.getTable_Spec())) == null ){;
					CONDITIONS_LTE = new HashMap<String, Condition>();
					standard.CONDITIONS.put(con.getTable_Spec(), CONDITIONS_LTE);
				}
				CONDITIONS_LTE.put(con.getCondition_ID(), con);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getTestcase(Standard standard, Project project){
		get2GTestcase(project);
		get3GTestcase(standard, project);
		getLTETestcase(project);
	}
	
	private void load2GTestcase(Standard standard, Project project){
		String SQL;
		// 2G
		try {
			// ---------- SIM 2G ----------
			SQL = "SELECT ALL [IDENTIFY_EB]"
					+ ",[CLAUSE_ID]"
					+ ",[E1_DESC]"
					+ ",[RELEASE]"
					+ ",[REL96]"
					+ ",[REL97]"
					+ ",[REL98]"
					+ ",[REL99]"
					+ ",[TERMINAL_PRO]"
					+ ",[RSTATUS]"
					+ "FROM [" + DBNAME + "].[dbo].[ETSIE1BODY]";
			ResultSet rs = stmt.executeQuery(SQL);
			while(rs.next()){
				
				ArrayList<String> releases = new ArrayList<String>();
				for(int i = 99; i>=96; i--){
					releases.add(rs.getString("REL" + i));
				}
				
				Testcase tc = new Testcase("2G"
										  ,rs.getString("CLAUSE_ID")
										  ,rs.getString("E1_DESC")
										  ,rs.getString("RELEASE")
										  ,rs.getString("TERMINAL_PRO")
										  ,""
										  ,releases
										  ,rs.getString("RSTATUS")
										  ,""
				);
				standard.TESTCASES.add(tc);
			}
			// -----------------------------------------------
			// ------- TestCase 2G --------
			SQL = "SELECT ALL "
					+ "[IDENTIFY_VERSION],"
					+ "[CLAUSE_ID],"
					+ "[TITLE_NAME],"
					+ "[RELEASE_VERSION],"
					+ "[APPLICABALITY],"
					+ "[STATUS],"
					+ "[REDUENCYTC],"
					+ "[RSTATUS]"
					+ "FROM [" + DBNAME + "].[dbo].[ETSITC]";
			rs = stmt.executeQuery(SQL);
			while(rs.next()){
				Testcase tc = new Testcase("2G"
										  ,rs.getString("CLAUSE_ID")
										  ,rs.getString("TITLE_NAME")
										  ,rs.getString("RELEASE_VERSION")
										  ,rs.getString("STATUS")
										  ,rs.getString("APPLICABALITY")
										  ,null
										  ,rs.getString("REDUENCYTC")
										  ,rs.getString("RSTATUS")
				);
				standard.TESTCASES.add(tc);
			}
			
			rs.close();
		} catch (BatchUpdateException e){
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void load3GTestcase(Standard standard, Project project){
		String SQL ="";
		// --------- 3G ----------
		try {
			// ------- SIM 3G -------			
			SQL = "SELECT ALL "
				+ "[TABLE_SPEC]"
				+ ",[CLAUSE_ID]"
				+ ",[TITLE]"
				+ ",[RELEASE]"
				+ ",[R99ME]"
				+ ",[R4ME]"
				+ ",[R5ME]"
				+ ",[R6ME]"
				+ ",[TERMINAL_PROFILE]"
				+ ",[R7ME]"
				+ ",[R8ME]"
				+ ",[R9ME]"
				+ ",[R10ME]"
				+ ",[R11ME]"
				+ ",[R12ME]"
				+ ",[R13ME]"
				+ ",[R14ME]"
				+ ",[R15ME]"
				+ ",[R16ME]"
				+ ",[R17ME]"
				+ ",[R18ME]"
				+ ",[R19ME]"
				+ ",[R20ME]"
				+ "FROM [" + DBNAME + "].[dbo].[TESTCASE_SIM_3G]";
			ResultSet rs = stmt.executeQuery(SQL);
			System.out.println(project.get3GInfo().getCategory());
			int REL = standard.RELEASE.get(project.get3GInfo().getCategory()).intValue()-1;
			while(rs.next()){
				ArrayList<String> releases = new ArrayList<String>();
				for(int i = REL; i>=6; i--){
					releases.add(rs.getString("R" + standard.RELEASE_LIST.get(i).substring(4) + "ME"));
				}
				
				Testcase tc = new Testcase(rs.getString("TABLE_SPEC")
								, rs.getString("CLAUSE_ID")
								, rs.getString("TITLE")
								, rs.getString("RELEASE")
								, rs.getString("TERMINAL_PROFILE")
								, ""
								, releases
								, ""
								, "");
				
				standard.TESTCASES.add(tc);
			}// while
			// -------------------------------------------------
			// ------- Testcase 3G -------
			SQL = "SELECT ALL "
				+ "[TABLE_SPEC],"
				+ "[CLAUSE_ID],"
				+ "[TITLE],"
				+ "[RELEASE],"
				+ "[APPLICABILITY]," // Reference to Condition_ID
				+ "[COMMENT],"
				+ "[REDUNENCY],"
				+ "[R_CONDITION]"
				+ "FROM [" + DBNAME + "].[dbo].[TESTCASE_3G]";
			rs = stmt.executeQuery(SQL);
			while(rs.next()){
				Testcase tc = new Testcase(
						 rs.getString("TABLE_SPEC")
						,rs.getString("CLAUSE_ID")
						,rs.getString("TITLE")
						,rs.getString("RELEASE")
						,rs.getString("APPLICABILITY")
						,rs.getString("COMMENT")
						,null
						,rs.getString("REDUNENCY")
						,rs.getString("R_CONDITION")
				);
				
				standard.TESTCASES.add(tc);
			}// while	
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void loadLTETestcase(Standard standard, Project project){
		// LTE
		String SQL = "SELECT ALL "
			+ "[TABLE_SPEC],"
			+ "[CLAUSE_ID],"
			+ "[TITLE],"
			+ "[RELEASE],"
			+ "[APPLICABILITY]," // Reference to Condition_ID	
			+ "[COMMENT]"
			+ "FROM [" + DBNAME + "].[dbo].[TESTCASE_LTE]";

		try {
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				Testcase tc = new Testcase(
						rs.getString("TABLE_SPEC")
					   ,rs.getString("CLAUSE_ID")
					   ,rs.getString("TITLE")
					   ,rs.getString("RELEASE")
					   ,rs.getString("APPLICABILITY")
					   ,rs.getString("COMMENT")
				);
						
				standard.TESTCASES.add(tc);
			}// while
							
			rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}// try
	}
	
	private void get2GTestcase(Project project){
		String SQL;
		// 2G
		try {
			stmt.executeUpdate("TRUNCATE TABLE [" + DBNAME + "].[dbo].[TCLIST_2G];");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO [" + DBNAME + "].[dbo].[TCLIST_2G] VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			// ---------- SIM 2G ----------		
			SQL = "SELECT ALL [IDENTIFY_EB]"
					+ ",[CLAUSE_ID]"
					+ ",[E1_DESC]"
					+ ",[RELEASE]"
					+ ",[REL96]"
					+ ",[REL97]"
					+ ",[REL98]"
					+ ",[REL99]"
					+ ",[TERMINAL_PRO]"
					+ ",[EB_DESC]"
					+ ",[RSTATUS]"
					+ "FROM [" + DBNAME + "].[dbo].[ETSIE1BODY]";
			ResultSet rs = stmt.executeQuery(SQL);
			while(rs.next()){
				String result = "N/A";
				for(int i = 99; i>=96; i--){
					String condition_id = "";
					condition_id = rs.getString("REL" + i);
					if(condition_id == null)
						break;
					simparser.EG1 parser = new simparser.EG1(condition_id, project, "2G");
					
					if(parser.getResult()){
						result = "R";
						break;
					}
				}
				
				String rstatus = rs.getString("RSTATUS");
				Condition condition = project.getCondition(rstatus, "2G");
				
				if(condition == null)
					rstatus = "N";
				else if(condition.getResult().getValue())
					rstatus = "Y";
				else
					rstatus = "N";
				
				uploadTestCaseResult(
						pstmt, 
						project.getProject_Code(), 
						"", 
						rs.getString("CLAUSE_ID"), 
						rs.getString("E1_DESC"),
						rs.getString("RELEASE"),
						result,
						rstatus,
						""
				);
			}
			// -----------------------------------------------
			// ------- TestCase 2G --------
			SQL = "SELECT ALL "
					+ "[IDENTIFY_VERSION],"
					+ "[CLAUSE_ID],"
					+ "[TITLE_NAME],"
					+ "[RELEASE_VERSION],"
					+ "[APPLICABALITY],"
					+ "[STATUS],"
					+ "[REDUENCYTC],"
					+ "[RSTATUS]"
					+ "FROM [" + DBNAME + "].[dbo].[ETSITC]";
			rs = stmt.executeQuery(SQL);
			while(rs.next()){
				String condition_id = rs.getString("STATUS");
				String redundancy = rs.getString("REDUENCYTC");
				String r_condition = rs.getString("RSTATUS");
				String result = "N/A";
				Condition condition = project.getCondition(r_condition, "2G");
				boolean r = false;
				boolean rc = true;
				if(condition == null){ // Check R_condition
					if(r_condition != null){
						if(r_condition.equalsIgnoreCase("N"))
							rc = false;
					}
				}else {
					rc = condition.getResult().getValue();
				}
				if(rc){ // Check Redundancy
					if(redundancy != null){
						if(redundancy.equalsIgnoreCase("Y"))
							r = true;
					}
				}
				if(condition_id != null){
					simparser.EG1 parser = new simparser.EG1(condition_id, project, "2G");
					if(parser.getResult()){
						result = "R";
					}
				}
				
				String fresult = "N/A";
				if(!r)
					fresult = result;
				
				uploadTestCaseResult(
					pstmt,
					project.getProject_Code(),
					"",
					rs.getString("CLAUSE_ID"),
					rs.getString("TITLE_NAME"),
					rs.getString("RELEASE_VERSION"),
					result,
					String.valueOf(r),
					fresult
				);
			}
			
			pstmt.executeBatch();
			rs.close();
		} catch (BatchUpdateException e){
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void get3GTestcase(Standard standard, Project project){
		String SQL ="";
		// --------- 3G ----------
		try {
			// ------ Prepare Update Batch ------
			stmt.executeUpdate("TRUNCATE TABLE [" + DBNAME + "].[dbo].[TCLIST_3G];");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO [" + DBNAME + "].[dbo].[TCLIST_3G] VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			// ------- SIM 3G -------			
			SQL = "SELECT ALL [IDENTIFY_SIM_TC]"
				+ ",[TABLE_SPEC]"
				+ ",[CLAUSE_ID]"
				+ ",[TITLE]"
				+ ",[RELEASE]"
				+ ",[TEST_SEQ]"
				+ " ,[R99ME]"
				+ ",[R4ME]"
				+ ",[R5ME]"
				+ ",[R6ME]"
				+ ",[TERMINAL_PROFILE]"
				+ ",[R7ME]"
				+ ",[R8ME]"
				+ ",[R9ME]"
				+ ",[R10ME]"
				+ ",[R11ME]"
				+ ",[R12ME]"
				+ ",[R13ME]"
				+ ",[R14ME]"
				+ ",[R15ME]"
				+ ",[R16ME]"
				+ ",[R17ME]"
				+ ",[R18ME]"
				+ ",[R19ME]"
				+ ",[R20ME]"
				+ "FROM [" + DBNAME + "].[dbo].[TESTCASE_SIM_3G]";
			ResultSet rs = stmt.executeQuery(SQL);
			int REL = standard.RELEASE.get(project.get3GInfo().getCategory()).intValue()-1;
			while(rs.next()){
				String result = "N/A";
				String table_spec = rs.getString("TABLE_SPEC");
				for(int i = REL; i>=6; i--){
					String condition_id = rs.getString("R" + standard.RELEASE_LIST.get(i).substring(4) + "ME");
					if(condition_id == null)
						condition_id = "";
							
					simparser.EG1 parser = new simparser.EG1(condition_id, project, table_spec);
					if(parser.getResult()){
						result = "R";
						break;
					}
				}
				uploadTestCaseResult(
					pstmt, 
					project.getProject_Code(), 
					rs.getString("TABLE_SPEC"), 
					rs.getString("CLAUSE_ID"), 
					rs.getString("TITLE"),
					rs.getString("RELEASE"),
					result,
					"",
					result
				);
			}// while
			// -------------------------------------------------
			// ------- Testcase 3G -------
			SQL = "SELECT ALL "
				+ "[TABLE_SPEC],"
				+ "[CLAUSE_ID],"
				+ "[TITLE],"
				+ "[RELEASE],"
				+ "[APPLICABILITY]," // Reference to Condition_ID
				+ "[REDUNENCY],"
				+ "[R_CONDITION]"
				+ "FROM [" + DBNAME + "].[dbo].[TESTCASE_3G]";
			rs = stmt.executeQuery(SQL);
			while(rs.next()){
				String condition_id = rs.getString("APPLICABILITY");
				String table_spec = rs.getString("TABLE_SPEC");
				String redundancy = rs.getString("REDUNENCY");
				String r_condition = rs.getString("R_CONDITION");
				String result = "N/A";
				Condition condition = project.getCondition(r_condition, table_spec);
				boolean r = false;
				boolean rc = true;
				if(condition == null){ // Check R_condition
					if(r_condition != null){
						if(r_condition.equalsIgnoreCase("N"))
							rc = false;
					}
				}else {
					rc = condition.getResult().getValue();
				}
				if(rc){ // Check Redundancy
					if(redundancy != null){
						if(redundancy.equalsIgnoreCase("Y"))
							r = true;
					}
				}
				if(condition_id != null){
					simparser.EG1 parser = new simparser.EG1(condition_id, project, table_spec);
					if(parser.getResult()){
						result = "R";
					}
				}
				
				String fresult = "N/A";
				if(!r)
					fresult = result;
			
				uploadTestCaseResult(
						pstmt, 
						project.getProject_Code(), 
						rs.getString("TABLE_SPEC"), 
						rs.getString("CLAUSE_ID"), 
						rs.getString("TITLE"),
						rs.getString("RELEASE"),
						result,
						String.valueOf(r),
						fresult
				);
			}// while	
			pstmt.executeBatch();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getLTETestcase(Project project){
		// LTE
		String SQL = "SELECT ALL "
			+ "[TABLE_SPEC],"
			+ "[CLAUSE_ID],"
			+ "[TITLE],"
			+ "[RELEASE],"
			+ "[APPLICABILITY]" // Reference to Condition_ID	
			+ "FROM [" + DBNAME + "].[dbo].[TESTCASE_LTE]";

		try {
			stmt.executeUpdate("TRUNCATE TABLE [" + DBNAME + "].[dbo].[TCLIST_LTE];");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO [" + DBNAME + "].[dbo].[TCLIST_LTE] VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ResultSet rs = stmt.executeQuery(SQL);
			// Print搜尋結果
			while(rs.next()){
				String condition_id = rs.getString("APPLICABILITY");
				String table_spec = rs.getString("TABLE_SPEC");
				Condition condition = project.getCondition(condition_id, table_spec);
				String result = "null";
				if(condition == null){
					result = condition_id;
				}else if(condition.getResult().getValue()){
					result = "R";
				}else{
					result = "N/A";
				}
				
				uploadTestCaseResult(
						pstmt, 
						project.getProject_Code(), 
						rs.getString("TABLE_SPEC"), 
						rs.getString("CLAUSE_ID"), 
						rs.getString("TITLE"),
						rs.getString("RELEASE"),
						result,
						"",
						result
				);
			}// while
					
			pstmt.executeBatch();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}// try
	}
	
	public void uploadTestCase(Standard standard, Project project){
		// SIM
		try {
			stmt.executeUpdate("TRUNCATE TABLE [" + DBNAME + "].[dbo].[TCLIST_LTE];");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO [" + DBNAME + "].[dbo].[TCLIST_LTE] VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			for(Testcase tc : standard.TESTCASES){
				String result = "null";
				// 判斷是否為SIM
				// Not SIM Part
				if(tc.getRelease() == null){
					Condition condition = standard.getCondition(tc.getApplication(), tc.getTable_spec());
					
					if(condition == null){
						result = tc.getApplication();
					}else if(condition.getResult().getValue()){
						result = "R";
					}else{
						result = "N/A";
					}
				}else{ // SIM Part
					result = "N/A";
					String table_spec = tc.getTable_spec();
					for(int i = tc.getRelease().size()-1; i>=0; i--){
						String condition_id = tc.getRelease().get(i);
						if(condition_id == null)
							condition_id = "";
								
						simparser.EG1 parser = new simparser.EG1(condition_id, project, table_spec);
						if(parser.getResult()){
							result = "R";
							break;
						}
					}
				}
				
				// 判斷是否有Redundancy
				String r_condition = tc.getR_condition();
				String redundancy = tc.getRedundancy();
				Condition condition = project.getCondition(r_condition, tc.getTable_spec());
				boolean r = false;
				boolean rc = true;
				if(condition == null){ // Check R_condition
					if(r_condition != null){
						if(r_condition.equalsIgnoreCase("N"))
							rc = false;
					}
				}else {
					rc = condition.getResult().getValue();
				}
				if(rc){ // Check Redundancy
					if(redundancy != null){
						if(redundancy.equalsIgnoreCase("Y"))
							r = true;
					}
				}
				
				String fresult = "N/A";
				if(!r)
					fresult = result;
				
				
				uploadTestCaseResult(
						pstmt, 
						project.getProject_Code(), 
						tc.getTable_spec(), 
						tc.getClause_id(), 
						tc.getTitle(),
						tc.getRelease_version(),
						fresult,
						"",
						result
				);
				
			}
			
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}// try
		
		
		// NOT SIM
	}
	
	private void uploadTestCaseResult(PreparedStatement pstmt, 
									  String project_code, 
									  String spec, 
									  String tc_id, 
									  String tc_desc,
									  String release,
									  String status_result,
									  String r_result,
									  String final_result){
		try {
			pstmt.setString(1, UUID.randomUUID().toString());
			pstmt.setString(2, project_code);
			pstmt.setString(3, spec);
			pstmt.setString(4, tc_id);
			pstmt.setString(5, tc_desc);
			pstmt.setString(6, release);
			pstmt.setString(7, status_result);
			pstmt.setString(8, r_result);
			pstmt.setString(9, final_result);
						
			pstmt.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadError(Project project){
		//System.out.println("Error: " + ERROR_LIST.size());
		
		try {
			stmt.executeUpdate("TRUNCATE TABLE [" + DBNAME + "].[dbo].[ERROR_LIST];");
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO [" + DBNAME + "].[dbo].[ERROR_LIST] VALUES( ?, ?, ?, ?, ?)");
			for(ConditionError conerr : project.getERROR_LIST()){
				pstmt.setString(1, UUID.randomUUID().toString());
				pstmt.setString(2, conerr.getProject_code());
				pstmt.setString(3, conerr.getSpec());
				pstmt.setString(4, conerr.getCondition_id());
				pstmt.setString(5, conerr.getError_desc());
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}

