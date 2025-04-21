import java.io.*;
import java.net.*;
import java.util.concurrent.*;

//********************************************************************
//
//Developer:    Mauricio Rivas
//
//Program #:    Two
//
//File Name:    PrimeServer.java
//
//Course:       COSC 4302 - Operating Systems
//
//Due Date:     03/14/2025
//
//Instructor:   Prof. Fred Kumi 
//
//Description:  This program implements a multi-threaded server that  
//              listens on port 4301. It accepts multiple client 
//              connections and assigns each client to a separate  
//              thread for processing. The server remains active  
//              even if clients disconnect.
//
//********************************************************************

public class PrimeServer {
	//***************************************************************
    //
    //  Method:       main
    //
    //  Description:  The main method of the program.
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A
    //
    //***************************************************************
    private static final int PORT = 4301;

    public static void main(String[] args) {
        PrimeServer server = new PrimeServer();
        server.startServer();
    }
    //***************************************************************
    //
    //  Method:       startServer (Non-Static)
    //
    //  Description:  This method initializes the server, sets up a  
    //                thread pool based on the number of available  
    //                CPU cores, and continuously listens for incoming  
    //                client connections. Each client is assigned a  
    //                separate thread using an ExecutorService.
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //***************************************************************
    private void startServer() {
        int maxThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}