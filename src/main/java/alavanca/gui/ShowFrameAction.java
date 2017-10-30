package alavanca.gui;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

public class ShowFrameAction extends AbstractAction implements WindowListener {
	private JFrame frame;
	private String frameName;
	
	public ShowFrameAction(JFrame frame, String frameName) {
		super();
		this.frame = frame;
		this.frameName = frameName;
		updName();
		
		frame.addWindowListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.setVisible(!frame.isVisible());
		updName();
	}
	
	public void updName() {
		if (frame.isVisible()) {
			putValue(NAME, "Hide "+frameName);
		} else {
			putValue(NAME, "Show "+frameName);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		updName();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		frame.setVisible(false);
		updName();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}