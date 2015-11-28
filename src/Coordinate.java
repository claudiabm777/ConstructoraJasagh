

public class Coordinate {
	
	//Clase q representa una coordenada. Se usa en los mapas
	
	//-----------------------------------------------------------------------------------------
	//ATRIBUTOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	String latitude;
	
	String longitude;
	//-----------------------------------------------------------------------------------------
	//CONSTRUCTOR--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public Coordinate(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	//-----------------------------------------------------------------------------------------
	//METODOS--------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "lat:"+latitude+"   long:"+longitude;
	}
}
