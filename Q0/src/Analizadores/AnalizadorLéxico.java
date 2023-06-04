package Analizadores;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;

class ExcepcionAnalisis extends Exception {
    ExcepcionAnalisis(String message) {
        super(message);
    }
}

public class AnalizadorLéxico {
    private String document = "0.23\n#Resistencia1_N#";
    public List<String> errores = new ArrayList<String>(); // almacenar errores
    public DefaultTableModel tabla = new DefaultTableModel(0, 2);
    private int index = 0;
    private int fila = 1;
    private int columna;

    public AnalizadorLéxico(String document) {
        this.document = document;
        iniciarPalabrasReservadas();
    }

    private void iniciarPalabrasReservadas() {
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
    }

    public boolean esIdentificador() throws Exception {
        final String mensajeError = "Error (Fila %d, Columna %d): %s no es un identificador válido.";

        // Columnas de transicion
        final int gato = 0;
        final int letra = 1;
        final int numero = 2;
        final int guionBajo = 3;

        Function<Character, Integer> evaluarCaracter = (caracter) -> {
            int cCode = (int) caracter;
            if (cCode == (int) '#') {
                return gato;
            } else if ((cCode >= 65 && cCode <= 90) || (cCode >= 97 && cCode <= 122))
                // 65-90 mayúsculas 97-122 minúsculas
                return letra;
            else if (cCode >= 48 && cCode <= 57)
                return numero;
            else if ((int) '_' == cCode)
                return guionBajo;
            return -1;
        };
        // Estados
        final int q1 = 1;
        final int q2 = 2;
        final int q3 = 3;
        final int q4 = 4;
        final int fin = 5;// Retorno inicial

        final int error = -1;
        final int q0 = 0;
        final int estados[][] = {
                // #, letra, numero, _, otro
                { q1, fin, fin, fin }, // Falso
                { error, q2, error, error }, // Error
                { q4, q2, q2, q3 }, // Error
                { error, q2, q2, error }, // Error
                { error, error, error, error }// Verdadero
        };
        // Constantes de método. Fin de declaración

        final int initialIndex = index;

        int estado = q0;
        int caracter = q0;
        // Declaración de variables

        char c;
        do {
            c = document.charAt(index);
            if ((caracter = evaluarCaracter.apply(c)) == -1)
                break;
            if ((estado = estados[estado][caracter]) == error) {
                while (++index < document.length()) {
                    c = document.charAt(index);
                    if (evaluarCaracter.apply(c) == -1)
                        break;
                }
                throw new ExcepcionAnalisis(
                        String.format(mensajeError, fila, getColumna(), document.substring(initialIndex, index)));
            }
            if (estado == fin)
                break;
        } while (++index < document.length());
        switch (estado) {
            case q0:
            case fin:
                return false;
            case q4:
                tabla.addRow(new String[] { document.substring(initialIndex, index), "Identificador" });
                return true;
            default:
                throw new ExcepcionAnalisis(
                        String.format(mensajeError, fila, getColumna(), document.substring(initialIndex, index)));
        }
    }

    /**
     * Evalúa el documento para encontrar un número con la E.R.
     * ^([0-9]+.[0-9]+|.[0-9]+)$
     * 
     * @return Verdadero si finaliza el recorrido en un estado de aceptación.
     * @throws Exception Con el mensaje de error que incluye la palabra erronea.
     */
    public boolean esNumero() throws Exception {
        final String mensajeError = "Error (Fila %d, Columna %d): %s no es un número válido.";

        // Columnas de transicion
        final int numero = 0;
        final int punto = 1;

        Function<Character, Integer> evaluarCaracteres = (caracter) -> {
            int cCode = (int) caracter;
            if (cCode >= 48 && cCode <= 57) // Se corrige esta condición
                return numero;
            else if ((int) '.' == cCode)
                return punto;
            return -1;
        };

        // Estados
        final int q1 = 1;
        final int q2 = 2;
        final int q3 = 3;
        final int error = -1;
        final int q0 = 0;

        final int estados[][] = {
                // {numero, punto, otro}
                { q1, q2 }, // False
                { q1, q2 }, // True
                { q3, error }, // error
                { q3, error }// True
        };

        int estado = q0;
        int caracter = q0;
        // Puntero del autómata
        final int initialIndex = index;
        // Puntero inicial para el caso del error

        char c;
        do {
            c = document.charAt(index);
            if ((caracter = evaluarCaracteres.apply(c)) == -1)
                break;
            if ((estado = estados[estado][caracter]) == error) {
                while (++index < document.length()) {
                    c = document.charAt(index);
                    if (evaluarCaracteres.apply(c) == -1)
                        break;
                }
                throw new ExcepcionAnalisis(
                        String.format(mensajeError, fila, getColumna(), document.substring(initialIndex, index)));
            }
            // Se agrega esta condición para evitar el error StringIndexOutOfBoundsException
        } while (++index < document.length());
        switch (estado) {
            case q0:
                return false;
            case q2:
                throw new ExcepcionAnalisis(
                        String.format(mensajeError, fila, getColumna(), document.substring(initialIndex, index)));
            case q3:
                tabla.addRow(new String[] { document.substring(initialIndex, index), "Número decimal" });
                return true;
            case q1:
                tabla.addRow(new String[] { document.substring(initialIndex, index), "Número Entero" });
                return true;
        }
        return false;
    }

