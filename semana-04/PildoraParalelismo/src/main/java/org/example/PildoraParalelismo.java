package org.example;

import java.io.IOException;

public class PildoraParalelismo {
    public static void main(String[] args){
        System.out.println("==============================================");
        System.out.println("PILDORA TÉCNICA: SECUENCIAL VS PARALELO");
        System.out.println("==============================================\n");

        try{
            System.out.println(" INICIANDO EJECUCIÓN SECUENCIAL......");
            long inicioSecuencial = System.currentTimeMillis();

            System.out.println("    --> Lanzando proceso 1 ( y esperando que muera...)");
            Process p1 = new ProcessBuilder("ping", "-n", "2", "127.0.0.1").start();
            p1.waitFor();
            //una vez que el proceso 1 termine, por fin lanzamos el SEGUNDO proceso
            System.out.println("    --> Lanzando proceso 2 ( y esperando que muera...)");
            Process p2 = new ProcessBuilder("ping", "-n", "2", "8.8.8.8").start();
            p2.waitFor(); //Java se vuelve a congelar

            long finSecuencial = System.currentTimeMillis();
            System.out.println("⏱🕐 TIEMPO TOTAL SECUENCIAL: " + (finSecuencial - inicioSecuencial) + "ms\n");
        //2. EL CAMINO PARALELO (Ejecucion solapada)
            System.out.println("==============================================");
            System.out.println(" INICIADO EJECUCIÓN PARALELA");
            System.out.println("==============================================\n");

            //rESETEO EL CRONOMETRO
            long inicioParalelo = System.currentTimeMillis();

            //PASO A: Apretamos todos los gatillos primero
            System.out.println("    --> Lanzando Proceso 3 (No esperamos!)");
            Process p3 = new ProcessBuilder("ping", "-n", "2", "127.0.0.1").start();
            System.out.println("    --> Lanzando Proceso 3 (No esperamos!)");
            Process p4 = new ProcessBuilder("ping", "-n", "2", "8.8.8.8").start();

            //PASO B: Ahora si, le decimos a java que recoja los resultados
            //Como ya estan corriendo simultaneamente en el sistema operativo, el tiempo de espero se solapa

            System.out.println("   --> Bloqueando Java para recoger resultados");
            p3.waitFor();
            p4.waitFor();

            long finParalelo = System.currentTimeMillis();
            System.out.println("⏱🕐 TIEMPO TOTAL PARALELO: " + (finParalelo -inicioParalelo) + "ms\n");

        } catch (IOException e){
            System.out.println("Error: No se pudo lanzar el proceso");
        } catch (InterruptedException e){
            System.out.println("Error: La espera fue interrumpida");
        }


    }
}
