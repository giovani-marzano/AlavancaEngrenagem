package alavanca.gui;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import alavanca.Barra;

public class BarraInfoPanel extends JPanel {
	private Barra barra;
	
	private JLabel lbTamanho = new JLabel();

	private PropertyChangeHandler handler = new PropertyChangeHandler();
	
	public BarraInfoPanel(Barra barra) {
		super(new GridLayout(0, 2));
		this.barra = barra;
		initComponents();		
	}
	
	private void initComponents() {
		add(new JLabel("Tamanho:"));
		add(lbTamanho);
	}
	
	private void updTamanho(Double tamanho) {
		lbTamanho.setText(String.format("%1$.2f", tamanho));
	}

	@Override
	public void addNotify() {
		super.addNotify();
		updTamanho(barra.getTamanho());
		barra.addPropertyChangeListener(handler);
	}

	@Override
	public void removeNotify() {
		super.removeNotify();
		barra.removePropertyChangeListener(handler);
	}

	private class PropertyChangeHandler implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			switch (evt.getPropertyName()) {
			case "tamanho":
				Double tamanho = (Double) evt.getNewValue();
				updTamanho(tamanho);
				break;
			}
		}
	}
}