    private int getColumna() {
        return index - columna;
    }

    /*
     * resistencia #_res1# = 37.5 ohm;
     * resistencia #re_s2# = 1 kiloohm;
     * resistencia #resResultante_# = 0;
     * #resResultante_# = #re_s2# + #_res1#;
     */

    Map<String, String> tablaComponentes;

    private void inicializarTablaDeComponentes() {
        if (tablaComponentes != null)
            return;
        tablaComponentes = new HashMap<String, String>();
        tablaComponentes.put("LED", "Componente");
        tablaComponentes.put("RELE", "Componente");
        tablaComponentes.put("POTENCIOMETRO", "Componente");
        tablaComponentes.put("TRANSISTOR", "Componente");
        tablaComponentes.put("RESISTENCIA", "Componente");
        tablaComponentes.put("DIODO", "Componente");
        tablaComponentes.put("CAPACITOR", "Componente");
        tablaComponentes.put("CIRCUITO_INTEGRADO", "Componente");
        tablaComponentes.put("SENSOR", "Componente");
        tablaComponentes.put("TRANSFORMADOR", "Componente");
        tablaComponentes.put("SENSOR", "Componente");
        tablaComponentes.put("INTERRUPTOR", "Componente");
        tablaComponentes.put("MOTOR", "Componente");
        tablaComponentes.put("DISPLAY", "Componente");
        tablaComponentes.put("FUENTE", "Componente");
        tablaComponentes.put("CONDUCTORES", "Componente");
        tablaComponentes.put("INDUCTOR", "Componente");
        tablaComponentes.put("SERVO", "Componente");
        tablaComponentes.put("COMPUERTA", "Componente");
        tablaComponentes.put("PIN", "Componente");
        tablaComponentes.put("FUENTE", "Componente");
        tablaComponentes.put("TIERRA", "Componente");
    }

    Map<String, String> tablaUnidades;

    private void inicializarTablaDeUnidades() {
        if (tablaUnidades != null)
            return;
        tablaUnidades = new HashMap<String, String>();
        tablaUnidades.put("VOLTIO", "Unidad");
        tablaUnidades.put("AMPERIO", "Unidad");
        tablaUnidades.put("HERCIO", "Unidad");
        tablaUnidades.put("OHM", "Unidad");
        tablaUnidades.put("WATT", "Unidad");
        tablaUnidades.put("HENRIO", "Unidad");
        tablaUnidades.put("FARADIO", "Unidad");
        tablaUnidades.put("JULIO", "Unidad");
        tablaUnidades.put("SEGUNDO", "Unidad");
    }

    Map<String, String> tablaAcciones;

    private void inicializarTablaDeAcciones() {
        if (tablaAcciones != null)
            return;
        tablaAcciones = new HashMap<String, String>();
        tablaAcciones.put("ESPERAR", "Acción");
        tablaAcciones.put("IMPORTAR", "Acción");
        tablaAcciones.put("FUNCION", "Acción");
        tablaAcciones.put("INICIAR", "Acción");
        tablaAcciones.put("DETENER", "Acción");
        tablaAcciones.put("TIEMPO", "Acción");
        tablaAcciones.put("LEER", "Acción");
        tablaAcciones.put("ESCRIBIR", "Acción");
        tablaAcciones.put("INTERRUMPIR", "Acción");
        tablaAcciones.put("CONECTAR", "Acción");
        tablaAcciones.put("ENCENDER", "Acción");
        tablaAcciones.put("APAGAR", "Acción");
        tablaAcciones.put("EN", "Acción");
    }

    Map<String, String> tablaPrefijos;

