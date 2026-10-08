Reto 2 · Pipeline de Auditoría UDITversum (Fase 1)

Módulo: 0490 · Programación de Servicios y Procesos
Autor/a: Shaghayegh Asghari
Tecnología: Java + ProcessBuilder (procesos del sistema operativo)
RA vinculado: RA1 · Programación de aplicaciones compuestas por varios procesos

📺 Qué es esta app

Un programa de consola que simula la primera fase de una auditoría de UDITversum.

El programa:

Lanza dos comprobaciones (ping) a la vez, cada una en su propio proceso del sistema operativo.

Espera a que ambas terminen.

Lee el código de salida de cada una.

Según el resultado de los dos procesos, toma una decisión y abre una aplicación del sistema.

En mi caso, si los dos procesos terminan correctamente se abre el Bloc de Notas. Si alguno falla, se abre la Calculadora.

             Mi programa Java
                    |
          +---------+---------+
          |                   |
       start()             start()
          |                   |
          v                   v
      ping A              ping B
  127.0.0.1            error.invalid
          |                   |
       waitFor()           waitFor()
          |                   |
          +---------+---------+
                    |
             comprobar códigos
                    |
              +-----+-----+
              |           |
           ambos 0      alguno != 0
              |           |
              v           v
        Bloc de Notas  Calculadora

🧠 Antes de empezar: planifico

El reto me pide crear un programa Java que ejecute dos procesos del sistema operativo al mismo tiempo. Los dos procesos son comandos ping.

Después tengo que esperar a que terminen, comprobar el código de salida de cada proceso y decidir qué aplicación abrir dependiendo de los resultados.

¿Qué parte del Reto 1 voy a reutilizar tal cual?

Voy a reutilizar la creación de procesos mediante ProcessBuilder, el método start() y la gestión de excepciones mediante try/catch.

¿Qué es nuevo respecto al Reto 1 y me da más respeto?

Lo nuevo es tener dos procesos ejecutándose al mismo tiempo y tener que esperar a los dos para poder tomar una decisión.

También tengo que entender bien la diferencia entre lanzar un proceso con start() y esperar a que termine con waitFor().

Mi plan en 4-5 pasos

Crear el primer proceso ping.

Crear el segundo proceso ping antes de esperar al primero.

Esperar a que terminen los dos procesos con waitFor().

Guardar y mostrar los códigos de salida.

Comprobar los resultados y abrir Notepad o Calculadora.

Predicciones

Escenario

¿Qué código de salida espero en cada ping?

¿Qué aplicación se abre?

Los dos pings a 127.0.0.1

Los dos deberían devolver 0

Bloc de Notas

Un ping válido y otro a una dirección inexistente

El válido debería devolver 0 y el otro un valor distinto de 0

Calculadora

Los dos pings a direcciones inexistentes

Los dos deberían devolver un valor distinto de 0

Calculadora

Predicción de tiempo

Si cada ping tardase unos 3 segundos y los ejecuto en paralelo, el programa debería tardar aproximadamente unos 3 segundos en lugar de unos 6 segundos.

Si los ejecutara uno detrás de otro, tendría que esperar a que terminase el primero antes de lanzar el segundo, por lo que el tiempo sería aproximadamente la suma de los dos.

🎯 Objetivo del reto

El objetivo es aprender a coordinar varios procesos simultáneamente.

En mi programa utilizo:

ProcessBuilder para preparar los comandos.

start() para lanzar los procesos.

waitFor() para esperar a que terminen.

Códigos de salida para saber cómo ha terminado cada proceso.

&& para comprobar que los dos procesos han terminado correctamente.

try/catch para controlar IOException e InterruptedException.

🛠️ Componentes y conceptos utilizados

Componente / concepto

Para qué se usa en esta app

Con mis palabras

ProcessBuilder

Prepara la orden que se enviará al sistema operativo

Lo utilizo para indicar qué programa o comando quiero ejecutar.

start()

Lanza de verdad el proceso y devuelve el control enseguida

Sirve para iniciar el proceso sin esperar a que termine.

Process

Objeto con el que controlo cada proceso ya en marcha

Es el objeto que representa al proceso que he lanzado.

waitFor()

Espera a que el proceso termine y devuelve su código

Hace que mi programa espere hasta que termine ese proceso.

Código de salida (int)

