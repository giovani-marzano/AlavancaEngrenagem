package alavanca.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

import alavanca.view.BarraView;

public class BarraViewConfPanel extends ElementViewConfPanel<BarraView> {

	private JButton btnInvalidColor;
	
	public BarraViewConfPanel(BarraView view) {
		super(view);
	}

	@Override
	protected void initComponents() {
		super.initComponents();
		
		btnInvalidColor = new JButton("");
		add(new JLabel("Cor Inválido:"));
		add(btnInvalidColor);
		btnInvalidColor.setToolTipText("Cor que indica configuração inválida.");

		btnInvalidColor.addActionListener((ev) -> {
			Color c = JColorChooser.showDialog(
					this, "Cor Inválida", elementView.getInvalidColor());
			if (c != null) {
				elementView.setInvalidColor(c);
			}
		});
	}

	@Override
	protected void updateComponents() {
		super.updateComponents();
		btnInvalidColor.setBackground(elementView.getInvalidColor());
	}
}