    private void inicializarTablaDePrefijos() {
        if (tablaPrefijos != null)
            return;
        tablaPrefijos = new HashMap<String, String>();
        tablaPrefijos.put("PICO", "Prefijo");
        tablaPrefijos.put("NANO", "Prefijo");
        tablaPrefijos.put("MICRO", "Prefijo");
        tablaPrefijos.put("MILI", "Prefijo");
        tablaPrefijos.put("KILO", "Prefijo");
        tablaPrefijos.put("MEGA", "Prefijo");
        tablaPrefijos.put("GIGA", "Prefijo");
    }

    Map<String, String> tablaOperadores;

    private void inicializarTablaDeOperadores() {
        if (tablaOperadores != null)
            return;
        tablaOperadores = new HashMap<String, String>();
        // '!', '#', '^', '&', '*', '-', '+', '=', '{', '}', '[', ']',
        // '(', ')', '<', '>', '?', '|', '\\', '/', ',', '.', ';', ':', '\'', '"'
        tablaOperadores.put("!", "Operador Negación");
        tablaOperadores.put("**", "Operador Potencia");
        tablaOperadores.put("++", "Operador Incremento");
        tablaOperadores.put("/", "Operador División");
        tablaOperadores.put("+", "Operador Suma");
        tablaOperadores.put("-", "Operador Resta");
        tablaOperadores.put("=", "Operador Asignación");
        tablaOperadores.put("=", "Operador Asignación");
        tablaOperadores.put("(", "Operador LParent");
        tablaOperadores.put("{", "Operador LBracket");
        tablaOperadores.put("[", "Operador LCuadra");
        tablaOperadores.put(")", "Operador RParent");
        tablaOperadores.put("}", "Operador RBracket");
        tablaOperadores.put("]", "Operador RCuadra");
        tablaOperadores.put(";", "Operador Delimitador");
        tablaOperadores.put(",", "Operador Separador");
    }

    Map<String, String> tablaBooleana;

    private void inicializarTablaBooleana() {
        if (tablaBooleana != null)
            return;
        tablaBooleana = new HashMap<String, String>();
        tablaBooleana.put("AND", "COMPUERTA");
        tablaBooleana.put("OR", "COMPUERTA");
        tablaBooleana.put("XOR", "COMPUERTA");
        tablaBooleana.put("NOT", "COMPUERTA");
        tablaBooleana.put("NAND", "COMPUERTA");
        tablaBooleana.put("NOR", "COMPUERTA");
        tablaBooleana.put("XNOR", "COMPUERTA");
        tablaBooleana.put("FALSO", "Booleano");
        tablaBooleana.put("VERDADERO", "Booleano");
        tablaBooleana.put("MAXIMO", "Booleano");
        tablaBooleana.put("MINIMO", "Booleano");
    }

    Map<String, String> tablaConfiguración;

    private void inicializarTablaConfiguración() {
        if (tablaConfiguración != null)
            return;
        tablaConfiguración = new HashMap<String, String>();
        tablaConfiguración.put("ALTERNA", "Configuración");
        tablaConfiguración.put("CONTINUA", "Configuración");
        tablaConfiguración.put("POTENCIA", "Configuración");
        tablaConfiguración.put("FRECUENCIA", "Configuración");
        tablaConfiguración.put("SERIE", "Configuración");
        tablaConfiguración.put("PARALELO", "Configuración");
        tablaConfiguración.put("DIGITAL", "Configuración");
        tablaConfiguración.put("ANALOGICO", "Configuración");
    }

    Map<String, String> tablaCiclo;

    private void inicializarTablaCiclo() {
        if (tablaCiclo != null)
            return;
        tablaCiclo = new HashMap<String, String>();
        tablaCiclo.put("REPETIR", "Ciclo");
        tablaCiclo.put("MIENTRAS", "Ciclo");
        tablaCiclo.put("HASTA", "Ciclo");
        tablaCiclo.put("QUE", "Ciclo");
        tablaCiclo.put("POR", "Ciclo");
        tablaCiclo.put("PARA", "Ciclo");
    }

    Map<String, String> tablaControl;

    private void inicializarTablaControl() {
        if (tablaControl != null)
            return;
        tablaControl = new HashMap<String, String>();
        tablaControl.put("SI", "Condicion");
        tablaControl.put("NO", "Condicion");
        tablaControl.put("SI NO", "Condicion");
        tablaControl.put("ELEGIR", "Condicion");
        tablaControl.put("OPCION", "Condicion");
        tablaControl.put("POR", "Condicion");
        tablaControl.put("DEFECTO", "Condicion");
    }

