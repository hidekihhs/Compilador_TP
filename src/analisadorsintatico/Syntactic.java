package analisadorsintatico;

/**
 * @author daniele,hideki,padovani
 */
import analisadorlexico.Tag;
import analisadorlexico.Token;
import analisadorlexico.Lexer;
import java.io.IOException;

public class Syntactic{

private Lexer lex;
private Token current;

public Syntactic(Lexer lex) throws IOException {
    this.lex = lex;    
}

/**
 * Métodos auxiliares do analisador sintático
 */
public void eat(int t) throws IOException {
    try {
        if (current.tag == t) {
             System.out.println("Eat [" + current.toString() + "]");
            advance();
        } else {
         showError();  
        }
        
    } catch (Exception e) {
       
    }
}

public void advance() throws IOException {
    try {
        current = lex.scan();
        System.out.println("Tag: " + current.tag + " Token: " + current.toString());

        if (current.tag == Tag.EOF) {
            System.out.println("[EOF]");
        }
    } catch (Exception e) {
        // Quando ocorre erro da sintaxe a mensagem
        // provém do Lexer
        System.out.println(e.getMessage());
    }
}

    /**
     * @throws Exception
     * @throws IOException
     * 
     */
    private void showError() throws IOException, Exception {
    System.out.printf("%02d: Sintatico - ", this.lex.getLine());

    switch (current.tag) {
        case Tag.INVALID_TOKEN:
            System.out.println("Token Invalido");
            break;
        case Tag.EOF:
            System.out.println("Fim de arquivo inesperado");
            break;
        default:
            System.out.println("Lexema não esperado\n");
            break;
    }
    System.exit(1);
}

public boolean execute() {
     
    try {
      
        advance();
        program();

        if (current.tag == Tag.EOF) {
            return true;
        } else {
            this.showError();
        return false;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
    return true;
}

/**
 * program ::= app identify body
 */
public void program(){
    System.out.println("execute Program: " + current.toString());
    try {
        switch(current.tag){
            case Tag.APP:
                eat(Tag.APP);                
                identifier();
                body();
                break;
            default:
                showError();
                break;
        }    
    } catch (Exception e) {
        //TODO: handle exception
    }
    
}

/**
 * body ::= [decl-list] start stmt-list stop
 */
public void body() {
    System.out.println("Body: " + current.toString());
    try {
        decl_list();
        eat(Tag.START);
        stmt_list();
        eat(Tag.STOP);
    } catch (Exception e) {
        //TODO: handle exception
    }
    
}

/**
 * decl-list ::= decl {";" decl}
 */
public void decl_list(){
    System.out.println("decli-list: " + current.toString());
    try {
        decl();
        while(current.tag == Tag.SEMI){
            eat(Tag.SEMI);
            decl();
        }
    } catch (Exception e) {
        //TODO: handle exception
    }

}

/**
 * decl ::= type ident-list
 */
public void decl() {
    System.out.println("decl: " + current.toString());
    try {
        switch (current.tag) {
            case Tag.INTEGER:
            case Tag.REAL:
                type();
                ident_list();
                break;
            default:
                break;
        }
        
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * ident-list ::= identifier {"," identifier}
 */
public void ident_list () {
    System.out.println("ident-list: " + current.toString());
    try{
        identifier();
        while(current.tag == Tag.COMMA){
            eat(Tag.COMMA);
            identifier();
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * type ::= integer|real
 */
public void type() {
    System.out.println("type: " + current.toString());
    try {
        switch(current.tag){
            case Tag.INTEGER:
                eat(Tag.INTEGER);
                break;
            case Tag.REAL:
                eat(Tag.REAL);
                break;
            default:
                showError();
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * stmt-list ::= stmt {";" stmt}
 */
public void stmt_list() {
    System.out.println("stmt-list" + current.toString());
    try {
        stmt();
        while(current.tag == Tag.SEMI){
            eat(Tag.SEMI);
            stmt();
        }
    } catch (Exception e) { }
}

/**
* stmt ::= assign-stmt | if-stmt | while-stmt |
*           read-stmt | write-stmt | repeat-stmt
 */
public void stmt() {
    System.out.println("stmt: " + current.toString());
    try {
        switch(current.tag){
            case(Tag.ID):
                assign_stmt();
                break;
            case(Tag.IF):
                if_stmt();
                break;
            case(Tag.WHILE):
                while_stmt();
                break;
            case(Tag.REPEAT):
                repeat_stmt();
                break;
            case(Tag.READ):
                read_stmt();
                break;
            case(Tag.WRITE):
                write_stmt();
                break;
            default:
                break;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * assign-stmt ::= identifier ":=" simple_expr
 */
public void assign_stmt() { 
    System.out.println("assign-stmt: " + current.toString());
    try {
        identifier();
        eat(Tag.ASSIGN);
        simple_expr();
    } catch (Exception e) {
        //TODO: handle exception
    }

}

/**
 * if-stmt ::= if condition then stmt-list end
*             | if condition then stmt-list else stmt-list end 
 */
public void if_stmt() {
    System.out.println("if-stmt: " + current.toString());
    try {
        switch (current.tag) {
            case Tag.IF:
                eat(Tag.IF);
                condition();
                eat(Tag.THEN);
                stmt_list();
                if_stmt_2();
                break;
            default:
                showError();
                break;
        }        
    } catch (Exception e) {
        //TODO: handle exception
    }
}

public void if_stmt_2(){
    System.out.println("if-stmt-2: " + current.toString());
    try {
        switch(current.tag){
            case Tag.END:
                eat(Tag.END);
            break;
            case Tag.ELSE:
                eat(Tag.ELSE);
                stmt_list();
                eat(Tag.END);
            break;
            default:
                showError();
                break;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * condition ::= expression
 */
public void condition() {
    System.out.println("condition: " + current.toString());
    try{
        expression();
    } catch (Exception e) {

    }
}

/**
 * repeat-stmt ::= repeat stmt-list stmt-suffix
 */
public void repeat_stmt() {
    System.out.println("repeat-stmt: " + current.toString());
    try {
        eat(Tag.REPEAT);
        stmt_list();
        stmt_suffix();
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * stmt-suffix ::= until condition
 */
public void stmt_suffix() {
    System.out.println("stmt-suffix: " + current.toString());
    try {
        eat(Tag.UNTIL);
        condition();
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * while-stmt ::= stmt-prefix stmt-list end
 */
public void while_stmt() {
    System.out.println("while-stmt: " + current.toString());
    try {
        stmt_prefix();
        stmt_list();
        eat(Tag.END);
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * stmt-prefix ::= while condition do
 */
public void stmt_prefix() {
    System.out.println("stmt-preffix: " + current.toString());
    try {
        eat(Tag.WHILE);
        condition();
        eat(Tag.DO);
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * read-stmt ::= read "(" identifier ")"
 */
public void read_stmt() {
    System.out.println("read-stmt: " + current.toString());
    try {
        eat(Tag.READ);
        eat(Tag.PAR_OPEN);
        identifier();
        eat(Tag.PAR_CLOSE);
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * write-stmt ::= write "(" writable ")"
 */
public void write_stmt () {
    System.out.println("write-stmt: " + current.toString());
    try {
        eat(Tag.WRITE);
        eat(Tag.PAR_OPEN);
        writable();
        eat(Tag.PAR_CLOSE);
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * writable ::= simple-expr | literal
 */
public void writable() {
    System.out.println("writable: " + current.toString());
    try {
        switch (current.tag) {
            case Tag.LITERAL:
                literal();
                break;
            default:
            case Tag.ID:
            case Tag.PAR_OPEN:
            case Tag.INT_CONST:
            case Tag.FLOAT_CONST:
            case Tag.NOT:
            case Tag.MINUS:
                simple_expr();
                break;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * expression ::= simple-expr | simple-expr relop simple-expr
 */
public void expression() {
    System.out.println("expression: " + current.toString());
    try {
        simple_expr();
        switch(current.tag){
            case Tag.EQ:
            case Tag.GT:
            case Tag.GE:
            case Tag.LT:
            case Tag.LE:
            case Tag.NE: 
                relop();
                expression(); // simple_expr();
            break;
        }
    } catch (Exception e) { 

    }
}

/**
 * simple-expr ::= term | simple-expr addop term
 */
public void simple_expr() {
    System.out.println("simple-expr: " + current.toString());
    try {
        term();
         switch (current.tag) {
            case Tag.SUM:
            case Tag.MINUS:
            case Tag.OR:
                addop();
                simple_expr(); //term()
                break;
            default:
                break;
         }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * term ::= factor-a | term mulop factor-a 
 */
public void term() {
    System.out.println("term: " + current.toString());
     try {
        factor_a();
            switch (current.tag) {
                case Tag.MULT:
                case Tag.DIV:
                case Tag.AND:
                    mulop();
                    factor_a();
                    break;
                default:
                    break;
            }
        
        } catch (Exception e) { }
}

/**
 * factor-a ::= factor | "!" factor | "-" factor
 */
public void factor_a() {
    System.out.println("factor-a: " + current.toString());
    try {
        switch (current.tag) {
            case Tag.NOT:
                eat(Tag.NOT);
                factor();
                break;
            case Tag.MINUS:
                eat(Tag.MINUS);
                factor();
                break;
            default:
                factor();
                break;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * factor ::= identifier | constant | "(" expression ")"
 */
public void factor() {
    System.out.println("factor: " + current.toString());
    try {
        switch (current.tag) {
            case Tag.PAR_OPEN:
                eat(Tag.PAR_OPEN);
                expression();
                eat(Tag.PAR_CLOSE);
                break;
            case Tag.FLOAT_CONST:
            case Tag.INT_CONST:
                constant();
                break;
            case Tag.ID:
                identifier();
                break;
            default:
                showError();
                break;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * relop ::= "=" | ">" | ">=" | "<" | "<=" | "!="
 */
public void relop() {
    System.out.println("relop: " + current.toString());
    try {
        switch (current.tag) {
            case Tag.EQ:
                eat(Tag.EQ);
                break;
            case Tag.GT:
                eat(Tag.GT);
                break;
            case Tag.GE:
                eat(Tag.GE);
                break;
            case Tag.LT:
                eat(Tag.LT);
                break;
            case Tag.LE:
                eat(Tag.LE);
                break;
            case Tag.NE:
                eat(Tag.NE);
                break;
            default:
            showError();
                break;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * addop ::= "+" | "-" | "||"
 */
public void addop() {
    System.out.println("addop: " + current.toString());
    try {
        switch (current.tag) {
            case Tag.SUM:
                eat(Tag.SUM);
                break;
            case Tag.MINUS:
                eat(Tag.MINUS);
                break;
            case Tag.OR:
                eat(Tag.OR);
                break;
            default:
                showError();
                break;
        }
    } catch (Exception e) {
       
    }
}

/**
 * mulop ::= "*" | "/" | "&&"
 */
public void mulop() {
    System.out.println("mulop: " + current.toString());
   try {
       switch (current.tag) {
           case Tag.AND:
               eat(Tag.AND);
               break;
            case Tag.DIV:
               eat(Tag.DIV);
               break;
            case Tag.MULT:
                eat(Tag.MULT);
                break;
           default:
               showError();
               break;
       }
   } catch (Exception e) {
      
   }
}

/**
 * constant ::= integer_const | float_const
 */
public void constant() {
    System.out.println("constant: " + current.toString());
    try {
        switch (current.tag) {
            case Tag.FLOAT_CONST:
                eat(Tag.FLOAT_CONST);
                break;
            case Tag.INT_CONST:
                eat(Tag.INT_CONST);
                break;
            default:
                showError();
                break;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * literal ::= "{" {caractere} "}"
 */
public void literal() {
    System.out.println("literal: " + current.toString());
    try {
    switch (current.tag) {
            case Tag.LITERAL:
                eat(Tag.LITERAL);
                break;
            default:
                showError();
                break;
        }
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * identifier ::= letter | "_" {letter | digit | "_"}
 */
public void identifier(){
    System.out.println("identifier: " + current.toString());
    try {
        eat(Tag.ID);
    } catch (Exception e) {
        //TODO: handle exception
    }
}

/**
 * caractere ::= 256 caracteres do ASCII, exceto "}" e quebra de linha
 */

/**
 * integer_const ::= digit {digit}
 */

/**
 * float_const ::= digit {digit} "." digit {digit}
 */

 /**
 * letter ::= [A-Za-z]
 */

/**
 * digit ::= [0-9]
 */

}
