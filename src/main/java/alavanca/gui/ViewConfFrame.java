package alavanca.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import alavanca.view.BarraView;
import alavanca.view.EngrenagemView;

public class ViewConfFrame extends JFrame {
	
	private EngrenagemView leader;
	private EngrenagemView follower;
	private BarraView barra;
	
	public ViewConfFrame(EngrenagemView leader, EngrenagemView follower, BarraView barra) {
		super();
		this.leader = leader;
		this.follower = follower;
		this.barra = barra;
		initComponents();
	}

	public void initComponents() {
		Box main = new Box(BoxLayout.Y_AXIS);
		
		EngrenagemViewConfPanel pnLeader = new EngrenagemViewConfPanel(leader);
		pnLeader.setBorder(BorderFactory.createTitledBorder("Engr. Líder"));
		main.add(pnLeader);
		
		EngrenagemViewConfPanel pnFollower = new EngrenagemViewConfPanel(follower);
		pnFollower.setBorder(BorderFactory.createTitledBorder("Engr. Seguidora"));
		main.add(pnFollower);

		BarraViewConfPanel pnBarra = new BarraViewConfPanel(barra);
		pnBarra.setBorder(BorderFactory.createTitledBorder("Barra"));
		main.add(pnBarra);

		JScrollPane scroll = new JScrollPane(main);		
		add(scroll);
		pack();
	}
}
