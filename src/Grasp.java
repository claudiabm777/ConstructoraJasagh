import java.util.List;


public class Grasp {
	public Integer k;
	public Grasp(Integer k){
		this.k=k;
	}
	
	
	public Ruteo procedimientoIterativo(){
		Auxiliar a=new Auxiliar();
		Double mejorCosto=9999999999999999.9;
		Ruteo mejorRuteo=null;
		for(int i=0;i<k;i++){
			HeuristicasTSPManagger h=new HeuristicasTSPManagger();
			List<Obra>tsp=h.CWJPC();
			List<Obra>tsp1=a.busquedaLocal(tsp);
			Ruteo r2=a.split(tsp1);
			if(mejorCosto>r2.costoTotal){
				mejorCosto=r2.costoTotal;
				mejorRuteo=r2;
			}
		}
		return mejorRuteo;
	}
}
