package co.edu.escuelaing.attosparkweb;

import co.edu.escuelaing.httpserver.HttpServer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Federico Barrios Meneses
 */
public class AttoSparkServer implements Processor {

    private static AttoSparkServer _instance = new AttoSparkServer();
    private int port;
    private int httpPort = 36000;
    Map<String, String> functions = new HashMap<>();
    HttpServer httpServer = new HttpServer();

    private AttoSparkServer() {
        httpServer.registerProcessor("/Apps", this);
    }

    public static AttoSparkServer getInstance() {
        return _instance;
    }

    public void get(String url, String body) {
        functions.put(url, body);
    }

    public void startServer() {
        try {
            httpServer.startServer(httpPort);
        } catch (IOException ex) {
            Logger.getLogger(AttoSparkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void port(int serverPort) {
        this.httpPort = serverPort;
    }

    @Override
    public String handle(String path) {
        if (functions.containsKey(path)) {
            return validHttpHeader() + functions.get(path);
        }
        return validErrorHttpHeader() + "Error 404";
    }

    private String validHttpHeader() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
    }

    private String validErrorHttpHeader() {
        return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<title>Error</title>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<h1>Error 404: page not found</h1>\n"
                    + "</body>\n"
                    + "</html>\n";
    }
}
