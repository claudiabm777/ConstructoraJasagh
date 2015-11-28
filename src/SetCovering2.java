import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.omg.CORBA.INITIALIZE;

public class SetCovering2 {

	
	//================================================================
	//							ATRIBUTES 
	//================================================================
	
	
	//VARIABLES
	public GRBVar[]x;
	public GRBVar z;
	public Double fo;
	
	//================================================================
	//							METHODS 
	//================================================================

	
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
			
			//Variable for min max !!!!!!WARNING UB must be set according to parameters
			z=model.addVar(0, 10000, 0.0, GRB.CONTINUOUS, "z");
			
			
			model.update();

			//Objective Function
			GRBLinExpr expr = new GRBLinExpr();			
				expr.addTerm(1, z);
			model.setObjective(expr, GRB.MINIMIZE);	
			
			
			//Group of Constraints 1: Every node is visited
			for (int i = 0; i < a.length; i++) {
				expr = new GRBLinExpr();
				for (int k = 0; k < c.size(); k++) {					
					expr.addTerm(a[i][k], x[k]);										
				}
				model.addConstr(expr, GRB.GREATER_EQUAL, 1, "c_"+i);				
			}
			
			//Group of Constraints 2: Argument for min max
			for (int k = 0; k < c.size(); k++) {
				expr = new GRBLinExpr();
				expr.addTerm(t.get(k), x[k]);
				model.addConstr(expr, GRB.LESS_EQUAL, z, "z_"+k);
			}
			
			model.optimize();
			model.write("model2.lp");
			
			sol= new ArrayList<Integer>();
			for (int k = 0; k < c.size(); k++) {
				if (x[k].get(GRB.DoubleAttr.X)>0.0) {
					sol.add(k);
				}
				
			}
			
			System.out.println("RUTAS: "+sol);
			fo = model.get(GRB.DoubleAttr.ObjVal);
		model.dispose();
		env.dispose();
		} catch (GRBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	return sol;
	}


	
	
}
