import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;


public class InterfazConstructora extends JFrame implements ActionListener {
	private ConstructoraJasagh mundo;
	
	private JTextField txtSencilla;
	private JLabel lblSencilla;
	private JButton btnSencilla;
	
	private JTextField txtSet;
	private JLabel lblSet;
	private JButton btnSet;
	
	private Auxiliar a;
	
	private JButton btnMap;
	private JLabel lblMap;
	private JTextField txtMap;
	private JLabel lblMapID;
	
	public Ruteo elRuteo=null;
	private JList list;
	public int metodoSimple;
	public int metdodoMultiple;
	public String[] selections = { "Minimizar Costos", "Min-Max Tiempo Rutas","Minimizar Numero Rutas","Minimizar Tiempo"};
	public InterfazConstructora(Auxiliar a){
		this.a=a;
		setTitle("Constructora Jasagh");
		setSize(450,280);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		mundo=new ConstructoraJasagh();
		
		
	    list = new JList(selections);
	    list.setSelectedIndex(0);
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		
		JPanel panelSimSencilla=new JPanel();
		panelSimSencilla.setLayout(new GridLayout(2,2));
		panelSimSencilla.setPreferredSize(new Dimension(0,75));
		TitledBorder border=BorderFactory.createTitledBorder("Ruteo con GRASP");
		border.setTitleColor(Color.GRAY);
		panelSimSencilla.setBorder(border);
		
		JPanel panelSimVecinos=new JPanel();
		panelSimVecinos.setLayout(new GridLayout(3,2));
		panelSimVecinos.setPreferredSize(new Dimension(0,93));
		TitledBorder border3=BorderFactory.createTitledBorder("Ruteo con GRASP+Set Covering");
		border3.setTitleColor(Color.GRAY);
		panelSimVecinos.setBorder(border3);
		
		JPanel panelMap=new JPanel();
		panelMap.setLayout(new GridLayout(2,2));
		panelMap.setPreferredSize(new Dimension(0,75));
		TitledBorder border4=BorderFactory.createTitledBorder("Mapas de rutas");
		border4.setTitleColor(Color.GRAY);
		panelMap.setBorder(border4);
		
		txtSencilla=new JTextField("5");
		lblSencilla=new JLabel("Ingrese el numero k:") ;
		btnSencilla=new JButton("Comenzar!");
		btnSencilla.setActionCommand("SENCILLA");
		btnSencilla.addActionListener(this);
		
		panelSimSencilla.add(lblSencilla);
		panelSimSencilla.add(txtSencilla);
		panelSimSencilla.add(new JLabel(""));
		panelSimSencilla.add(btnSencilla);
		
		txtSet=new JTextField("5");
		lblSet=new JLabel("Ingrese el numero k:");
		btnSet=new JButton("Comenzar!");
		btnSet.setActionCommand("MULTIPLE");
		btnSet.addActionListener(this);
		
		panelSimVecinos.add(new JLabel("Ingrese el numero k:"));
		panelSimVecinos.add(txtSet);
		panelSimVecinos.add(new JLabel("Seleccione la FO:"));
		panelSimVecinos.add(new JScrollPane(list));
		panelSimVecinos.add(lblSet);
		panelSimVecinos.add(btnSet);
		
		txtMap=new JTextField("2");
		lblMapID=new JLabel("Ingrese el id de la ruta:");
		lblMap=new JLabel(" ");
		btnMap=new JButton("Ver ruta en mapa");
		btnMap.setActionCommand("MAP");
		btnMap.addActionListener(this);
		panelMap.add(lblMapID);
		panelMap.add(txtMap);
		panelMap.add(lblMap);
		panelMap.add(btnMap);
		
		
		add(panelSimVecinos,BorderLayout.CENTER);
		add(panelSimSencilla,BorderLayout.NORTH);
		add(panelMap,BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args) {
		Auxiliar a=new Auxiliar();
		InterfazConstructora interfaz=new InterfazConstructora(a);
		interfaz.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando=e.getActionCommand();
		if(comando.equals("SENCILLA")){
			try{
			int t=Integer.parseInt(txtSencilla.getText().trim());
			if(t<1){
				JOptionPane.showMessageDialog (null, "k no debe ser menor a uno. Vuelva a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);

			}else{
			
			
			elRuteo=mundo.grasp(t,a);
			
			}
			}catch(Exception ex){
				JOptionPane.showMessageDialog (null, "Debe ingresar un número. Vuelva a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);

			}
			
		}
		else if(comando.equals("MULTIPLE")){
			int t=Integer.parseInt(txtSet.getText().trim());
			if(t<1){
				JOptionPane.showMessageDialog (null, "k no debe ser menor a uno. Vuelva a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);

			}else{
			int index=list.getSelectedIndex();
			System.out.println("Index: "+list.getSelectedIndex());
			elRuteo=mundo.graspMasSetCovering(t, index+1,a);
			
			}
		}
		else if(comando.equals("MAP")){
			if(elRuteo==null){
				JOptionPane.showMessageDialog (null, "Debe correr un modelo para ver mapas de rutas. Vuelva a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				int t=Integer.parseInt(txtMap.getText().trim());
				if(t<1){
					JOptionPane.showMessageDialog (null, "k no debe ser menor a uno. Vuelva a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);

				}else{
					ArrayList<Coordinate>coordenadas=new ArrayList<Coordinate>();
					Ruta r=elRuteo.rutas.get(t-1);
					for(int j=0;j<r.obras.size();j++){
						
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
					
					MapViewer m=new MapViewer();
					m.mapa(elRuteo.origenDestino, elRuteo.origenDestino, elRuteo.origenDestino, coordenadas);
				}
			}
		}
	}
}
