package Analizadores;

import java.awt.Point;
import java.util.ArrayList;
import static Analizadores.EToken.*;

class ExcepcionSintactica extends Exception {
    ExcepcionSintactica(String s) {
        super(s);
    }
}

public class AnalizadorSintáctico {
    Tokenizer tokens;
    public ArrayList<String> errores = new ArrayList<String>();

    public AnalizadorSintáctico(AnalizadorLéxico l) {
        this.tokens = l.tokens;
    }

    public void matchToken(EToken etoken) throws Exception {
        try {
            if (!etoken.equals(tokens.getnNextToken().token)) {
                Token t = tokens.getTokenActual();
                Point p = t.point;
                throw new ExcepcionSintactica(
                        String.format("Error sintáctico (Fila %s, Columna %s). Se esperaba: <%s>", p.x, p.y, etoken));
            }
        } catch (ExcepcionSintactica e) {
            errores.add(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            Token t = tokens.getTokenActual();
            Point p = t.point;
            errores.add(String.format("Error sintáctico (Fila %s, Columna %s). Se esperaba seguir con: <%s>", p.x, p.y,
                    etoken));
            throw new Exception();
        }
    }

    public String getErrores() {
        String errores = "";
        for (String err : this.errores) {
            errores += err + "\n";
        }
        return errores;
    }

    private void matchLexema(String lexema) throws Exception {
        try {
            if (!lexema.equals(tokens.getnNextToken().lexema)) {
                Token t = tokens.getTokenActual();
                Point p = t.point;
                throw new ExcepcionSintactica(
                        String.format("Error sintáctico (Fila %s, Columna %s). Se esperaba: <%s>", p.x, p.y, lexema));
            }
        } catch (ExcepcionSintactica e) {
            errores.add(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            Token t = tokens.getTokenActual();
            Point p = t.point;
            errores.add(String.format("Error sintáctico (Fila %s, Columna %s). Se esperaba seguir con: <%s>", p.x, p.y,
                    lexema));
            throw new Exception();
        }
    }

    public void parse() {
        try {
            matchLexema("INICIO#");
            if (!tokens.getAhead(1).lexema.equals("FIN#"))
                bloque();
            matchLexema("FIN#");
        } catch (Exception e) {
        }
    }

    private void bloque() {
        while (true) {
            encabezado();
            sentencia();
            ciclo();
            try {
                matchLexema("}");
            } catch (Exception e) {}
            if (!tokens.getAhead(1).token.equals(BLOQUE))
            break;
        }
    }

    private void ciclo() {
        try {
            matchLexema("REPETIR");
            matchToken(CICLO);
            matchLexema("(");
            EToken t = tokens.getAhead(1).token;
            switch (t) {
                case IDENTIFICADOR:
                    matchToken(IDENTIFICADOR);
                    break;
                default:
                    matchToken(NUMERO);
                    break;
            }
            matchToken(OPERADOR_COMPARADOR);
            matchToken(OPERADOR_COMPARADOR);
            matchToken(OPERADOR_COMPARADOR);
            t = tokens.getAhead(1).token;
            switch (t) {
                case IDENTIFICADOR:
                    matchToken(IDENTIFICADOR);
                    break;
                default:
                matchToken(NUMERO);
                break;
            }
            matchLexema(")");
            matchLexema("{");
            t = tokens.getAhead(1).token;
            String tS = tokens.getAhead(1).lexema;
            if (tS.equals("REPETIR"))
                ciclo();
            switch (t) {
                case COMPONENTE:
                case IDENTIFICADOR:
                case TIPO_DE_DATO:
                    sentencia();
                default:
            }
            matchLexema("}");
        } catch (Exception e) {}
    }

    private void sentencia() {
        try {
            ciclo: while (true) {
                EToken t = tokens.getAhead(1).token;
                if (!(t.equals(COMPONENTE) || t.equals(IDENTIFICADOR) || t.equals(TIPO_DE_DATO)))
                    break ciclo;
                switch (t) {
                    case COMPONENTE:
                        matchToken(COMPONENTE);
                        break;
                    case TIPO_DE_DATO:
                        matchToken(TIPO_DE_DATO);
                        break;
                    default:
                        break;
                }
                matchToken(IDENTIFICADOR);
                matchLexema("=");
                t = tokens.getAhead(2).token;
                if (!(t.equals(PREFIJO) || t.equals(UNIDAD)))
                    ciclo2: while (true) {
                        switch (tokens.getAhead(1).token) {
                            case IDENTIFICADOR:
                                matchToken(IDENTIFICADOR);
                                switch (tokens.getAhead(1).token) {
                                    case OPERADOR_ARITMETICO:
                                        matchToken(OPERADOR_ARITMETICO);
                                        continue ciclo2;
                                    default:
                                        matchLexema(";");
                                        continue ciclo;
                                }
                            case NUMERO:
                                matchToken(NUMERO);
                                switch (tokens.getAhead(1).token) {
                                    case OPERADOR_ARITMETICO:
                                        matchToken(OPERADOR_ARITMETICO);
                                        continue ciclo2;
                                    default:
                                        matchLexema(";");
                                        continue ciclo;
                                }
                            case BOOLEANO:
                                matchToken(BOOLEANO);
                                matchLexema(";");
                                continue ciclo;
                            case ACCION_BOOLEANA:
                                matchToken(ACCION_BOOLEANA);
                                matchLexema(";");
                                continue ciclo;
                            default:
                                continue ciclo;
                        }
                    }
                matchToken(NUMERO);
                t = tokens.getAhead(1).token;
                switch (t) {
                    case PREFIJO:
                        matchToken(PREFIJO);
                        matchLexema("-");
                        matchToken(UNIDAD);
                        break;
                    default:
                        matchToken(UNIDAD);
                }
                matchLexema(";");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void encabezado() {
        try {
            matchToken(BLOQUE);
            switch (tokens.getTokenActual().lexema) {
                case "CIRCUITO":
                    matchToken(CONFIGURACION_CIRCUITO);
                    matchToken(IDENTIFICADOR);
                    break;
                case "FUNCION":
                    switch (tokens.getAhead(1).lexema) {
                        case "PRINCIPAL":
                            matchLexema("PRINCIPAL");
                            break;
                        default:
                            matchToken(IDENTIFICADOR);
                    }
                    break;
            }
            matchLexema("{");
        } catch (Exception e) {

        }
    }
}