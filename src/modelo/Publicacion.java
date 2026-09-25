package modelo;

public class Publicacion {

    private String autor;
    private String contenido;
    private boolean verdadera;

    public Publicacion(String autor, String contenido, boolean verdadera) {
        this.autor = autor;
        this.contenido = contenido;
        this.verdadera = verdadera;
    }

    public String getAutor() {
        return autor;
    }

    public String getContenido() {
        return contenido;
    }

    public boolean isVerdadera() {
        return verdadera;
    }
}
