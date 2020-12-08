import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/************************************************************************
 * Client class allows the user to connect with the server at the
 * given ip and port number. The user sends a request to the server for
 * a file to be sent back to the client. It uses TCP protocol. The user
 * may receive multiple files on the same connection.
 *

 ***********************************************************************/
class Client implements ActionListener{
  JFrame fr1,fr2 ;
	JPanel jp1,jp2 ;
	JButton bt1,bt2 ;
	JTextField tf11,tf12,tf21 ;
	JLabel jl11,jl12,jl21,jl22 ;
	Toolkit toolkit=Toolkit.getDefaultToolkit();
	Dimension dim=toolkit.getScreenSize();
	Socket clientSocket;
  Client()
  {
	  clientSocket =null ;
	  
	  
	  	//Connection ui
		fr1 =  new JFrame("Client") ;
		bt1 = new JButton() ;
		bt2 = new JButton();
		jp1 = new JPanel() ;

		
		jp1.setLayout(null);
		jp1.setBounds(0,0,500,400);
		jp1.setBackground(Color.DARK_GRAY);


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
		
		bt2.setBounds(120,240,200,30) ;
		bt2.setText("Download");
		bt2.setVisible(false);

		fr1.setLayout(null);

		fr1.add(jp1) ;
		jp1.add(jl11);
		jp1.add(bt1) ;
		jp1.add(tf11);
		jp1.add(tf12);
		jp1.add(jl12);
		jp1.add(bt2) ;


		fr1.setSize(500, 400);
		fr1.setVisible(true);
		
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		
		//ftp ui
		
		

	}
  
  	
  public int connection(String ip,String port)
  {
	  String file = "";
	  BufferedReader inFromUser = new BufferedReader(
	   new InputStreamReader(System.in));
	  try {
	   // asking for ip and port of server

	   

	   // ensure ip and port are usable.
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
  public void transfer(String file)
  {
	  try {
		  
	  // input-output streams
	   DataOutputStream outToServer = new DataOutputStream(
	    clientSocket.getOutputStream());
	   DataInputStream inFromServer = new DataInputStream(
	    clientSocket.getInputStream());

	   // retrieve file name from user
	   System.out.println("Enter the file name you wish to send or " +
	    "type 'quit' to leave: ");
	   //file = inFromUser.readLine();

	   // continue looping until user quits
	   //myloop: while (!file.equalsIgnoreCase("Quit")) {

	    outToServer.writeBytes(file + '\n');

	    // receive file size
	    int fileSize = inFromServer.readInt();

	    // if there is no file
	    if (fileSize == -1) {
	     System.out.println("File does not exist, now quitting.");
	     //break myloop;
	    }

	    // array with large enough of space to use
	    byte[] bytes = new byte[fileSize];
	    DataInputStream inputStream =
	     new DataInputStream(clientSocket.getInputStream());

	    // put "My-" to differentiate from original file when saved
	    file = "My-" + file;

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
	     File fileOp = new File("My-"+file);
	        if(fileOp.exists()) desktop.open(fileOp);

	     System.out.println("File transfer complete.");

	     // clean up and end
	     boutput.close();
	     //System.out.println("Enter the file name you wish to send or " +
	      //"type 'quit' to leave: ");
	     //file = inFromUser.readLine();
	    } catch (Exception e) {
	     System.out.println("File could not transferred.");
	     clientSocket.close();
	    }
	  // }

	   try {

	    // inform server that client is quitting before leaving
	    outToServer.writeBytes("quit");
	    clientSocket.close();
	   } catch (Exception e) {
	    System.out.println("Cannot close socket.");
	    System.exit(0);
	   }

	  } catch (Exception e) {
	   System.out.println("IO exception. Now exitting.");
	   System.exit(0);
	  }
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
	        			bt2.setVisible(true);
	        			bt1.setVisible(false);
	        		}
	        		
	        	}
	        	
	        	if(ae.getSource()== bt2)
	        	{
	        		
	        		String fileName = tf12.getText() ;
	        		//System.out.println(s1+" "+s2) ;
	        		transfer(fileName ) ;
	        		
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
