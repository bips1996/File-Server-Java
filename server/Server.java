import java.io.*;
import java.net.*;
class Server {
 public static void main(String args[]) throws Exception {

  // ask user for the port number.
  BufferedReader inFromUser = new BufferedReader (new InputStreamReader(System.in));
  System.out.println("Enter a port number");
  String port = inFromUser.readLine();

  ServerSocket listenSocket = null;
  Socket clientSocket = null;

  // try to connect on port
  try {
   listenSocket = new ServerSocket(Integer.parseInt(port));
  }
  // if port cannot be used
  catch (Exception e) {
   System.out.println("Invalid port number. Shutting down.");
   System.exit(0);
  }

  System.out.println("Waiting for a client...");

  // accept client when found and begin ClientHandler tasks
  while (true) {
   clientSocket = listenSocket.accept();
   Runnable r = new ClientHandler(clientSocket);
   Thread t = new Thread(r);
   t.start();
   System.out.println("Found a client. Wait for client to request file.");
  }
 }
}


 //ClientHandler Class made for : file transfer and communication with the client
class ClientHandler implements Runnable {
 Socket clientSocket;

  // constructor
   ClientHandler(Socket connection) {
    clientSocket = connection;
   }

   public void run() {
     //infinite server  loop
    while (true) {
     try {

      BufferedReader inFromUser = null;
      DataOutputStream outToClient = null;
      BufferedReader inFromClient = null;
      String sendFile = "";

      try {
       inFromUser = new BufferedReader(new InputStreamReader(System.in));
       outToClient = new DataOutputStream(clientSocket.getOutputStream());
       inFromClient = new BufferedReader
  		   (new InputStreamReader(clientSocket.getInputStream()));

       System.out.println("Waiting for client to request file.");
       sendFile = inFromClient.readLine();
       if (sendFile.equalsIgnoreCase("quit"))
        break ;
       System.out.println("File to send: " + sendFile);

      } catch (IOException e) {
       System.out.println("Error in obtaining file name.");
       break ;
      }

      try {
       File fileToSend = new File(sendFile);

       if (!fileToSend.exists()) {
        System.out.println("File does not exist. Now quitting.");

        // send -1 file size to indicate invalid file to client
        outToClient.writeInt(-1);
        break ;
       } else {

        // send file size to client
        outToClient.writeInt((int) fileToSend.length());
        outToClient.flush();

        byte[] bytes = new byte[(int) fileToSend.length()];
        FileInputStream finput = new FileInputStream(fileToSend);
        BufferedInputStream binput = new BufferedInputStream(finput);

        binput.read(bytes, 0, bytes.length);

        outToClient.write(bytes, 0, bytes.length);
        System.out.println("Success: sent file to client.");

        binput.close();
        finput.close();
        outToClient.flush();
       }


      } catch (IOException e) {
       System.out.println("Sending file failed.");
       break ;
      }
     } catch (Exception e) {
      System.out.println("Unknown ");
      System.exit(0);
     }
    }

    // disconnect
       try {
           clientSocket.close();
           System.out.println("Disconnected from a client.");
        }
      catch (Exception e)
      {
           System.out.println("Cannot close socket. Exitting.");
           System.exit(0);
      }

    }
}
