package alavanca.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import alavanca.Barra;
import alavanca.Engrenagem;

public class InfoFrame extends JFrame {
	
	public InfoFrame(Engrenagem mestre, Engrenagem escravo, Barra barra) {
		initComponents(mestre, escravo, barra);
	}
	
	private void initComponents(Engrenagem mestre, Engrenagem escravo, Barra barra) {
		Box main = new Box(BoxLayout.Y_AXIS);

		EngrenagemInfoPanel pnMestre = new EngrenagemInfoPanel(mestre);
		pnMestre.setBorder(BorderFactory.createTitledBorder("Engr. Mestre"));
		main.add(pnMestre);

		EngrenagemInfoPanel pnEscravo = new EngrenagemInfoPanel(escravo);
		pnEscravo.setBorder(BorderFactory.createTitledBorder("Engr. Escrava"));
		main.add(pnEscravo);

		BarraInfoPanel pnBarra = new BarraInfoPanel(barra);
		pnBarra.setBorder(BorderFactory.createTitledBorder("Barra"));
		main.add(pnBarra);
		
		JScrollPane scroll = new JScrollPane(main);
		
		add(scroll);
		
		pack();
	}
}
