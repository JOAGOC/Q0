package Analizadores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Analizadores.EToken.*;

public class PalabrasReservadas {

    public static void iniciarPalabrasReservadas() {
        inicializarTablaDeOperadores();
        inicializarTablaDeComponentes();
        inicializarTablaDePrefijos();
        inicializarTablaDeUnidades();
        inicializarTablaDeAcciones();
        inicializarTablaBooleana();
        inicializarTablaConfiguración();
        inicializarTablaCiclo();
        inicializarTablaControl();
        inicializarTablaComparador();
        inicializarTablaDatos();
        inicializarSeparadores();
    }

    public static Map<String, EToken> tablaComponentes;

    public static void inicializarTablaDeComponentes() {
        if (tablaComponentes != null)
            return;
        tablaComponentes = new HashMap<String, EToken>();
        tablaComponentes.put("LED", COMPONENTE);
        tablaComponentes.put("RELE", COMPONENTE);
        tablaComponentes.put("POTENCIOMETRO", COMPONENTE);
        tablaComponentes.put("TRANSISTOR", COMPONENTE);
        tablaComponentes.put("RESISTENCIA", COMPONENTE);
        tablaComponentes.put("DIODO", COMPONENTE);
        tablaComponentes.put("CAPACITOR", COMPONENTE);
        tablaComponentes.put("CIRCUITO_INTEGRADO", COMPONENTE);
        tablaComponentes.put("SENSOR", COMPONENTE);
        tablaComponentes.put("TRANSFORMADOR", COMPONENTE);
        tablaComponentes.put("SENSOR", COMPONENTE);
        tablaComponentes.put("INTERRUPTOR", COMPONENTE);
        tablaComponentes.put("MOTOR", COMPONENTE);
        tablaComponentes.put("DISPLAY", COMPONENTE);
        tablaComponentes.put("FUENTE", COMPONENTE);
        tablaComponentes.put("CONDUCTORES", COMPONENTE);
        tablaComponentes.put("INDUCTOR", COMPONENTE);
        tablaComponentes.put("SERVO", COMPONENTE);
        tablaComponentes.put("COMPUERTA", COMPONENTE);
        tablaComponentes.put("PIN", COMPONENTE);
        tablaComponentes.put("FUENTE", COMPONENTE);
        tablaComponentes.put("TIERRA", COMPONENTE);
    }

    public static Map<String, EToken> tablaUnidades;

    public static void inicializarTablaDeUnidades() {
        if (tablaUnidades != null)
            return;
        tablaUnidades = new HashMap<String, EToken>();
        tablaUnidades.put("VOLTIO", UNIDAD);
        tablaUnidades.put("AMPERIO", UNIDAD);
        tablaUnidades.put("HERCIO", UNIDAD);
        tablaUnidades.put("OHM", UNIDAD);
        tablaUnidades.put("WATT", UNIDAD);
        tablaUnidades.put("HENRIO", UNIDAD);
        tablaUnidades.put("FARADIO", UNIDAD);
        tablaUnidades.put("JULIO", UNIDAD);
        tablaUnidades.put("SEGUNDO", UNIDAD);
    }

    public static Map<String, EToken> tablaAcciones;

    public static void inicializarTablaDeAcciones() {
        if (tablaAcciones != null)
            return;
        tablaAcciones = new HashMap<String, EToken>();
        tablaAcciones.put("ESPERAR", ACCION);
        tablaAcciones.put("IMPORTAR", ACCION);
        tablaAcciones.put("FUNCION", BLOQUE);
        tablaAcciones.put("CIRCUITO", BLOQUE);
        tablaAcciones.put("INICIAR", ACCION);
        tablaAcciones.put("DETENER", ACCION);
        tablaAcciones.put("TIEMPO", ACCION);
        tablaAcciones.put("LEER", ACCION);
        tablaAcciones.put("ESCRIBIR", ACCION);
        tablaAcciones.put("INTERRUMPIR", INTERRUPCION);
        tablaAcciones.put("CONECTAR", ACCION);
        tablaAcciones.put("CON", AUXILIAR);
        tablaAcciones.put("ENCENDER", ACCION_BOOLEANA);
        tablaAcciones.put("APAGAR", ACCION_BOOLEANA);
        tablaAcciones.put("EN", ACCION);
    }

    public static Map<String, EToken> tablaPrefijos;

    public static void inicializarTablaDePrefijos() {
        if (tablaPrefijos != null)
            return;
        tablaPrefijos = new HashMap<String, EToken>();
        tablaPrefijos.put("PICO", PREFIJO);
        tablaPrefijos.put("NANO", PREFIJO);
        tablaPrefijos.put("MICRO", PREFIJO);
        tablaPrefijos.put("MILI", PREFIJO);
        tablaPrefijos.put("KILO", PREFIJO);
        tablaPrefijos.put("MEGA", PREFIJO);
        tablaPrefijos.put("GIGA", PREFIJO);
    }

    public static Map<String, EToken> tablaOperadores;

    public static void inicializarTablaDeOperadores() {
        if (tablaOperadores != null)
            return;
        tablaOperadores = new HashMap<String, EToken>();
        // '!', '#', '^', '&', '*', '-', '+', '=', '{', '}', '[', ']',
        // '(', ')', '<', '>', '?', '|', '\\', '/', ',', '.', ';', ':', '\'', '"'
        tablaOperadores.put("!", OPERADOR);
        tablaOperadores.put("**", OPERADOR_ARITMETICO);
        tablaOperadores.put("*", OPERADOR_ARITMETICO);
        tablaOperadores.put("++", OPERADOR_UNARIO);
        tablaOperadores.put("--", OPERADOR_UNARIO);
        tablaOperadores.put("/", OPERADOR_ARITMETICO);
        tablaOperadores.put("+", OPERADOR_ARITMETICO);
        tablaOperadores.put("-", OPERADOR_ARITMETICO);
        tablaOperadores.put("=", OPERADOR);
        tablaOperadores.put("(", OPERADOR);
        tablaOperadores.put("{", OPERADOR);
        tablaOperadores.put("[", OPERADOR);
        tablaOperadores.put(")", OPERADOR);
        tablaOperadores.put("}", OPERADOR);
        tablaOperadores.put("]", OPERADOR);
        tablaOperadores.put(";", OPERADOR);
        tablaOperadores.put(":", OPERADOR);
        tablaOperadores.put(",", OPERADOR);
    }

