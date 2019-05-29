/*
 * Finalidade: representa um token genérico. 
 * Contém a constante que representa o token.
 */
package analisadorlexico;
/**
 * @author daniele,hideki,padovani
 */
public class Token {
	//constante que representa o token
    public final int tag; 
    public Token(int t) {
        tag = t;
    }
    @Override
    public String toString() {
        return "" + tag;
    }
}
