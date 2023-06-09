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
        iniciarIniciadores();
        iniciarFinalizadores();
    }

    public void matchToken(EToken etoken) throws Exception {
        try {
            if (!etoken.equals(tokens.getnNextToken().token)) {
                Token t = tokens.getAhead(-1);
                Point p = t.point;
                throw new ExcepcionSintactica(
                        String.format(
                                "Error sintáctico (Fila %s, Columna %s). Se esperaba seguir con: <%s> en lugar de <%s>",
                                p.x, p.y, etoken, tokens.actual.lexema));
            }
        } catch (ExcepcionSintactica e) {
            errores.add(e.getMessage());
            throw e;
        } catch (IndexOutOfBoundsException e) {
            Token t = tokens.getTokenActual();
            Point p = t.point;
            errores.add(String.format("Error sintáctico (Fila %s, Columna %s). Se esperaba seguir con: <%s>", p.x, p.y,
                    etoken));
            throw e;
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
                Token t = tokens.getAhead(-1);
                Point p = t.point;
                throw new ExcepcionSintactica(
                        String.format("Error sintáctico (Fila %s, Columna %s). Se esperaba: <%s>, en lugar de <%s>",
                                p.x, p.y, lexema, tokens.actual.lexema));
            }
        } catch (ExcepcionSintactica e) {
            errores.add(e.getMessage());
            throw e;
        } catch (IndexOutOfBoundsException e) {
            Token t = tokens.getTokenActual();
            Point p = t.point;
            errores.add(String.format("Error sintáctico (Fila %s, Columna %s). Se esperaba seguir con: <%s>", p.x, p.y,
                    lexema));
            throw e;
        }
    }

    private void panico() {
        EToken t;
        String tS;
        int limite = tokens.size() - tokens.index;
        for (int index = 0; index < limite; index++) {
            t = tokens.getAhead(index).token;
            tS = tokens.getAhead(index).lexema;
            if (iniciadores.contains(t) || finalizadores.contains(tS)) {
                tokens.index += index - 1;
                sentencia();
                return;
            }
        }
    }

    public void parse() {
        try {
            matchLexema("INICIO#");
        } catch (Exception e) {
        }
        try {
            if (!tokens.getAhead(1).lexema.equals("FIN#"))
                bloque();
        } catch (Exception e) {
        }
        try {
            matchLexema("FIN#");
        } catch (Exception e) {
        }
    }

    private void bloque() {
        while (tokens.index < tokens.size()) {
            try {
                encabezado();
                sentencia();
                matchLexema("}");
                if (!tokens.getAhead(1).token.equals(BLOQUE))
                    break;
            } catch (Exception e) {
                panico();
                try {
                    matchLexema("}");
                } catch (Exception ex) {
                }
                if (!tokens.getAhead(1).token.equals(BLOQUE))
                    break;
            }
        }
    }

    private void ciclo() {
        try {
            matchLexema("REPETIR");
            matchToken(CICLO);
            operacionBooleana();
            matchLexema("{");
            sentencia();
            matchLexema("}");
        } catch (Exception e) {
            panico();
        }
    }

    private void operacionBooleana() throws Exception {
        matchLexema("(");
        EToken t = tokens.getAhead(1).token;
        identificadorONumero();
        while (true) {
            t = tokens.getAhead(1).token;
            if (COMPUERTA.equals(t))
                matchToken(COMPUERTA);
            else {
                matchToken(OPERADOR_COMPARADOR);
                matchToken(OPERADOR_COMPARADOR);
                matchToken(OPERADOR_COMPARADOR);
            }
            identificadorONumero();
            t = tokens.getAhead(1).token;
            if (!(OPERADOR_COMPARADOR.equals(t) || COMPUERTA.equals(t)))
                break;
        }
        matchLexema(")");
    }

    private void identificadorONumero() throws Exception {
        EToken t = tokens.getAhead(1).token;
        switch (t) {
            case IDENTIFICADOR:
                matchToken(IDENTIFICADOR);
                break;
            default:
                matchToken(NUMERO);
                break;
        }
    }

    public static ArrayList<EToken> iniciadores;

    public void iniciarIniciadores() {
        iniciadores = new ArrayList<EToken>();
        iniciadores.add(IDENTIFICADOR);
        iniciadores.add(COMPONENTE);
        iniciadores.add(CICLO);
        iniciadores.add(TIPO_DE_DATO);
        iniciadores.add(CONDICION_INICIAL);
        iniciadores.add(ACCION);
    }
    
    public static ArrayList<String> finalizadores;

    public void iniciarFinalizadores() {
        finalizadores = new ArrayList<String>();
        finalizadores.add("}");
        finalizadores.add("INTERRUMPIR");
        finalizadores.add("FIN#");
    }

    private void sentencia() {
        try {
            ciclo: while (true) {
                EToken t = tokens.getAhead(1).token;
                if (!iniciadores.contains(t)) {
                    String tS = tokens.getAhead(1).lexema;
                    if (finalizadores.contains(tS))
                        return;
                    Point p = tokens.actual.point;
                    errores.add(String.format("Error sintáctico (Fila %s, Columna %s). Se esperaba seguir con: <%s>",
                            p.x, p.y, "Iniciador de sentencia"));
                    panico();
                    break ciclo;
                }
                switch (t) {
                    case COMPONENTE:
                        matchToken(COMPONENTE);
                        break;
                    case TIPO_DE_DATO:
                        matchToken(TIPO_DE_DATO);
                        break;
                    case CICLO:
                        ciclo();
                        continue ciclo;
                    case CONDICION_INICIAL:
                        condicion();
                        continue ciclo;
                    case ACCION:
                        matchLexema("CONECTAR");
                        matchToken(IDENTIFICADOR);
                        matchLexema("CON");
                        matchToken(IDENTIFICADOR);
                        matchLexema(";");
                        continue ciclo;
                    default:
                        break;
                }
                matchToken(IDENTIFICADOR);
                t = tokens.getAhead(1).token;
                if (OPERADOR_UNARIO.equals(t)) {
                    matchToken(OPERADOR_UNARIO);
                    matchLexema(";");
                    continue ciclo;
                }
                String tS = tokens.getAhead(1).lexema;
                if (tS.equals("=")) {
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
                }
                matchLexema(";");
            }
        } catch (Exception e) {
            panico();
        }
    }

    private void condicion() {
        try {
            String tS = tokens.getAhead(1).lexema;
            if (tS.equals("SI")) {
                matchLexema("SI");
                operacionBooleana();
                matchLexema("{");
                sentencia();
                matchLexema("}");
                EToken t = tokens.getAhead(1).token;
                if (t.equals(CONDICION)) {
                    matchLexema("SI_NO");
                    tS = tokens.getAhead(1).lexema;
                    if (tS.equals("("))
                        operacionBooleana();
                    matchLexema("{");
                    sentencia();
                    matchLexema("}");
                }
            } else {
                matchLexema("ELEGIR");
                matchLexema("(");
                matchToken(IDENTIFICADOR);
                matchLexema(")");
                matchLexema(":");
                matchLexema("{");
                while (true) {
                    tS = tokens.getAhead(1).lexema;
                    if (tS.equals("OPCION")) {
                        matchLexema("OPCION");
                        identificadorONumero();
                        matchLexema(":");
                        sentencia();
                        matchLexema("INTERRUMPIR");
                        matchLexema(";");
                    } else {
                        matchLexema("POR");
                        matchLexema("DEFECTO");
                        matchLexema(":");
                        sentencia();
                        matchLexema("INTERRUMPIR");
                        matchLexema(";");
                    }
                    EToken t = tokens.getAhead(1).token;
                    if (!CONDICION.equals(t)) {
                        break;
                    }
                }
                matchLexema("}");
            }
        } catch (Exception e) {
            panico();
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
            panico();
        }
    }
}