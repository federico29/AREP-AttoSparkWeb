package co.edu.escuelaing.attosparkweb;

/**
 *
 * @author Federico Barrios Meneses
 */
public class AttoSpark {
    
    public static void get(String url, String body){
        AttoSparkServer server = AttoSparkServer.getInstance();
        server.get(url, body);
    }
    
    public static void port(int port){
        AttoSparkServer server = AttoSparkServer.getInstance();
        server.port(port);
    }
    
    public static void startServer(){
        AttoSparkServer server = AttoSparkServer.getInstance();
        server.startServer();
    }
}
