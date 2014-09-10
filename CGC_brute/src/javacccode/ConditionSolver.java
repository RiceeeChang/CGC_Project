package javacccode;

import java.util.ArrayList;

import cgcdb.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ConditionSolver.
 * �ΨӳB�zcondition�����l
 */
public class ConditionSolver {
	
	/**
	 * Solve condition.
	 *
	 * static method �i�����I�s�ϥθӤ�k
	 *
	 * @param standard the standard 
	 * @param project the project 
	 * @param con the con �n�Ѫ�Condition
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
