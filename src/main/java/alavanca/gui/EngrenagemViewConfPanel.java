package alavanca.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

import alavanca.view.EngrenagemView;

public class EngrenagemViewConfPanel extends ElementViewConfPanel<EngrenagemView> {

	private JCheckBox cbAlcanceVisible;
	private JButton btnAlcanceColor;
	
	public EngrenagemViewConfPanel(EngrenagemView view) {
		super(view);
	}

	@Override
	protected void initComponents() {
		super.initComponents();
		
		cbAlcanceVisible = new JCheckBox("");
		add(new JLabel("Alcance Visível:"));
		add(cbAlcanceVisible);
		cbAlcanceVisible.addItemListener((ev) -> {
			elementView.setAlcanceVisible(cbAlcanceVisible.isSelected());
		});
		cbAlcanceVisible.setToolTipText("Indica se a região de alcance será desenhada");
		
		btnAlcanceColor = new JButton("");
		add(new JLabel("Cor do Alcance:"));
		add(btnAlcanceColor);
		btnAlcanceColor.setToolTipText("Cor da região de alcance");
		
		btnAlcanceColor.addActionListener((ev) -> {
			Color c = JColorChooser.showDialog(
					this, "Cor da Região de Alcance", elementView.getAlcanceColor());
			if (c != null) {
				elementView.setAlcanceColor(c);
			}
		});

	}

	@Override
	protected void updateComponents() {
		super.updateComponents();
		
		cbAlcanceVisible.setSelected(elementView.isAlcanceVisible());
		btnAlcanceColor.setBackground(elementView.getAlcanceColor());
	}
}
