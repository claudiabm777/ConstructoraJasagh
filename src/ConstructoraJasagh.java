import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ConstructoraJasagh {
	
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
