
public class Obra {
	//-----------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Integer id;
	public Double tipoProyecto;
	public String municipio;
	public String departamento;
	public String regional;
	
	//-----------------------------------------------------------------------------------------
	//CONSTRUCTOR------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Obra(Integer id, Double tipoPoroyecto, String municipio, String departamento, String regional){
		this.id=id;
		this.tipoProyecto=tipoPoroyecto;
		this.municipio=municipio;
		this.departamento=departamento;
		this.regional=regional;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[id: "+id+"]";
	}
}
