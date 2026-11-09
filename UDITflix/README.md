
# Reto 3 · Motor Concurrente en UDITflix

**Módulo:** 0490 · Programación de Servicios y Procesos (2º DAM)  
**RA vinculado:** RA2 · Programación de aplicaciones multihilo  
**Prueba realizada:** B · El Optimizador de Descarga Masiva de Assets  
**Autor/a:** Shaghayegh Asghari

---

## 📺 1. Resumen del problema

UDITflix necesita reducir el tiempo que tarda en mostrar la pantalla de inicio de sesión de su aplicación. El problema es que antes de permitir el acceso tiene que descargar varios módulos de información y recursos gráficos, y hacerlo de uno en uno provoca esperas innecesarias.

Para solucionar este problema se ha desarrollado un pequeño motor concurrente en Java que simula la descarga de cuatro módulos simultáneamente. Cada descarga tiene una latencia aleatoria y una prioridad diferente según la importancia de sus datos.

El programa espera a que terminen todas las descargas antes de habilitar el login y muestra el tiempo total, la duración de la tarea más lenta y la suma de todas las latencias.

---

## 🧠 2. Antes de empezar: planifico

### ¿Qué parte de la base v0 de clase voy a reutilizar?

Se reutiliza la estructura de la práctica anterior: una clase que implementa `Runnable`, la creación de objetos `Thread`, el uso de `start()` para iniciar las tareas y `join()` para esperar a que terminen. También se mantiene la simulación de latencias mediante `Random` y `Thread.sleep()`.

### ¿Qué es nuevo y me da más respeto?

La parte nueva es gestionar los cuatro hilos mediante un `ArrayList<Thread>` y asignar prioridades diferentes antes de iniciarlos. También hay que separar el inicio de todos los hilos de la espera mediante dos bucles distintos.

### Mi plan en 4-5 pasos

1. Adaptar la clase de tarea para representar las descargas de los cuatro módulos.
2. Crear los cuatro objetos `Thread` con sus nombres y prioridades correspondientes.
3. Guardar los hilos en un `ArrayList` y arrancarlos mediante un bucle.
4. Esperar a que terminen todos mediante otro bucle con `join()`.
5. Calcular las estadísticas y habilitar el login cuando todas las tareas hayan finalizado.

### Predicciones

| Pregunta | Predicción |
|---|---|
| ¿Cuánto tardará el programa si los cuatro hilos van a la vez? | Aproximadamente lo que tarde el módulo más lento, más un pequeño margen de planificación. |
| ¿Y si se ejecutaran uno detrás de otro? | La suma de las cuatro latencias sería aproximadamente el tiempo total. |
| ¿Saldrán los hilos siempre en el mismo orden? ¿Por qué? | No, porque las latencias son aleatorias y el planificador puede organizar la ejecución de distintas formas. |
| ¿Terminarán antes los hilos de prioridad alta? | En principio podrían recibir preferencia, pero no esperaba que la prioridad garantizase un orden concreto. |
| ¿El tiempo total se parecerá a la duración del hilo más lento o a la suma? | Se parecerá a la duración del hilo más lento porque las tareas se ejecutan en paralelo. |

---

## 🏗️ 3. Arquitectura de la solución

### 3.1 Esquema

```text
                  MAIN
                    |
          Crear los cuatro Thread
                    |
              ArrayList<Thread>
                    |
             Bucle de start()
                    |
          +---------+---------+
          |         |         |
       DL-catalogo DL-sesion DL-portadas DL-analitica
          |         |         |          |
          +---------+---------+----------+
                    |
              Bucle de join()
                    |
           Todos los hilos terminan
                    |
            Calcular estadísticas
                    |
              LOGIN HABILITADO
```

### 3.2 Componentes y conceptos

| Componente | Para qué lo uso en el programa | Explicación |
|---|---|---|
| `Runnable` | Define la tarea de descarga. | Es una interfaz que permite indicar qué trabajo debe realizar un hilo. |
| `Thread` | Ejecuta cada descarga. | Representa un hilo de ejecución independiente. |
| `start()` | Inicia cada descarga. | Arranca un nuevo hilo y hace que se ejecute su método `run()`. |
| `run()` | Contiene la lógica de descarga. | Es el código que ejecuta el hilo cuando se inicia. |
| `join()` | Espera a que terminen las descargas. | Hace que el hilo que llama a este método espere a que termine el hilo correspondiente. |
| `getState()` | Consulta el estado de un hilo. | Permite conocer en qué estado se encuentra un hilo durante su ciclo de vida. |
| `Thread.sleep()` | Simula el tiempo de descarga. | Hace que el hilo actual permanezca dormido durante un tiempo determinado. |
| `setPriority()` | Asigna prioridades. | Establece una preferencia que el planificador puede tener en cuenta, pero no garantiza un orden. |
| `setDaemon()` | No utilizado. | Permite marcar un hilo como demonio, que no impide que la JVM termine cuando ya no quedan hilos normales activos. |

