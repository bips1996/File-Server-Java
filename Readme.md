
# TCP File server
This mini projects enables the file sharing between the Client and Server where Client could request and download any file available at server directory.

## 1. Technology  stack

I have followed the TCP client/server reliable communication implemented through the [JAVA](https://www.java.com/en/) socket programming.

## 2. Dependencies
 java(jdk7 or more)

## 3. How to run
First compile and run the server by following command in command prompt or terminal opened in server directory
```bash
javac Server.java
java Server.java
```
It will definitely ask for a port to run the server enter a port number(eg- 3000,8000)

Once the server is running and waiting for the client , open the client directory and open a command prompt/ Terminal there and run following command :-
```bash
javac Client.java
java Client.java
```
A GUI will be opened enter the ip as **localhost** and port as **server port number**.

Request with a file name to download

## 4. Explanation of Classes and Methods
## 4.1 Server
The server  class follows the follwing classes and key methods
**class SERVER : **
```java
class Server
{
    public static void main(String[] args)
    {
        //Take a port from the user
        //Create two sockets
        ServerSocket listenSocket = null;
        Socket clientSocket = null;
        //try to connect to the port
        //if port not free :: Throw Exception
        //if port is free :: wait for the client
        while(true)
        {
            clientSocket = listenSocket.accept();
            //Create a clientHandler thread and start
        }
    }
}
```
**class clientHandler : thread implementation for file transfer and communication with the client**
```java
class ClientHandler implementts Runnable
{
    public void run()
    {
        while(true)
        {
            //Receive the client request filename
            //if req == 'quit' it disconnect from the client
            //else
            //Collect the file name and search whether it is in the server directory or not
            //if present :: Serialize it send the length of the file followled by the byte stream of the file
            //wait for the next file request
        }
    }
}
```
## 4.2 Client
The client follows the follwing class and key methods
```java
class Client implements ActionListner
{
    Client()
    {
        //The constructor initializes the ui Elements for the client class
        //Initilizes the client socket with null
    }
    public int connection(IP,port)
    {
        //This method takes the IP and port number as the argument
        //check availibility of the server
        //Connect to the server
    }
    public int download(fileName)
    {
        //Take file name as the argument
        //Request the file from the server
        //Collect the file length and the bytestream of the file
        //Store the file into server directory with name 'Sample-filename'
    }
    public void closeConnection()
    {
        //This method close the clientSocket and exit the program   
    }
    public void actionPerformed(ActionEvent ae)
    {
        //integrate the UI with other function
        if(bt1 clicked)
        {
            //call the connection method with argument from tf11(ip address field) and tf12(port number field)
        }
        if(bt2 clicked)
        {
            //Collect the requested file name from tf12 and caall the method download(filename)
        }
        if(bt2quit clicked)
        {
            //The closeConnection() method is closed
        }
    }
}
```
## 5. Issues to be solved
- Specific Exception Hadling to be done
- Some UI updation at client end
- UI at the serverside to be created
## 6. Scope of the project
- To be developed for a multicasting environment with UDP implementation
- Target will be to achive the distributed fileserver
- Rather than directory ,file to be accessed from the database at each peer
## 7. References
- The key references is the [TCP program of adding numbers that discussed in the class](https://github.com/bips1996/TCP_client_server_java) and the concepts that are taught in the Distributed Computing class.
- Some information collected from web for better understanding
## 8. Conclusion
TCP provides reliability ,so creating a file server with TCP implementation is relatively easy task than implenting the file server in a distributed environment. This project includes the **java socket programming with client server architecture** . The further works to be implemented soon by enabling this project to work as **a peer to peer file sharing application**.
