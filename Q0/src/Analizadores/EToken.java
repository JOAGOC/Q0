package Analizadores;

public enum EToken {
    COMPONENTE("Componente"),
    UNIDAD("Unidad"),
    ACCION("Acción"),
    PREFIJO("Prefijo"),
    OPERADOR("Operador"),
    COMPUERTA("Compuerta"),
    BOOLEANO("Booleano"),
    CONFIGURACION("Configuración"),
    CICLO("Ciclo"),
    CONDICION("Condición"),
    ESTRUCTURA("Estructura"),
    OPERADOR_COMPARADOR("Operador comparador"),
    TIPO_DE_DATO("Tipo de dato"),
    IDENTIFICADOR("Identificador"),
    NUMERO_DECIMAL("Número Decimal"),
    NUMERO_ENTERO("Número Entero");
    
    private String token;

    private EToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}