Indica cómo terminó el proceso
<img width="487" height="303" alt="image" src="https://github.com/user-attachments/assets/b20a61be-52e3-4833-8ab6-7c9172876220" />


Un 0 significa que terminó correctamente y otro valor indica algún tipo de error.

&&

Comprueba que las dos condiciones sean verdaderas

Lo uso porque necesito que los dos pings hayan terminado correctamente.

`



`

Se cumple si al menos una condición es verdadera

No lo utilizo en mi condición final.

try/catch

Controla excepciones

Lo utilizo para controlar errores al lanzar procesos o durante la espera.

InterruptedException

Puede aparecer cuando el hilo que espera es interrumpido

La gestiono porque waitFor() puede ser interrumpido.

¿Cómo se llaman mis dos objetos Process y qué lanza cada uno?

Mis dos objetos son:

Process p1
Process p2

p1 lanza:

ping -n 1 127.0.0.1

y p2 lanza:

ping -n 1 error.invalid

🔀 Secuencial vs paralelo: el corazón de este reto

La diferencia está en el orden de start() y waitFor().

En mi programa primero lanzo los dos procesos:

Process p1 = new ProcessBuilder("ping", "-n", "1", "127.0.0.1").start();

Process p2 = new ProcessBuilder("ping", "-n", "1", "error.invalid").start();

y después espero:

int resultado1 = p1.waitFor();
int resultado2 = p2.waitFor();

Por eso los dos procesos pueden estar ejecutándose al mismo tiempo.

Si pusiera el primer waitFor() antes del segundo start(), tendría que esperar a que terminase el primer ping antes de lanzar el segundo. En ese caso la ejecución sería secuencial.

Mi orden real de llamadas

Process p1 = new ProcessBuilder("ping", "-n", "1", "127.0.0.1").start();

Process p2 = new ProcessBuilder("ping", "-n", "1", "error.invalid").start();

int resultado1 = p1.waitFor();

int resultado2 = p2.waitFor();

🔢 Tabla de verdad de mi decisión

Mi condición es:

if (resultado1 == 0 && resultado2 == 0)

Código ping A

Código ping B

¿Ping A OK?

¿Ping B OK?

Condición

Aplicación

0

0

Sí

Sí

true && true

Bloc de Notas

0

≠ 0

Sí

No

true && false

Calculadora

≠ 0

0

No

Sí

false && true

Calculadora

≠ 0

≠ 0

No

No

false && false

Calculadora

¿Cambiaría el resultado si cambiara && por ||?

Sí.

Con && necesito que los dos pings sean correctos.

Con || bastaría con que uno de los dos fuese correcto.

Por ejemplo, si los resultados fueran 0 y 1:

0 == 0  → verdadero
1 == 0  → falso

Con &&:

verdadero && falso → falso

Por lo tanto se abriría la Calculadora.

🚀 Cómo ejecutar el proyecto

Abrir el proyecto en IntelliJ IDEA.

Esperar a que IntelliJ termine de cargar el proyecto.

Ejecutar la clase principal.

Comprobar los códigos de salida.

Comprobar qué aplicación se abre.

He probado el programa en:

Windows

El programa utiliza:

ping -n
notepad.exe
calc.exe

por lo que está preparado para Windows.

🔍 Mientras programo: mi diario de decisiones

Qué intentaba

Qué pasó realmente

Qué hice / qué aprendí

Lanzar los dos pings a la vez

Al principio dudaba dónde poner waitFor()

Entendí que primero tengo que hacer los dos start() y después los waitFor().

Obtener el resultado de cada proceso

waitFor() devuelve un valor entero

Guardé los resultados en resultado1 y resultado2.

Decidir qué aplicación abrir

Necesitaba comprobar los dos resultados

Utilicé && porque ambos procesos deben terminar correctamente.

Gestionar una interrupción

waitFor() puede lanzar InterruptedException

Añadí el catch y restauro el estado de interrupción del hilo.

Mi pregunta-brújula cuando me bloqueo

¿Qué espero que haga esta línea?

¿Qué está haciendo realmente?

¿Qué valor está devolviendo?

¿En qué punto exacto se separan las dos respuestas?

🧠 Análisis técnico

1. Secuencial vs paralelo

Las líneas que hacen posible la ejecución paralela son los dos start() consecutivos:

Process p1 = new ProcessBuilder("ping", "-n", "1", "127.0.0.1").start();

