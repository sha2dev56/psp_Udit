package org.example;

import java.io.IOException;

public class patronRetry {
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("             PATRÓN RETRY");
        System.out.println("===========================================");

        try {
            System.out.println("Iniciando...");
            long inicio = System.currentTimeMillis();

            int intentos = 0;
            int resultado1 = 0;
            int resultado2 = 0;

            while (intentos < 3) {
                System.out.println("Intento " + (intentos + 1) + "...");

                Process p1 = new ProcessBuilder("ping", "-n", "1", "-w", "3000", "127.0.0.1").start();
                Process p2 = new ProcessBuilder("ping", "-n", "1", "-w", "3000", "192.0.2.1").start();

                resultado1 = p1.waitFor();
                resultado2 = p2.waitFor();

                System.out.println("El resultado1: " + resultado1);
                System.out.println("El resultado2: " + resultado2);

                intentos++;
                if (intentos < 3) {
                    System.out.println("Esperando 2 segundos antes del siguiente intento...\n");
                    Thread.sleep(2000);
                }
            }

            // Evaluamos el resultado tras el bucle
            if (resultado2 != 0) {
                System.out.println("Lanzando calculadora (ping falló)...");
                new ProcessBuilder("calc.exe").start();
            } else {
                System.out.println("Lanzando bloc de notas (ping exitoso)...");
                new ProcessBuilder("notepad.exe").start();
            }

            System.out.println("Tiempo total: " + (System.currentTimeMillis() - inicio) + " ms");

        } catch (IOException e) {
            System.out.println("ERROR: No se pudo lanzar el proceso.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El proceso fue interrumpido.");
        }
    }
}