import java.util.ArrayList;
import java.util.List;


public class Pareja {
	//-----------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Obra obra1;
	public Obra obra2;
	
	//-----------------------------------------------------------------------------------------
	//CONSTRUCTOR------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Pareja(Obra obra1, Obra obra2){
		this.obra1=obra1;
		this.obra2=obra2;
	}
	/**
	 * Da la lista ordenada de acuerdo a la obra mas cercana de la pareja, a la ultima obra del tsp
	 * @param ultimaTSP
	 * @return lista con las obras en el orden correspondiente
	 */
	public List<Obra> darListaObrasOrdenadas(Obra ultimaTSP){
		
		List<Obra>lista=new ArrayList<Obra>();
		if(obra2!=null){
		String o1=obra1.municipio+" - "+obra1.departamento;
		String o2=obra2.municipio+" - "+obra2.departamento;
		String ult=ultimaTSP.municipio+" - "+ultimaTSP.departamento;
		Integer oo1=Auxiliar.MUNICIPIO_ID.get(o1);
		Integer oo2=Auxiliar.MUNICIPIO_ID.get(o2);
		Integer uult=Auxiliar.MUNICIPIO_ID.get(ult);
		Double distAo1=Auxiliar.DISTANCIAS.get(oo1+" - "+uult);
		Double distAo2=Auxiliar.DISTANCIAS.get(oo2+" - "+uult);
		
		 if(distAo1<distAo2 && distAo1>=0){ 
			 lista.add(obra1);
			 lista.add(obra2);
		 }else if (distAo2>=0.0){
			 lista.add(obra2);
			 lista.add(obra1);
		 }else{
			 return null;
		 }
		}else{
			String o1=obra1.municipio+" - "+obra1.departamento;
			String ult=ultimaTSP.municipio+" - "+ultimaTSP.departamento;
			Integer oo1=Auxiliar.MUNICIPIO_ID.get(o1);
			Integer uult=Auxiliar.MUNICIPIO_ID.get(ult);
			Double distAo1=Auxiliar.DISTANCIAS.get(oo1+" - "+uult);
			if(distAo1<0.0){
				return null;
			}else{
				lista.add(obra1);
			}
		}
		return lista;
	}
}
