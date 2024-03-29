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
	 * Engrenagem seguidora em que a barra está ligada
	 */
	private Engrenagem follower;
	/**
	 * Engrenagem líder em que a barra está ligada
	 */
	private Engrenagem leader;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Barra(Engrenagem follower, Engrenagem leader) {
		this.follower = follower;
		follower.setBarra(this);
		this.leader = leader;
		leader.setBarra(this);

		PropertyChangeListener listener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateFollowerAlfa();
			}
		};

		follower.addPropertyChangeListener(listener);
		leader.addPropertyChangeListener(listener);
	}

	/**
	 * Ajusta a posição angular da engrenagem escravo de acordo com o estado do
	 * sistema.
	 */
	private void updateFollowerAlfa() {
		double dist = leader.getPontoBarra().distance(follower.getCentro());

		if (follower.getAlcanceMenor() <= dist && dist <= follower.getAlcanceMaior()) {
			// Existe posição de barra na engrenagem maior que alcança a posição da
			// barra na engrenagem menor.
			Point2D cenLeader = leader.getCentro();
			Point2D cenEng = follower.getCentro();
			Point2D cenBar = leader.getPontoBarra();

			final double rEng = follower.getRaioBarra();
			final double rBar = getTamanho();

			double alfa = Utils.circlesIntersectionAlfa(cenEng, rEng, cenBar, rBar, dist);

			double distGears = cenEng.distance(cenLeader);
			double alfaMin = Utils.circlesIntersectionAlfa(cenEng, rEng, cenLeader, leader.getAlcanceMenor(),
					distGears);
			double alfaMax = Utils.circlesIntersectionAlfa(cenEng, rEng, cenLeader, leader.getAlcanceMaior(),
					distGears);

			follower.setAlfa(alfa);
			follower.setAlfaMin(alfaMin);
			follower.setAlfaMax(alfaMax);
		} else {
			// Não é possível conectar a barra nas duas engrenagens.
			// Apenas rodamos a engrenagem maior de forma que os pontos de conexão
			// fiquem o mais próximo possível.
			if (dist > 0.0) {
				Point2D p1 = follower.getCentro();
				Point2D p2 = leader.getPontoBarra();
				double x = (p2.getX() - p1.getX());
				double y = (p2.getY() - p1.getY());
				double alfa = Math.atan2(-y, x);

				follower.setAlfa(alfa);
				follower.setAlfaMin(alfa);
				follower.setAlfaMax(alfa);
			}
		}
	}

	public void enforceFollowerAngularRange(double range) {
		final Point2D cenFol = follower.getCentro();
		final double dist = cenFol.distance(leader.getCentro());

		double alfaMax = (Math.PI + range) / 2;
		double alfaMin = (Math.PI - range) / 2;

		double rMin = Utils.circlesIntersectionRadius(cenFol, follower.getRaioBarra(), alfaMin, dist);
		double rMax = Utils.circlesIntersectionRadius(cenFol, follower.getRaioBarra(), alfaMax, dist);

		leader.setRaioBarra((rMax - rMin) / 2);
		setTamanho((rMax + rMin) / 2);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public Engrenagem getFollower() {
		return follower;
	}

	public Engrenagem getLeader() {
		return leader;
	}

	public double getTamanho() {
		return tamanho;
	}

	public void setTamanho(double newValue) {
		if (tamanho != newValue) {
			double oldValue = tamanho;
			tamanho = newValue;
			pcs.firePropertyChange("tamanho", oldValue, newValue);
			updateFollowerAlfa();
		}
	}

	public Point2D getPontoFollower() {
		return follower.getPontoBarra();
	}

	public Point2D getPontoLeader() {
		return leader.getPontoBarra();
	}

	public boolean isValid() {
		double dist = getPontoLeader().distance(getPontoFollower());

		return Math.abs(dist - tamanho) < ERR;
	}
}
