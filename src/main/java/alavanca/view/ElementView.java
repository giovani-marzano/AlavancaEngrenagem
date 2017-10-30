package alavanca.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ElementView {

	protected double offset = 10;

	public abstract void paint(Graphics2D g2);

	private Color estruturaColor;
	private Color esquemaColor;
	protected boolean esquemaVisible = true;
	protected boolean estruturaVisible = true;

	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private boolean dirty = true;

	public ElementView() {
		super();
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		if (this.offset != offset) {
			double old = this.offset;
			this.offset = offset;
			pcs.firePropertyChange("offset", old, offset);
			setDirty(true);
		}
	}

	public Color getEstruturaColor() {
		if (estruturaColor == null) {
			estruturaColor = Color.BLACK;
		}
		return estruturaColor;
	}

	public void setEstruturaColor(Color estruturaColor) {
		if (!this.estruturaColor.equals(estruturaColor)) {
			Color old = this.estruturaColor;
			this.estruturaColor = estruturaColor;
			pcs.firePropertyChange("estruturaColor", old, estruturaColor);
			setDirty(estruturaVisible);
		}
	}

	public Color getEsquemaColor() {
		if (esquemaColor == null) {
			esquemaColor = Color.BLUE;
		}
		return esquemaColor;
	}

	public void setEsquemaColor(Color esquemaColor) {
		if (!this.esquemaColor.equals(esquemaColor)) {
			Color old = this.esquemaColor;
			this.esquemaColor = esquemaColor;
			pcs.firePropertyChange("esquemaColor", old, esquemaColor);
			setDirty(esquemaVisible);
		}
	}

	public boolean isEsquemaVisible() {
		return esquemaVisible;
	}

	public void setEsquemaVisible(boolean esquemaVisible) {
		if (this.esquemaVisible != esquemaVisible) {
			boolean old = this.esquemaVisible;
			this.esquemaVisible = esquemaVisible;
			pcs.firePropertyChange("esquemaVisible", old, esquemaVisible);
			setDirty(true);
		}
	}

	public boolean isEstruturaVisible() {
		return estruturaVisible;
	}

	public void setEstruturaVisible(boolean estruturaVisible) {
		boolean old = this.estruturaVisible;
		if (old != estruturaVisible) {
			this.estruturaVisible = estruturaVisible;
			pcs.firePropertyChange("estruturaVisible", old, estruturaVisible);
			setDirty(true);
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		boolean old = this.dirty;
		this.dirty = dirty;
		pcs.firePropertyChange("dirty", old, dirty);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}
}