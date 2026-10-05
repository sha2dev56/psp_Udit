package org.example;

import java.io.IOException;

public class CodigoSalida {
    public static void main(String[]args){
        System.out.println("=================================");
        System.out.println("        COMPROBACIÓN DE SERVIDOR");
        System.out.println("=================================");

        try{
            // 1 PREPARAMOS EL PRCCESO EXTERNO
            // vamos a ejecutar el comando "ping"
            //en windows, ping -n 1 8.8.8.8
            // -n 1 -> realiza un sola comprobacion
            // 8.8.8.8 -> direccion
            ProcessBuilder pb = new ProcessBuilder(
                    "ping",
                    "-n",
                    "1",
                    "8.8.8.8"
            );
            //2 LANZAMOS EL PROCESO
            //start() ejecuta el proceso externo
            // el resultado de start() es un objeto Process
            Process proceso = pb.start();

            // 3 OBTENEMOS EL PID
            //pid() nos permite conocer el identificador
            System.out.println("El PID: " + proceso.pid());

            //4 ESPERAMOS A QUE TERMINE
            //waitfor() detiene nuestro proceso JAVA
            //hasta que el proceso externo termine
            //ademas, devuelve un numero entero

            int codigoSalida = proceso.waitFor();

            //5. MOSTRAMOS EL CODIGO DE SALIDA
            System.out.println("Codigo de salida: " + codigoSalida);

            //6. INTERPRETAMOS EL RESULTADO
            //CODIGO 0: RESULTADO CORRECTO
            //OTRO CODIGO: RESULTADO NO SATISFACTORIO
            if(codigoSalida == 0){
                System.out.println("ESTADO: ACTIVO");
            } else{
                System.out.println("ESTADO: CAÍDO");
            }
        } catch (IOException e){
            System.out.println("Error al lanzar el proceso.");
        } catch (InterruptedException e){
            System.out.println("La espera del proceso fue interrumpido");
        }

        System.out.println("=================================");
        System.out.println("        FIN DE LA COMPROBACIÓN");
        System.out.println("=================================");
    }
}
