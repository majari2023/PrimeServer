import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

//********************************************************************
//
//Developer:    Mauricio Rivas
//
//Program #:    Two
//
//File Name:    ClientHandler.java
//
//Course:       COSC 4302 - Operating Systems
//
//Due Date:     03/14/2025
//
//Instructor:   Prof. Fred Kumi 
//
//Description:  This program implements the client handler for the  
//              PrimeServer. It processes input from a connected  
//              client, validates the data, finds prime numbers in  
//              the specified range, and calculates their sum, mean,  
//              and standard deviation. It allows continuous  
//              interaction until the client sends "Quit".
//
//********************************************************************
public class ClientHandler implements Runnable {
	
    private final Socket clientSocket;
    private final PrimeTest primeTest = new PrimeTest();

    //***************************************************************
    //
    //  Method:       ClientHandler (Constructor)
    //
    //  Description:  Initializes a new client handler with the given  
    //                client socket.
    //
    //  Parameters:   Socket - The client socket connection.
    //
    //  Returns:      N/A
    //
    //***************************************************************
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    //***************************************************************
    //
    //  Method:       run (Override)
    //
    //  Description:  Handles client interaction by receiving input,  
    //                validating data, processing requests, and  
    //                returning results. The method continues running  
    //                until the client sends "Quit".
    //
    //  Parameters:   N/A
    //
    //  Returns:      N/A
    //
    //***************************************************************
    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println("Connected to PrimeServer. Enter two integers (comma/space separated) or 'Quit' to exit.");

            String input;
            while (true) { // Keep the client connected
                input = in.readLine(); // Read client input

                if (input == null || input.equalsIgnoreCase("Quit")) {
                    out.println("Goodbye!");
                    break; // Exit loop when user enters 'Quit'
                }

                // Validate and process input
                String response = processInput(input);
                out.println(response); // Send response to client
                out.println(); // Send an empty line to signal end of response
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        } finally {
            try {
                clientSocket.close(); // Ensure the socket is closed when the client exits
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    //***************************************************************
    //
    //  Method:       processInput (Private)
    //
    //  Description:  Processes user input by extracting integers,  
    //                validating the range, and computing prime  
    //                numbers. It also calculates the sum, mean, and  
    //                standard deviation of the primes and returns  
    //                the formatted result.
    //
    //  Parameters:   String - The input string received from the client.
    //
    //  Returns:      String - The formatted response to be sent back  
    //                         to the client.
    //
    //***************************************************************
    private String processInput(String input) {
        try {
            // Extract integers from input
            List<Integer> numbers = Arrays.stream(input.split("[,\\s]+"))
                                          .map(Integer::parseInt)
                                          .collect(Collectors.toList());

            if (numbers.size() != 2) return "Error: Please enter exactly two integers.";

            int start = numbers.get(0);
            int end = numbers.get(1);

            // Validate the integers
            if (start <= 0 || end <= 0) return "Error: Both numbers must be greater than zero.";
            if (start >= end) return "Error: First number must be less than the second.";

            // Find prime numbers
            List<Integer> primes = new ArrayList<>();
            for (int i = start; i <= end; i++) {
                if (primeTest.isPrime(i)) primes.add(i);
            }

            if (primes.isEmpty()) return "No prime numbers found in the given range.";

            // Calculate statistics
            int sum = primes.stream().mapToInt(Integer::intValue).sum();
            double mean = sum / (double) primes.size();
            double variance = primes.stream().mapToDouble(n -> Math.pow(n - mean, 2)).sum() / primes.size();
            double stdDev = Math.sqrt(variance);

            // Return full formatted response
            return String.format(
                "Primes: %s\nSum: %d\nMean: %.2f\nStandard Deviation: %.2f",
                primes, sum, mean, stdDev
            );

        } catch (NumberFormatException e) {
            return "Error: Please enter valid integers.";
        }
    }
}