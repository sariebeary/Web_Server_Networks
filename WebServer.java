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
            System.out.println("Exception caught when trying to listen on port."
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

        // read request from the socket
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuilder req = new StringBuilder();
            String userInput;
            while ((userInput = in.readLine()) != null) {
                System.out.println(userInput);
                if (userInput.length() == 0) // end of headers
                {
                    break;
                }
                req.append(userInput);
                req.append("\r\n");
            }
            HttpRequest request = new HttpRequest();
            request.parseRequest(req.toString());
            System.out.println("HTTP request read");
            if (request.isPersistent() == 1) {
                client.setSoTimeout(2000); //set timer to 2 seconds 
				System.out.println("Persistent connection established. Setting timer.");
            }
            byte[] response = formHttpResponse(request);
            System.out.println("Formed a response for the client");
            sendHttpResponse(client, response);
            System.out.println("Sent response to the client");
            if (request.isPersistent() == 0) {
                in.close();
                client.close();
                System.out.println("Connection closed");

            }
        } catch (SocketTimeoutException e) {
            client.close();
            System.out.println("Timeout. Connection closed. ");

        }

    }
	

    /**
     * Sends a response back to the client
     *
     * @param client Socket that handles the client connection
     * @param response the response that should be send to the client
     */
    private void sendHttpResponse(Socket client, byte[] response) throws IOException {
        try { // NEEDS IMPLEMENTATION
            BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
            out.write(response);
			out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("Unable to send response.");
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Form a response to an HttpRequest
     *
     * @param request the HTTP request
     * @return a byte[] that contains the data that should be send to the client
     */
    private byte[] formHttpResponse(HttpRequest request) throws IOException {
        // HTTP/1.* SP 200 OK CRLF
        // Content-Length: SP Number CRLF
        // CRLF 
        byte[] data = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + request.getFilePath()));
        String statusLine = request.getVerison() + " 200 OK\r\n";
        System.out.print(statusLine);
        String entityHeader = "Content-Length: " + data.length + "\r\n\r\n";
        System.out.print(entityHeader);
        byte[] response = concatenate(statusLine.getBytes(), entityHeader.getBytes());
		response = concatenate(response, data);

	
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

    public HttpRequest() {

    }

    String getFilePath() {
        return filePath;
    }

    String getVerison() {
        return verison;
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
    }
}