### ¿Cómo se llaman mis hilos y qué hace cada uno?

- `DL-catalogo`: descarga el catálogo, los géneros y las fichas de contenido. Tiene prioridad máxima.
- `DL-sesion`: descarga el token de sesión y la información del perfil. Tiene prioridad máxima.
- `DL-portadas`: descarga los pósteres y banners. Tiene prioridad normal.
- `DL-analitica`: descarga los datos de telemetría. Tiene prioridad mínima.

---

## 🚀 4. Cómo ejecutar el proyecto

1. Clonar el repositorio de GitHub.
2. Abrir el proyecto en IntelliJ IDEA.
3. Esperar a que Maven o IntelliJ cargue las dependencias y configure el proyecto.
4. Ejecutar la clase principal `MainUDITflix`.

**Versión de Java (JDK):** OpenJDK version 17.0.20.1

**Sistema operativo y número de núcleos:** Windows 10, con procesador Intel(R) Core(TM) i7-10750H CPU @ 2.60GHz, 6 núcleos físicos y 12 procesadores lógicos.

**Archivos externos necesarios:** No se necesitan archivos externos. El programa utiliza las clases de Java y las dependencias configuradas en el proyecto.

---

## 🔍 5. Mientras programo: mi diario de decisiones

| Qué intentaba | Qué pasó realmente | Qué hice / qué aprendí |
|---|---|---|
| Crear las clases del programa. | Al copiar el código aparecieron problemas de formato, declaraciones duplicadas y errores de compilación. | Revisé las clases y los imports, eliminé las duplicaciones y comprobé la estructura del código. |
| Gestionar los cuatro hilos. | Era necesario organizar correctamente la creación y el arranque. | Utilicé un `ArrayList<Thread>` para mantener los hilos juntos y poder recorrerlos mediante bucles. |
| Esperar a que terminasen todas las descargas. | Si se esperaba a cada hilo justo después de iniciarlo, se podía perder el paralelismo. | Separé el inicio (`start()`) y la espera (`join()`) en dos bucles diferentes. |
| Subir el proyecto a GitHub. | Git rechazó inicialmente el `push` porque el repositorio remoto tenía cambios que no estaban integrados localmente. | Guardé temporalmente los cambios de otros proyectos con `git stash`, integré los cambios remotos y después subí los commits. |

### Mi pregunta-brújula cuando me bloqueo

1. ¿Qué espero que haga esta línea?
2. ¿Qué está haciendo realmente? Compruebo los valores mediante mensajes de consola.
3. ¿En qué punto exacto se separan las dos respuestas?

---

## 📊 6. Evidencias de ejecución

### 6.1 Traza de consola

Ejemplo de la tercera ejecución realizada:

```text
[MAIN] Descargando 4 módulos en paralelo...

--> [DL-analitica] prio=1 latencia=1819 ms
--> [DL-sesion] prio=10 latencia=2842 ms
--> [DL-catalogo] prio=10 latencia=2999 ms
--> [DL-portadas] prio=5 latencia=2083 ms

<-- [DL-analitica] LISTO | prio=1
<-- [DL-portadas] LISTO | prio=5
<-- [DL-sesion] LISTO | prio=10
<-- [DL-catalogo] LISTO | prio=10

====================================
[MAIN] Todos los módulos listos
[MAIN] LOGIN HABILITADO
====================================
Tiempo total real: 3010 ms
Duración del más lento: 2999 ms
Suma de todas: 9743 ms
```

### 6.2 Tabla de ejecuciones

#### Prueba B

| Ejecución | Latencias: analítica, sesión, catálogo y portadas | Orden de finalización | Tiempo total | Más lento | Suma |
|---|---|---|---:|---:|---:|
| 1 | 1486, 2895, 3755, 2825 ms | Analítica → Portadas → Sesión → Catálogo | 3939 ms | 3755 ms | 10961 ms |
| 2 | 1266, 3975, 3860, 2866 ms | Analítica → Sesión → Catálogo → Portadas | 3986 ms | 3975 ms | 11967 ms |
| 3 | 1819, 2842, 2999, 2083 ms | Analítica → Portadas → Sesión → Catálogo | 3010 ms | 2999 ms | 9743 ms |

### 6.3 Solo Prueba B · Análisis breve del planificador

Después de realizar las tres ejecuciones, se observa que los módulos de prioridad alta no han terminado necesariamente antes que los demás. El módulo de analítica, que tiene prioridad mínima, ha terminado primero en las tres pruebas porque sus latencias aleatorias han sido las más bajas. Los módulos de catálogo y sesión tienen prioridad máxima, pero eso no garantiza que finalicen antes. El tiempo total real se aproxima a la duración del módulo más lento porque las tareas se ejecutan simultáneamente. La suma de las latencias es mayor porque los tiempos de las descargas se solapan. Las prioridades de Java son orientativas y su efecto depende también de la JVM y del sistema operativo. Por tanto, en estas pruebas la latencia ha tenido más influencia en el orden de finalización que la prioridad asignada.