    Map<String, String> tablaComparador;

    private void inicializarTablaComparador() {
        if (tablaComparador != null)
            return;
        tablaComparador = new HashMap<String, String>();
        tablaComparador.put("ES", "OPERADOR COMPARACION");
        tablaComparador.put("IGUAL", "OPERADOR COMPARACION");
        tablaComparador.put("A", "OPERADOR COMPARACION");
        tablaComparador.put("MAYOR", "OPERADOR COMPARACION");
        tablaComparador.put("MENOR", "OPERADOR COMPARACION");
        tablaComparador.put("QUE", "OPERADOR COMPARACION");
        tablaComparador.put("DIFERENTE", "OPERADOR COMPARACION");
    }

    Map<String, String> tablaDatos;

    private void inicializarTablaDatos() {
        if (tablaDatos != null)
            return;
        tablaDatos = new HashMap<String, String>();
        tablaDatos.put("DECIMAL", "TIPO DE DATO");
        tablaDatos.put("ENTERO", "TIPO DE DATO");
        tablaDatos.put("CARACTER", "TIPO DE DATO");
        tablaDatos.put("CADENA", "TIPO DE DATO");
        tablaDatos.put("BOOLEANO", "TIPO DE DATO");
        tablaDatos.put("TABLA", "TIPO DE DATO");
        tablaDatos.put("COMPONENTE", "TIPO DE DATO");
    }

    public void analizar() {
        final String mensajeError = "Error (Fila %d, Columna %d): %s no puede ser resuelto.";

        index = columna = 0;
        fila = 1;
        char xd;
        for (; index < document.length(); index++) {
            switch (xd = document.charAt(index)) {
                case '\n':
                    fila++;
                    columna = index;
                case ' ':
                case '\t':
                    break;
                default:
                    try {
                        if (esOperador())
                            ;
                        else if (esNumero())
                            index--;
                        else if (esIdentificador())
                            index--;
                        else {
                            List<Character> separadores = new ArrayList<Character>();
                            // Separadores
                            // !, @, #, $, %, ^, &, *, -, +, =, {, }, [, ], (, ), <, >, ?, |, \, /, ,, ., ;,
                            // :, ', "
                            char[] caracteres = new char[] { '!', '#', '^', '&', '*', '-', '+', '=', '{', '}', '[',
                                    ']',
                                    '(', ')', '<', '>', '?', '|', ',', '\\', '/', '.', ';', ' ', ':', '\'', '"' };
                            for (char c : caracteres) {
                                separadores.add(c);
                            }
                            final int initialIndex = index;
                            do {
                                if (separadores.contains(document.charAt(index)))
                                    break;
                            } while (++index < document.length());
                            String substring = document.substring(initialIndex, index--);
                            if (esPalabraReservada(substring))
                                ;
                            else {
                                throw new ExcepcionAnalisis(
                                        String.format(mensajeError, fila, getColumna(),
                                                substring));
                            }
                        }
                    } catch (ExcepcionAnalisis e) {
                        errores.add(e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } // Hay que validar identificadores no válidos
            }
        }
    }

    private boolean esPalabraReservada(String substring) {
        return es(tablaComponentes, substring) || es(tablaUnidades, substring)
                || es(tablaPrefijos, substring) || es(tablaAcciones, substring) || es(tablaBooleana, substring)
                || es(tablaConfiguración, substring) || es(tablaCiclo, substring) || es(tablaControl, substring)
                || es(tablaDatos, substring) || es(tablaComparador, substring);
    }

    private boolean es(Map<String, String> tabla, String substring) {
        if (tabla.containsKey(substring)) {
            this.tabla.addRow(new String[] { substring, tabla.get(substring) });
            return true;
        }
        return false;
    }

    private boolean esOperador() {
        char c;
        c = document.charAt(index);
        if (!tablaOperadores.containsKey(c + ""))
            return false;
        char c2;
        if (++index >= document.length()) {
            tabla.addRow(new String[] { c + "", tablaOperadores.get(c + "") });
            return true;
        }
        c2 = document.charAt(index);
        if (!tablaOperadores.containsKey(c + c2 + "")) {
            tabla.addRow(new String[] { c + "", tablaOperadores.get(c + "") });
            index--;
            return true;
        } else {
            tabla.addRow(new String[] { c + c2 + "", tablaOperadores.get(c + c2 + "") });
            return true;
        }
    }
}