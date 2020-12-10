import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Client implements ActionListener{
  JFrame fr1,fr2 ;
	JPanel jp1,jp2 ;
	JButton bt1,bt2,bt2quit ;
	JTextField tf11,tf12,tf21 ;
	JLabel jl11,jl12,jl10 ;
	Toolkit toolkit=Toolkit.getDefaultToolkit();
	Dimension dim=toolkit.getScreenSize();
	Socket clientSocket;
	
//Client constructor
	
  Client()
  {
	  clientSocket =null ;
	  
	  //The UI part 
		fr1 =  new JFrame("Client") ;
		bt1 = new JButton() ;
		bt2 = new JButton();
		bt2quit = new JButton();
		jp1 = new JPanel() ;

		
		jp1.setLayout(null);
		jp1.setBounds(0,0,500,400);
		jp1.setBackground(Color.DARK_GRAY);

		jl10 = new JLabel("File Server ini project by Biplaba Samantaray");
		jl10.setBounds(40,280, 500,30);
		jl10.setFont(new Font("Serif", Font.BOLD, 18));
		jl10.setForeground(Color.white) ;
		
		
		jl11 = new JLabel("Enter the IP address");
		jl11.setBounds(115, 20, 300,30);
		jl11.setFont(new Font("Serif", Font.BOLD, 18));
		jl11.setForeground(Color.ORANGE) ;


		tf11=new JTextField();
		tf11.setBounds(120,60,200,30);

		jl12 = new JLabel("Enter the Port");
		jl12.setBounds(115, 110, 300,20);
		jl12.setFont(new Font("Serif", Font.BOLD, 18));
		jl12.setForeground(Color.ORANGE) ;


		tf12=new JTextField();
		tf12.setBounds(120,150,200,30);

		bt1.setBounds(120,210,200,30);
		bt1.setText("Connect To Server");
		
		bt2.setBounds(120,240,100,30) ;
		bt2.setText("Download");
		bt2.setVisible(false);
		
		bt2quit.setBounds(230,240,100,30);
		bt2quit.setText("Exit");
		bt2quit.setVisible(false);

		fr1.setLayout(null);

		fr1.add(jp1) ;
		jp1.add(jl11);
		jp1.add(bt1) ;
		jp1.add(tf11);
		jp1.add(tf12);
		jp1.add(jl12);
		jp1.add(bt2) ;
		jp1.add(bt2quit);
		jp1.add(jl10);


		fr1.setSize(500, 400);
		fr1.setVisible(true);
		
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt2quit.addActionListener(this);
		
		
		

	}
  
//Client Connection with Server
  
  public int connection(String ip,String port)
  {
	  //Establish the connection
	  try {
		  
	   try {
	    clientSocket = new Socket(ip, Integer.parseInt(port));
	    return 1 ;
	   } catch (Exception e) {
		   
		   System.out.println("Invalid IP or port number. Now quitting.");
		   return -1 ;
		   // end communications if not valid
		  
	   }
	  }
	  catch(Exception e)
	  {
		  
	  }
	return -1;
  }
  
  
  //File transfer once connection is established
  
  public int download(String file)
  {
	  try {
		  
	  // input-output streams
	   DataOutputStream outToServer = new DataOutputStream(
	    clientSocket.getOutputStream());
	   DataInputStream inFromServer = new DataInputStream(
	    clientSocket.getInputStream());

	   //Requsting and searching for file at server
	    outToServer.writeBytes(file + '\n');

	    // receive file size
	    int fileSize = inFromServer.readInt();

	    // if there is no file
	    if (fileSize == -1) {
	     System.out.println("File does not exist, now quitting.");
	     
	    }

	    byte[] bytes = new byte[fileSize];
	    
	    DataInputStream inputStream =
	     new DataInputStream(clientSocket.getInputStream());

	    // save file as Sample-file name
	    file = "Sample-" + file;

	    // obtaining file from server
	    try {
	     FileOutputStream foutput = new FileOutputStream(file);
	     BufferedOutputStream boutput = new BufferedOutputStream(foutput);
	     int bytesRead = inputStream.read(bytes, 0, bytes.length);
	     int toSend = bytesRead;

	     // write to file
	     for (int i = fileSize; i >= 0; i--) {
	      bytesRead = inputStream.read(bytes, toSend, (bytes.length - toSend));
	      toSend += bytesRead;
	     }

	     boutput.write(bytes, 0, toSend);
	     boutput.flush();
	     
	     //File GUI
	     Desktop desktop = Desktop.getDesktop();
	     File fileOp = new File("Sample-"+file);
	        if(fileOp.exists()) desktop.open(fileOp);
	        
	        
	     System.out.println("File transfer complete.");
	     boutput.close();
	     return 1;
	     
	    } catch (Exception e) {
	     System.out.println("File download failed");
	     clientSocket.close();
	     
	    }

	  } catch (Exception e) {
	   System.out.println("IO exception. Now exitting.");
	   System.exit(0);
	  }
	  return -1 ;
	 }
  
 
  	public void closeConnection()
  	{
  		try
  		{
  			DataOutputStream outToServer = new DataOutputStream(
  				    clientSocket.getOutputStream());
  			outToServer.writeBytes("quit");
  			clientSocket.close();
  			System.exit(0);
  		}
  		catch(Exception e)
  		{
  			 System.out.println("Cannot close socket.");
  		    System.exit(0);
  		}
  		return ;
  		
  	}
  
  
  
  
	  public void actionPerformed(ActionEvent ae){  
		  try
      	{
	        	if(ae.getSource()== bt1)
	        	{
	        		String s1 = tf11.getText() ;
	        		String s2 = tf12.getText() ;
	        		//System.out.println(s1+" "+s2) ;
	        		int res = connection(s1,s2) ;
	        		if(res == -1)
	        		{
	        			jl11.setText("Invalid IP or Port");
	        			 System.exit(0);
	        		}
	        		else if(res == 1)
	        		{
	        			jl11.setText("Connection Established");
	        			jl12.setText("Enter a file name to download") ;
	        			tf11.setVisible(false);
	        			
	        			bt1.setVisible(false);
	        			bt2.setVisible(true);
	        			bt2quit.setVisible(true);
	        		}
	        		
	        	}
	        	
	        	
	        	if(ae.getSource()== bt2)
	        	{
	        		
	        		String fileName = tf12.getText() ;
	        		//System.out.println(s1+" "+s2) ;
	        		int res = download(fileName ) ;
	        		
	        		if(res == 1) {
	        			jl10.setText("File Downloaded , Enter another file request");
	        		}
	        		
	        	}
	        	
	        	if(ae.getSource() == bt2quit)
	        	{
	        		closeConnection() ;
	        	}
	        	
      	}
		  catch(Exception e)
		  {
			  
		  }
	}  
  	
	 public static void main(String args[]) {
	   Client x = new Client();
	 	}
 }
