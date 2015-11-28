import java.util.ArrayList;
import java.util.List;


public class Ruteo {
	//-----------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Double costoTotal;
	public List<Ruta>rutas;
	public Coordinate origenDestino;
	public ArrayList<Coordinate>coordenadas=new ArrayList<Coordinate>();
	
	//-----------------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Ruteo(Double costoTotal,List<Ruta>rutas){
		this.costoTotal=costoTotal;
		this.rutas=rutas;
		calcularCoordenadas();
	}
	//-----------------------------------------------------------------------------------------
	//METODOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	/**
	 * Calcula coordenadas
	 */
	public void calcularCoordenadas(){
		for(int i=0;i<rutas.size()-1;i++){
			Ruta r=rutas.get(i);
			for(int j=0;j<r.obras.size();j++){
				if(i==0 && j==0){
					String longit=Auxiliar.MUNICIPIO_LONG.get("Bogota - Colombia");
					String lat=Auxiliar.MUNICIPIO_LAT.get("Bogota - Colombia");
					origenDestino=new Coordinate(lat, longit) ;				
				}else{
					if(r.obras.get(j)!=null){
						String longit=Auxiliar.MUNICIPIO_LONG.get(r.obras.get(j).municipio+" - "+r.obras.get(j).departamento);
						String lat=Auxiliar.MUNICIPIO_LAT.get(r.obras.get(j).municipio+" - "+r.obras.get(j).departamento);
					Coordinate c=new Coordinate(lat, longit) ;
					coordenadas.add(c);
					}else{
						String longit=Auxiliar.MUNICIPIO_LONG.get("Bogota - Colombia");
						String lat=Auxiliar.MUNICIPIO_LAT.get("Bogota - Colombia");
						Coordinate c=new Coordinate(lat, longit) ;
						coordenadas.add(c);
					}
				}
			}
		}
		System.out.println("Coor: "+coordenadas);
		System.out.println("origendestion"+origenDestino);
	}
	
}
