package alavanca.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import alavanca.Barra;
import alavanca.Engrenagem;

public class InfoFrame extends JFrame {
	
	public InfoFrame(Engrenagem leader, Engrenagem follower, Barra barra) {
		initComponents(leader, follower, barra);
	}
	
	private void initComponents(Engrenagem leader, Engrenagem follower, Barra barra) {
		Box main = new Box(BoxLayout.Y_AXIS);

		EngrenagemInfoPanel pnLeader = new EngrenagemInfoPanel(leader);
		pnLeader.setBorder(BorderFactory.createTitledBorder("Engr. Líder"));
		main.add(pnLeader);

		EngrenagemInfoPanel pnFollower = new EngrenagemInfoPanel(follower);
		pnFollower.setBorder(BorderFactory.createTitledBorder("Engr. Seguidora"));
		main.add(pnFollower);

		BarraInfoPanel pnBarra = new BarraInfoPanel(barra);
		pnBarra.setBorder(BorderFactory.createTitledBorder("Barra"));
		main.add(pnBarra);
		
		JScrollPane scroll = new JScrollPane(main);
		
		add(scroll);
		
		pack();
	}
}
