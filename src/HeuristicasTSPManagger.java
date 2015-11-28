import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HeuristicasTSPManagger {
	/**
	 * Heuristica para encontrar el TSP. Esta heuristica fue desarrollada e implementada por el grupo.
	 * Respeta las prohibiciones de comunicacion entre algunos municipios
	 * @return lista con las obras en el orden que se deben visitar. La primera (null) representa el dipot
	 */
	public List<Obra> CWJPC(){
		//obras totales
		List<Obra>obras=new ArrayList<Obra>(Auxiliar.OBRAS);
		
		//lista tsp
		List<Obra>tspFinal=new ArrayList<Obra>();
		
		//seleccion del nodo inicial aleatoreamente
		int max=176;
		int min=0;
		Random rand=new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;	   
	    tspFinal.add(null);
	    tspFinal.add(obras.remove(randomNum));
	    
	    //armar parejas mas cercanas
	    List<Obra>obras1=new ArrayList<Obra>(obras);
	    List<Obra>obrasEliminadas=new ArrayList<Obra>();
	    List<Pareja>parejas=new ArrayList<Pareja>();
	    for(Obra x:obras){
	    	if(!obrasEliminadas.contains(x)){
	    		Obra menor=null;
	    		Double menordist=999999999999.0;
	    		for(Obra y: obras1){
	    			if(!x.equals(y)){
	    				if(menor==null){

	    					Integer ix=Auxiliar.MUNICIPIO_ID.get(x.municipio+" - "+x.departamento);
	    					Integer iy=Auxiliar.MUNICIPIO_ID.get(y.municipio+" - "+y.departamento);
	    					Double distancia=Auxiliar.DISTANCIAS.get(ix+" - "+iy);
	    					if(distancia>=0){
	    						menor=y;
	    						menordist=distancia;
	    					}
	    				}else{
	    					Integer ix=Auxiliar.MUNICIPIO_ID.get(x.municipio+" - "+x.departamento);
	    					Integer iy=Auxiliar.MUNICIPIO_ID.get(y.municipio+" - "+y.departamento);
	    					Double distancia=Auxiliar.DISTANCIAS.get(ix+" - "+iy);

	    					if(distancia>=0&&menordist>distancia){
	    						menor=y;
	    						menordist=distancia;
	    					}
	    				}
	    			}
	    		}
	    		if(menor==null){
	    			Pareja p=new Pareja(x,null);
	    			parejas.add(p);
	    			obras1.remove(x);
	    			obrasEliminadas.add(x);
	    		}else{
	    			Pareja p=new Pareja(x,menor);
	    			parejas.add(p);
	    			obras1.remove(x);
	    			obras1.remove(menor);
	    			obrasEliminadas.add(x);
	    			obrasEliminadas.add(menor);
	    		}
	    	}
	    }
	    
	    //armando el tsp
	    List<Pareja>parejasFaltan=new ArrayList<Pareja>(parejas);
	    for(int i=0;i<parejas.size();i++){
	    	Ruta rutaF=new Ruta(tspFinal,null);
	    	Double costo=9999999999999999.0;
	    	Pareja par=null;
	    	for(Pareja p:parejasFaltan){
	    		List<Obra>tsp=new ArrayList<Obra>(tspFinal);
	    		Obra ult=tsp.get(tsp.size()-1);
	    		List<Obra>ob=p.darListaObrasOrdenadas(ult);
	    		if(ob!=null){
	    			tsp.addAll(ob);
	    			Ruta ruta=new Ruta(tsp,null);
	    			if(costo>ruta.costoTrans){
	    				costo=ruta.costoTrans;
	    				rutaF=ruta;
	    				par=p;
	    			}
	    		}
	    	}
	    	tspFinal=rutaF.obras;
	    	if(par!=null){
	    		parejasFaltan.remove(par);
	    	}
	    }
	    //aca solo entra si quedaron parejas sin revisar por restriccion de municipios incomunicados
	    if(parejasFaltan.size()>0){
	    	List<Obra>listaFaltantes=new ArrayList<Obra>();
	    	for(int i=0;i<parejasFaltan.size();i++){
	    		listaFaltantes.add(parejasFaltan.get(i).obra1);
	    		if(parejasFaltan.get(i).obra2!=null){
	    			listaFaltantes.add(parejasFaltan.get(i).obra2);
	    		}
	    	}
	    	for(int i=0;i<listaFaltantes.size();i++){
	    		for(int j=1;j<tspFinal.size();j++){
	    			Integer tspI=Auxiliar.MUNICIPIO_ID.get(tspFinal.get(j).municipio+" - "+tspFinal.get(j).departamento);
	    			Integer lfI=Auxiliar.MUNICIPIO_ID.get(listaFaltantes.get(i).municipio+" - "+listaFaltantes.get(i).departamento);
	    			
	    			Double d=Auxiliar.DISTANCIAS.get(tspI+" - "+lfI);
	    			if(d>=0.0){
	    				tspFinal.add(j, listaFaltantes.get(i));
	    				break;
	    			}
	    		}
	    	}
	    }
	    return tspFinal;
	}

}
