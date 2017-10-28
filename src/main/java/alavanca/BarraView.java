package alavanca;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

public class BarraView {
	private final Barra barra;
	
	private double offset = 10;
	
	private Color estruturaColor;
	private Color esquemaColor;
	private Color invalidColor;
	
	private Line2D esquema;
	private RoundRectangle2D estrutura;
	
	private boolean esquemaVisible = true;
	private boolean estruturaVisible = true;
	
	public BarraView(Barra barra) {
		this.barra = barra;
		esquema = new Line2D.Double();
		estrutura = new RoundRectangle2D.Double();
		updateEsquema();
		updateEstrutura();
	}

	public void paint(Graphics2D g2) {
		boolean valid = barra.isValid();
		
		if (estruturaVisible) {
			updateEstrutura();
			
			AffineTransform at = g2.getTransform();
			AffineTransform tr = AffineTransform.getRotateInstance(
					esquema.getX2() - esquema.getX1(), esquema.getY2() - esquema.getY1());
			g2.translate(esquema.getX1(), esquema.getY1());
			g2.transform(tr);

			if (valid) {
				g2.setColor(getEstruturaColor());
				g2.draw(estrutura);
			} else {
				g2.setColor(getInvalidColor());
				g2.fill(estrutura);	
			}
			
			g2.setTransform(at);
		}

		if (esquemaVisible) {
			updateEsquema();

			if (valid) {
				g2.setColor(getEsquemaColor());
			} else {
				g2.setColor(getInvalidColor());
			}
			g2.draw(esquema);
		}
	}
	
	private void updateEsquema() {
		esquema.setLine(barra.getPontoMestre(), barra.getPontoEscravo());		
	}
	
	private void updateEstrutura() {
		estrutura = new RoundRectangle2D.Double(-offset, -offset,
				barra.getTamanho() + 2*offset, 2*offset, offset, offset);
	}
	
	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
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

	public Color getEsquemaColor() {
		if (esquemaColor == null) {
			esquemaColor = Color.BLUE;
		}
		return esquemaColor;
	}

	public void setEsquemaColor(Color esquemaColor) {
		this.esquemaColor = esquemaColor;
	}

	public Color getInvalidColor() {
		if (invalidColor == null) {
			invalidColor = Color.RED;
		}
		return invalidColor;
	}

	public void setInvalidColor(Color invalidColor) {
		this.invalidColor = invalidColor;
	}

	public boolean isEsquemaVisible() {
		return esquemaVisible;
	}

	public void setEsquemaVisible(boolean esquemaVisible) {
		this.esquemaVisible = esquemaVisible;
	}

	public boolean isEstruturaVisible() {
		return estruturaVisible;
	}

	public void setEstruturaVisible(boolean estruturaVisible) {
		this.estruturaVisible = estruturaVisible;
	}
}
