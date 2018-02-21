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
        // NEEDS IMPLEMENTATION
        // You have to understand how sockets work and how to program
        // them in Java.
        // A good starting point is the socket tutorial from Oracle
        // http://docs.oracle.com/javase/tutorial/networking/sockets/
        // But there are a billion other resources on the Internet.
        //
        // Hints
        // 1. You should set up the socket(s) and then call handleClientSocket.
        try {
            // create ServerSocket
            ServerSocket welcomeSocket = new ServerSocket(port);
            // listen to new connection requests 
            while (true) {
                Socket clientSocket = welcomeSocket.accept();
                System.out.println("Connection established with new client.");
                // once tcp connection established, pass connection socket to handleClientSocket
                handleClientSocket(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + port);
            System.out.println(e.getMessage());
        }

    }

    /**
     * Handles requests sent by a client
     *
     * @param client Socket that handles the client connection
     */
    private void handleClientSocket(Socket client) throws IOException {
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

        // read request from the socket
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        StringBuilder req = new StringBuilder();
        String userInput;
        while ((userInput = in.readLine()) != null) {
            if (userInput.length() == 0) // end of headers
            {
                break;
            }
            req.append(userInput);
            req.append("\r\n");
        }
        in.close();
        HttpRequest request = new HttpRequest();
        request.parseRequest(req.toString());
        System.out.println("HTTP request read");
        if (request.isPersistent() == 1) {
            client.setSoTimeout(2000);
        }
        byte[] response = formHttpResponse(request);
        System.out.println("Formed a response for the client");
        sendHttpResponse(client, response);
        System.out.println("Sent response to the client");

    }

    /**
     * Sends a response back to the client
     *
     * @param client Socket that handles the client connection
     * @param response the response that should be send to the client
     */
    private void sendHttpResponse(Socket client, byte[] response) throws IOException {
        // NEEDS IMPLEMENTATION
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
        //PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        //out.print(response);
        //out.close();
    }

    /**
     * Form a response to an HttpRequest
     *
     * @param request the HTTP request
     * @return a byte[] that contains the data that should be send to the client
     */
    private byte[] formHttpResponse(HttpRequest request) throws IOException {
        // NEEDS IMPLEMENTATION
        // Make sure you follow the (modified) HTTP specification
        // in the assignment regarding header fields and newlines
        // You might want to use the concatenate method,
        // but you do not have to.
        // If you want to you can use a StringBuilder here
        // but it is possible to solve this in multiple different ways.

        // HTTP/1.* SP 200 OK CRLF
        // Content-Length: SP Number CRLF
        // CRLF 
        byte[] data = Files.readAllBytes(Paths.get(request.getFilePath()));
        String statusLine = request.getVerison() + " 200 OK\r\n";
        String entityHeader = "Content-Length: " + data.length + "\r\n\r\n"; 
        byte[] response = concatenate(statusLine.getBytes(), entityHeader.getBytes());

        return response;

    }

    /**
     * Concatenates 2 byte[] into a single byte[] This is a function provided
     * for your convenience.
     *
     * @param buffer1 a byte array
     * @param buffer2 another byte array
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

    private String filePath;
    private String verison; // HTTP/1.0 or 1.1 
    private String fieldName;
    private String fieldValue;

    // NEEDS IMPLEMENTATION
    // This class should represent a HTTP request.
    // Feel free to add more attributes if needed.
    public HttpRequest() {

    }

    String getFilePath() {
        return filePath;
    }

    // NEEDS IMPLEMENTATION
    // If you add more private variables, add your getter methods here
    String getVerison() {
        return verison;
    }

    String getFieldName() {
        return fieldName;
    }

    String getFieldValue() {
        return fieldValue;
    }

    int isPersistent() {
        if (verison.equals("HTTP/1.0")) {
            return 0;
        } else if (verison.equals("HTTP/1.1")) {
            return 1;
        }
        return -1;
    }

    public void parseRequest(String data) {
        // Request format 
        // "GET" SP absolutepath SP "HTTP/1.*" CRLF 
        // Field-Name: SP FieldValue CRLF 
        // CRLF 
        String[] request = data.split("\r\n"); //CRLF
        String[] requestLine = request[0].split(" "); //SP
        filePath = requestLine[1];
        verison = requestLine[2];
        String[] headerLine = request[1].split(" "); //SP 
        fieldName = headerLine[0].substring(0, headerLine[0].length() - 1);
        fieldValue = headerLine[1];
    }
}
