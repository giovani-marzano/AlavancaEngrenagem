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
			
			// X e Y de um ponto de interseção em um sistema de coordenadas em que
			// a origem é o centro da engrenagem e o eixo x é a linha que liga o
			// centro da engrenagem maior e o ponto de conexão na engrenagem menor
			double px = (rEng*rEng - rBar*rBar + dist*dist)/(2*dist);
			double py = Math.sqrt(rEng*rEng - px*px);
			
			// Convertendo px e py para as coordenadas reais
			//
			// Coordenadas do vetor unitario que liga cenEng e cenBar
			double vx = (cenBar.getX() - cenEng.getX())/dist;
			double vy = (cenBar.getY() - cenEng.getY())/dist;
			
			// Para encontrar o ponto real, devemos andar:
			// - px na direção (vx,vy)
			// - py na direção (-vy, vx), que é perpendicular a (vx,vy)
			double x = px * vx + py * (-vy);
			double y = px * vy + py * vx;

			double alfa = Math.atan2(y, x);

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
				double alfa = Math.atan2(y, x);

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
