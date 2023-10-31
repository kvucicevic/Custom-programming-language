package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class If extends Line {

    /**
     * If it is true that a is higher then b, then :
     * If it is true that "num" is lower than 0 , then :
     * If it is true that "startIdx" is higher than arr (of i), then:   KASNIJE!!!
     */

    private final LineType type = LineType.If;
    private Map<String, TokenType> map;

    public If(String inputLine) {
        super(inputLine);
        this.map = new HashMap<>();
    }

    @Override
    public void analyzeLine(String inputLine) {

        String[] words = inputLine.split(" ");

        this.map.put(words[3], TokenType.Boolean);
        if(words[5].contains("\"")) {
            this.map.put(words[5], TokenType.Argument);
        } else {
            this.map.put(words[5], TokenType.VarName);
        }

        this.map.put(words[7], TokenType.Relation);
    }
}
