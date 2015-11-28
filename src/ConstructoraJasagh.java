import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ConstructoraJasagh {
	/**
	 * Ruteo solo aplicando grasp
	 * @param k numero de iteraciones de grasp
	 * @param a auxiliar que se usa para los algoritmos
	 * @return Ruteo con la lista de rutas y el costo total
	 */
	public Ruteo grasp(int k, Auxiliar a){
		JFrame parentFrame = new JFrame();
		 
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Especifique el nombre del archivo .txt");   
		 
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    //
		    Grasp g=new Grasp(k,4);
			Ruteo ruteo=g.procedimientoIterativoGrasp(a);
			g.imprimirResultadosSoloGrasp(fileToSave, ruteo);
		    JOptionPane.showMessageDialog (null, "Se llevó a cabo la simulación exitosamente. Consulte el archivo", "Succes!", JOptionPane.INFORMATION_MESSAGE);
		    return ruteo;
		}else{
			JOptionPane.showMessageDialog (null, "No se llevó a cabo la simulación.", "Error", JOptionPane.ERROR_MESSAGE);

		}
		return null;
	}
	
	/**
	 * Ruteo aplicando GRASP y luego set covering
	 * @param k numero de iteraciones de grasp
	 * @param fo funcion objetivo de set covering
	 * @param a auxiliar de algoritmos
	 * @return Ruteo con las rutas encontradas y los costos
	 */
	public Ruteo graspMasSetCovering(int k,int fo,Auxiliar a){
		JFrame parentFrame = new JFrame();
		 
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Especifique el nombre del archivo .txt");   
		 
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    Grasp g=new Grasp(k,fo);
			g.procedimientoIterativoGrasp(a);
			Ruteo ru=g.setCovering();
			g.imprimirResultados(fo, fileToSave, ru);
		    //
		    JOptionPane.showMessageDialog (null, "Se llevó a cabo la simulación exitosamente. Consulte el archivo", "Succes!", JOptionPane.INFORMATION_MESSAGE);
		    return ru;
		}else{
			JOptionPane.showMessageDialog (null, "No se llevó a cabo la simulación.", "Error", JOptionPane.ERROR_MESSAGE);

		}
		return null;
	}
}
