package modelo;

import java.util.ArrayList;
import java.util.List;

public class Nodo {

    private String pregunta; // si es null, el nodo es una acción (hoja)
    private String etiquetaRama; // con qué opción se llegó aquí desde el padre
    private List<Nodo> hijos;

    private Impacto impactoSiVerdadera;
    private Impacto impactoSiFalsa;

    public Nodo(String pregunta) {
        this.pregunta = pregunta;
        this.hijos = new ArrayList<>();
    }

    public void agregarHijo(Nodo hijo, String etiquetaRama) {
        hijo.etiquetaRama = etiquetaRama;
        this.hijos.add(hijo);
    }

    public boolean esHoja() {
        return hijos.isEmpty();
    }

    public Impacto obtenerImpacto(boolean publicacionVerdadera) {
        return publicacionVerdadera ? impactoSiVerdadera : impactoSiFalsa;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getEtiquetaRama() {
        return etiquetaRama;
    }

    public List<Nodo> getHijos() {
        return hijos;
    }

    public Impacto getImpactoSiVerdadera() {
        return impactoSiVerdadera;
    }

    public void setImpactoSiVerdadera(Impacto impactoSiVerdadera) {
        this.impactoSiVerdadera = impactoSiVerdadera;
    }

    public Impacto getImpactoSiFalsa() {
        return impactoSiFalsa;
    }

    public void setImpactoSiFalsa(Impacto impactoSiFalsa) {
        this.impactoSiFalsa = impactoSiFalsa;
    }
}
