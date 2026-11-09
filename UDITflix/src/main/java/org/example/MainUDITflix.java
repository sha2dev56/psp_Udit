package org.example;

import java.util.ArrayList;

public class MainUDITflix {

    public static void main(String[] args) {

        ArrayList<Thread> trabajadores = new ArrayList<>();
        ArrayList<TareaDescarga> tareas = new ArrayList<>();

        // Crear las tareas
        tareas.add(new TareaDescarga("catalogo-metadatos"));
        tareas.add(new TareaDescarga("sesion-y-perfil"));
        tareas.add(new TareaDescarga("portadas-assets"));
        tareas.add(new TareaDescarga("analiticas"));

        // Configurar prioridades
        int[] prioridades = {
                Thread.MAX_PRIORITY,
                Thread.MAX_PRIORITY,
                Thread.NORM_PRIORITY,
                Thread.MIN_PRIORITY
        };

        String[] nombres = {
                "DL-catalogo",
                "DL-sesion",
                "DL-portadas",
                "DL-analitica"
        };

        // Crear los hilos y almacenarlos
        for (int i = 0; i < tareas.size(); i++) {

            Thread hilo = new Thread(tareas.get(i), nombres[i]);

            hilo.setPriority(prioridades[i]);

            trabajadores.add(hilo);
        }

        System.out.println("[MAIN] Descargando 4 módulos en paralelo...\n");

        long inicio = System.currentTimeMillis();

        // Arrancar todos los hilos
        for (Thread hilo : trabajadores) {
            hilo.start();
        }

        // Esperar a que terminen todos
        for (Thread hilo : trabajadores) {
            try {
                hilo.join();

            } catch (InterruptedException e) {
                System.out.println("[MAIN] Espera interrumpida");
                Thread.currentThread().interrupt();
                return;
            }
        }

        long fin = System.currentTimeMillis();

        // Calcular métricas
        long tiempoTotal = fin - inicio;
        int sumaLatencias = 0;
        int duracionMaxima = 0;

        for (TareaDescarga tarea : tareas) {

            int latencia = tarea.getLatencia();

            sumaLatencias += latencia;

            if (latencia > duracionMaxima) {
                duracionMaxima = latencia;
            }
        }

        // Resultado final
        System.out.println("\n====================================");
        System.out.println("[MAIN] Todos los módulos listos");
        System.out.println("[MAIN] LOGIN HABILITADO");
        System.out.println("====================================");

        System.out.println("Tiempo total real: " + tiempoTotal + " ms");
        System.out.println("Duración del más lento: " + duracionMaxima + " ms");
        System.out.println("Suma de todas: " + sumaLatencias + " ms");
    }
}