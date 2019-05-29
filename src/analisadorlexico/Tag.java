/*
 * Finalidade: classe que define as constantes para os tokens.
 */
package analisadorlexico;
/**
 * @author daniele,hideki,padovani
 */
public class Tag {
    public final static int
		//Palavras reservadas
        APP     	  = 256,
        START   	  = 257,
        STOP    	  = 258,
        INTEGER 	  = 259,
        REAL    	  = 260,
        IF      	  = 261,
        THEN    	  = 262,
        END     	  = 263,
        ELSE    	  = 264,
        REPEAT  	  = 265,
        UNTIL   	  = 266,
        DO      	  = 267,
        WHILE   	  = 268,
        READ    	  = 269,
        WRITE   	  = 270,
        //Operadores e pontuação
        ASSIGN    	  = 271, // :=    
        NOT       	  = 272, // !    
        EQ        	  = 273, // =
        GT        	  = 274, // >
        GE        	  = 275, // >=
        LT        	  = 276, // <
        LE        	  = 277, // <=
        NE        	  = 278, // !=     
        SUM       	  = 279, // +
        MINUS         = 280, // - 
        OR        	  = 281, // ||  
        MULT      	  = 282, // *
        DIV       	  = 283, // /
        AND       	  = 284, // &&
        PAR_OPEN  	  = 285, // (
        PAR_CLOSE 	  = 286, // )
        BRA_OPEN  	  = 296, // (
        BRA_CLOSE 	  = 297, // )
        COMMA     	  = 287, // ,
        SEMI      	  = 288, // ;  
        //Outros Tokens
        INT_CONST     = 289,
        FLOAT_CONST   = 290, 
        DIGIT         = 291,
        LITERAL       = 292,
        ID            = 293,
        EOF           = 294,
        INVALID_TOKEN = 295;
}