### ¿Acerté en mis predicciones del apartado 2?

La predicción sobre el tiempo total fue correcta, ya que los resultados se acercan a la duración del módulo más lento. También acerté al pensar que el orden de finalización podía cambiar. En cambio, las ejecuciones muestran que los hilos de prioridad alta no terminan necesariamente antes. La prioridad no garantiza el orden de llegada y las latencias aleatorias influyen directamente en el resultado.

---

## 🧠 7. Preguntas de metacognición técnica

### 7.1 Preguntas comunes (Prueba A y Prueba B)

**1. ¿Por qué el tiempo total es el del hilo más lento y no la suma de todos?**

Porque los cuatro hilos trabajan al mismo tiempo. Cuando se utiliza `start()`, el hilo principal no espera a que termine cada tarea para iniciar la siguiente. Las descargas se solapan, por lo que el tiempo total depende principalmente de la que más tarda. En mis resultados, por ejemplo, en la tercera ejecución la suma fue de 9743 ms, mientras que el tiempo total fue de 3010 ms.

**2. ¿Qué diferencia real hay entre llamar a `start()` y llamar a `run()`?**

`start()` inicia un hilo independiente y permite que el trabajo se ejecute concurrentemente. En cambio, llamar directamente a `run()` ejecuta el método como una llamada normal dentro del hilo que lo invoca. Si utilizase `run()` directamente desde `main`, las tareas se ejecutarían una detrás de otra y no habría el mismo paralelismo.

**3. ¿Qué pasaría si quitaras los `join()`? ¿Y si pusieras el `join()` justo después de cada `start()`?**

Si quitase los `join()`, el hilo principal podría imprimir el mensaje de login antes de que terminasen todas las descargas. Si colocase un `join()` justo después de cada `start()`, el programa esperaría a que terminase cada tarea antes de iniciar la siguiente, perdiendo gran parte de la concurrencia. Por eso es mejor iniciar primero todos los hilos y después esperar por ellos.

**4. ¿Qué diferencias hay entre trabajar con duraciones fijas y con latencias aleatorias?**

Con duraciones fijas es más sencillo comparar los resultados porque cada tarea tarda siempre lo mismo. Con latencias aleatorias las ejecuciones son más variadas y el orden de finalización puede cambiar. Esto hace que las pruebas se parezcan más a una situación donde cada descarga puede tardar un tiempo diferente.

**5. El orden en que terminan los hilos puede cambiar entre ejecuciones. ¿A qué se debe? ¿Podrías garantizar un orden concreto?**

Se debe a que los hilos se ejecutan concurrentemente, tienen latencias diferentes y el sistema operativo decide cómo repartir el tiempo de ejecución. No se puede garantizar un orden concreto únicamente creando los hilos o asignándoles prioridades. Si necesitase un orden fijo, tendría que utilizar mecanismos de sincronización o ejecutar las tareas de forma secuencial según el orden requerido.

**6. ¿Qué problema te costó más depurar y cómo lo resolviste?**

Uno de los problemas fue corregir errores de estructura al copiar el código, como clases duplicadas, imports colocados en sitios incorrectos y caracteres extraños. El síntoma era que el proyecto no compilaba. Revisando las clases y organizando correctamente las declaraciones pude localizar los errores. Para evitarlo, es importante comprobar el código después de cada cambio y no copiar fragmentos sin revisar su formato.

**7. Si en lugar de cuatro tareas fueran 500, ¿seguiría teniendo sentido crear un `Thread` por tarea? ¿Qué problema crees que aparecería?**

No sería lo más recomendable. Crear 500 hilos puede consumir muchos recursos, aumentar el cambio de contexto y provocar una sobrecarga de memoria y planificación. En una aplicación real utilizaría un `ExecutorService` con un grupo limitado de hilos para controlar cuántas tareas se ejecutan simultáneamente.

### 7.2 Solo Prueba B · ArrayList y prioridades

**11. ¿Qué dificultad tuviste al gestionar el `ArrayList<Thread>`?**

La principal dificultad es organizar correctamente los hilos y no mezclar su creación, inicio y espera. Es necesario utilizar dos bucles: uno para iniciar todos los hilos y otro para esperar a que terminen mediante `join()`. Si se hiciera todo en un único bucle, se podría acabar esperando a cada hilo antes de iniciar el siguiente. Para añadir un quinto módulo bastaría con añadir su tarea, su nombre y su prioridad a las estructuras correspondientes, manteniendo los bucles generales.

