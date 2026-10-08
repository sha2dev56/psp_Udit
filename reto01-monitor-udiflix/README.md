# Reto 1 · Monitor del catálogo de UDITflix

**Módulo:** 0490 · Programación de Servicios y Procesos
**Autor/a:** *Shaghayegh Asghari*
**Reto:** ☐ Reto A (vídeos) · ☑ Reto B (contenidos)
**Tecnología:** Java + `ProcessBuilder` (procesos del sistema operativo)
**RA vinculado:** RA1 · Programación de aplicaciones compuestas por varios procesos

---

## 📺 Qué es esta app

Esta aplicación es un programa de consola que simula un monitor del catálogo de UDITflix.

El programa comprueba cinco categorías diferentes de contenidos:

* Series
* Películas
* Documentales
* Anime
* Infantil

Para cada contenido se guarda su nombre y una dirección IP dentro de una matriz de dos dimensiones. Después, mediante un bucle `for`, el programa recorre todos los contenidos y ejecuta un proceso externo utilizando `ping`.

El programa muestra el PID de cada proceso y espera a que termine mediante `waitFor()`. Después comprueba el código de salida del proceso para determinar si el servicio está activo o si se ha producido un error.

En este proyecto se utiliza `127.0.0.1` para representar una dirección disponible y `192.0.2.1` para simular una dirección que no responde.

### Captura de la ejecución

<img width="429" height="578" alt="image" src="https://github.com/user-attachments/assets/ef27c5c3-50c1-4231-baf1-41ebb3692dd3" />

### 1. Objetivo del reto

El reto me pide crear un programa que compruebe cinco contenidos diferentes de UDITflix utilizando procesos externos.

Tengo que guardar los contenidos y sus direcciones en una matriz, recorrerlos con un `for`, ejecutar `ping` mediante `ProcessBuilder`, obtener el PID y esperar a que termine el proceso para comprobar su resultado.

El enunciado establece que los cinco contenidos son Series, Películas, Documentales, Anime e Infantil.

### 2. ¿Qué parte de la píldora de clase creo que voy a reutilizar?

La parte que voy a reutilizar es la creación de un proceso externo mediante `ProcessBuilder`.

También reutilizo conceptos como `start()`, `Process`, `pid()` y `waitFor()`.

La diferencia es que en lugar de trabajar con un único proceso, en este reto tengo que realizar la comprobación de cinco contenidos.

### 3. ¿Qué parte me da más respeto o no sé por dónde empezar?

La parte que más me cuesta es entender cómo organizar los cinco contenidos dentro de una matriz y después utilizar un único bucle para realizar la misma comprobación sobre todos ellos.

También tengo que entender correctamente qué devuelve `waitFor()` y cómo utilizar ese resultado.

### 4. Mi plan en 3-4 pasos, en orden

1. Crear una matriz con los cinco contenidos y sus direcciones.
2. Recorrer la matriz mediante un bucle `for`.
3. Crear un `ProcessBuilder` para ejecutar `ping` y lanzar el proceso.
4. Mostrar el PID, esperar con `waitFor()` y mostrar el estado del servicio.

### 5. Predicción

Si todas las direcciones fueran `127.0.0.1`, esperaba que los cinco contenidos aparecieran como activos.

Si todas las direcciones fueran inexistentes o no respondieran, esperaba que los procesos terminaran con un código diferente de cero y que los contenidos aparecieran con error.

**Comprobación final de mi predicción:** *(completar después de ejecutar el programa).*

---

## 🎯 Objetivo del reto

El objetivo del reto es pasar de la ejecución de un proceso individual a la comprobación de varios contenidos mediante una matriz y un bucle.

En mi programa utilizo:

* `ProcessBuilder` para preparar el comando `ping`.
* `start()` para iniciar el proceso.
* `Process` para representar el proceso externo.
* `pid()` para obtener el identificador del proceso.
* `waitFor()` para esperar a que termine.
* Una matriz `String[][]` para guardar los contenidos.
* Un bucle `for` para recorrer la matriz.

El enunciado pide guardar los cinco contenidos en una matriz, recorrerlos con un `for`, crear el proceso externo, mostrar el PID, esperar a su finalización y determinar el estado.

---

## 🛠️ Componentes y conceptos utilizados

