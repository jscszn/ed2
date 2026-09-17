package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Conjunto de publicaciones de ejemplo que van llegando al jugador.
 * La selección es aleatoria (componente aleatorio del laboratorio, sección 8):
 * cada partida presenta las publicaciones en un orden distinto.
 */
public class BancoPublicaciones {

    private List<Publicacion> publicaciones;
    private Random random;

    public BancoPublicaciones() {
        this.random = new Random();
        this.publicaciones = new ArrayList<>();
        cargarPublicaciones();
    }

    private void cargarPublicaciones() {
        publicaciones.add(new Publicacion("Ciudadano anónimo",
                "El candidato Juan quiere cerrar el colegio.", false));
        publicaciones.add(new Publicacion("Ciudadano anónimo",
                "El alcalde actual está robando dinero.", false));
        publicaciones.add(new Publicacion("Ciudadano anónimo",
                "Mañana cerrarán todos los parques.", false));
        publicaciones.add(new Publicacion("Ciudadano anónimo",
                "La candidata María prometió un puente que no está en su plan de gobierno.", false));
        publicaciones.add(new Publicacion("Prensa Civitas",
                "El debate entre los cuatro candidatos se transmitirá el viernes.", true));
        publicaciones.add(new Publicacion("Secretaría de Educación",
                "Las jornadas de vacunación escolar continúan esta semana.", true));
        publicaciones.add(new Publicacion("Alcaldía de Ciudad Nova",
                "El cronograma oficial de las elecciones ya está publicado.", true));
    }

    public Publicacion siguiente() {
        return publicaciones.get(random.nextInt(publicaciones.size()));
    }
}
