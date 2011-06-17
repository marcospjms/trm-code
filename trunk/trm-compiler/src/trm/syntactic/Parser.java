package trm.syntactic;

import java.util.List;
import java.util.Stack;
import trm.lexical.ILexical;
import trm.lexical.Token;
import trm.lexical.TokenClass;

public class Parser implements SyntacticAnalyser {

    private PreditiveTable table;
    private Stack<Element> variables;

    public Parser(PreditiveTable table, Variable initial) {
        this.table = table;
        variables = new Stack<Element>();
        variables.push(new Terminal(TokenClass.TK_EOF));
        variables.push(initial);
    }

    @Override
    public void parse(ILexical lexical) {
        Token token = null;

        while ((token = lexical.nextToken()) != null) {

            if (variables.isEmpty()) {
                erro(token);
            }

            Terminal terminal = new Terminal(token.getTokenClass());

            Element top = null;

            while (!((top = variables.pop()) instanceof Terminal)) {
                Variable var = (Variable) top;
                Derivation derivation = table.getDerivation(var, terminal);

                

                System.out.println(var.getLabel() + ", " + terminal.getLabel() + " = " + derivation);

                if(derivation == null) {
                    erro(token);
                }
                List<Element> elements = derivation.getTargets();

                for (int i = elements.size() - 1; i >= 0; i--) {
                    variables.push(elements.get(i));
                }
            }
            if (!top.equals(terminal)) {
                erro(token);
            }
        }
    }

    private void erro(Token token) {
        throw new RuntimeException("erro de sintaxe em: " + token.getLine() + ", " + token.getcolumn());
    }
}