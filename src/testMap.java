	import java.awt.BorderLayout;
	import java.awt.Dimension;
	import javax.swing.JEditorPane;
	import javax.swing.JFrame;
	import javax.swing.JScrollPane;
	import javax.swing.SwingUtilities;
	import javax.swing.text.Document;
	import javax.swing.text.html.HTMLEditorKit;
	import javax.swing.text.html.StyleSheet;
public class testMap {



	  public static void main(String[] args)
	  {
	    new testMap();
	  }
	  
	  public testMap()
	  {
	    SwingUtilities.invokeLater(new Runnable()
	    {
	      public void run()
	      {
	        // create jeditorpane
	        JEditorPane jEditorPane = new JEditorPane();
	        
	        // make it read-only
	        jEditorPane.setEditable(false);
	        
	        // create a scrollpane; modify its attributes as desired
	        JScrollPane scrollPane = new JScrollPane(jEditorPane);
	        
	        // add an html editor kit
	        HTMLEditorKit kit = new HTMLEditorKit();
	        jEditorPane.setEditorKit(kit);
	        
	        // add some styles to the html
	        StyleSheet styleSheet = kit.getStyleSheet();
	        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
	        styleSheet.addRule("h1 {color: blue;}");
	        styleSheet.addRule("h2 {color: #ff0000;}");
	        styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");

	        // create some simple html as a string
	        String htmlString = "<html>\n"
	                          + "<body>\n"
	        		+"<div id=\"map\"></div>\n"
	        		+"<script>\n"
	
	+"function initMap() {\n"
  +"var chicago = {lat: 4.7109886, lng: -74.072092};\n"
  +"var indianapolis = {lat: 4.97088, lng: -73.3799};\n"

  +"var map = new google.maps.Map(document.getElementById('map'), {\n"
   +"center: chicago,\n"
    +"scrollwheel: false,\n"
    +"zoom: 7\n"
    +"});\n"

  +"var directionsDisplay = new google.maps.DirectionsRenderer({\n"
    +"map: map\n"
    +"});\n"

  // Set destination, origin and travel mode.
  +"var request = {\n"
    +"destination: indianapolis,\n"
    +"origin: chicago,\n"
    +"travelMode: google.maps.TravelMode.DRIVING\n"
    +"};\n"

  // Pass the directions request to the directions service.
  +"var directionsService = new google.maps.DirectionsService();\n"
  +"directionsService.route(request, function(response, status) {\n"
    +"if (status == google.maps.DirectionsStatus.OK) {\n"
      // Display the route on the map.
      +"directionsDisplay.setDirections(response);\n"
      +"}\n"
      +"});\n"
      +"}\n"
	+"</script>\n"
	+"<script async defer src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyAimlUUEJSiGvOMzb_1S_VlHUipA8B_TY0&signed_in=true&callback=initMap\"></script>\n"
	                          + "</body>\n";
	        
	        // create a document, set it on the jeditorpane, then add the html
	        Document doc = kit.createDefaultDocument();
	        jEditorPane.setDocument(doc);
	        jEditorPane.setText(htmlString);

	        // now add it all to a frame
	        JFrame j = new JFrame("HtmlEditorKit Test");
	        j.getContentPane().add(scrollPane, BorderLayout.CENTER);

	        // make it easy to close the application
	        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        // display the frame
	        j.setSize(new Dimension(300,200));
	        
	        // pack it, if you prefer
	        //j.pack();
	        
	        // center the jframe, then make it visible
	        j.setLocationRelativeTo(null);
	        j.setVisible(true);
	      }
	    });
	  
	}
}
