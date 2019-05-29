/*
 * Finalidade: classe que implementa o analisador léxico. 
 * Seu constrututor insere as palavras reservadas na tabela de símbolos. 
 * Possui um método scan que devolve um Token
 */
package analisadorlexico;

/**
 * @author daniele,hideki,padovani
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Lexer {
    public static int line = 1; //contador de linhas
    private char c = ' '; //caractere lido do arquivo
    private FileReader file; 

    private HashMap<String, Word> words = new HashMap<>();
    
    // Método para inserir palavras reservadas na HashTable 
    void reserve(Word w) {
        words.put(w.getLexeme(), w);
        // lexema é a chave para entrada na HashTable
    }

    public Lexer(String filename) throws FileNotFoundException {
        try {
            file = new FileReader(filename);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
            throw e;
        }
      
        //Insere palavras reservadas na HashTable
        reserve(new Word("app", Tag.APP));
        reserve(new Word("start", Tag.START));
        reserve(new Word("stop", Tag.STOP));
        reserve(new Word("integer", Tag.INTEGER));
        reserve(new Word("real", Tag.REAL));
        reserve(new Word("if", Tag.IF));
        reserve(new Word("then", Tag.THEN));
        reserve(new Word("end", Tag.END));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("repeat", Tag.REPEAT));
        reserve(new Word("until", Tag.UNTIL));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("read", Tag.READ));
        reserve(new Word("write", Tag.WRITE));
    }
    // Lê o próximo caractere do arquivo
    private void readch() throws IOException {
        c = (char) file.read();
    }
    // Lê o próximo caractere do arquivo e verifica se é igual a c
    private boolean readch(char ch) throws IOException {
        readch();
        if (c != ch) {
            return false;
        }
        c = ' ';
        return true;
    }
    private Token getLiteral() throws IOException, Exception {
        StringBuilder s1 = new StringBuilder();
        do {
            s1.append(c);
            readch();
            if (c == '\uFFFF' || c == '\n') { //APAGAR
                throw new Exception("Erro na linha " + line + ": literal inválido");
            }
        } while (c != '}');
        s1.append(c);
        readch();
        String s = s1.toString(); 
        Word w = new Word(s, Tag.LITERAL);
        return w;
    }
    
    // Imprime tabela de símbolos
    public void imprimeTabelaDeSimbolos() {
        Set<String> lexemas = words.keySet();
        for (String lexema : lexemas) {
            System.out.println("<" + lexema + ", " + words.get(lexema).tag + ">");
        }
    }
	
	public int getLine(){
		return line;
	}
	
    public Token scan() throws IOException, Exception {
        //Desconsidera delimitadores na entrada
        for (;; readch()) {
            if (c == ' ' || c == '\t' || c == '\r' || c == '\b') {
            } else if (c == '%') { // Tratamento nas linhas dos comentários
                do {
                    readch();
                    if (c == '\uFFFF') {
                        throw new Exception("Erro na linha " + line + ": a "
                        + "linguagem permite apenas comentarios de uma linha");
                    }
                } while (c != '\n');
            } else if (c == '\n') // ConTagem de linhas
                line++;
            else
                break;
    }
    switch (c) {
        // Operadores, símbolos e literal
        case ':':
            if (readch('=')) {
                return Word.ASSIGN;
            } else{
                return new Word("Invalid token", Tag.INVALID_TOKEN);
            }
        case '!':
            if (readch('=')) {
                return Word.NE;
            } else{
                return Word.NOT;
            }
        case '=':
            readch();
            return Word.EQ;
        case '>':
            if (readch('=')) {
                return Word.GE;
            } else {
                return Word.GT;
            }
        case '<':
            if (readch('=')) {
                return Word.LE;
            } else {
                return Word.LT;
            }
        case '+':
            readch();
            return Word.SUM;
        case '-':
            readch();
            return Word.MINUS;
        case '|':
            if (readch('|')) {
                return Word.OR;
            } else {
                return new Word("Invalid token", Tag.INVALID_TOKEN);
            }
        case '*':
            readch();
            return Word.MULT;
        case '/':
            readch();
            return Word.DIV;
        case '&':
            if (readch('&')) {
                return Word.AND;
            } else {
                return new Word("Invalid token", Tag.INVALID_TOKEN);
            }
       /* case '_':
            readch();
            return Word.UNDERLINE;
        */
        case '(':
            readch();
            return Word.PAR_OPEN;
        case ')':
            readch();
            return Word.PAR_CLOSE;
        case ',':
            readch();
            return Word.COMMA;
        case ';':
            readch();
            return Word.SEMI;
        case '{':
            return getLiteral();
        default:
           // if(!Character.isLetterOrDigit(c) || c != '_'){
           //     return new Word("Invalid token", Tag.INVALID_TOKEN);
          //  }clear
    }
    
    // Identificadores
        if (Character.isLetter(c) || c == '_') {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(c);
                readch();
            } while (Character.isLetterOrDigit(c) || c == '_');
            String s = sb.toString();
            Word w = (Word) words.get(s.toLowerCase());

            if (w != null) {
                return w; //palavra já existe na HashTable
            }else{
                // a regra diz
                // identifier ::= letter | "_" {letter|digit|"_"} 
                //
                // não aceita teste1, soma, result
                // exemplos aceitos: a, b, _, __, _soma, _1_adddd
                //                      _tempo, _145Soma
                if(s.length() == 1 || s.startsWith("_")){
                    w = new Word(s, Tag.ID);
                    words.put(s, w);
                    return w;
                }else{
                    throw new Exception("Erro na linha " + line + ": Token inexistente: " + s);
                }
            }
        }
                    

        // Números
        if (Character.isDigit(c)) {
            int value = 0;
            do {
                value = 10 * value + Character.digit(c, 10);
                readch();
            } while (Character.isDigit(c));
            if(Character.isLetter(c)){
                throw new Exception("Erro na linha " + line + ": Token inexistente: " + value + c);
            }
            if (c == '.') {
                readch();
                if (Character.isDigit(c)) {
                    float v = 0;
                    do {
                        v = v / 10 + Character.digit(c, 10);
                        readch();
                    } while (Character.isDigit(c));

                    if(Character.isLetter(c)){
                        throw new Exception("Erro na linha " + line + ": Token inexistente: " + value + c);
                    }

                    v = value + v / 10;
                    return new Num(Tag.FLOAT_CONST, v);
                } else {
                    throw new Exception("Erro na linha " + line + ": Token inexistente: " + value + c);
                }
            } else {
                return new Num(Tag.INT_CONST, value);
            }
        }
        
        // Fim de arquivo
        if (c == '\uFFFF') {
            return new Word("EOF", Tag.EOF);
        }
        
        // Qualquer outro caracter não específicado acima é inválido
        if (!Character.isLetterOrDigit(c)) {
            readch();
            throw new Exception("Erro na linha " + line + ": caractere inválido." + c);
        }
        
        // Caracteres não especificados
        Token t = new Token(c);
        c = ' ';
        return t;
    }    
}
