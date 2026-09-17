package modelo;

/**
 * Estado de Ciudad Nova (sección 10 del laboratorio). Cada Impacto
 * producido por una hoja del árbol de decisiones se aplica aquí.
 */
public class Ciudad {

    private int informacionVerificada;
    private int confianzaCiudadana;
    private int convivencia;
    private int bienestarDigital;
    private int desinformacion;
    private int conflictos;

    public Ciudad() {
        this.informacionVerificada = 82;
        this.confianzaCiudadana = 74;
        this.convivencia = 88;
        this.bienestarDigital = 79;
        this.desinformacion = 21;
        this.conflictos = 14;
    }

    public void aplicarImpacto(Impacto impacto) {
        informacionVerificada = limitar(informacionVerificada + impacto.getInformacionVerificada());
        confianzaCiudadana = limitar(confianzaCiudadana + impacto.getConfianza());
        convivencia = limitar(convivencia + impacto.getConvivencia());
        bienestarDigital = limitar(bienestarDigital + impacto.getBienestarDigital());
        desinformacion = limitar(desinformacion + impacto.getDesinformacion());
        conflictos = limitar(conflictos + impacto.getConflictos());
    }

    private int limitar(int valor) {
        return Math.max(0, Math.min(100, valor));
    }

    public int getInformacionVerificada() {
        return informacionVerificada;
    }

    public int getConfianzaCiudadana() {
        return confianzaCiudadana;
    }

    public int getConvivencia() {
        return convivencia;
    }

    public int getBienestarDigital() {
        return bienestarDigital;
    }

    public int getDesinformacion() {
        return desinformacion;
    }

    public int getConflictos() {
        return conflictos;
    }
}