    public static Map<String, EToken> tablaBooleana;

    public static void inicializarTablaBooleana() {
        if (tablaBooleana != null)
            return;
        tablaBooleana = new HashMap<String, EToken>();
        tablaBooleana.put("AND", COMPUERTA);
        tablaBooleana.put("OR", COMPUERTA);
        tablaBooleana.put("XOR", COMPUERTA);
        tablaBooleana.put("NOT", COMPUERTA);
        tablaBooleana.put("NAND", COMPUERTA);
        tablaBooleana.put("NOR", COMPUERTA);
        tablaBooleana.put("XNOR", COMPUERTA);
        tablaBooleana.put("FALSO", BOOLEANO);
        tablaBooleana.put("VERDADERO", BOOLEANO);
        tablaBooleana.put("MAXIMO", BOOLEANO);
        tablaBooleana.put("MINIMO", BOOLEANO);
    }

    public static Map<String, EToken> tablaConfiguración;

    public static void inicializarTablaConfiguración() {
        if (tablaConfiguración != null)
            return;
        tablaConfiguración = new HashMap<String, EToken>();
        tablaConfiguración.put("ALTERNA", CONFIGURACION);
        tablaConfiguración.put("CONTINUA", CONFIGURACION);
        tablaConfiguración.put("POTENCIA", CONFIGURACION);
        tablaConfiguración.put("FRECUENCIA", CONFIGURACION);
        tablaConfiguración.put("SERIE", CONFIGURACION);
        tablaConfiguración.put("PARALELO", CONFIGURACION);
        tablaConfiguración.put("PRINCIPAL", CONFIGURACION);
        tablaConfiguración.put("DIGITAL", CONFIGURACION_CIRCUITO);
        tablaConfiguración.put("ANALOGICO", CONFIGURACION_CIRCUITO);
    }

    public static Map<String, EToken> tablaCiclo;

    public static void inicializarTablaCiclo() {
        if (tablaCiclo != null)
            return;
        tablaCiclo = new HashMap<String, EToken>();
        tablaCiclo.put("REPETIR", CICLO);
        tablaCiclo.put("MIENTRAS", CICLO);
        tablaCiclo.put("HASTA", CICLO);
        tablaCiclo.put("POR", CICLO);
        tablaCiclo.put("PARA", CICLO);
    }

    public static Map<String, EToken> tablaControl;

    public static void inicializarTablaControl() {
        if (tablaControl != null)
            return;
        tablaControl = new HashMap<String, EToken>();
        tablaControl.put("SI", CONDICION_INICIAL);
        tablaControl.put("SI_NO", CONDICION);
        tablaControl.put("ELEGIR", CONDICION_INICIAL);
        tablaControl.put("OPCION", CONDICION);
        tablaControl.put("POR", CONDICION);
        tablaControl.put("DEFECTO", CONDICION);
        tablaControl.put("INICIO#", ESTRUCTURA);
        tablaControl.put("FIN#", ESTRUCTURA);
    }

    public static Map<String, EToken> tablaComparador;

    public static void inicializarTablaComparador() {
        if (tablaComparador != null)
            return;
        tablaComparador = new HashMap<String, EToken>();
        tablaComparador.put("ES", OPERADOR_COMPARADOR);
        tablaComparador.put("IGUAL", OPERADOR_COMPARADOR);
        tablaComparador.put("A", OPERADOR_COMPARADOR);
        tablaComparador.put("MAYOR", OPERADOR_COMPARADOR);
        tablaComparador.put("MENOR", OPERADOR_COMPARADOR);
        tablaComparador.put("QUE", OPERADOR_COMPARADOR);
        tablaComparador.put("DIFERENTE", OPERADOR_COMPARADOR);
    }

    public static Map<String, EToken> tablaDatos;

    public static void inicializarTablaDatos() {
        if (tablaDatos != null)
            return;
        tablaDatos = new HashMap<String, EToken>();
        tablaDatos.put("DECIMAL", TIPO_DE_DATO);
        tablaDatos.put("ENTERO", TIPO_DE_DATO);
        tablaDatos.put("CARACTER", TIPO_DE_DATO);
        tablaDatos.put("CADENA", TIPO_DE_DATO);
        tablaDatos.put("BOOLEANO", TIPO_DE_DATO);
        tablaDatos.put("TABLA", TIPO_DE_DATO);
        tablaDatos.put("COMPONENTE", TIPO_DE_DATO);
    }

    public static ArrayList<Character> separadores;

    public static void inicializarSeparadores() {
        if (separadores != null)
            return;
        separadores = new ArrayList<Character>();
        separadores.add('!');
        separadores.add('*');
        separadores.add('+');
        separadores.add('-');
        separadores.add('/');
        separadores.add('=');
        separadores.add('(');
        separadores.add('{');
        separadores.add('[');
        separadores.add(')');
        separadores.add('}');
        separadores.add(']');
        separadores.add(';');
        separadores.add(':');
        separadores.add(',');
        separadores.add('\n');
        separadores.add(' ');
        separadores.add('\t');
        separadores.add('&');
    }
}
