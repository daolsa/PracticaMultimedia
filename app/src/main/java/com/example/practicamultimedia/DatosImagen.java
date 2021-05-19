package com.example.practicamultimedia;

public class DatosImagen {
    private boolean clicked;
    private int id_pareja;

    public DatosImagen(){}

    public DatosImagen(boolean clicked, int id_pareja){
        this.clicked = clicked;
        this.id_pareja = id_pareja;

    }
    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public int getId_pareja() {
        return id_pareja;
    }

    public void setId_pareja(int id_pareja) {
        this.id_pareja = id_pareja;
    }
}
