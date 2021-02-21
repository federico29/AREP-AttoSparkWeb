package co.edu.escuelaing.attosparkweb.runtime;

import static co.edu.escuelaing.attosparkweb.AttoSpark.*;
/**
 *
 * @author Federico Barrios Meneses
 */
public class DemoRunTime {
    public static void main(String[] args){
        port(getPort());
        get("/hello", "Hello world!");
        get("/ramona", "Hola Ramona!");
        get("/cr7", "El Bicho");
        startServer();
    }

    private static int getPort() {
        if(System.getenv("PORT") != null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 36000;
    }
}