| Componente / concepto           | Para qué se usa en esta app                                         | Con mis palabras                                                                         |
| ------------------------------- | ------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| `ProcessBuilder`                | Prepara el comando `ping` que se ejecutará en el sistema operativo. | Me permite preparar el comando que quiero ejecutar como proceso.                         |
| `start()`                       | Inicia el proceso y devuelve un objeto `Process`.                   | Es el método que hace que el proceso empiece a ejecutarse.                               |
| `Process`                       | Representa el proceso que se ha iniciado.                           | Es el objeto que representa el proceso externo que estoy controlando.                    |
| `pid()`                         | Obtiene el identificador del proceso.                               | Es el número que identifica al proceso dentro del sistema operativo.                     |
| `getInputStream()`              | Permite leer la salida del proceso.                                 | No se utiliza en mi versión actual del programa.                                         |
| `BufferedReader` + `readLine()` | Permiten leer la salida línea a línea.                              | No se utilizan en mi versión actual del programa.                                        |
| `waitFor()`                     | Espera hasta que el proceso termina y devuelve su código de salida. | Hace que mi programa espere hasta que termine `ping`.                                    |
| Matriz `String[][]`             | Guarda el nombre y la dirección de cada contenido.                  | Es una tabla donde cada fila contiene un contenido y su dirección.                       |
| Bucle `for`                     | Repite la comprobación para todos los contenidos.                   | Me permite realizar la misma comprobación cinco veces sin repetir manualmente el código. |

> **Nota:** el enunciado incluye `getInputStream()` y `BufferedReader` entre los elementos obligatorios. Esta versión del código todavía no los utiliza.

### ¿Qué contiene cada posición de mi matriz?

```text
matriz[i][0] → nombre o categoría del contenido
matriz[i][1] → dirección IP que se comprueba
```

En mi código:

```java
String categoria = contenidos[i][0];
String ip = contenidos[i][1];
```

La matriz es:

```java
String[][] contenidos = {
        {"Series", "127.0.0.1"},
        {"Películas", "127.0.0.1"},
        {"Documentales", "127.0.0.1"},
        {"Anime", "192.0.2.1"},
        {"Infantil", "127.0.0.1"}
};
```

---

## 🚀 Cómo ejecutar el proyecto

1. Abrir el proyecto en IntelliJ IDEA.
2. Esperar a que IntelliJ termine de cargar el proyecto.
3. Ejecutar la clase `Main`.
4. Observar la consola.
5. Comprobar el PID y el estado de cada contenido.

### Sistema operativo

El código está preparado para utilizar `ping` en Windows:

```text
ping -n 1 dirección
```

Por ejemplo:

```text
ping -n 1 127.0.0.1
```

**Sistema operativo utilizado:** *(completar)*

En Windows se utiliza `-n` para indicar el número de paquetes que se envían. El enunciado indica que en Linux/Mac habría que utilizar `-c`.

---

## 🔍 Mientras programo: mi diario de decisiones

| Qué intentaba                         | Qué pasó realmente                                     | Qué hice / qué aprendí                                                   |
| ------------------------------------- | ------------------------------------------------------ | ------------------------------------------------------------------------ |
| Ejecutar `ping` para cada contenido   | El proceso se ejecutaba y mostraba un PID.             | Entendí que `start()` inicia el proceso y `pid()` permite identificarlo. |
| Comprobar el resultado del proceso    | `waitFor()` devolvía un código de salida.              | Utilicé ese código para determinar si el proceso terminó correctamente.  |
| Recorrer los cinco contenidos         | Necesitaba evitar repetir cinco veces el mismo código. | Utilicé una matriz y un bucle `for`.                                     |
| *(Añadir una dificultad real)*        | *(Qué ocurrió)*                                        | *(Cómo lo solucioné)*                                                    |
| *(Añadir otra dificultad si la hubo)* | *(Qué ocurrió)*                                        | *(Qué aprendí)*                                                          |

### Mi pregunta-brújula cuando me bloqueo

1. ¿Qué espero que haga esta línea?
2. ¿Qué está haciendo realmente?
3. ¿Qué valor puedo imprimir para comprobarlo?
4. ¿En qué punto exacto se separan las dos respuestas?

---

## 🧭 De la píldora al reto: cómo di el salto

### ¿Qué tenía la píldora que ya no me sirve tal cual?

La píldora trabajaba con la ejecución de un proceso. En este reto necesito realizar la misma operación para cinco contenidos diferentes.

Por eso tuve que organizar los datos y utilizar un bucle que repita la comprobación.

### ¿Qué he tenido que añadir para repetirlo cinco veces? ¿Por qué esa estructura y no otra?

He añadido una matriz bidimensional:

```java
String[][] contenidos
```

En ella guardo el nombre y la dirección de cada contenido.

Después utilizo un `for` para recorrer todas las filas.

Elegí esta estructura porque permite tener todos los contenidos organizados y utilizar el mismo código para comprobar cada uno.

### ¿Qué parte del código es exactamente igual en todas las vueltas del bucle y qué parte cambia?

