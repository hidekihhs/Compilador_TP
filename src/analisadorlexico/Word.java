/*
 * Finalidade: representa um token de palavras reservadas, 
 * identificadores e tokens compostos como && e !=
 */
package analisadorlexico;
/**
 * @author daniele,hideki,padovani
 */
public class Word extends Token {
    private String lexeme = "";
    public static final Word ASSIGN    = new Word(":=", Tag.ASSIGN);
    public static final Word NOT       = new Word("!",  Tag.NOT);
    public static final Word EQ        = new Word("=",  Tag.EQ);
    public static final Word GT        = new Word(">",  Tag.GT);
    public static final Word GE        = new Word(">=", Tag.GE);
    public static final Word LT        = new Word("<",  Tag.LT);
    public static final Word LE        = new Word("<=", Tag.LE);
    public static final Word NE        = new Word("!=", Tag.NE);
    public static final Word SUM       = new Word("+",  Tag.SUM);
    public static final Word MINUS     = new Word("-",  Tag.MINUS);
    public static final Word OR        = new Word("||", Tag.OR);
    public static final Word MULT      = new Word("*",  Tag.MULT);
    public static final Word DIV       = new Word("/",  Tag.DIV);
    public static final Word AND       = new Word("&&", Tag.AND);
    public static final Word PAR_OPEN  = new Word("(",  Tag.PAR_OPEN);
    public static final Word PAR_CLOSE = new Word(")",  Tag.PAR_CLOSE);
    public static final Word COMMA     = new Word(",",  Tag.COMMA);
    public static final Word SEMI      = new Word(";",  Tag.SEMI);   
    
    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    @Override
    public String toString() {
        return lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }   
}

