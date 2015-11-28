

import java.awt.BorderLayout;
import javax.swing.JFrame;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class MapFrame {
	//Esta clase da soporte a los mapas
	private JFrame frame = new JFrame("Mapa");
	
	public MapFrame(String htmlFile) {
		
		final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        frame = new JFrame("Visualizacion Mapa");
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);       

        browser.loadHTML(htmlFile);
	}
	
}
