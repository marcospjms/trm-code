package trm.syntactic;

import trm.lexical.ILexical;
import trm.lexical.LexicalAnalyzer;
import trm.lexical.Token;
import trm.lexical.TokenClass;
import trm.syntactic.Instruction.InstructionType;

/**
 *  
 */
public class CommandAnalyserImpl extends CommandAnalyser {
    
    public CommandAnalyserImpl(ILexical lexical) {
        
        super(TokenClass.TK_ID, lexical);
    }
    
    @Override
    protected InstructionType doAnalysis(Token token) {
        Token temp = lexical.nextToken();
        
        //olhando para frente e readicionado...
        ((LexicalAnalyzer)lexical).putToken(token);
        ((LexicalAnalyzer)lexical).putToken(temp);

        switch (temp.getTokenClass()) {
            //se ele ler esse token a instrução possivelmente sera uma declaração
            case TK_COMMA:
            case TK_COLON:
                GLC declaration = GLCFacotory.createGLCDeclaration();
                analysiInstruction(declaration);
                
                return InstructionType.DECLARATION;
            //se ele ler esse token a instrução possivelmente sera uma atribuição
            case TK_OPEN_SQUARE_BRACKET:
            case TK_ATTRIBUTION:
                GLC atribution = GLCFacotory.createGLCAtribuition();
                analysiInstruction(atribution);
                return InstructionType.ATTRIBUTION;
            //se ele ler esse token a instrução possivelmente sera uma chamada
            //de função
            case TK_OPEN_PARENTHESES:
                //gambiarra!
                //tirando token id dos que vão ser analisado
                //a glc de chamada de função está considerando que a chamada de 
                //função ja começa com o token '('
                lexical.nextToken();
                //implementar a GLC para chamada de função
                GLC functionCall = GLCFacotory.createGLCFunctionCall();
                analysiInstruction(functionCall);
                return InstructionType.FUNCTION_CALL;
            default:
                erro();
        }
        //nunca vai chegar aqui!
        throw new RuntimeException("erro desconhecido");
    }

}