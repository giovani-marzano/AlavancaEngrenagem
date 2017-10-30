package alavanca.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import alavanca.Engrenagem;

public class EngrenagemView extends ElementView {
	private Ellipse2D esquema = new Ellipse2D.Double();

	private Ellipse2D estrutura = new Ellipse2D.Double();
	private Arc2D estrDetalhe = new Arc2D.Double(Arc2D.PIE);
	
	/**
	 * Se o alcance da barra está visível;
	 */
	private boolean alcanceVisible = true;
	private Ellipse2D alcanceMaior = new Ellipse2D.Double();
	private Ellipse2D alcanceMenor = new Ellipse2D.Double();
	
	/**
	 * Cor do alcance da barra.
	 */
	private Color alcanceColor;

	/**
	 * Engrenagem visualizada
	 */
	private Engrenagem engrenagem;
	
	public EngrenagemView(Engrenagem engrenagem) {
		this.engrenagem = engrenagem;
	}

	public void paintEstrutura(Graphics2D g) {
		if (!isEstruturaVisible()) return;
		
		Point2D c = engrenagem.getCentro();
		double w = engrenagem.getRaioBarra() + offset;
		estrutura.setFrameFromCenter(c.getX(), c.getY(),
				c.getX()+w, c.getY()+w);
		double alfaDeg = Math.toDegrees(engrenagem.getAlfa());
		estrDetalhe.setArc(estrutura.getBounds2D(), alfaDeg - 10 + 180, 20.0, Arc2D.PIE);
		
		g.setColor(getEstruturaColor());
		g.draw(estrutura);
		g.fill(estrDetalhe);
		
		setDirty(false);
	}
	
	public void paintEsquema(Graphics2D g) {
		if (!isEsquemaVisible()) return;
		
		Point2D c = engrenagem.getCentro();
		double w = engrenagem.getRaioBarra();
		esquema.setFrameFromCenter(c.getX(), c.getY(),
				c.getX()+w, c.getY()+w);
		
		g.setColor(getEsquemaColor());
		g.draw(esquema);
		
		setDirty(false);
	}
	
	public void paintAlcance(Graphics2D g)  {
		if (!alcanceVisible) return;
		if (engrenagem.getBarra() == null) return;
		
		Point2D c = engrenagem.getCentro();
		double maior = engrenagem.getAlcanceMaior();
		double menor = engrenagem.getAlcanceMenor();
		
		alcanceMaior.setFrameFromCenter(c.getX(), c.getY(),
				c.getX() + maior, c.getY() + maior);
		alcanceMenor.setFrameFromCenter(c.getX(), c.getY(),
				c.getX() + menor, c.getY() + menor);
		
		Area area = new Area(alcanceMaior);
		area.subtract(new Area(alcanceMenor));
		
		g.setColor(getAlcanceColor());
		g.fill(area);
		
		setDirty(false);
	}
	
	public void paint(Graphics2D g) {
		paintAlcance(g);
		paintEstrutura(g);
		paintEsquema(g);		
	}
	
	public boolean contains(Point2D point) {
		if (isEstruturaVisible()) {
			return estrutura.contains(point);
		} else {
			return esquema.contains(point);
		}
	}
	
	public boolean contains(double x, double y) {
		if (isEstruturaVisible()) {
			return estrutura.contains(x,y);
		} else {
			return esquema.contains(x,y);
		}
	}

	public boolean isAlcanceVisible() {
		return alcanceVisible;
	}

	public void setAlcanceVisible(boolean alcanceVisivel) {
		boolean old = this.alcanceVisible;
		if (old != alcanceVisivel) {
			this.alcanceVisible = alcanceVisivel;
			pcs.firePropertyChange("alcanceVisible", old, alcanceVisivel);
			setDirty(true);
		}
	}

	public Color getAlcanceColor() {
		if (alcanceColor == null) {
			alcanceColor = new Color(0x8800ff00, true);
		}
		return alcanceColor;
	}

	public void setAlcanceColor(Color alcanceColor) {
		Color old = this.alcanceColor;
		if (!old.equals(alcanceColor)) {
			this.alcanceColor = alcanceColor;
			pcs.firePropertyChange("alcanceColor", old, alcanceColor);
			setDirty(true);
		}
	}

	public Engrenagem getEngrenagem() {
		return engrenagem;
	}

	public void setEngrenagem(Engrenagem engrenagem) {
		this.engrenagem = engrenagem;
	}
}