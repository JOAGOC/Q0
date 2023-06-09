package Analizadores;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import static Analizadores.PalabrasReservadas.*;
import static Analizadores.EToken.*;

class ExcepcionAnalisis extends Exception {
    ExcepcionAnalisis(String message) {
        super(message);
    }
}

public class AnalizadorLéxico {
    private String document = "0.23\n#Resistencia1_N#";
    public List<String> errores = new ArrayList<String>(); // almacenar errores
    public Tokenizer tokens = new Tokenizer();
    public Map<String,EToken> tokensT = new HashMap<String,EToken>();
    private int index = 0;
    private int fila = 1;
    private int columna;

    public AnalizadorLéxico(String document) {
        this.document = document;
        iniciarPalabrasReservadas();
    }

    public String getErrores(){
        String errores = "";
        for (String err : this.errores) {
            errores += err + "\n";
        }
        return errores;
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
                tokens.add(t = new Token(document.substring(initialIndex, index), IDENTIFICADOR, new Point(fila,getColumna())));
                tokensT.put(t.lexema,t.token);
                return true;
            default:
                throw new ExcepcionAnalisis(
                        String.format(mensajeError, fila, getColumna(), document.substring(initialIndex, index)));
        }
    }

    Token t;
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
                tokens.add(t = new Token(document.substring(initialIndex, index), NUMERO, new Point(fila,getColumna())));
                tokensT.put(t.lexema,t.token);
                return true;
            case q1:
                tokens.add(t = new Token(document.substring(initialIndex, index), NUMERO, new Point(fila,getColumna())));
                tokensT.put(t.lexema,t.token);
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
                        if (esComentario())
                            index--;
                        else if (esOperador())
                            ;
                        else if (esNumero())
                            index--;
                        else if (esIdentificador())
                            index--;
                        else {
                            final int initialIndex = index;
                            do {
                                if (separadores.contains(document.charAt(index)))
                                    break;
                            } while (++index < document.length());
                            index=initialIndex == index ? index+1:index;
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

    private boolean esComentario() throws Exception{
        // Columnas de transicion
        final int andP = 0;
        final int otro = 1;

        // Estados
        final int q1 = 1;
        final int q0 = 0;

        final int estados[][] = {
                // {andP, otro}
                { q1, q0 }, // False
                { q1, q1 } // True
        };

        int estado = q0;
        int caracter = q0;
        // Puntero del autómata
        // Puntero inicial para el caso del error

        char c;
        do {
            c = document.charAt(index);
            if (c == '\n')
                break;
            if (c == '&')
                caracter = andP;
            else
                caracter = otro;
            if ((estado = estados[estado][caracter]) == q0 && caracter == otro)
                break;
        } while (++index < document.length());
        switch (estado) {
            case q1:
                return true;
            default:
                return false;
        }
    }

    private boolean esPalabraReservada(String substring) {
        try {
            return es(tablaComponentes, substring) || es(tablaUnidades, substring)
                    || es(tablaPrefijos, substring) || es(tablaAcciones, substring) || es(tablaBooleana, substring)
                    || es(tablaConfiguración, substring) || es(tablaCiclo, substring) || es(tablaControl, substring)
                    || es(tablaDatos, substring) || es(tablaComparador, substring);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean es(Map<String, EToken> tabla, String substring) {
        if (tabla.containsKey(substring)) {
            tokens.add(t = new Token(substring, tabla.get(substring), new Point(fila,getColumna())));
            tokensT.put(t.lexema,t.token);
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
            tokens.add(t = new Token(c + "", tablaOperadores.get(c + ""), new Point(fila,getColumna())));
            tokensT.put(t.lexema,t.token);
            return true;
        }
        c2 = document.charAt(index);
        String xd = (c+"") + (c2+"");
        if (!tablaOperadores.containsKey(xd)) {
            tokens.add(t = new Token(c + "", tablaOperadores.get(c + ""), new Point(fila,getColumna())));
            tokensT.put(t.lexema,t.token);
            index--;
            return true;
        } else {
            tokens.add(t = new Token(xd, tablaOperadores.get(xd), new Point(fila,getColumna())));
            tokensT.put(t.lexema,t.token);
            return true;
        }
    }

    public String getTokens() {
        String tokens = "";
        for (Map.Entry<String, EToken> entry : tokensT.entrySet()) {
            tokens += entry.getKey()+"\t\t"+entry.getValue()+"\n";
        }
        return tokens;        
    }
}