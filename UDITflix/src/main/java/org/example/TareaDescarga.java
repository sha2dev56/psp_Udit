package org.example;

import java.util.Random;

public class TareaDescarga implements Runnable {

    private final String nombreModulo;
    private int latencia;

    public TareaDescarga(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public int getLatencia() {
        return latencia;
    }

    @Override
    public void run() {

        Random azar = new Random();
        latencia = 1000 + azar.nextInt(3001);

        Thread hiloActual = Thread.currentThread();

        System.out.println("--> [" + hiloActual.getName() +
                "] prio=" + hiloActual.getPriority() +
                " latencia=" + latencia + " ms");

        try {
            Thread.sleep(latencia);

            System.out.println("<-- [" + hiloActual.getName() +
                    "] LISTO | prio=" + hiloActual.getPriority());

        } catch (InterruptedException e) {

            System.out.println("[ERROR] Descarga interrumpida: "
                    + hiloActual.getName());

            Thread.currentThread().interrupt();
        }
    }
}