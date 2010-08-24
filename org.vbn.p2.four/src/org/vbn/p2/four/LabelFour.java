package org.vbn.p2.four;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

@SuppressWarnings("serial")
public class LabelFour extends JPanel
{
	public LabelFour()
	{
		JLabel label = new JLabel("Four: This is label Four!");
		label.setFont(label.getFont().deriveFont(Font.BOLD,24));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		this.add(label);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}