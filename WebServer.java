/*
Name: Sarah Helen Bednar
Student number: A0179788X
Is this a group submission (yes/no)? no
*/


import java.net.*;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.io.*;



public class WebServer {
    public static void main(String[] args) {
        // dummy value that is overwritten below
        int port = 8080;
        try {
          port = Integer.parseInt(args[0]);
        } catch (Exception e) {
          System.out.println("Usage: java WebServer <port> ");
          System.exit(0);
        }

        WebServer serverInstance = new WebServer();
        serverInstance.start(port);
    }

    private void start(int port) {
      System.out.println("Starting server on port " + port);
      // START_HERE

      // Please DO NOT copy from the Internet (or anywhere else)
      // Instead, if you see nice code somewhere try to understand it.
      //
      // After understanding the code, put it away, do not look at it,
      // and write your own code.
      // Subsequent exercises will build on the knowledge that
      // you gain during this exercise. Possibly also the exam.
      //
      // We will check for plagiarism. Please be extra careful and
      // do not share solutions with your friends.
      //
      // Good practices include
      // (1) Discussion of general approaches to solve the problem
      //     excluding detailed design discussions and code reviews.
      // (2) Hints about which classes to use
      // (3) High level UML diagrams
      //
      // Bad practices include (but are not limited to)
      // (1) Passing your solution to your friends
      // (2) Uploading your solution to the Internet including
      //     public repositories
      // (3) Passing almost complete skeleton codes to your friends
      // (4) Coding the solution for your friend
      // (5) Sharing the screen with a friend during coding
      // (6) Sharing notes
      //
      // If you want to solve this assignment in a group,
      // you are free to do so, but declare it as group work above.
      
      


      // NEEDS IMPLEMENTATION
      // You have to understand how sockets work and how to program
      // them in Java.
      // A good starting point is the socket tutorial from Oracle
      // http://docs.oracle.com/javase/tutorial/networking/sockets/
      // But there are a billion other resources on the Internet.
      //
      // Hints
      // 1. You should set up the socket(s) and then call handleClientSocket.

      
    }

    /**
     * Handles requests sent by a client
     * @param  client Socket that handles the client connection
     */
    private void handleClientSocket(Socket client) {
      // NEEDS IMPLEMENTATION
      // This function is supposed to handle the request
      // Things to do:
      // (1) Read the request from the socket 
      // (2) Parse the request and set variables of 
      //     the HttpRequest class (at the end of the file!)
      // (3) Form a response using formHttpResponse.
      // (4) Send a response using sendHttpResponse.
      //
      // A BufferedReader might be useful here, but you can also
      // solve this in many other ways.

    }

    /**
     * Sends a response back to the client
     * @param  client Socket that handles the client connection
     * @param  response the response that should be send to the client
     */
    private void sendHttpResponse(Socket client, byte[] response) {
      // NEEDS IMPLEMENTATION
    }

    /**
     * Form a response to an HttpRequest
     * @param  request the HTTP request
     * @return a byte[] that contains the data that should be send to the client
     */
    private byte[] formHttpResponse(HttpRequest request) {
      // NEEDS IMPLEMENTATION
      // Make sure you follow the (modified) HTTP specification
      // in the assignment regarding header fields and newlines
      // You might want to use the concatenate method,
      // but you do not have to.
      // If you want to you can use a StringBuilder here
      // but it is possible to solve this in multiple different ways.

    }
    

    /**
     * Concatenates 2 byte[] into a single byte[]
     * This is a function provided for your convenience.
     * @param  buffer1 a byte array
     * @param  buffer2 another byte array
     * @return concatenation of the 2 buffers
     */
    private byte[] concatenate(byte[] buffer1, byte[] buffer2) {
        byte[] returnBuffer = new byte[buffer1.length + buffer2.length];
        System.arraycopy(buffer1, 0, returnBuffer, 0, buffer1.length);
        System.arraycopy(buffer2, 0, returnBuffer, buffer1.length, buffer2.length);
        return returnBuffer;
    }
}



class HttpRequest {
    // NEEDS IMPLEMENTATION
    // This class should represent a HTTP request.
    // Feel free to add more attributes if needed.
    private String filePath;

    String getFilePath() {
        return filePath;
    }
    // NEEDS IMPLEMENTATION
    // If you add more private variables, add your getter methods here
}