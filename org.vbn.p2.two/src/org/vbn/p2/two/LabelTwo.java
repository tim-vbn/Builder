package org.vbn.p2.two;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

@SuppressWarnings("serial")
public class LabelTwo extends JPanel
{
	public LabelTwo()
	{
		JLabel label = new JLabel("Two: version 1.2.3.b");
		label.setFont(label.getFont().deriveFont(Font.BOLD,24));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		this.add(label);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}