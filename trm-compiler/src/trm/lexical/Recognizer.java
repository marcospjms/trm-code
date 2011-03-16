package trm.lexical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que verifica se uma fita é reconhecida por um automato.
 */
public class Recognizer {

    private static final Logger LOGGER = Logger.getLogger(Recognizer.class.getName());

    private Automaton automaton;

    private State currentState;
    private StringBuilder currentWord;

    private List<Token> tokens;

    private char[] tape;

    private Map<String, TokenClass> tokenMap;
    
    public Recognizer(Automaton automaton, char[] tape) {

        this.automaton = automaton;

        this.tape = tape;
        
        this.tokens = new ArrayList<Token>();

        this.tokenMap = new HashMap<String, TokenClass>();

        this.tokenMap.put("enquanto", TokenClass.TK_WHILE);

        this.tokenMap.put("escolha", TokenClass.TK_SWITCH);

        this.tokenMap.put("se", TokenClass.TK_IF);

        reset();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private void reset() {
        this.currentState = automaton.getStartState();
        this.currentWord = new StringBuilder();
    }

    private void generateToken() {

        if (!automaton.getFinalStates().contains(currentState)) {
            throw new RuntimeException("Impossivel gerar token.");
        }

        String value = currentWord.toString().trim();

        Token token = new Token(value, getTokenClass(currentState, value));
        tokens.add(token);

        LOGGER.log(Level.INFO, "token criado " + token, token);

    }
    /**
     * Verifica se a fita é reconhecida pelo automato.
     */
    public void run() {

        reset();
        
        for (char c : tape) {

            try {

                transition(c);
                currentWord.append(c);

            } catch (TransitionException e) {

                generateToken();
                reset();

            }
        }

        generateToken();

    }

    //FIXME terminar de implementar esse metodo.
    private TokenClass getTokenClass(State state, String word) {
        
        TokenClass result = null;

        switch(state.getDescription().getWordType()) {

            case WORD:
                result = tokenMap.get(word);
                if (result == null) {
                    result = TokenClass.TK_ID;
                }
                break;
            case INTEGER_CTE:
                result = TokenClass.TK_INTEGER_CTE;
                break;
            case FLOATING_CTE:
                result = TokenClass.TK_FLOATING_CTE;
                break;

        }

        return result == null ? TokenClass.TK_UNDEFINED : result;
    }

    private void transition(char c) {

        State target = automaton.nextState(currentState, c);


        if (target == null) {
            throw new TransitionException("Transição para esse valor não "
                    + "encontrada.");
        }

        currentState = target;
    }
}

/**
 * Exceção que sinaliza quando não existe mais transição em um estado para
 * leitura de um valor.
 */
class TransitionException extends RuntimeException {

    public TransitionException(Throwable cause) {
        super(cause);
    }

    public TransitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransitionException(String message) {

        super(message);
    }
}
