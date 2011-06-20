package trm.syntactic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import trm.lexical.ILexical;
import trm.lexical.LexicalAnalyzer;
import trm.lexical.Token;
import trm.lexical.TokenClass;
import trm.syntactic.Instruction.InstructionType;

/**
 *
 */
public class SyntacticAnalyserImpl implements SyntacticAnalyser {
    
    private Set<InstructionType> blocksType;
    
    private List<Instruction> instructions;
    /**
     * usado para registrar os blocos de código, sabe onde eles começão e 
     * terminam
     */
    private Stack<Instruction> blocks;
    
    public SyntacticAnalyserImpl() {
        instructions = new ArrayList<Instruction>();
        
        blocks = new Stack<Instruction>();
        blocksType = new HashSet<InstructionType>() {{
            add(InstructionType.FOR);
            add(InstructionType.FUNCTION);
            add(InstructionType.WHILE);
            add(InstructionType.IF);
            add(InstructionType.ELSE);
        }};
    }
    
    @Override
    public void parse(ILexical lexical) {
        //primeira chamada de tem que ser a declaração de uma função
        Token token = null;
        while ((token = lexical.nextToken()) != null) {
            switch (token.getTokenClass()) {
                
                case TK_ID:
                    Instruction functionInstruction = 
                            new FuncitionAnalayser(lexical).analyze(token);
                    
                    instructions.add(functionInstruction);
                    pushBlock();
                    
                    instruction(lexical);
                    break;
                case TK_EOF:
                    break;
                default:
                    erro(token);
            }
        }
        
    }
    
    public List<Instruction> getInstructions() {
        return Collections.unmodifiableList(instructions);
    }
    
    private void pushBlock() {
        
        int start = instructions.size() - 1;
        
        Instruction instruction = instructions.get(start);
        if (!blocksType.contains(instruction.getType())) {
            throw new RuntimeException("essa instrução não é de bloco");
        }
        
        instruction.setStart(start);
        
        blocks.push(instruction);
    }
    
    /**
     * metodo que marca o fim de um bloco de código
     */
    private void popBlock() {
        
        //ultima instrução do bloco
        int end = instructions.size() - 1;
        
        //instrução do inicio do bloco
        Instruction startBlock = blocks.pop();

        if (startBlock == null) {
            //TODO colocar informação sobre o erro
            throw new RuntimeException("erro no bloco de codigo");
        }
        
        startBlock.setEnd(end);
    }
    
    private void saveInstruction(Instruction instruction) {
        instructions.add(instruction);
        if (blocksType.contains(instruction.getType())) {
            pushBlock();
        }

    }
    
    private void instruction(ILexical lexical) {
        Token token = null;
        
        here : while ((token = lexical.nextToken()) != null) {
            
            
            switch (token.getTokenClass()) {
                case TK_ID:
                    saveInstruction(
                            new CommandAnalyserImpl(lexical).analyze(token));
                    break;
                case TK_FOR:
//                    ((LexicalAnalyzer) lexical).putToken(token);
                    //analisar se o 'for' é valido, marcar seu inicio
                    saveInstruction(new ForAnalayser(lexical).analyze(token));
//                    saveInstruction(null);
                    break;
                    
                case TK_WHILE:
                    //analisar se o 'while' é valido, marcar seu inicio
                    saveInstruction(new WhileAnalayser(lexical).analyze(token));
                    break;
                case TK_IF:
                    //analisar se o 'if' é valido, marcar seu inicio
                    saveInstruction(new IfAnalayser(lexical).analyze(token));
                    break;
                case TK_ELSE:
                    //analisar se o 'else' é valido, marcar seu inicio
                    saveInstruction(new ElseAnalayser(lexical).analyze(token));
                    break;
                case TK_CLOSE_CURLY_BRACKET:
                    //verificar a quem ele pertecence
                    final Token temp = token;
                    saveInstruction(new Instruction(InstructionType.END_BLOCK,
                            new ArrayList<Token>(){{add(temp);}}));
                    popBlock();
                    
                    if (blocks.isEmpty()) {
                        break here;
                    }
                    break;
                default:
                    erro(token);
            }
        }
        //fazer analise do codigo dentro da função
        
    }
    
