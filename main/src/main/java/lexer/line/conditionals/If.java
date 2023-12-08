package lexer.line.conditionals;

import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class If extends Line {

    /**
     * If it is true that a is higher than b , then :
     * If it is true that a is higher than b and b is lower than c , then :
     * If it is true that "num" is lower than 0 , then :
     * If it is true that "startIdx" is higher than arr (of i), then:  todo KASNIJE!!! UZETI U OBZIR LOGICKE OPERATORE
     */

    private final LineType type = LineType.If;
    private Map<String, TokenType> map;

    public If(String inputLine) {
        super(inputLine);
    }

    @Override
    public void analyzeLine(String inputLine) {

        this.map = new HashMap<>();
        String[] words = inputLine.split(" ");

        this.map.put(words[3], TokenType.Boolean);
        if(words[5].contains("\"")) {
            this.map.put(words[5], TokenType.Argument);
        } else {
            this.map.put(words[5], TokenType.VarName);
        }

        this.map.put(words[7], TokenType.Relation);
    }

    @Override
    public boolean syntaxChecker() {
        return false;
    }

    @Override
    public boolean incorrectWord() {
        return false;
    }

    @Override
    public boolean wordMissing() {
        return false;
    }

    @Override
    public boolean invalidWordOrder() {
        return false;
    }
}
