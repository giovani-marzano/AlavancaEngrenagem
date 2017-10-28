package alavanca.gui;

import java.awt.GridLayout;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import alavanca.Engrenagem;

public class EngrenagemInfoPanel extends JPanel {
	private Engrenagem engrenagem;
	
	private JLabel lbPosX = new JLabel();
	private JLabel lbPosY = new JLabel();
	private JLabel lbRaio = new JLabel();
	private JLabel lbAlfa = new JLabel();

	private PropertyChangeHandler handler = new PropertyChangeHandler();
	
	public EngrenagemInfoPanel(Engrenagem engrenagem) {
		super(new GridLayout(0, 2));
		this.engrenagem = engrenagem;
		
		initComponents();		
	}
	
	private void initComponents() {
		add(new JLabel("Pos X:"));
		add(lbPosX);
		add(new JLabel("Pos Y:"));
		add(lbPosY);
		add(new JLabel("Raio:"));
		add(lbRaio);
		add(new JLabel("Alfa:"));
		add(lbAlfa);
	}
	
	private void updCentro(Point2D centro) {
		lbPosX.setText(String.format("%1$.2f", centro.getX()));
		lbPosY.setText(String.format("%1$.2f", centro.getY()));
	}

	private void updAlfa(Double alfa) {
		lbAlfa.setText(String.format("%1$.2f rad", alfa));
	}

	private void updRaio(Double raio) {
		lbRaio.setText(String.format("%1$.2f", raio));
	}

	@Override
	public void addNotify() {
		super.addNotify();

		updAlfa(engrenagem.getAlfa());
		updRaio(engrenagem.getRaioBarra());
		updCentro(engrenagem.getCentro());
		engrenagem.addPropertyChangeListener(handler);
	}

	@Override
	public void removeNotify() {
		super.removeNotify();
		engrenagem.removePropertyChangeListener(handler);
	}

	private class PropertyChangeHandler implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			switch (evt.getPropertyName()) {
			case "centro":
				Point2D centro = (Point2D) evt.getNewValue();
				updCentro(centro);
				break;
			case "alfa":
				Double alfa = (Double) evt.getNewValue();
				updAlfa(alfa);
				break;
			case "raioBarra":
				Double raio = (Double) evt.getNewValue();
				updRaio(raio);
			}
		}
	}
}
