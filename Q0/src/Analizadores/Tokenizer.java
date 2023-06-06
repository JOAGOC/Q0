package Analizadores;

import java.awt.Point;
import java.util.ArrayList;

class Token{
    public String lexema;
    public EToken token;
    public Point point;

    public Token(String lexema, EToken token, Point p){
        this.lexema = lexema;
        this.token = token;
        this.point = p;
    }
}

public class Tokenizer extends ArrayList<Token>{
    public int index = -1;

    public Token getAhead(int i){
        return this.get(index + i);
    }

    public Token getTokenActual(){
        return this.get(index);
    }

    public Token getnNextToken(){
        try {
            return this.get(++index);
        } catch (IndexOutOfBoundsException e) {
            index--;
            throw e;
        }
    }

    @Override
    public String toString() {
        String xd = "";
        for (Token x : this){
            xd+=x.lexema+"\t"+x.token+"\n";
        }
        return xd;
    }
}