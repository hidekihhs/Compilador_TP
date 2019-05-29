/**
 * Finalidade: classe principal do compilador.
 */
package compilador;
/**
 * @author daniele,hideki,padovani
 */
import analisadorlexico.*;
import java.io.IOException;
import java.util.Scanner;
import analisadorsintatico.*;

public class Compilador {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
		     
		if (args.length != 1) {
            System.out.println("Usage: java -cp build/classes compilador.Compilador test/[arquivo]");
            return;
        }
        

        /**
         * Segunda etapa: analise sintatica
         */
        Lexer l = new Lexer(args[0]);
        boolean teste;

        try {
            Syntactic s = new Syntactic(l);
            teste = s.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        
        /**
         * Primeira etapa: analise lexica
         *
        String s = "";
        Lexer lexer;

        lexer = new Lexer(args[0]);
    
        try {
            s = lexer.scan().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            s = "EOF"; 
        }

        System.out.println("\n\nTokens:");
        while (!s.equals("EOF")) {
			System.out.println(s);			   		       
	        try {
	            s = lexer.scan().toString();
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            s = "EOF"; 
	        }
				
        }
        System.out.println("\n\nTabela de símbolos\n<lexema,ID>");
        lexer.imprimeTabelaDeSimbolos();
        */
    }
}
