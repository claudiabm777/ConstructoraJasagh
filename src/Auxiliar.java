import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class Auxiliar {
	//-----------------------------------------------------------------------------------------
	//CONSTANTES-------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	
	//Tenga en cuenta que todas las unidades de distancia se pasaron a Kilometros, y las de tiempo a horas
	public final static Double NUMERO_TOTAL_OBRAS=177.0;
	public final static Double NUMERO_TOTAL_MUNICIPIOS=161.0;
	public final static Double TIEMPO_MAXIMO_VIAJE=20.0*24.0;
	public final static Double VELOCIDAD_VEHICULOS=40.0;
	public final static Double HORAS_DESPUES_LLEGAR_MUNICIPIO=2.0;
	public final static Double KILOMETROS_POR_GALON=25.0;
	public final static Double PRECIO_GALON=8000.0;
	public final static Double PESOS_POR_KILOMETRO_PEAJE=90.0;
	public final static Double COSTO_HOTEL_HORA=150000.0/24.0;
	public final static Double COSTO_ALIMENTACION_HORA=80000.0/24.0;
	
	//Informacion de la  tabla en el enunciado por tipo de proyecto
	public static HashMap<Double, Double>DURACIONES_INSPECCION=new HashMap<Double, Double>();
	public static HashMap<Double, Double>TIEMPOS_DESCANSO=new HashMap<Double, Double>();
	
	//Informacion del archivo de excel
	public static List<Municipio> MUNICIPIOS=new ArrayList<Municipio>();
	public static List<Obra> OBRAS=new ArrayList<Obra>();
	public static HashMap<String, Double>DISTANCIAS=new HashMap<String, Double>();
	public static HashMap<String, Integer>MUNICIPIO_ID=new HashMap<String, Integer>();
	
	//-----------------------------------------------------------------------------------------
	//CONSTRUCTOR----------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Auxiliar(){
		//Valores del hashmap (unidades en horas)
		DURACIONES_INSPECCION.put(1.0, (2.0*24.0));
		DURACIONES_INSPECCION.put(2.0, (1.5*24.0));
		DURACIONES_INSPECCION.put(3.0, (1.0*24.0));
		DURACIONES_INSPECCION.put(4.0, (2.0*24.0));
		DURACIONES_INSPECCION.put(5.0, (2.5*24.0));
		
		//Valores del hashmap (unidades en horas)
		TIEMPOS_DESCANSO.put(1.0, 24.0);
		TIEMPOS_DESCANSO.put(2.0, 12.0);
		TIEMPOS_DESCANSO.put(3.0, 0.0);
		TIEMPOS_DESCANSO.put(4.0, 12.0);
		TIEMPOS_DESCANSO.put(5.0, 24.0);
		
		//Lectura de excel
		leerDatosMunicipios();
		leerDatosObras();
		leerDatosDistancias();
	}
	
	
	//-----------------------------------------------------------------------------------------
	//MÉTODOS----------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	/**
	 * Metodo SPLIT que devuelve las rutas para el problema de ruteo
	 * @param tspSinCiclo - Es una lista en el orden del tsp, con el cual se realiza el split
	 * @return ruteo - un objeto ruteo el cual tiene la lista con las rutas y los costos totales del ruteo
	 */
	public  Ruteo split(List<Obra>tspSinCiclo){
		List<List<Ruta>>rutasDesde=new ArrayList<List<Ruta>>(tspSinCiclo.size());
		for (int i = 0; i < tspSinCiclo.size(); i++) {
			List<Ruta>l=new ArrayList<Ruta>();
			rutasDesde.add(l);
		}
		for(int i=0;i<tspSinCiclo.size();i++){
			Obra o=tspSinCiclo.get(i);
			for(int j=i+1;j<tspSinCiclo.size();j++){
				List<Obra>obrasRuta=new ArrayList<Obra>();
				obrasRuta.add(tspSinCiclo.get(0));
				for(int k=1;k<=(j-i);k++){
					obrasRuta.add(tspSinCiclo.get(i+k));
				}
				obrasRuta.add(tspSinCiclo.get(0));
				Ruta ruta=new Ruta(obrasRuta,o);
				ruta.posicionUltObraEnTSP=j;
				if(ruta.tiempoTotal<Auxiliar.TIEMPO_MAXIMO_VIAJE){
					rutasDesde.get(i).add(ruta);
				}
			}
		}
		Ruteo respuesta=bellmanFord(rutasDesde);
		return respuesta;
	}
	/**
	 * Metodo que realiza bellman ford especializado para SPLIT
	 * @param rutasDesde - Lista de listas con las rutas. Ordenado de acuedo a la posicion de las obras en el tsp
	 * @return ruteo - un objeto ruteo el cual tiene la lista con las rutas y los costos totales del ruteo
	 */
	public Ruteo bellmanFord(List<List<Ruta>>rutasDesde){
		NodoInfo[]bellmanFord=new NodoInfo[rutasDesde.size()];
		bellmanFord[0]=new NodoInfo(-1,0.0,null);
		for(int i=1;i<rutasDesde.size();i++){
			bellmanFord[i]=new NodoInfo(-2,9999999999999999999.9,null);
		}
		for(int i=0;i<rutasDesde.size();i++){
			List<Ruta>rutasi=rutasDesde.get(i);
			for(int j=0;j<rutasi.size();j++){
				Ruta ruta=rutasi.get(j);
				if(bellmanFord[ruta.posicionUltObraEnTSP].costo>bellmanFord[i].costo+ruta.costoTrans){
					bellmanFord[ruta.posicionUltObraEnTSP].costo=bellmanFord[i].costo+ruta.costoTrans;
					bellmanFord[ruta.posicionUltObraEnTSP].ruta=ruta;
					bellmanFord[ruta.posicionUltObraEnTSP].posicion=i;
				}
			}
		}
		int num=bellmanFord.length-1;
		Double costoTot=bellmanFord[num].costo;
		List<Ruta>rutasRuteo=new ArrayList<Ruta>();
		while(num>0){
			rutasRuteo.add(bellmanFord[num].ruta);
			num=bellmanFord[num].posicion;
		}
		Ruteo respuesta=new Ruteo(costoTot,rutasRuteo);
		return respuesta;
	}
	
	public List<Obra>busquedaLocal(List<Obra>tsp){
		tsp.add(null);
		Ruta r=new Ruta(tsp,null);
		Double mejorCosto=r.costosTotales;
		Ruta mejorRuta=r;
		tsp.remove(tsp.size()-1);
		for(int i=1;i<tsp.size();i++){		
			for(int j=tsp.size()-1;j>i;j--){
				List<Obra>l1=tsp.subList(0, i);
				List<Obra>l2=tsp.subList(i, j);
				List<Obra>l3=tsp.subList(j, tsp.size());
				List<Obra>total1=new ArrayList<Obra>();
				total1.addAll(l1);
				total1.addAll(l3);
				total1.addAll(l2);
				total1.add(null);
				Ruta rc=new Ruta(total1,null);
				if(rc.soyFactible==true){
					if(mejorCosto>rc.costosTotales)System.out.println(mejorCosto+" * "+rc.costosTotales);
					if(mejorCosto>rc.costosTotales){
						mejorCosto=rc.costosTotales;
						mejorRuta=rc;
					}
				}
			}
		}
		List<Obra>resp=mejorRuta.obras;
		System.out.println(resp);
		resp.remove(resp.size()-1);
		return resp;
	}
	public boolean revisarFactibilidad(List<Obra>tsp){
		for(int i=2;i<tsp.size();i++){
			Integer l1=Auxiliar.MUNICIPIO_ID.get(tsp.get(i-1).municipio+" - "+tsp.get(i-1).departamento);
			Integer l2=Auxiliar.MUNICIPIO_ID.get(tsp.get(i).municipio+" - "+tsp.get(i).departamento);
			Double d=Auxiliar.DISTANCIAS.get(l1+" - "+l2);
			if(d<0){
				return false;
			}
		}
		return true;
	}
	/**
	 * Lectura de informacion de los municipios de excel
	 */
	private void leerDatosMunicipios(){
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(Auxiliar.class.getResourceAsStream("Datos.xls"));
			Sheet sheet = workbook.getSheet("Municipios");
			for(int i=1;i<=Auxiliar.NUMERO_TOTAL_MUNICIPIOS+1;i++){
				String idN = sheet.getCell(0,i).getContents();
				String municipioN = sheet.getCell(1,i).getContents();
				String munN=municipioN.split(" - ")[0];
				String depN=municipioN.split(" - ")[1];
				String lonN = sheet.getCell(2,i).getContents().replace(",", ".");
				String latN = sheet.getCell(3,i).getContents().replace(",", ".");
				Municipio municipio=new Municipio(Integer.parseInt(idN),munN,depN,Double.parseDouble(lonN),Double.parseDouble(latN));
				MUNICIPIOS.add(municipio);
				MUNICIPIO_ID.put(munN+" - "+depN, Integer.parseInt(idN));
			}
			workbook.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Lectura de informacion de las obras de excel
	 */
	private void leerDatosObras(){
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(Auxiliar.class.getResourceAsStream("Datos.xls"));
			Sheet sheet = workbook.getSheet("Obras");
			for(int i=1;i<=Auxiliar.NUMERO_TOTAL_OBRAS;i++){
				String idN = sheet.getCell(0,i).getContents();
				String tipoProyectoN = sheet.getCell(1,i).getContents();
				String municipioN = sheet.getCell(2,i).getContents();
				String munN=municipioN.split(" - ")[0];
				String depN=municipioN.split(" - ")[1];
				String regionalN = sheet.getCell(3,i).getContents();
				Obra obra=new Obra(Integer.parseInt(idN),darIdDadoNombreTipoProyecto(tipoProyectoN),munN,depN,regionalN);
				OBRAS.add(obra);
			}
			workbook.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Lectura de informacion de las distancias entre municipios de excel
	 */
	private void leerDatosDistancias(){
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(Auxiliar.class.getResourceAsStream("Datos.xls"));
			Sheet sheet = workbook.getSheet("Matriz de Distancias (en Km)");
			for(int i=1;i<=NUMERO_TOTAL_MUNICIPIOS+1;i++){
				for(int j=1;j<=NUMERO_TOTAL_MUNICIPIOS+1;j++){
					String distanciaN = sheet.getCell(i,j).getContents().replace(",", ".");
					String nombreOrigenN= sheet.getCell(0,j).getContents();
					String nombreDestinoN= sheet.getCell(i,0).getContents();
					Integer origen=MUNICIPIO_ID.get(nombreOrigenN);
					Integer destino=MUNICIPIO_ID.get(nombreDestinoN);
					if(distanciaN.trim().equals("")){
						distanciaN+="-5.0";
					}
					DISTANCIAS.put(origen+" - "+destino, Double.parseDouble(distanciaN));
				}
			}
			workbook.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Dado el nombre del tipo de proyecto, devuelve su ID
	 * @param tipoProyecto - String con el nombre del tipo de proyecto
	 * @return un Double con el ID del tipo de proyecto
	 */
	public Double darIdDadoNombreTipoProyecto(String tipoProyecto){
		Double respuesta=0.0;
		if(tipoProyecto.equals("Adecuacion de vias veredales")){
			respuesta=1.0;
		}
		else if(tipoProyecto.equals("Construccion de sistemas de riego y obras de drenaje comunitario")){
			respuesta=2.0;
		}
		else if(tipoProyecto.equals("Construccion Invernadero comunitario")){
			respuesta=3.0;
		}
		else if(tipoProyecto.equals("Construccion y/o mejora de plazas de mercado")){
			respuesta=4.0;
		}
		else if(tipoProyecto.equals("Mantenimiento y/o limpieza de canales de agua, cunetas o vallado")){
			respuesta=5.0;
		}
		return respuesta;
	}
	
	/**
	 * Calcula el tiempo desde que se sale del municipio de origen, se llega al de origen, se suman las dos 
	 * horas de ir al hotel, el tiempo de evaluacion de la obra y el tiempo de descanso.
	 * @param nombreMunicipioOrigen String con el nomnre del municipio de origen (municipio - depertamento)
	 * @param obraSiguiente la obra a evaluar en el municipio de destino
	 * @return Double con el tiempo del trayecto. Si no se puede ir de un 
	 * lugar a otro, se devuelve un número negativo
	 */
	public static Double calcularTiempoTrayecto(String nombreMunicipioOrigen,Obra obraSiguiente,Double des,Double rec){
		Double respuesta=0.0;
		Integer idOrigen=MUNICIPIO_ID.get(nombreMunicipioOrigen);
		Integer idDestino;
		if(obraSiguiente!=null){
			idDestino=MUNICIPIO_ID.get(obraSiguiente.municipio+" - "+obraSiguiente.departamento);
			Double idTipoProyecto=obraSiguiente.tipoProyecto;
			Double distancia=DISTANCIAS.get(idOrigen+" - "+idDestino);
			if(distancia<0){
				return distancia;
			}
			Double tiempoDist=distancia/Auxiliar.VELOCIDAD_VEHICULOS;
			Double tiempoInspeccionDescanso=DURACIONES_INSPECCION.get(idTipoProyecto)+TIEMPOS_DESCANSO.get(idTipoProyecto);
			if(nombreMunicipioOrigen.equals(obraSiguiente.municipio+" - "+obraSiguiente.departamento)){
				des+=tiempoInspeccionDescanso;
				rec+=tiempoDist;
				respuesta=tiempoDist+tiempoInspeccionDescanso;
			}else{
				des+=tiempoInspeccionDescanso+Auxiliar.HORAS_DESPUES_LLEGAR_MUNICIPIO;
				rec+=tiempoDist;
				respuesta=tiempoDist+Auxiliar.HORAS_DESPUES_LLEGAR_MUNICIPIO+tiempoInspeccionDescanso;
			}
		}
		else{
			idDestino=MUNICIPIO_ID.get("Bogota - Colombia");
			Double distancia=DISTANCIAS.get(idOrigen+" - "+idDestino);
			if(distancia<0){
				return distancia;
			}
			Double tiempoDist=distancia/Auxiliar.VELOCIDAD_VEHICULOS;
			rec+=tiempoDist;
			respuesta=tiempoDist;
		}
		return respuesta;
	}
	
	public static void main(String[] args) {
		Auxiliar a=new Auxiliar();
		HeuristicasTSPManagger h=new HeuristicasTSPManagger();
		List<Obra>tsp=h.CWJPC();
		Ruteo r1=a.split(tsp);
		System.out.println("Costos 1: "+r1.costoTotal);
		List<Obra>tsp1=a.busquedaLocal(tsp);
		Ruteo r2=a.split(tsp1);
		System.out.println("Costos 2: "+r2.costoTotal);
		System.out.println(a.revisarFactibilidad(tsp1));
		System.out.println(a.revisarFactibilidad(tsp));
//		Ruteo r=a.split(tsp);
//		System.out.println(r.costoTotal);
//		for(int i=0;i<r.rutas.size();i++){
//			Ruta rut=r.rutas.get(i);
//			System.out.println("Ruta "+i+":");
//			for(int j=0;j<rut.obras.size();j++){
//				System.out.println("    "+rut.obras.get(j));
//			}
//		}
			
	}
	
}
