package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Arithmetics extends Line {

    /**
     *  Add ( 1 to j ) then put it in number a .
     */

    private final LineType type = LineType.Arithmetics;
    private Map<String, TokenType> map;

    public Arithmetics(String inputLine) {
        super(inputLine);
        this.map = new HashMap<>();
    }

    @Override
    public void analyzeLine(String inputLine) {

        String[] words = inputLine.split(" ");

        this.map.put(words[0], TokenType.Arithmetics);
        this.map.put(words[2], TokenType.VarValue);
        this.map.put(words[4], TokenType.VarName);
        this.map.put(words[10], TokenType.DataType);
        this.map.put(words[11], TokenType.VarName);
        this.map.put(words[12], TokenType.Punctuation);

    }

    public LineType getType() {
        return type;
    }
}
