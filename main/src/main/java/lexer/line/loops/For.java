package lexer.line.loops;

import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class For extends Line {

    /**
     * For values of counter i from 0 to len , do :
     * For values of counter i from add (1 to "startIdx") to subtract (1 from len) , do :
     * For values of counter i from 0 to subtract (1 from "y") , do :
     * For values of counter i from "end" to add(1 to "start") step -1 , do :
     * For values of counter k from j until "word" (of k) is not equal to EOS , do :
     */

    private final LineType type = LineType.For;
    private Map<String, TokenType> map;

    public For(String inputLine) {
        super(inputLine);
    }

    @Override
    public void analyzeLine(String inputLine) {

        this.map = new HashMap<>();
        String[] words = inputLine.split(" ");

        this.map.put(words[3], TokenType.DataType);
        this.map.put(words[4], TokenType.VarName);

        this.map.put(words[6], TokenType.VarValue);
        this.map.put(words[8], TokenType.VarValue);

    }

    public LineType getType() {
        return type;
    }
}
