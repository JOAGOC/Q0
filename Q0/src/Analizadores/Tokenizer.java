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

    public Token getnNextToken(){
        return this.get(++index);
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