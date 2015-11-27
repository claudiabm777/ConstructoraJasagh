
public class Municipio {
	//-----------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Integer id;
	public String nombre;
	public String departamento;
	public String longitud;
	public String latitud;
	
	//-----------------------------------------------------------------------------------------
	//CONSTRUCTOR------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Municipio(Integer id,String nombre,String departamento,String longitud,String latitud){
		this.id=id;
		this.nombre=nombre;
		this.departamento=departamento;
		this.latitud=latitud;
		this.longitud=longitud;
	}
}
