package alavanca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import alavanca.gui.InfoFrame;
import alavanca.gui.ShowFrameAction;
import alavanca.gui.ViewConfFrame;
import alavanca.view.BarraView;
import alavanca.view.ElementView;
import alavanca.view.EngrenagemView;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App extends JPanel {
	
	static final int WIDTH = 600;
	static final int HEIGHT = 480;
	
	private EngrenagemView followerGear;
	private EngrenagemView leaderGear;
	private BarraView barra;
	
	private MouseHandler mouseHandler;
	private PlayAction playAction;
	private StopAction stopAction;
	
	private Timer playTimer;
	private final int frameMs = 1000/30;
	private double radPerSec = Math.PI;
	
	private InfoFrame infoFrame;
	private ShowFrameAction showInfoAction;
	
	private ViewConfFrame confFrame;
	private ShowFrameAction showConfAction;
	
	public App() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		setBackground(Color.WHITE);
		
		Engrenagem follower = new Engrenagem();
		Engrenagem leader = new Engrenagem();
		
		leader.setCentro(300, 242);
		leader.setAlfa(0);
		leader.setRaioBarra(20);
				
		Barra barraModel = new Barra(follower, leader);
		
		follower.setCentro(300, 148);
		follower.setRaioBarra(30);
		
		barra = new BarraView(barraModel);
		
		followerGear = new EngrenagemView(follower);
		leaderGear = new EngrenagemView(leader);		
	
		playTimer = new Timer(frameMs, (ev) -> {
			double alfa = leader.getAlfa();
			alfa += (radPerSec * frameMs)/1000;
			leader.setAlfa(alfa);
		});
				
		playAction = new PlayAction();
		stopAction = new StopAction();
		
		infoFrame = new InfoFrame(leader, follower, barraModel);
		showInfoAction = new ShowFrameAction(infoFrame, "Info");
		
		confFrame = new ViewConfFrame(leaderGear, followerGear, barra);
		showConfAction = new ShowFrameAction(confFrame, "Conf");
		
		mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);

		PropertyChangeListener dirtyListener = (ev) -> {
			ElementView view = (ElementView) ev.getSource();
			if (view.isDirty()) {
				repaint();
			}
		};
		
		barra.addPropertyChangeListener("dirty", dirtyListener);
		leaderGear.addPropertyChangeListener("dirty", dirtyListener);
		followerGear.addPropertyChangeListener("dirty", dirtyListener);
	}
	
    @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		followerGear.paintAlcance(g2);
		leaderGear.paintAlcance(g2);
		followerGear.paintEstrutura(g2);
		leaderGear.paintEstrutura(g2);
		followerGear.paintEsquema(g2);
		leaderGear.paintEsquema(g2);

		barra.paint(g2);
	}

    public double getRadPerSec() {
		return radPerSec;
	}

	public void setRadPerSec(double radPerSec) {
		this.radPerSec = radPerSec;
	}

	public PlayAction getPlayAction() {
		return playAction;
	}

	public StopAction getStopAction() {
		return stopAction;
	}

	public int getFrameMs() {
		return frameMs;
	}

	public ShowFrameAction getShowInfoAction() {
		return showInfoAction;
	}
	
	public ShowFrameAction getShowConfAction () {
		return showConfAction;
	}

	private class MouseHandler extends MouseAdapter {
    	private EngrenagemView selected; 
    	
    	@Override
		public void mouseDragged(MouseEvent e) {
    		if (selected != null) {
    			selected.getEngrenagem().setCentro(e.getPoint());
    		}
    	}

		@Override
		public void mousePressed(MouseEvent e) {
			if (leaderGear.contains(e.getPoint())) {
				selected = leaderGear;
			} else if (followerGear.contains(e.getPoint())) {
				selected = followerGear;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			selected = null;
		}
    }
    
    private class PlayAction extends AbstractAction {
    	public PlayAction() {
    		super("Play");
    	}
    	
    	@Override
		public void actionPerformed(ActionEvent e) {
			setEnabled(false);
			App.this.stopAction.setEnabled(true);
			App.this.playTimer.start();
    	}
    }
    
    private class StopAction extends AbstractAction {
    	public StopAction() {
    		super("Stop");
    		setEnabled(false);
    	}
    	
    	@Override
		public void actionPerformed(ActionEvent e) {
    		App.this.playTimer.stop();
			App.this.playAction.setEnabled(true);
			setEnabled(false);
		}
    }
    
    public static void main(String[] args) {
    	SwingUtilities.invokeLater( () -> {
    		JFrame frame = new JFrame("Alavanca e Engrenagens");
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		
    		JPanel panel = new JPanel(new BorderLayout());
    		
    		App app = new App();
    		
    		JToolBar toolBar = new JToolBar("Tool Bar");
    		JButton btnPlay = new JButton(app.getPlayAction());
    		toolBar.add(btnPlay);
    		JButton btnStop = new JButton(app.getStopAction());
    		toolBar.add(btnStop);
    		
    		JButton btnInfo = new JButton(app.getShowInfoAction());
    		toolBar.add(btnInfo);
    		
    		JButton btnConf = new JButton(app.getShowConfAction());
    		toolBar.add(btnConf);

    		panel.add(app, BorderLayout.CENTER);
    		panel.add(toolBar, BorderLayout.NORTH);
    		
    		frame.add(panel);
    		
    		frame.pack();
    		frame.setVisible(true);
    	});
    }
}
