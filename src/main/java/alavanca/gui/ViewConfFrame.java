package alavanca.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import alavanca.view.BarraView;
import alavanca.view.EngrenagemView;

public class ViewConfFrame extends JFrame {
	
	private EngrenagemView mestre;
	private EngrenagemView escravo;
	private BarraView barra;
	
	public ViewConfFrame(EngrenagemView mestre, EngrenagemView escravo, BarraView barra) {
		super();
		this.mestre = mestre;
		this.escravo = escravo;
		this.barra = barra;
		initComponents();
	}

	public void initComponents() {
		Box main = new Box(BoxLayout.Y_AXIS);
		
		EngrenagemViewConfPanel pnMestre = new EngrenagemViewConfPanel(mestre);
		pnMestre.setBorder(BorderFactory.createTitledBorder("Engr. Mestre"));
		main.add(pnMestre);
		
		EngrenagemViewConfPanel pnEscravo = new EngrenagemViewConfPanel(escravo);
		pnEscravo.setBorder(BorderFactory.createTitledBorder("Engr. Escravo"));
		main.add(pnEscravo);

		BarraViewConfPanel pnBarra = new BarraViewConfPanel(barra);
		pnBarra.setBorder(BorderFactory.createTitledBorder("Barra"));
		main.add(pnBarra);

		JScrollPane scroll = new JScrollPane(main);		
		add(scroll);
		pack();
	}
}
