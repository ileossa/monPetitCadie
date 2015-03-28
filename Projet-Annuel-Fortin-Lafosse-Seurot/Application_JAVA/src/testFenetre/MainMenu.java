package testFenetre;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JPanel {  

  public static void main(String s[]) {

	JFrame menu = new JFrame("Acceuil");
	
	menu.setLayout(new GridLayout(4, 1, 50, 10));
	
	JButton jb1 = new JButton("Manage produit");
	JButton jb2 = new JButton("Manage promotions");
	JButton jb3 = new JButton("Utilisateurs");
	JButton jb4 = new JButton("Quitter");
	//JButton jb3 = new JButton("Statistiques");

	
	
	menu.add(jb1);
	menu.add(jb2);
	menu.add(jb3);
	menu.add(jb4);
	//menu.add(jb5);
	
	
	jb1.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {
	    ManageProduct.manageProduct();
	  }
	});
	
	jb2.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {
	    ManagePromotion.managePromotion();
	  }
	});
	
	/*jb5.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {
	    System.out.println("OK");
	  }
	});*/
	
	jb3.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {
	    ManageUser.manageUser();
	  }
	});
	
	jb4.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {
	    System.exit(0);
	  }
	});
	
	
	menu.pack();
	menu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	menu.setSize(600,400);
	
	
	menu.setVisible(true);
  }
  
  
  class MyWindowListener implements WindowListener {

	  public void windowClosing(WindowEvent arg0) {
	    System.exit(0);
	  }

	  public void windowOpened(WindowEvent arg0) {
	  }

	  public void windowClosed(WindowEvent arg0) {
	  }

	  public void windowIconified(WindowEvent arg0) {
	  }

	  public void windowDeiconified(WindowEvent arg0) {
	  }

	  public void windowActivated(WindowEvent arg0) {
	  }

	  public void windowDeactivated(WindowEvent arg0) {
	  }

	}
  
}