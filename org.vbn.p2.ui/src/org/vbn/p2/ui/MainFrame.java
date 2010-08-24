package org.vbn.p2.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import org.vbn.p2.four.LabelFour;
import org.vbn.p2.one.LabelOne;
import org.vbn.p2.three.LabelThree;
import org.vbn.p2.two.LabelTwo;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements WindowListener
{

	public MainFrame()
	{
		super("P2 Auto Update and Automatic Build Test");
		
		try
		{
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
	    	System.out.println("Error loading System Look and Feel: " + e.getMessage());
	    	try {
	    		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    	} catch(Exception ex) {
	    		System.out.println("Error loading CrossPlatform Look and Feel: " + e.getMessage());
	    	}
	    }
		
		LabelOne one = new LabelOne();
		LabelTwo two = new LabelTwo();
		LabelThree three = new LabelThree();
		LabelFour four = new LabelFour();
		
		
		JPanel mainPanel = new JPanel();
		GroupLayout layout = new GroupLayout(mainPanel);
		mainPanel.setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		
		layout.setHorizontalGroup(layout.createParallelGroup()
			.addComponent(one)
			.addComponent(two)
			.addComponent(three)
			.addComponent(four)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(one)
			.addComponent(two)
			.addComponent(three)
			.addComponent(four)
		);
		
		this.addWindowListener(this);
		
		this.add(mainPanel);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowClosing(WindowEvent e)
	{
		this.setVisible(false);
		Application.exitApplication();
	}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
}