Process p2 = new ProcessBuilder("ping", "-n", "1", "error.invalid").start();

Los dos procesos se lanzan antes de hacer cualquier waitFor().

start() inicia el proceso y mi programa puede continuar ejecutando las siguientes instrucciones.

Si pusiera:

p1.waitFor();

antes de lanzar p2, mi programa tendría que esperar a que terminase el primer proceso antes de iniciar el segundo.

Por eso el orden es importante.

2. El código de salida

waitFor() devuelve un int.

En mi programa guardo ese valor:

int resultado1 = p1.waitFor();
int resultado2 = p2.waitFor();

Un código 0 normalmente representa que el proceso ha terminado correctamente.

Un código distinto de 0 indica que el proceso ha terminado con algún tipo de error o resultado diferente.

En mi caso puedo comprobar los valores con:

System.out.println("El resultado1: " + resultado1);
System.out.println("El resultado2: " + resultado2);

Es importante distinguir entre que el programa ping no pueda arrancar y que ping arranque correctamente pero obtenga un resultado de error. El primer caso puede provocar una IOException, mientras que el segundo se refleja mediante el código de salida.

3. Lógica condicional

Mi condición exacta es:

if (resultado1 == 0 && resultado2 == 0) {
    new ProcessBuilder("notepad.exe").start();
} else {
    new ProcessBuilder("calc.exe").start();
}

Utilizo && porque quiero que se cumplan las dos condiciones.

Es decir:

resultado1 == 0

y además:

resultado2 == 0

Si las dos son verdaderas, los dos procesos han terminado correctamente y abro el Bloc de Notas.

Si una de las dos es falsa, entro en else y abro la Calculadora.

4. Gestión de excepciones

Utilizo:

catch (IOException e) {
    System.out.println("Error: No se pudo lanzar el proceso");
}

Una situación que podría provocar IOException sería intentar ejecutar un programa que no existe o escribir incorrectamente el nombre del ejecutable.

Por ejemplo, si intentase ejecutar:

notepd.exe

en lugar de:

notepad.exe

Java no encontraría ese ejecutable y no podría iniciar el proceso.

Esto es diferente a que ping se ejecute pero obtenga un resultado negativo.

En ese segundo caso el proceso sí se ha iniciado, por lo que puedo recibir un código de salida distinto de 0.

InterruptedException

También controlo:

catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}

waitFor() hace que el hilo que ejecuta mi programa espere a que termine el proceso.

Si ese hilo es interrumpido mientras está esperando, se produce InterruptedException.

Thread.currentThread() obtiene el hilo que está ejecutando actualmente el código.

interrupt() vuelve a marcar ese hilo como interrumpido.

Por eso utilizo:

Thread.currentThread().interrupt();

en el catch.

🛡️ Preparación para la defensa

Cambiar la condición para que se abra la Calculadora solo si falla uno de los dos pings

Tendría que modificar la lógica de la condición para distinguir exactamente el caso en el que uno funciona y el otro falla.

Añadir un tercer ping

Tendría que crear otro objeto Process, lanzar su start() antes de comenzar los waitFor() y después incluir su resultado en la decisión.

Mostrar el PID

Java permite obtener información del proceso mediante el objeto Process.

Medir el tiempo

Podría guardar el tiempo antes de lanzar los procesos y volver a obtenerlo después de los waitFor() para calcular la diferencia.

Hacerlo funcionar en Linux

En Linux tendría que cambiar las opciones de ping, porque en Windows utilizo -n.

También tendría que cambiar las aplicaciones que abro, porque notepad.exe y calc.exe son aplicaciones de Windows.

Provocar una IOException

Podría intentar lanzar intencionadamente un ejecutable que no exista y comprobar que entra en el catch.

¿Qué pasaría si quito waitFor()?

El programa podría continuar sin esperar a que los procesos terminasen.

En ese caso no tendría garantizado que los códigos de salida estuvieran disponibles antes de tomar la decisión.

🧭 Del Reto 1 al Reto 2: cómo di el salto

¿Qué hacía mi Reto 1 que aquí ya no me sirve tal cual?

En el Reto 1 los procesos se gestionaban uno detrás de otro.

En este reto necesito que los dos procesos se lancen antes de esperar a cualquiera de ellos.

¿Qué he tenido que cambiar?

He separado el lanzamiento de los procesos de la espera.

Primero:

start()
start()

