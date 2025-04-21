# Prime Number Client-Server Application

A multithreaded client-server Java application that allows users to input a range of integers and receive a list of prime numbers within that range, along with their sum, mean, and standard deviation. The server handles multiple clients concurrently using threads.

## 📁 Project Structure

- `PrimeServer.java` - Starts the server and handles incoming client connections using a thread pool.
- `ClientHandler.java` - Manages communication with an individual client, including input validation and prime number computations.
- `PrimeClient.java` - Connects to the server and provides a CLI interface for users to input values.
- `PrimeTest.java` - Contains the logic to determine whether a given number is prime.

## 🚀 How It Works

1. **Server** (`PrimeServer.java`) listens on port `4301`.
2. Clients connect using the **Client** (`PrimeClient.java`) and send two integers (comma or space-separated).
3. The server calculates:
   - List of prime numbers in the range
   - Sum
   - Mean
   - Standard Deviation
4. The client displays the result.
5. Type `Quit` to end the session.

## 🛠️ Technologies Used

- Java 17+
- Sockets (java.net)
- Multithreading (`ExecutorService`)
- Input/output handling

## 🧪 How to Run

### Compile all files:

```bash
javac *.java
