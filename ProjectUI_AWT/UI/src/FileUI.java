import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;
import java.lang.*;

class UI  
{
	JFrame fr ;
	JPanel Jp ;
	JButton bt ;
	JTextField tf ;
	JLabel jl ;
	Toolkit toolkit=Toolkit.getDefaultToolkit();
	Dimension dim=toolkit.getScreenSize();
	UI()
	{
		fr =  new JFrame("Client") ;
		bt = new JButton() ;
		Jp = new JPanel() ;
		
	
		
		Jp.setLayout(null);
		Jp.setBounds(0,0,500,300);
		Jp.setBackground(Color.DARK_GRAY);
		
		jl = new JLabel("Eneter the file to download");
		jl.setBounds(115, 20, 300,100);
		jl.setFont(new Font("Serif", Font.BOLD, 18));
		jl.setForeground(Color.ORANGE) ;
		
		
		tf=new JTextField();
		tf.setBounds(120,100,200,20);
		
		bt.setBounds(120,200,200,30);
		bt.setText("Download");
		
		fr.setLayout(null);
		
		fr.add(Jp) ;
		Jp.add(jl); 
		Jp.add(bt) ;
		Jp.add(tf);
		Jp.add(new JLabel("Enter the Text File"));
		
		
		fr.setSize(500, 300);
		fr.setVisible(true);

	}
	

}


public class FileUI
{
	public static void main(String[] args)
	{
		UI x = new UI() ;
		System.out.println("Sdn") ;
	}
}