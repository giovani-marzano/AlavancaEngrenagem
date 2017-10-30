package alavanca.gui;

import java.awt.GridLayout;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import alavanca.Engrenagem;

public class EngrenagemInfoPanel extends JPanel {
	private Engrenagem engrenagem;

	private JSpinner spPosX;
	private JSpinner spPosY;;
	private JSpinner spRaio;
	private JLabel lbAlfaRange;

	private PropertyChangeHandler handler = new PropertyChangeHandler();

	public EngrenagemInfoPanel(Engrenagem engrenagem) {
		super(new GridLayout(0, 2));
		this.engrenagem = engrenagem;

		initComponents();
	}

	private void initComponents() {
		add(new JLabel("Pos X: "));
		spPosX = new JSpinner(new SpinnerNumberModel(0.0, -1000.0, 1000.0, 1.0));
		add(spPosX);

		spPosX.addChangeListener((ev) -> {
			Double val = (Double) spPosX.getValue();
			engrenagem.setCentro(val, engrenagem.getCentro().getY());
		});

		add(new JLabel("Pos Y: "));
		spPosY = new JSpinner(new SpinnerNumberModel(0.0, -1000.0, 1000.0, 1.0));
		add(spPosY);

		spPosY.addChangeListener((ev) -> {
			Double val = (Double) spPosY.getValue();
			engrenagem.setCentro(engrenagem.getCentro().getX(), val);
		});

		add(new JLabel("Raio: "));
		spRaio = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 5));
		add(spRaio);

		spRaio.addChangeListener((ev) -> {
			Double val = (Double) spRaio.getValue();
			engrenagem.setRaioBarra(val);
		});

		lbAlfaRange = new JLabel();
		lbAlfaRange.setToolTipText("Amplitude do movimento angular.");
		add(new JLabel("Amplitude: "));
		add(lbAlfaRange);
	}

	private void updCentro(Point2D centro) {
		spPosX.setValue(centro.getX());
		spPosY.setValue(centro.getY());
	}

	private void updAlfaRange(Double alfa) {
		lbAlfaRange.setText(String.format("%1$.1f deg", Math.toDegrees(alfa)));
	}

	private void updRaio(Double raio) {
		spRaio.setValue(raio);
	}

	@Override
	public void addNotify() {
		super.addNotify();

		updAlfaRange(engrenagem.getAlfaRange());
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
			case "alfaRange":
				Double alfaRange = (Double) evt.getNewValue();
				updAlfaRange(alfaRange);
				break;
			case "raioBarra":
				Double raio = (Double) evt.getNewValue();
				updRaio(raio);
			}
		}
	}
}
