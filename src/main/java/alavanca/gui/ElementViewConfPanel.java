package alavanca.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import alavanca.view.ElementView;

public class ElementViewConfPanel<T extends ElementView> extends JPanel {

	protected T elementView;
	
	private PropertyChangeListener propChangHandler;
	
	private JCheckBox cbEstruturaVisible = new JCheckBox("");
	private JCheckBox cbEsquemaVisible = new JCheckBox("");
	private JSpinner spOffset = new JSpinner(new SpinnerNumberModel(10.0, 1.0, 100.0, 1.0));
	private JButton btnEstruturaColor = new JButton("");
	private JButton btnEsquemaColor = new JButton("");
	
	public ElementViewConfPanel(T view) {
		super(new GridLayout(0, 2));
		this.elementView = view;

		initComponents();
		updateComponents();
		
		propChangHandler = (evt) -> {
			updateComponents();
		};

		view.addPropertyChangeListener(propChangHandler);
	}

	protected void initComponents() {
		add(new JLabel("Estr. Visível:"));
		add(cbEstruturaVisible);
		cbEstruturaVisible.addItemListener((ev) -> {
			elementView.setEstruturaVisible(cbEstruturaVisible.isSelected());
		});
		cbEstruturaVisible.setToolTipText("Indica se a estrutura do elemento será desenhada");
		
		add(new JLabel("Estr. Offset:"));
		add(spOffset);
		spOffset.addChangeListener((ev) -> {
			elementView.setOffset((Double)spOffset.getValue());
		});
		spOffset.setToolTipText("Distancia usada para desenhar a estrutura entorn do esquema.");
		
		add(new JLabel("Estr. Color:"));
		add(btnEstruturaColor);
		btnEstruturaColor.setToolTipText("Cor da estrutura do elemento");
		
		btnEstruturaColor.addActionListener((ev) -> {
			Color c = JColorChooser.showDialog(
					this, "Cor da Estrutura", elementView.getEstruturaColor());
			if (c != null) {
				elementView.setEstruturaColor(c);
			}
		});

		add(new JLabel("Esqu. Visível:"));
		add(cbEsquemaVisible);
		cbEsquemaVisible.addItemListener((ev) -> {
			elementView.setEsquemaVisible(cbEsquemaVisible.isSelected());
		});
		cbEsquemaVisible.setToolTipText("Indica se o esquema do elemento será desenhada");
				
		add(new JLabel("Esqu. Color:"));
		add(btnEsquemaColor);
		btnEsquemaColor.setToolTipText("Cor do esquema do elemento");
		
		btnEsquemaColor.addActionListener((ev) -> {
			Color c = JColorChooser.showDialog(
					this, "Cor do Esquema", elementView.getEstruturaColor());
			if (c != null) {
				elementView.setEsquemaColor(c);
			}
		});
	}
	
	protected void updateComponents() {
		cbEstruturaVisible.setSelected(elementView.isEstruturaVisible());		
		cbEsquemaVisible.setSelected(elementView.isEsquemaVisible());
		spOffset.setValue(elementView.getOffset());
		btnEstruturaColor.setBackground(elementView.getEstruturaColor());
		btnEsquemaColor.setBackground(elementView.getEsquemaColor());
	}
}