    private void erro(Token token) {
        throw new RuntimeException("erro no token " + token);
        
    }
    public static void main(String[] args) {
//        here :while (true) {
//            int i = 1;
//            switch (i)  {
//                case 1:
//                    break here;
//                        
//            }
//        }
        SyntacticAnalyserImpl syntacticAnalyser = new SyntacticAnalyserImpl();
        
        
        LexicalAnalyzer lexical = new LexicalAnalyzer("x_test");
//        FuncitionAnalayser parserId = new FuncitionAnalayser(lexical);
//        parserId.analyze(lexical.nextToken());
        syntacticAnalyser.parse(lexical);
        
        for (Instruction instruction: syntacticAnalyser.getInstructions()) {
            System.out.println("instruction type : " + instruction.getType());
            System.out.println("end = " + instruction.getEnd());
//            System.out.println("end = " + );
            
//            for (Token token: intruction.getTokens()) {
//                System.out.print(" " + token);
//            }
//            System.out.println("");
            
        }
//
//        for (Token token : parserId.getTokens()) {
//            System.out.println(token.getTokenClass());
//
//        }
    }

    /**
     * analisa a declaração de uma função
     */
    private static class FuncitionAnalayser extends CommandAnalyser {

        public FuncitionAnalayser(ILexical lexical) {
            super(TokenClass.TK_ID, lexical);
        }

        @Override
        protected InstructionType doAnalysis(Token token) {

            ((LexicalAnalyzer) lexical).putToken(token);
            
            
            GLC glcFuntionDeclaration = GLCFacotory.createGLCFuntionDeclaration();
            analysi(glcFuntionDeclaration, null, true, true);

            return InstructionType.FUNCTION;
        }

    }
    
     private static class ForAnalayser extends CommandAnalyser {

        public ForAnalayser(ILexical lexical) {
            super(TokenClass.TK_FOR, lexical);
        }

        @Override
        protected InstructionType doAnalysis(Token token) {

            ((LexicalAnalyzer) lexical).putToken(token);
            
            
            GLC glcFuntionDeclaration = GLCFacotory.createGLCFor();
            analysi(glcFuntionDeclaration, null, true, true);

            return InstructionType.FOR;
        }

    }
     
    private static class WhileAnalayser extends CommandAnalyser {

        public WhileAnalayser(ILexical lexical) {
            super(TokenClass.TK_WHILE, lexical);
        }

        @Override
        protected InstructionType doAnalysis(Token token) {

            ((LexicalAnalyzer) lexical).putToken(token);
            
            
            GLC glcFuntionDeclaration = GLCFacotory.createGLCWhile();
            analysi(glcFuntionDeclaration, null, true, true);

            return InstructionType.WHILE;
        }

    }
    
      private static class IfAnalayser extends CommandAnalyser {

        public IfAnalayser(ILexical lexical) {
            super(TokenClass.TK_IF, lexical);
        }

        @Override
        protected InstructionType doAnalysis(Token token) {

            ((LexicalAnalyzer) lexical).putToken(token);
            
            
            GLC glcFuntionDeclaration = GLCFacotory.createGLCIf();
            analysi(glcFuntionDeclaration, null, true, true);

            return InstructionType.IF;
        }

    }
      
    private static class ElseAnalayser extends CommandAnalyser {

        public ElseAnalayser(ILexical lexical) {
            super(TokenClass.TK_ELSE, lexical);
        }

        @Override
        protected InstructionType doAnalysis(Token token) {

            ((LexicalAnalyzer) lexical).putToken(token);


            GLC glcFuntionDeclaration = GLCFacotory.createGLCElse();
            analysi(glcFuntionDeclaration, null, true, true);

            return InstructionType.ELSE;
        }
    }

}
