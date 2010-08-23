package org.vbn.p2.three;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

@SuppressWarnings("serial")
public class LabelThree extends JPanel
{
	public LabelThree()
	{
		JLabel label = new JLabel("Three: version 1.0");
		label.setFont(label.getFont().deriveFont(Font.BOLD,24));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		this.add(label);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}