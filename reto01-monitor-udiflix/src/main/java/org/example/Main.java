import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        String[][] contenidos = {
                {"Series", "127.0.0.1"},
                {"Películas", "127.0.0.1"},
                {"Documentales", "127.0.0.1"},
                {"Anime", "192.0.2.1"},
                {"Infantil", "127.0.0.1"}
        };

        System.out.println("========================================");
        System.out.println("        UDITFLIX - CATÁLOGO");
        System.out.println("========================================");

        for (int i = 0 ; i < contenidos.length ; i++) {
            String categoria = contenidos[i][0];
            String ip = contenidos[i][1];
            System.out.println("[CONTENIDO] " + categoria);
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "ping", "-n", "1", ip
                );
                pb.redirectErrorStream(true);
                Process proceso = pb.start();
                System.out.println("PID: " + proceso.pid());
                int codigo = proceso.waitFor();
                if (codigo == 0) {
                    System.out.println("ESTADO : SERVICIO ACTIVO");
                } else {
                    System.out.println("ESTADO : SERVICIO CON ERROR");
                }
            } catch (IOException e) {
                System.out.println("No se pudo lanzar el proceso");
            } catch (InterruptedException e) {
                System.out.println("La ejecución fue interrumpida");
            }
        }
        System.out.println("========================================");
        System.out.println("        COMPROBACION FINALIZADA ");
        System.out.println("========================================");
    }
}