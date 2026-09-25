package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// árbol general (no binario), un nodo puede tener más de 2 hijos
// la raíz tiene 3: Compartir, Verificar, Ignorar (y Verificar abre otra pregunta con 2 más)
public class ArbolDecision {

    private Nodo raiz;

    public ArbolDecision() {
        this.raiz = new Nodo("¿Qué quieres hacer con esta publicación?");
        construirArbolPorDefecto(this.raiz);
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public Nodo insertarPregunta(Nodo padre, String etiquetaRama, String pregunta) {
        Nodo nodo = new Nodo(pregunta);
        padre.agregarHijo(nodo, etiquetaRama);
        return nodo;
    }

    public Nodo insertarAccion(Nodo padre, String etiquetaRama, Impacto impactoSiVerdadera, Impacto impactoSiFalsa) {
        Nodo nodo = new Nodo(null);
        nodo.setImpactoSiVerdadera(impactoSiVerdadera);
        nodo.setImpactoSiFalsa(impactoSiFalsa);
        padre.agregarHijo(nodo, etiquetaRama);
        return nodo;
    }

    public boolean eliminarRama(Nodo padre, String etiquetaRama) {
        if (padre == null || etiquetaRama == null) {
            return false;
        }
        return padre.getHijos().removeIf(hijo -> etiquetaRama.equalsIgnoreCase(hijo.getEtiquetaRama()));
    }

    public List<Nodo> preOrden() {
        List<Nodo> resultado = new ArrayList<>();
        preOrden(raiz, resultado);
        return resultado;
    }

    private void preOrden(Nodo nodo, List<Nodo> resultado) {
        if (nodo == null) {
            return;
        }
        resultado.add(nodo);
        for (Nodo hijo : nodo.getHijos()) {
            preOrden(hijo, resultado);
        }
    }

    public List<Nodo> porNiveles() {
        List<Nodo> resultado = new ArrayList<>();
        Queue<Nodo> cola = new LinkedList<>();
        if (raiz != null) {
            cola.add(raiz);
        }
        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            resultado.add(actual);
            cola.addAll(actual.getHijos());
        }
        return resultado;
    }

    public List<Nodo> obtenerHojas() {
        List<Nodo> hojas = new ArrayList<>();
        obtenerHojas(raiz, hojas);
        return hojas;
    }

    private void obtenerHojas(Nodo nodo, List<Nodo> hojas) {
        if (nodo == null) {
            return;
        }
        if (nodo.esHoja()) {
            hojas.add(nodo);
            return;
        }
        for (Nodo hijo : nodo.getHijos()) {
            obtenerHojas(hijo, hojas);
        }
    }

    public int altura(Nodo nodo) {
        if (nodo == null || nodo.esHoja()) {
            return 1;
        }
        int maximo = 0;
        for (Nodo hijo : nodo.getHijos()) {
            maximo = Math.max(maximo, altura(hijo));
        }
        return maximo + 1;
    }

    public Nodo avanzar(Nodo actual, String etiquetaRama) {
        if (actual == null || etiquetaRama == null) {
            return null;
        }
        for (Nodo hijo : actual.getHijos()) {
            if (hijo.getEtiquetaRama().equalsIgnoreCase(etiquetaRama)) {
                return hijo;
            }
        }
        return null;
    }

    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        imprimir(raiz, 0, sb);
        return sb.toString();
    }

    private void imprimir(Nodo nodo, int nivel, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        sb.append("  ".repeat(nivel));
        if (nodo.getEtiquetaRama() != null) {
            sb.append("[").append(nodo.getEtiquetaRama()).append("] ");
        }
        sb.append(nodo.esHoja() ? "(acción final)" : nodo.getPregunta()).append("\n");
        for (Nodo hijo : nodo.getHijos()) {
            imprimir(hijo, nivel + 1, sb);
        }
    }

    private void construirArbolPorDefecto(Nodo raiz) {
        // compartir sin verificar es la opción más arriesgada
        insertarAccion(raiz, "Compartir",
                new Impacto("La compartiste y era cierta: la comunidad se mantuvo informada.",
                        4, 3, 4, 1, 0, 0),
                new Impacto("La compartiste sin verificar y era falsa: ayudaste a propagar un rumor.",
                        -6, -8, -6, -2, 12, 6));

        // verificar abre la subpregunta de si es verdadera o no
        Nodo verificar = insertarPregunta(raiz, "Verificar", "¿La publicación es verdadera?");
        insertarAccion(verificar, "Sí",
                new Impacto("Verificaste, era cierta, y la compartiste con confianza.",
                        10, 6, 5, 2, -2, -1),
                new Impacto("Verificaste, era cierta, y la compartiste con confianza.",
                        10, 6, 5, 2, -2, -1));
        insertarAccion(verificar, "No",
                new Impacto("Verificaste, era falsa, y la reportaste a tiempo.",
                        8, 4, 2, 1, -10, -3),
                new Impacto("Verificaste, era falsa, y la reportaste a tiempo.",
                        8, 4, 2, 1, -10, -3));

        // ignorar no arriesga pero tampoco ayuda a la ciudad
        insertarAccion(raiz, "Ignorar",
                new Impacto("La ignoraste. La ciudad no se benefició, pero tampoco te arriesgaste.",
                        0, -1, -1, 1, 3, 0),
                new Impacto("La ignoraste. La ciudad no se benefició, pero tampoco te arriesgaste.",
                        0, -1, -1, 1, 3, 0));
    }
}