**12. ¿Qué diferencias observaste al asignar prioridades distintas en tu sistema operativo?**

En las tres ejecuciones, analítica terminó primero a pesar de tener prioridad mínima. Los módulos de prioridad máxima no fueron siempre los primeros en finalizar. Esto demuestra que en mis pruebas la prioridad no determinó el orden de finalización y que las latencias aleatorias tuvieron una influencia importante.

**13. Si no viste diferencia (o fue pequeña), ¿a qué crees que se debe?**

Creo que se debe a que las tareas pasan la mayor parte del tiempo dormidas mediante `Thread.sleep()`. Durante ese tiempo no están compitiendo activamente por utilizar la CPU. Además, mi equipo puede ejecutar varios hilos y la JVM y el sistema operativo gestionan las prioridades según su propia planificación. Por eso la prioridad asignada no produce necesariamente una diferencia visible.

**14. ¿Puede fiarse un programador de las prioridades de Java para garantizar que algo se ejecute antes?**

No. Las prioridades son una sugerencia para el planificador, no una garantía de ejecución ni de finalización. Su efecto depende de la JVM, del sistema operativo y de los recursos disponibles. Si una aplicación necesita garantizar que una tarea ocurra antes que otra, debe utilizar mecanismos de sincronización y coordinación, no confiar únicamente en las prioridades.

**15. (Si hiciste la ampliación) ¿Por qué el programa termina aunque el hilo de «Cargando…» nunca acabe por sí mismo?**

No realizada. Si se implementase, el hilo de carga tendría que marcarse como demonio mediante `setDaemon(true)` antes de iniciarlo. Los hilos demonio no impiden que la JVM termine cuando todos los hilos normales han finalizado.

---

## 🌍 8. Mundo real

Este tipo de programación se utiliza en aplicaciones móviles, plataformas de streaming y servicios en la nube. Por ejemplo, una plataforma de vídeo puede necesitar cargar simultáneamente los datos del usuario, el catálogo de contenidos, las imágenes y la información de uso.

La solución de este reto se parece a ese funcionamiento porque cada módulo se procesa en un hilo independiente. Así se evita esperar a que termine una descarga para comenzar la siguiente. También es importante esperar a que estén disponibles los datos necesarios antes de permitir que el usuario acceda a determinadas funciones.

---
| Puedo explicar a un compañero… | 🔴 No | 🟡 Más o menos | 🟢 Sí |
|---|:---:|:---:|:---:|
| Qué es un `Runnable` y qué es un `Thread` | ☐ | ☐ | 🟢 |
| Por qué `start()` no espera a que el hilo acabe | ☐ | 🟡 | ☐ |
| Qué hace `join()` y dónde lo he puesto | ☐ | ☐ | 🟢 |
| Qué estados tiene un hilo y cuándo cambia | ☐ | 🟡 | ☐ |
| Por qué el orden de los hilos no es siempre el mismo | ☐ | ☐ | 🟢 |
| Qué son las prioridades y qué garantizan (o no) | ☐ | 🟡 | ☐ |
| Qué es un hilo demonio | ☐ | 🟡 | ☐ |

**Lo que haría diferente si empezara de nuevo:**

Organizaría las clases y los nombres de los hilos desde el principio y comprobaría que el proyecto compila después de cada modificación. También prepararía las pruebas y anotaría los resultados desde la primera ejecución.

**Lo que todavía no tengo claro y quiero preguntar en clase:**

Me gustaría profundizar en cómo traduce cada sistema operativo las prioridades de Java y en qué condiciones se puede observar realmente una diferencia en el comportamiento de los hilos.

---

## 🤝 10. Declaración de autoría y aprendizaje

Esta sección debe completarse y confirmarse personalmente por el alumno o alumna.

- [ ] Confirmo que he diseñado, programado y depurado este código aplicando mi propio razonamiento, y que puedo explicarlo línea a línea.
- [ ] Confirmo que el vídeo de defensa técnica dura menos de 3 minutos y está grabado con mi propia voz.


---

## 📂 11. Estructura del proyecto

```text
UDITflix/
│
├── .gitignore
├── pom.xml
│
└── src/
    └── main/
        └── java/
            └── org/
                └── example/
                    ├── MainUDITflix.java
                    └── TareaDescarga.java
```

**Archivos principales:**

- `MainUDITflix.java`: crea y configura los cuatro hilos, los inicia, espera su finalización y calcula las estadísticas.
- `TareaDescarga.java`: implementa `Runnable`, genera la latencia aleatoria, simula la descarga y muestra los mensajes de inicio y finalización.
- `pom.xml`: contiene la configuración del proyecto Maven.
- `.gitignore`: indica qué archivos y carpetas no deben incluirse en el repositorio.
- `README.md`: documentación del proyecto y resultados de las pruebas.
