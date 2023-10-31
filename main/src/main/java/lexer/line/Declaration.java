package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Declaration extends Line {

    /**
     *  Declare number len .
     *  Declare big decimal number result . TODO - hendlati ove dablove
     */

    private final LineType type = LineType.Declaration;
    private Map<String, TokenType> map;

    public Declaration(String inputLine) {
        super(inputLine);
        this.map = new HashMap<>();
    }

    @Override
    public void analyzeLine(String inputLine) {

        String[] words = inputLine.split(" ");

        this.map.put(words[1], TokenType.DataType);
        this.map.put(words[2], TokenType.VarName);
        this.map.put(words[3], TokenType.Punctuation);
    }

    public LineType getType() {
        return type;
    }
}
