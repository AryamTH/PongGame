import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

//Hold game panel
//is the window frame
public class GameFrame extends JFrame{ //So i can treat it like JFrame Class

	GamePanel panel;
	ImageIcon image;
	
	GameFrame(){
		
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("Pong Game");
		this.setResizable(false);
		this.setBackground(new Color(39, 14, 69));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
                }
	
}