y después:

waitFor()
waitFor()

¿Qué ventaja tiene lanzar en paralelo?

La principal ventaja es que los dos procesos pueden trabajar al mismo tiempo y no tengo que esperar a que termine uno para empezar el otro.

¿Qué problema nuevo aparece?

Ahora tengo que controlar los resultados de varios procesos y tomar una decisión teniendo en cuenta todos ellos.

¿Qué pasaría con 50 comprobaciones?

La estructura actual sería poco cómoda si tuviera que crear manualmente 50 variables Process.

Para muchos procesos tendría más sentido utilizar estructuras como arrays o colecciones y recorrerlas con bucles.

🧠 Qué he aprendido

start() vs waitFor(): lanzar un proceso y esperar a que termine son cosas distintas. start() inicia el proceso y waitFor() espera a su finalización.

Paralelismo real: los dos procesos pueden ejecutarse a la vez porque primero hago los dos start() y después los waitFor().

Código de salida: 0 indica normalmente que el proceso terminó correctamente y otro valor indica un resultado diferente o error.

&& vs ||: utilizo && porque necesito que los dos pings sean correctos para abrir el Bloc de Notas.

IOException vs ping fallido: una IOException puede aparecer cuando Java no consigue iniciar el proceso; un ping puede ejecutarse correctamente y aun así terminar con un código distinto de 0.

Fiabilidad de mi decisión: la decisión depende de los códigos de salida que devuelve cada proceso. Un código de salida distinto de 0 no siempre explica por sí mismo exactamente cuál fue el problema.

🐞 Dificultades y cómo las resolví

Dificultad 1

Qué síntoma vi:

Al principio tenía dudas sobre dónde colocar waitFor().

Cuál era la causa real:

No tenía clara la diferencia entre lanzar un proceso y esperar a que terminase.

Cómo la encontré:

Revisando el funcionamiento de ProcessBuilder, start() y waitFor().

Cómo evitaré que me vuelva a pasar:

Recordando que para este ejercicio primero debo lanzar los dos procesos y después esperar por ellos.

Dificultad 2

Qué síntoma vi:

No sabía qué hacer con InterruptedException.

Cuál era la causa real:

No entendía qué significa que el hilo que está esperando sea interrumpido.

Cómo la encontré:

Analizando qué hace waitFor() y qué significa Thread.currentThread().interrupt().

Cómo evitaré que me vuelva a pasar:

Entendiendo que currentThread() obtiene el hilo actual y interrupt() vuelve a marcarlo como interrumpido.

🪞 Autoevaluación

Puedo explicar a un compañero…

🔴 No

🟡 Más o menos

🟢 Sí

Qué hace ProcessBuilder

☐

☐

☑

Por qué start() no espera

☐

☐

☑

Qué línea hace que mis procesos sean paralelos

☐

☐

☑

Qué pasaría si moviera el waitFor()

☐

☐

☑

Qué devuelve waitFor() y qué significa 0

☐

☐

☑

Por qué uso && en mi condición

☐

☐

☑

Cuándo se entra en el catch de IOException

☐

☑

☐

Mis predicciones del principio, ¿acerté?

La predicción principal fue correcta: el ping a 127.0.0.1 debería terminar correctamente y el de error.invalid debería producir un código distinto de 0.

Por eso se ejecuta la rama else y se abre la Calculadora.

Lo que haría diferente si empezara de nuevo

Intentaría planificar antes el orden de los start() y waitFor(), porque es la parte más importante para que los procesos se ejecuten en paralelo.

Lo que todavía no tengo claro

Me gustaría profundizar en cómo gestionar muchos procesos a la vez y cómo obtener más información sobre los códigos de error distintos de 0.

🤝 Declaración de autoría y aprendizaje

Esta sección corresponde a la entrega real del alumno y debe ser completada y aceptada únicamente por el alumno.

☐ Confirmo que he diseñado, programado y depurado este código aplicando mi propio razonamiento, y que puedo explicarlo línea a línea.

☐ Entiendo que durante la defensa el profesor me pedirá realizar pequeñas modificaciones sobre este código para comprobar mi comprensión del multiproceso.

📂 Estructura del proyecto

src/main/java/org/example/
    pipelineParalelo.java

README.md

🔗 Enlace

GitHub: https://github.com/joscanoav/psp_udit/tree/main/reto02_pipeline-auditoria
