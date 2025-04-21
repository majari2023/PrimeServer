import java.io.*;
import java.net.*;
import java.util.Scanner;
//********************************************************************
//
//Developer:    Mauricio Rivas
//
//Program #:    Two
//
//File Name:    PrimeClient.java
//
//Course:       COSC 4302 - Operating Systems
//
//Due Date:     03/14/2025
//
//Instructor:   Prof. Fred Kumi 
//
//Description:  This program implements a client that connects to  
//              PrimeServer on port 4301. The client sends two  
//              integers (entered as a string with commas/spaces) to  
//              the server, which processes them and returns the list  
//              of prime numbers, their sum, mean, and standard  
//              deviation. The client allows multiple queries until  
//              the user enters "Quit" to terminate.
//
//********************************************************************
public class PrimeClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 4301;
    
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

    public static void main(String[] args) {
        PrimeClient client = new PrimeClient();
        client.startClient();
    }

    //***************************************************************
    //
    //  Method:       startClient (Non-Static)
    //
    //  Description:  This method establishes a connection to the server,  
    //                sends user input for processing, and receives  
    //                results from the server. The client continues to  
    //                accept input until the user enters "Quit".
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //***************************************************************
    private void startClient() {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println(in.readLine()); // Initial server message

            while (true) { // Keep asking for input until the user quits
                System.out.print("Enter two numbers (comma/space separated) or 'Quit' to exit: ");
                String input = scanner.nextLine();
                out.println(input);

                if (input.equalsIgnoreCase("Quit")) {
                    System.out.println("Closing connection. Goodbye!");
                    developerInfo();
                    break; // Exit loop when user enters 'Quit'
                }

                // Read and display the full server response
                String serverResponse;
                while ((serverResponse = in.readLine()) != null && !serverResponse.isEmpty()) {
                    System.out.println(serverResponse);
                }
            }
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    //***************************************************************
    //
    //  Method:       developerInfo (Non-Static)
    //
    //  Description:  This method displays the developer's information,  
    //                including name, course, program number, and due  
    //                date.
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //***************************************************************
    public void developerInfo() {
        System.out.println("Name:     Mauricio Rivas");
        System.out.println("Course:   COSC 4302 - Operating Systems");
        System.out.println("Program:  Two");
        System.out.println("Due Date: 03/14/2025\n");
    }
}