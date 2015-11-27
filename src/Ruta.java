import java.util.ArrayList;
import java.util.List;


public class Ruta {
	//-----------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	
	public List<Obra>obras;
	public Double tiempoTotal;
	public Double costosTotales;
	public Double costoTrans;
	public List<Integer>listaMunicipios;
	public Obra origenGrafoAuxiliar;
	public Obra ultimaObra;
	public Integer posicionUltObraEnTSP;
	
	//****************************
	public Double costoRecorrido;
	public Double costoDescanso;
	public Double diasRecorrido;
	public Double diasDescanso;
	//****************************
	
	public boolean soyFactible;
	//-----------------------------------------------------------------------------------------
	//CONSTRUCTOR------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Ruta(List<Obra>obras,Obra origenGrafoAuxiliar){
		diasDescanso=0.0;
		diasRecorrido=0.0;
		posicionUltObraEnTSP=0;
		soyFactible=true;
		this.obras=obras;
		this.listaMunicipios=darListaIdsMunicipios();
		this.tiempoTotal=calcularTiempoDeUnaRuta();
		this.costosTotales=calcularCostosDeUnaRuta();
		this.origenGrafoAuxiliar=origenGrafoAuxiliar;
		this.ultimaObra=obras.get(obras.size()-2);

	}
	
	//-----------------------------------------------------------------------------------------
	//METODOS----------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	/**
	 * Calcula los costos de la ruta 
	 * @return Double con los costos totales de la ruta
	 */
	private Double calcularCostosDeUnaRuta(){
		Double respuesta=0.0;
		Double costoHotel=tiempoTotal*Auxiliar.COSTO_HOTEL_HORA;
		Double costoAliment=tiempoTotal*Auxiliar.COSTO_ALIMENTACION_HORA;
		Double costoTransporte=0.0;
		Double costoPeaje=0.0;
		for(int i=1;i<listaMunicipios.size();i++){
			Double distancia=Auxiliar.DISTANCIAS.get(listaMunicipios.get(i-1)+" - "+listaMunicipios.get(i));
			costoTransporte+=(distancia*Auxiliar.PRECIO_GALON/Auxiliar.KILOMETROS_POR_GALON);
			costoPeaje+=distancia*Auxiliar.PESOS_POR_KILOMETRO_PEAJE;
		}
		costoTrans=costoTransporte;
		costoDescanso=costoHotel+costoAliment;
		costoRecorrido=costoTransporte+costoPeaje;
		respuesta=costoHotel+costoAliment+costoTransporte+costoPeaje;
		return respuesta;
	}
	
	/**
	 * Calcula el tiempo total transcurrido en una ruta especifica
	 * @return Double con el tiempo total que toma la ruta
	 */
	private Double calcularTiempoDeUnaRuta(){
		Double tiempoRuta=0.0;
		Double tr=0.0;
		for(int i=1;i<obras.size();i++){
			String nombreMunicipioOrigen;
			if(obras.get(i-1)!=null){
			 nombreMunicipioOrigen=obras.get(i-1).municipio+" - "+obras.get(i-1).departamento;
			}
			else{
				nombreMunicipioOrigen="Bogota - Colombia";
			}
			Obra obraSiguiente=obras.get(i);
			Dob des=new Dob();
			Dob rec=new Dob();
			tr=Auxiliar.calcularTiempoTrayecto(nombreMunicipioOrigen,obraSiguiente,des,rec);
			diasRecorrido+=rec.num;
			diasDescanso+=des.num;
			if(tr<=-1){
				soyFactible=false;
			}
			tiempoRuta+=tr;
			//System.out.println("------"+tiempoRuta);
		}
		return tiempoRuta;
	}
	/**
	 * Metodo que da la distancia total de la ruta
	 * @return distancia total de la ruta
	 */
	public Double darDistanciaTotal(){
		Double respuesta=0.0;
		for(int i=1;i<listaMunicipios.size();i++){
			respuesta+=Auxiliar.DISTANCIAS.get(listaMunicipios.get(i-1)+" - "+listaMunicipios.get(i));
		}
		return respuesta;
	}
	/**
	 * Devuelve la lista de ids de todas las obras
	 * @return List con los ids de todas las obras
	 */
	private List<Integer>darListaIdsMunicipios(){
		List<Integer> listaIDs=new ArrayList<Integer>();
		//System.out.println(obras.toString());
		
		for(int i=0;i<obras.size();i++){
			if(obras.get(i)!=null){
			//System.out.println((obras.get(i)));
			listaIDs.add(Auxiliar.MUNICIPIO_ID.get(obras.get(i).municipio+" - "+obras.get(i).departamento));
			}else{
				listaIDs.add(1);
			}
			}
		return listaIDs;
	}
	
	/**
	 * Devuelve true si la obra esta en la ruta
	 * @param o obra que se quiere revisar
	 * @return true si la obra esta en la ruta
	 */
	public boolean estaObraEnRuta(Obra o){
		return obras.contains(o);
	}
	
}
