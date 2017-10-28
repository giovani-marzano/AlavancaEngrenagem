package alavanca;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class EngrenagemView {
	/**
	 * Distância entre o ponto de fixação da barra e a borda da engrenagem.
	 */
	private double raioOffset = 10;
	/**
	 * Se a parte esquemática da engrenagem está visível.
	 */
	private boolean esquemaVisivel = true;
	private Ellipse2D esquema = new Ellipse2D.Double();

	/**
	 * Se a parte estrutural da engrenagem está visível.
	 */
	private boolean estruturaVisivel = true;
	private Ellipse2D estrutura = new Ellipse2D.Double();
	
	/**
	 * Se o alcance da barra está visível;
	 */
	private boolean alcanceVisivel = true;
	private Ellipse2D alcanceMaior = new Ellipse2D.Double();
	private Ellipse2D alcanceMenor = new Ellipse2D.Double();
	
	/**
	 * Cor do esquema.
	 */
	private Color esquemaColor;
	/**
	 * Cor da estrutura.
	 */
	private Color estruturaColor;
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
		if (!estruturaVisivel) return;
		
		Point2D c = engrenagem.getCentro();
		double w = engrenagem.getRaioBarra() + raioOffset;
		estrutura.setFrameFromCenter(c.getX(), c.getY(),
				c.getX()+w, c.getY()+w);
		
		g.setColor(getEstruturaColor());
		g.draw(estrutura);
	}
	
	public void paintEsquema(Graphics2D g) {
		if (!esquemaVisivel) return;
		
		Point2D c = engrenagem.getCentro();
		double w = engrenagem.getRaioBarra();
		esquema.setFrameFromCenter(c.getX(), c.getY(),
				c.getX()+w, c.getY()+w);
		
		g.setColor(getEsquemaColor());
		g.draw(esquema);
	}
	
	public void paintAlcance(Graphics2D g)  {
		if (!alcanceVisivel) return;
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
	}
	
	public void paint(Graphics2D g) {
		paintAlcance(g);
		paintEstrutura(g);
		paintEsquema(g);
	}
	
	public boolean contains(Point2D point) {
		if (estruturaVisivel) {
			return estrutura.contains(point);
		} else {
			return esquema.contains(point);
		}
	}
	
	public boolean contains(double x, double y) {
		if (estruturaVisivel) {
			return estrutura.contains(x,y);
		} else {
			return esquema.contains(x,y);
		}
	}
	
	public boolean isEsquemaVisivel() {
		return esquemaVisivel;
	}

	public void setEsquemaVisivel(boolean esquemaVisivel) {
		this.esquemaVisivel = esquemaVisivel;
	}

	public boolean isEstruturaVisivel() {
		return estruturaVisivel;
	}

	public void setEstruturaVisivel(boolean estruturaVisivel) {
		this.estruturaVisivel = estruturaVisivel;
	}

	public boolean isAlcanceVisivel() {
		return alcanceVisivel;
	}

	public void setAlcanceVisivel(boolean alcanceVisivel) {
		this.alcanceVisivel = alcanceVisivel;
	}

	public Color getEsquemaColor() {
		if (esquemaColor == null) {
			esquemaColor = Color.BLUE;
		}
		return esquemaColor;
	}

	public void setEsquemaColor(Color esquemaColor) {
		this.esquemaColor = esquemaColor;
	}

	public Color getEstruturaColor() {
		if (estruturaColor == null) {
			estruturaColor = Color.BLACK;
		}
		return estruturaColor;
	}

	public void setEstruturaColor(Color estruturaColor) {
		this.estruturaColor = estruturaColor;
	}

	public Color getAlcanceColor() {
		if (alcanceColor == null) {
			alcanceColor = new Color(0x8800ff00, true);
		}
		return alcanceColor;
	}

	public void setAlcanceColor(Color alcanceColor) {
		this.alcanceColor = alcanceColor;
	}

	public Engrenagem getEngrenagem() {
		return engrenagem;
	}

	public void setEngrenagem(Engrenagem engrenagem) {
		this.engrenagem = engrenagem;
	}

	public double getRaioOffset() {
		return raioOffset;
	}

	public void setRaioOffset(double raioOffset) {
		this.raioOffset = raioOffset;
	}
}