import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.Desktop;
/************************************************************************
 * Client class allows the user to connect with the server at the
 * given ip and port number. The user sends a request to the server for
 * a file to be sent back to the client. It uses TCP protocol. The user
 * may receive multiple files on the same connection.
 *

 ***********************************************************************/
class Client {
  JFrame fr1,fr2 ;
	JPanel jp1,jp2 ;
	JButton bt1,bt2 ;
	JTextField tf11,tf12,tf21 ;
	JLabel jl11,jl12,jl21,jl22 ;
	Toolkit toolkit=Toolkit.getDefaultToolkit();
	Dimension dim=toolkit.getScreenSize();
  Client()
  {
		fr1 =  new JFrame("Client") ;
		bt1 = new JButton() ;
		jp1 = new JPanel() ;



		jp1.setLayout(null);
		jp1.setBounds(0,0,500,300);
		jp1.setBackground(Color.DARK_GRAY);

		jl11 = new JLabel("Enter th Ip address");
		jl11.setBounds(115, 20, 300,100);
		jl11.setFont(new Font("Serif", Font.BOLD, 18));
		jl11.setForeground(Color.ORANGE) ;


		tf11=new JTextField();
		tf11.setBounds(120,40,200,20);

    jl12 = new JLabel("Enter th Port");
		jl12.setBounds(115, 80, 300,100);
		jl12.setFont(new Font("Serif", Font.BOLD, 18));
		jl12.setForeground(Color.ORANGE) ;


		tf11=new JTextField();
		tf11.setBounds(120,120,200,20);

		bt1.setBounds(120,200,200,30);
		bt1.setText("Download");

		fr1.setLayout(null);

		fr1.add(jp1) ;
		jp1.add(jl11);
		jp1.add(bt1) ;
		jp1.add(tf12);
    jp1.add(jl12);


		fr1.setSize(500, 300);
		fr1.setVisible(true);
	}











 public static void main(String args[]) {
   Client x = new Client();
  String file = "";
  BufferedReader inFromUser = new BufferedReader(
   new InputStreamReader(System.in));
  try {
   // asking for ip and port of server
   System.out.println("Enter an IP address: ");
   String ip = inFromUser.readLine();
   System.out.println("Enter a port number: ");
   String port = inFromUser.readLine();
   Socket clientSocket = null;

   // ensure ip and port are usable.
   try {
    clientSocket = new Socket(ip, Integer.parseInt(port));
   } catch (Exception e) {
    System.out.println("Invalid IP or port number. Now quitting.");

    // end communications if not valid
    System.exit(0);
   }

   // input-output streams
   DataOutputStream outToServer = new DataOutputStream(
    clientSocket.getOutputStream());
   DataInputStream inFromServer = new DataInputStream(
    clientSocket.getInputStream());

   // retrieve file name from user
   System.out.println("Enter the file name you wish to send or " +
    "type 'quit' to leave: ");
   file = inFromUser.readLine();

   // continue looping until user quits
   myloop: while (!file.equalsIgnoreCase("Quit")) {

    outToServer.writeBytes(file + '\n');

    // receive file size
    int fileSize = inFromServer.readInt();

    // if there is no file
    if (fileSize == -1) {
     System.out.println("File does not exist, now quitting.");
     break myloop;
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
     System.out.println("Enter the file name you wish to send or " +
      "type 'quit' to leave: ");
     file = inFromUser.readLine();
    } catch (Exception e) {
     System.out.println("File could not transferred.");
     clientSocket.close();
    }
   }

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
}
