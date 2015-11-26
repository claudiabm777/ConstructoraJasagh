import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

import java.util.*;

import org.omg.CORBA.INITIALIZE;

public class SetCovering4 {

	
	//================================================================
	//							ATRIBUTES 
	//================================================================
	
	
	//VARIABLES
	public GRBVar[]x;
	public Double fo;
	
	//================================================================
	//							METHODS 
	//================================================================

	//Initializations
	
	//Procedure that runs the MIP
	public List<Integer> generateModel(List<Double>c,List<Double>t,Integer a[][]){
		GRBEnv env;
		List<Integer>sol=new ArrayList<Integer>();
		try {
			env = new GRBEnv("mip1.log");
			GRBModel  model = new GRBModel(env);
			x=new GRBVar[c.size()];
			for(int k=0;k<c.size();k++){
				x[k]=model.addVar(0.0, 1.0, 0.0, GRB.INTEGER, "x"+k);				
			}
			model.update();

			//Objective Function
			GRBLinExpr expr = new GRBLinExpr();
			for (int k = 0; k < t.size(); k++) {
				expr.addTerm(t.get(k), x[k]);
			}

			model.setObjective(expr, GRB.MINIMIZE);	
			
			
			//Group of Constraints 1: Every node is visited
			for (int i = 0; i < a.length; i++) {
				expr = new GRBLinExpr();
				for (int k = 0; k < c.size(); k++) {					
					expr.addTerm(a[i][k], x[k]);										
				}
				model.addConstr(expr, GRB.GREATER_EQUAL, 1, "c_"+i);				
			}
			
			model.optimize();
			model.write("model4.lp");
			
			sol= new ArrayList<Integer>();
			for (int k = 0; k < c.size(); k++) {
				if (x[k].get(GRB.DoubleAttr.X)>0.0) {
					sol.add(k);
				}
				
			}
			
			System.out.println("RUTAS: "+sol);
		model.dispose();
		env.dispose();
		} catch (GRBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return sol;

		
	
	}
	
	//================================================================
	//							MAIN
	//================================================================

	
	
	
	
	
}
