# Implementação de um Compilador
### PARTE 2: Analisador Sintático
________________________________________________________________________________
* Centro Federal de Educação Tecnológica de Minas Gerais 					   
* Engenharia de Computação - Disciplina de Compiladores                                            
* Alunos: Daniele de S. Lima, Gabriel F. Padovani, Henrique H. Sampaio		    ________________________________________________________________________________

### Referência: 
Implementação baseada no front-end do livro

Compiladores: Princípios, técnicas e ferramentas (Alfred et. al.), 2a Edição.

### Execução: 
1. javac -cp . src/compilador/Compilador.java src/analisadorlexico/*.java src/analisadorsintatico/*.java -d build/classes

1. java -cp build/classes compilador.Compilador test/[arquivo]

OBS.: deve-se substituir "[arquivo]" pelo nome e extensão do arquivo a ser 
considerado, por exemplo "teste1.txt"
