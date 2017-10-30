package alavanca;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Barra {
	public static final double ERR = 0.0001;
	
	/**
	 * Tamanho da barra.
	 */
	private double tamanho = 100;
	/**
	 * Engrenagem escrava em que a barra está ligada
	 */
	private Engrenagem escravo;
	/**
	 * Engrenagem mestre em que a barra está ligada
	 */
	private Engrenagem mestre;
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Barra(Engrenagem escravo, Engrenagem mestre) {
		this.escravo = escravo;
		escravo.setBarra(this);
		this.mestre = mestre;
		mestre.setBarra(this);
		
		PropertyChangeListener listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateEscravoAlfa();
			}
		};
		
		escravo.addPropertyChangeListener(listener);
		mestre.addPropertyChangeListener(listener);
	}
	
	/**
	 * Ajusta a posição angular da engrenagem escravo de acordo com o estado
	 * do sistema.
	 */
	private void updateEscravoAlfa() {
		double dist = mestre.getPontoBarra().distance(escravo.getCentro());

		if (escravo.getAlcanceMenor() <= dist && dist <= escravo.getAlcanceMaior()) {
			// Existe posição de barra na engrenagem maior que alcança a posição da
			// barra na engrenagem menor.
			Point2D cenEng = escravo.getCentro();
			Point2D cenBar = mestre.getPontoBarra();
			
			final double rEng = escravo.getRaioBarra();
			final double rBar = getTamanho();
			
			double alfa = Utils.circlesIntersectionAlfa(cenEng, cenBar, rEng, rBar, dist);

			escravo.setAlfa(alfa);
		} else {
			// Não é possível conectar a barra nas duas engrenagens.
			// Apenas rodamos a engrenagem maior de forma que os pontos de conexão
			// fiquem o mais próximo possível.
			if (dist > 0.0) {
				Point2D p1 = escravo.getCentro();
				Point2D p2 = mestre.getPontoBarra();
				double x = (p2.getX() - p1.getX());
				double y = (p2.getY() - p1.getY());
				double alfa = Math.atan2(-y, x);

				escravo.setAlfa(alfa);
			}
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public Engrenagem getEscravo() {
		return escravo;
	}

	public Engrenagem getMestre() {
		return mestre;
	}

	public double getTamanho() {
		return tamanho;
	}

	public void setTamanho(double newValue) {
		double oldValue = tamanho;
		tamanho = newValue;
		pcs.firePropertyChange("tamanho", oldValue, newValue);
	}
	
	public Point2D getPontoEscravo() {
		return escravo.getPontoBarra();
	}
	
	public Point2D getPontoMestre() {
		return mestre.getPontoBarra();
	}
	
	public boolean isValid() {		
		double dist = getPontoMestre().distance(getPontoEscravo());

		return Math.abs(dist - tamanho) < ERR;
	}
}
