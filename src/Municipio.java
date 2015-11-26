
public class Municipio {
	//-----------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Integer id;
	public String nombre;
	public String departamento;
	public Double longitud;
	public Double latitud;
	
	//-----------------------------------------------------------------------------------------
	//CONSTRUCTOR------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Municipio(Integer id,String nombre,String departamento,Double longitud,Double latitud){
		this.id=id;
		this.nombre=nombre;
		this.departamento=departamento;
		this.latitud=latitud;
		this.longitud=longitud;
	}
}
