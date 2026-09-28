package org.example;

import java.util.ArrayList;

public class Plataforma {

    private ArrayList<Episodio> catalogo;

    public Plataforma () {
        // MUY IMPORTANTE: Si no inicializamos la lista con new
        // Java lanzará un error NullPointerException al intentar usarla
        this.catalogo = new ArrayList<>();
    }
    // METODO AGREGAR:
    // Exigimos que el parámetro sea estrictamente de tipo Episodio
    // la 'e' es solo una variable temporal para usarla en la linea de abajo
    public void agregarEpisodio(Episodio e) {
        this.catalogo.add(e);
    }
    //METODO PROCESAR:
    // Movemos el bucle que estaba feo en el Main hacia su gestor natural
    public void procesarCatalogo(){
        for (Episodio ep : this.catalogo){
            ep.procesar();
        }
    }
}