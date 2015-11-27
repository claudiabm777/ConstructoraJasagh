

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FileHandler {
	
	public final String API_KEY = "AIzaSyAimlUUEJSiGvOMzb_1S_VlHUipA8B_TY0";
	
	public int zoom;
	
	public Coordinate center;
	
	public Coordinate origin;
	
	public Coordinate destination;
	
	public String routeColor;
	
	public List<Coordinate> coordinates;
	
	public FileHandler(int zoom, Coordinate center, Coordinate origin, Coordinate destination, List<Coordinate> coordinates) {
		this.zoom = zoom;
		this.center = center;
		this.origin = origin;
		this.destination = destination;
		this.routeColor = routeColor;
		this.coordinates = coordinates;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public Coordinate getCenter() {
		return center;
	}

	public void setCenter(Coordinate center) {
		this.center = center;
	}

	public String getRouteColor() {
		return routeColor;
	}

	public void setRouteColor(String routeColor) {
		this.routeColor = routeColor;
	}
	
	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}
	
	public String getHTMLHeader() {
		String ans = "";
		
		ans += "<!DOCTYPE html>";
		ans += "\n<html>";
		ans += "\n\n<head>";
		ans += "\n\t<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">";
		ans += "\n\t<meta charset=\"utf-8\">";
		ans += "\n\t<title></title>";
		ans += "\n\t<style>";
		ans += "\n\thtml,";
		ans += "\n\tbody {";
		ans += "\n\t\theight: 100%;";
		ans += "\n\t\tmargin: 0;";
		ans += "\n\t\tpadding: 0;";
		ans += "\n\t}";
		ans += "\n";
		ans += "\n\t#map {";
		ans += "\n\t\theight: 100%;";
		ans += "\n\t}";
		ans += "\n\t</style>";
		ans += "\n</head>";
		ans += "\n";
		ans += "\n<body>";
		ans += "\n\t<div id=\"map\"></div>";
		ans += "\n\t<script>";
		ans += "\nfunction initMap() {";
		ans += "\n\nvar directionsService = new google.maps.DirectionsService;";
		ans += "\n\nvar directionsDisplay = new google.maps.DirectionsRenderer;";
		ans += "\n\nvar map = new google.maps.Map(document.getElementById('map'), {";
		ans += "\n\t\t\tzoom: " + this.zoom + ",";
		ans += "\n\t\t\tcenter: {";
		ans += "\n\t\t\t\tlat: " + this.center.latitude + ",";
		ans += "\n\t\t\t\tlng: " + this.center.longitude;
		ans += "\n\t\t\t}";
		ans += "\n\t\t});";
		ans += "\n\n\t\tdirectionsDisplay.setMap(map);";
		ans += "\n\n\t\tcalculateAndDisplayRoute(directionsService, directionsDisplay);";
		ans += "\n}";
		
		ans += "\n\n\tfunction calculateAndDisplayRoute(directionsService, directionsDisplay) {";
		ans += "\n\ndirectionsService.route({";
		ans += String.format("\n\t\t\torigin: {lat: %s, lng: %s},", origin.latitude, origin.longitude);
		ans += String.format("\n\t\t\tdestination: {lat: %s, lng: %s},", destination.latitude, destination.longitude);
		
		return ans;		
	}
	
	public String getRoute() {
		String ans = "";	               
		
		ans += "\n\t\t\twaypoints: [";
		
		int i = 0, bound = this.coordinates.size();
		
		while(i < bound) {
			Coordinate current = this.coordinates.get(i);
			
			if(i == bound - 1)
				ans += String.format("\n\t\t\t\t{location: {lat: %s, lng: %s}, stopover: true}", current.latitude, current.longitude);
			else
				ans += String.format("\n\t\t\t\t{location: {lat: %s, lng: %s}, stopover: true},", current.latitude, current.longitude);
			
			i++;
		}
		
		ans += "],";
		
		return ans;
	}
	
	public String getHTMLFooter() {
		String ans = "";
		
		ans += "\n\t\t\toptimizeWaypoints: true,";
		ans += "\n\t\t\ttravelMode: google.maps.TravelMode.DRIVING";
		ans += "\n\t\t}, function(response, status) {";
		ans += "\n\t\t\tif (status === google.maps.DirectionsStatus.OK) {";
		ans += "\n\t\t\t\tdirectionsDisplay.setDirections(response);";
		ans += "\n\t\t\t}";
		ans += "\n\t\t});";
		ans += "\n\t}";
		ans += "\n\n\t</script>";
		ans += "\n\t<script async defer src=\"https://maps.googleapis.com/maps/api/js?key=" + this.API_KEY + "&signed_in=true&callback=initMap\"></script>";
		ans += "\n</body>\n";
		ans += "\n</html>";
		
		return ans;
	}
	
	public String getBody() {
		String body = this.getHTMLHeader() + this.getRoute() + this.getHTMLFooter();
		return body;
	}
	
	public void writeToFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		String body = getBody();
		PrintWriter writer = new PrintWriter(fileName);
		writer.println(body);
		writer.close();
	}
}
