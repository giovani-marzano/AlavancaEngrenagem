package alavanca;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Engrenagem {
	/**
	 * Centro de rotação da engrenagem
	 */
	private Point2D centro;
	/**
	 * Distância entre o centro da engrenagem e o ponto de fixação da barra.
	 */
	private double raioBarra = 30;
	/**
	 * Posição angular do ponto de conexção da barra.
	 */
	private double alfa = 0;
	private Point2D pontoBarra;
	
	/**
	 * Limite mínimo em radianos da amplitude do movimento angular da engrenagem.
	 */
	private double alfaMin = 0;
	/**
	 * Limite máximo em radianos da amplitude do movimento angular da engrenagem.
	 */
	private double alfaMax = 2*Math.PI;
	
	/**
	 * Barra conectada à engrenagem.
	 */
	private Barra barra;
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public Engrenagem() {
		centro = new Point2D.Double(100,100);
		pontoBarra = new Point2D.Double();
		updatePontoBarra();
	}

	/**
	 * Ponto em que a barra está conectada.
	 * @return
	 */
	public Point2D getPontoBarra() {
		return pontoBarra;
	}
	
	private void updatePontoBarra() {
		double x, y;
		x = Math.cos(alfa) * raioBarra + centro.getX();
		y = -Math.sin(alfa) * raioBarra + centro.getY();
		pontoBarra.setLocation(x, y);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	public Point2D getCentro() {
		return centro;
	}

	public void setCentro(Point2D newValue) {
		if (!newValue.equals(centro)) {
			Point2D oldValue = centro;
			this.centro = newValue;
			updatePontoBarra();
			pcs.firePropertyChange("centro", oldValue, centro);			
		}
	}
	
	public void setCentro(double x, double y) {
		setCentro(new Point2D.Double(x,y));
	}

	public double getRaioBarra() {
		return raioBarra;
	}

	public void setRaioBarra(double newValue) {
		if (raioBarra != newValue) {
			double oldValue = raioBarra;
			raioBarra = newValue;
			updatePontoBarra();
			pcs.firePropertyChange("raioBarra", oldValue, newValue);
		}
	}

	public double getAlfa() {
		return alfa;
	}
	
	public void setAlfa(double newValue) {
		while (newValue < -2*Math.PI) {
			newValue += 2*Math.PI;
		}
		while (newValue > 2*Math.PI) {
			newValue -= 2*Math.PI;
		}
		if (newValue != alfa) {
			double oldValue = alfa;
			alfa = newValue;
			updatePontoBarra();
			pcs.firePropertyChange("alfa", oldValue, newValue);
		}
	}

	public double getAlfaMin() {
		return alfaMin;
	}

	public void setAlfaMin(double alfaMin) {
		if (this.alfaMin != alfaMin) {
			double old = this.alfaMin;
			double oldRange = getAlfaRange();
			this.alfaMin = alfaMin;
			pcs.firePropertyChange("alfaMin", old, alfaMin);
			pcs.firePropertyChange("alfaRange", oldRange, getAlfaRange());
		}
	}

	public double getAlfaMax() {
		return alfaMax;
	}

	public void setAlfaMax(double alfaMax) {
		if (this.alfaMax != alfaMax) {
			double old = this.alfaMax;
			double oldRange = getAlfaRange();
			this.alfaMax = alfaMax;
			pcs.firePropertyChange("alfaMax", old, alfaMax);
			pcs.firePropertyChange("alfaRange", oldRange, getAlfaRange());
		}
	}
	
	/**
	 * Amplitude em radianos do movimento angular da engrenagem.
	 * 
	 * @return Amplitude total em radianos.
	 */
	public double getAlfaRange() {
		return Math.abs(alfaMax - alfaMin);
	}

	public double getAlcanceMaior() {
		if (barra == null) return 0;
		return barra.getTamanho() + raioBarra;
	}
	
	public double getAlcanceMenor() {
		if (barra == null) return 0;
		return barra.getTamanho() - raioBarra;
	}
	
	public Barra getBarra() {
		return barra;
	}

	public void setBarra(Barra barra) {
		this.barra = barra;
	}
}
