package Analizadores;

import javax.swing.border.EtchedBorder;

class AnalizadorSintáctico{
    Tokenizer tokens;

    public AnalizadorSintáctico(AnalizadorLéxico l){
        this.tokens = l.tokens;
    }

    public void matchToken(EToken xd){

    }

    private void matchLexema(String lexema) {

    }
    
    public void parse(){
        matchToken(tokens.getnNextToken().token);
        matchLexema(tokens.getnNextToken().lexema);
    }

}