package org.example;

import java.io.IOException;

public class pipelineParalelo {
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("        PIPELINE PARALELO");
        System.out.println("=============================================");

        try {
            System.out.println(" INICIANDO EJECUCIÓN PARALELA......");

            System.out.println("  --> Lanzando Proceso 1...");
            Process p1 = new ProcessBuilder("ping", "-n", "1", "127.0.0.1").start();
            System.out.println("  --> Lanzando Proceso 2...");
            Process p2 = new ProcessBuilder("ping", "-n", "1", "error.invalid").start();

            int resultado1 = p1.waitFor();
            int resultado2 = p2.waitFor();

            System.out.println("El resultado1: " + resultado1);
            System.out.println("El resultado2: " + resultado2);


            if (resultado1 == 0 && resultado2 == 0) {
                new ProcessBuilder("notepad.exe").start();

            } else {
                new ProcessBuilder("calc.exe").start();

            }

        } catch (IOException e) {
            System.out.println("Error: No se pudo lanzar el proceso");

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("=============================================");
        System.out.println("           FIN DE LA EJECUCIÓN");
        System.out.println("=============================================");
    }
}