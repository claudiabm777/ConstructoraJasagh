import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class Grasp {
	
	public  final static Integer FO1=1;
	public  final static Integer FO2=2;
	public  final static Integer FO3=3;
	public  final static Integer FO4=4;
	//-----------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Integer k;
	public Integer fo;
	public Double tiempoComputacionalTotal;
	public Double tiempoComputacionalIterGrasp;
	public Double tiempoComputacionalGrasp;
	public Double tiempoComputacionalSetCovering;
	public Double distanciaTotalRecorrida;
	public List<Ruta> listaSetCovering;
	
	//-----------------------------------------------------------------------------------------
	//CONSTRUCTOR------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Grasp(Integer k, Integer fo){
		this.k=k;
		this.fo=fo;
	}
	
	/**
	 * Metodo con el procedimiento iterativo Grasp
	 * @return objeto ruteo el cual contiene la lista de rutas y el costo total para el problema de ruteo
	 */
	public Ruteo procedimientoIterativoGrasp(){
		 double time1= System.currentTimeMillis();
		Auxiliar a=new Auxiliar();
		Double mejorCosto=9999999999999999.9;
		Ruteo mejorRuteo=null;
		listaSetCovering=new ArrayList<Ruta>();
		for(int i=0;i<k;i++){
			HeuristicasTSPManagger h=new HeuristicasTSPManagger();
			List<Obra>tsp=h.CWJPC();
			List<Obra>tsp1=a.busquedaLocal(tsp);
			Ruteo r2=a.split(tsp1);
			listaSetCovering.addAll(r2.rutas);
			if(mejorCosto>r2.costoTotal){
				mejorCosto=r2.costoTotal;
				mejorRuteo=r2;
			}
		}
		 double time2= System.currentTimeMillis();
		 tiempoComputacionalIterGrasp=(time2-time1)/(1000.0*k);
		 tiempoComputacionalGrasp=(time2-time1)/(1000.0);
		return mejorRuteo;
	}
	/**
	 * Da el tiempo total de grasp + set covering
	 * @return tiempo computacional total
	 */
	public Double darTiempoTotal(){
		return tiempoComputacionalGrasp+tiempoComputacionalSetCovering;
	}
	/**
	 * Dar distancia total de todo el ruteo. Esta es la distancia que tuvieron que recolectar 
	 * @param ruteo arrojado por los procedimientos implementados
	 * @return distancia total recorrida en todo el ruteo
	 */
	public Double darDistanciaTotal(Ruteo r){
		Double respuesta=0.0;
		for(int i=0;i<r.rutas.size();i++){
			Ruta ruta=r.rutas.get(i);
			respuesta+=ruta.darDistanciaTotal();
		}
		return respuesta;
	}
	
	/**
	 * 
	 * @return
	 */
	public Ruteo setCovering(){
		Ruteo resultado=null;
		 double time1= System.currentTimeMillis();
		 Integer [][]a=new Integer[Auxiliar.OBRAS.size()][listaSetCovering.size()];
		 List<Double>c=new ArrayList<Double>();
		 List<Double>t=new ArrayList<Double>();
		 List<Obra>listObras=Auxiliar.OBRAS;
		 for(int i=0;i<listaSetCovering.size();i++){
			 c.add(listaSetCovering.get(i).costosTotales);
			 t.add(listaSetCovering.get(i).tiempoTotal);
		 }
		 for(int i=0;i<Auxiliar.OBRAS.size();i++){
			 for(int j=0;j<listaSetCovering.size();j++){
				 if(listaSetCovering.get(j).estaObraEnRuta(listObras.get(i))){
					 a[i][j]=1;
				 }else{
					 a[i][j]=0;
				 }
			 }
		 }
		 List<Integer>respuesta=new ArrayList<Integer>();
		if(fo==FO1){
			SetCovering sc=new SetCovering();
			respuesta=sc.generateModel(c, a);
			Double costosRuteo=sc.fo;
			List<Ruta>rutasRuteo=new ArrayList<Ruta>();
			for(int i=0;i<respuesta.size();i++){
				Ruta ruta=listaSetCovering.get(respuesta.get(i));
				rutasRuteo.add(ruta);
			}
			Ruteo ruteo=new Ruteo(costosRuteo,rutasRuteo);
			resultado=ruteo;
		}else if(fo==FO2){
			SetCovering2 sc=new SetCovering2();
			respuesta=sc.generateModel(c,t, a);
			Double costosRuteo=sc.fo;
			List<Ruta>rutasRuteo=new ArrayList<Ruta>();
			for(int i=0;i<respuesta.size();i++){
				Ruta ruta=listaSetCovering.get(respuesta.get(i));
				rutasRuteo.add(ruta);
			}
			Ruteo ruteo=new Ruteo(costosRuteo,rutasRuteo);
			resultado=ruteo;
		}else if(fo==FO3){
			SetCovering3 sc=new SetCovering3();
			respuesta=sc.generateModel(c, a);
			Double costosRuteo=sc.fo;
			List<Ruta>rutasRuteo=new ArrayList<Ruta>();
			for(int i=0;i<respuesta.size();i++){
				Ruta ruta=listaSetCovering.get(respuesta.get(i));
				rutasRuteo.add(ruta);
			}
			Ruteo ruteo=new Ruteo(costosRuteo,rutasRuteo);
			resultado=ruteo;
		}else if(fo==FO4){
			SetCovering4 sc=new SetCovering4();
			respuesta=sc.generateModel(c,t, a);
			Double costosRuteo=sc.fo;
			List<Ruta>rutasRuteo=new ArrayList<Ruta>();
			for(int i=0;i<respuesta.size();i++){
				Ruta ruta=listaSetCovering.get(respuesta.get(i));
				rutasRuteo.add(ruta);
			}
			Ruteo ruteo=new Ruteo(costosRuteo,rutasRuteo);
			resultado=ruteo;
		}
		 double time2= System.currentTimeMillis();
		 tiempoComputacionalSetCovering=(time2-time1)/1000.0;
		 return resultado;
	}
	/**
	 * 
	 * @param r
	 * @return
	 */
	public Double darCostoRuteo(List<Ruta>r){
		Double respuesta=0.0;
		for(int i=0;i<r.size();i++){
			respuesta+=r.get(i).costosTotales;
		}
		return respuesta;
	}
	public void imprimirResultados(int fo,File archivoResultados){
		try{
		FileWriter fw = new FileWriter(archivoResultados.getAbsoluteFile());
		
		BufferedWriter bw = new BufferedWriter(fw);
		if(fo==FO1){
			bw.write("La funcion objetivo usada fue: Minimizar Costos");
		}else if(fo==FO2){
			bw.write("La funcion objetivo usada fue: Minimizar el maximo tiempo de las rutas");
		}else if(fo==FO3){
			bw.write("La funcion objetivo usada fue: Minimizar el numero de rutas");	
		}else if(fo==FO4){
			bw.write("La funcion objetivo usada fue: Minimizar el tiempo");
		}
		bw.newLine();
		bw.write("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		bw.newLine();
		bw.write("Distancia total recorrida por todos los interventores: "+distanciaTotalRecorrida);
		bw.newLine();
		bw.write("Tiempo computacional de GRASP + Set Covering: "+tiempoComputacionalTotal);
		bw.newLine();
		bw.write("Numero de iteraciones de GRASP: "+k);
		bw.newLine();
		bw.write("Tiempo computacional promedio de una iteración de GRASP: "+tiempoComputacionalIterGrasp);
		bw.newLine();
		bw.write("Tiempo computacional del Set Covering: "+tiempoComputacionalSetCovering);
		
		
				
		bw.write("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		bw.close();
		
		JOptionPane.showMessageDialog (null, "El archivo se guardo con los parametros satisfactoriamente.", "Archivo Guardado", JOptionPane.INFORMATION_MESSAGE);
		}catch(Exception ee){
			JOptionPane.showMessageDialog (null, "No se llevó a cabo la simulación.", "Error", JOptionPane.ERROR_MESSAGE);

		}
	}
	public static void main(String[] args) {
		Grasp g=new Grasp(3,4);
		g.procedimientoIterativoGrasp();
		g.setCovering();
		
	}
}
