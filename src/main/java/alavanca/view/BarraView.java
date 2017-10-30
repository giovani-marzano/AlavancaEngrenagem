package alavanca.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import alavanca.Barra;

public class BarraView extends ElementView {
	private final Barra barra;
	
	private Color invalidColor;
	
	private Line2D esquema;
	private RoundRectangle2D estrutura;
	
	protected PropertyChangeListener propChangeHandler = new PropertyChangeHandler();
	
	public BarraView(Barra barra) {
		this.barra = barra;
		esquema = new Line2D.Double();
		estrutura = new RoundRectangle2D.Double();
		updateEsquema();
		updateEstrutura();
		
		barra.addPropertyChangeListener(propChangeHandler);
		barra.getMestre().addPropertyChangeListener(propChangeHandler);
		barra.getEscravo().addPropertyChangeListener(propChangeHandler);
	}

	@Override
	public void paint(Graphics2D g2) {
		boolean valid = barra.isValid();
		
		if (estruturaVisible) {			
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
			if (valid) {
				g2.setColor(getEsquemaColor());
			} else {
				g2.setColor(getInvalidColor());
			}
			g2.draw(esquema);
		}
		
		setDirty(false);
	}
	
	private void updateEsquema() {
		esquema.setLine(barra.getPontoMestre(), barra.getPontoEscravo());		
		setDirty(true);
	}
	
	private void updateEstrutura() {
		estrutura = new RoundRectangle2D.Double(-offset, -offset,
				barra.getTamanho() + 2*offset, 2*offset, offset, offset);
		setDirty(true);
	}
	
	public Color getInvalidColor() {
		if (invalidColor == null) {
			invalidColor = Color.RED;
		}
		return invalidColor;
	}

	public void setInvalidColor(Color invalidColor) {
		if (!this.invalidColor.equals(invalidColor)) {
			Color old = this.invalidColor;
			this.invalidColor = invalidColor;
			pcs.firePropertyChange("invalidColor", old, invalidColor);
			setDirty(true);
		}
	}

	@Override
	public void setOffset(double offset) {
		super.setOffset(offset);
		if (isDirty()) {
			updateEstrutura();
		}
	}

	class PropertyChangeHandler implements PropertyChangeListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			updateEsquema();
			updateEstrutura();
		}
	}
}
