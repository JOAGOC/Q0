import java.util.List;
import java.util.function.Function;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Source;

import java.util.ArrayList;

public class AnalizadorLéxico {
    private static String codeLine = "  0.23;\n  #Resistencia1_N#\n134.123;\n#_#";
    private String document = "0.23\n#Resistencia1_N#";
    public List<String> errores = new ArrayList<String>(); // almacenar errores
    public DefaultTableModel tabla = new DefaultTableModel(0, 2);
    private int index = 0;
    private int fila = 1;
    private int columna;

    public AnalizadorLéxico(String document) {
        this.document = document;
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
                throw new Exception(
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
                tabla.addRow(new String[]{document.substring(initialIndex, index),"Identificador"});
                return true;
            default:
                throw new Exception(
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
                throw new Exception(
                        String.format(mensajeError, fila, getColumna(), document.substring(initialIndex, index)));
            }
            // Se agrega esta condición para evitar el error StringIndexOutOfBoundsException
        } while (++index < document.length());
        switch (estado) {
            case q0:
                return false;
            case q2:
                throw new Exception(
                        String.format(mensajeError, fila, getColumna(), document.substring(initialIndex, index)));
            default:
                tabla.addRow(new String[] { document.substring(initialIndex, index), "Número" });
                return true;
        }
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
    public static void main(String[] args) {
        new MiVentana().setVisible(true);
        // AnalizadorLéxico aLexico = new AnalizadorLéxico(AnalizadorLéxico.codeLine);
        // // Se corrige el nombre de la
        // // instancia
        // aLexico.analizar();
        // System.out.println("Tabla de tokens:\n");
        // System.out.println(aLexico.tabla.getDataVector().toString());
        // aLexico.errores.forEach((e) -> System.out.println(e));
        // try {
        // int index = 0;
        // String[] fila = { codeLine.substring(index, esNumero(0)), "Número" };
        // aLexico.tabla.add(fila);
        // } catch (Exception e) {
        // aLexico.errores.add(e.getMessage());
        // }

        // for (String error : aLexico.errores) {
        // System.out.println(error);
        // }
        // for (String[] arreglo : aLexico.tabla) {
        // System.out.println(arreglo[0] + " " + arreglo[1]);
        // }
    }

    void analizar() {
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
                case ';':
                case '+':
                case '-':
                case '*':
                case '/':
                case '=':
                    break;
                default:
                    try {
                        if (esNumero())
                            index--;
                        else if (esIdentificador())
                            index--;
                        else {
                            List<Character> separadores = new ArrayList<Character>();
                            // Separadores
                            // !, @, #, $, %, ^, &, *, -, +, =, {, }, [, ], (, ), <, >, ?, |, \, /, ,, ., ;,
                            // :, ', "
                            char[] caracteres = new char[] { '!', '#', '^', '&', '*', '-', '+', '=', '{', '}', '[', ']',
                                    '(', ')', '<', '>', '?', '|', '\\', '/', ',', '.', ';', ':', '\'', '"' };
                            for (char c : caracteres) {
                                separadores.add(c);
                            }
                            final int initialIndex = index;
                            do {
                                if (separadores.contains(document.charAt(index)))
                                    break;
                            } while (++index < document.length());
                            throw new Exception(
                                    String.format(mensajeError, fila, getColumna(),
                                            document.substring(initialIndex, index)));
                        }
                    } catch (Exception e) {
                        errores.add(e.getMessage());
                        System.out.println(e.getMessage());
                        index--;
                    } // Hay que validar identificadores no válidos
            }
        }
    }
}