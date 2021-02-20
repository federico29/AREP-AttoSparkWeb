package co.edu.escuelaing.httpserver;

import co.edu.escuelaing.attosparkweb.AttoSparkServer;
import co.edu.escuelaing.attosparkweb.Processor;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Federico Barrios Meneses
 */
public class HttpServer {

    private int port;
    
    Map<String, Processor> routesToProcessors = new HashMap<>();
    
    public void startServer(int httpPort) throws IOException {
        this.port = httpPort;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + getPort());
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir en puerto..." + getPort() + "...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            
            boolean isFirstLine = true;
            String path = "";
            
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if(isFirstLine){
                    path = inputLine.split(" ")[1];
                    isFirstLine = false;
                }
                if (!in.ready()) {
                    break;
                }
            }
            
            String resp = null;
            for(String k : routesToProcessors.keySet()){
                if(path.startsWith(k)){
                    String newPath = path.substring(k.length());
                    System.out.println(newPath);
                    resp = routesToProcessors.get(k).handle(newPath);
                }
            }
            
            if(resp == null){
                outputLine = validOkHttpResponse();
            }else{
                outputLine = resp;
            }
            
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public int getPort() {
        return this.port;
    }

    public void registerProcessor(String path, Processor proc) {
        routesToProcessors.put(path, proc);
    }
    
    public String validOkHttpResponse(){
        return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<title>Title of the document</title>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<h1>Mi propio mensaje</h1>\n"
                    + "</body>\n"
                    + "</html>\n";
    }
}