La parte que se mantiene igual es la creación del `ProcessBuilder`, la ejecución del proceso, la obtención del PID y la espera mediante `waitFor()`.

Lo que cambia en cada vuelta es la información obtenida de la matriz:

```java
String categoria = contenidos[i][0];
String ip = contenidos[i][1];
```

### Si mañana UDITflix tuviera 500 elementos en lugar de 5, ¿qué tendría que cambiar en mi código?

Tendría que añadir los nuevos contenidos a la matriz.

El bucle no necesitaría cambiar porque utiliza:

```java
i < contenidos.length
```

Por tanto, recorrería todas las filas que tuviera la matriz.

---

## 🧠 Qué he aprendido

### Hilo vs. proceso

Un proceso es una ejecución independiente gestionada por el sistema operativo. En mi programa, cada ejecución de `ping` se realiza mediante un proceso externo.

### PID

El PID es el identificador del proceso en el sistema operativo. Cada vez que se ejecuta un nuevo proceso, el sistema operativo le asigna un identificador.

### `start()` vs. `waitFor()`

`start()` sirve para iniciar el proceso.

`waitFor()` sirve para esperar hasta que el proceso termina y obtener su código de salida.

Por tanto, iniciar un proceso y esperar a que termine son dos operaciones diferentes.

### Código de salida

El código de salida indica cómo terminó el proceso.

En mi programa compruebo:

```java
if (codigo == 0)
```

Si el código es `0`, muestro:

```text
ESTADO : SERVICIO ACTIVO
```

Si es diferente de `0`, muestro:

```text
ESTADO : SERVICIO CON ERROR
```

### Lo que mi programa decide sobre ACTIVO / CAÍDO se basa en...

Mi programa basa la decisión en el código de salida que devuelve el comando `ping`.

Por tanto, es una simulación de disponibilidad. No está comprobando directamente que exista un servicio UDITflix real, sino que comprueba el resultado del `ping` a la dirección indicada.

---

## 🐞 Dificultades y cómo las resolví

### Dificultad 1: recorrer los cinco contenidos

* **Qué síntoma vi:** necesitaba ejecutar la misma comprobación para diferentes contenidos.
* **Cuál era la causa real:** no quería repetir manualmente todo el código cinco veces.
* **Cómo la encontré:** utilicé una matriz junto con un bucle `for`.
* **Cómo evitaré que me vuelva a pasar:** intentaré organizar los datos en una estructura antes de repetir código.

### Dificultad 2: controlar el final del proceso

* **Qué síntoma vi:** necesitaba saber cuándo terminaba `ping`.
* **Cuál era la causa real:** lanzar un proceso y esperar a que termine son operaciones diferentes.
* **Cómo la encontré:** utilicé `start()` para iniciarlo y `waitFor()` para esperar.
* **Cómo evitaré que me vuelva a pasar:** recordaré la diferencia entre iniciar y esperar un proceso.

---

## 🪞 Autoevaluación

| Puedo explicar a un compañero...       | 🔴 No | 🟡 Más o menos | 🟢 Sí |
| -------------------------------------- | :---: | :------------: | :---: |
| Qué hace `ProcessBuilder`              |   ☐   |        ☐       |   *   |
| Qué hace `start()` y por qué no espera |   ☐   |        ☐       |   *   |
| Qué representa el PID                  |   ☐   |        ☐       |   *  |
| Para qué sirve `getInputStream()`      |   ☐   |        *      |   ☐   |
| Qué hace `waitFor()` y qué devuelve    |   ☐   |        ☐       |   *   |
| Qué hay en cada posición de la matriz  |   ☐   |        *       |   ☐   |
| Qué hace el `for` en mi programa       |   ☐   |        *       |   ☐   |

### Mi predicción del principio, ¿acerté?

*(Completar después de ejecutar el programa.)*

### Lo que haría diferente si empezara de nuevo

Intentaría comprobar desde el principio todos los requisitos del enunciado y probaría cada contenido por separado antes de ejecutar la comprobación completa.

### Lo que todavía no tengo claro y quiero preguntar en clase

*(Escribir aquí la duda real que quede después de terminar el proyecto.)*

---

## 🤝 Declaración de autoría

Este reto no permite herramientas de generación de código mediante IA.

Consulté únicamente:

* la píldora realizada en clase;
* mis apuntes;
* la documentación de Java;
* IntelliJ IDEA.

☐ Confirmo que el código es mío y que puedo explicarlo línea a línea.

---

## 📂 Estructura del proyecto

```text
src/
└── main/
    └── java/
        └── org/
            └── example/
                └── Main.java

README.md
```

---

## 🔗 Enlace

* **GitHub:** https://github.com/sha2dev56/psp_Udit/tree/main/reto01-monitor-udiflix **
