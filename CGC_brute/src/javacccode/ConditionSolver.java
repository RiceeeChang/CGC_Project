package javacccode;

import java.util.ArrayList;

import cgcdb.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ConditionSolver.
 * 用來處理condition的式子
 */
public class ConditionSolver {
	
	/**
	 * Solve condition.
	 *
	 * static method 可直接呼叫使用該方法
	 *
	 * @param standard the standard 
	 * @param project the project 
	 * @param con the con 要解的Condition
	 * @param ERROR_LIST the error list
	 */
	public static void solveCondition(Standard standard, Project project, Condition con, ArrayList<ConditionError> ERROR_LIST){
		
		try{
			if(!con.isDone()){
				try{
					EG1 parser = new EG1(standard, project, con, ERROR_LIST);
					con.setResult(parser.condition());
					con.Done();
				}catch (ParseException e){
					ERROR_LIST.add(new ConditionError(project.getProject_Code(), con.getTable_Spec(), con.getCondition_ID(), e.getMessage()));
				}catch (Error e){
					ERROR_LIST.add(new ConditionError(project.getProject_Code(), con.getTable_Spec(), con.getCondition_ID(), e.getMessage()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
