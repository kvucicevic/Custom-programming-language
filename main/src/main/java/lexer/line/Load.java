package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Load extends Line {

    /**
     * Load number a .
     * Load numbers a , b .
     */

    private final LineType type = LineType.Load;
    private Map<String, TokenType> map;

    public Load(String inputLine) {
        super(inputLine);
        this.map = new HashMap<>();
    }

    @Override
    public void analyzeLine(String inputLine) {
        String[] words = inputLine.split(" ");

        if(inputLine.contains("numbers")){
            this.map.put(words[1], TokenType.DataType);
            this.map.put(words[2], TokenType.VarName);
            this.map.put(words[3], TokenType.Punctuation);
            this.map.put(words[4], TokenType.VarName);
            this.map.put(words[5], TokenType.Punctuation);
        } else {
            this.map.put(words[1], TokenType.DataType);
            this.map.put(words[2], TokenType.VarName);
            this.map.put(words[3], TokenType.Punctuation);
        }

    }

    public LineType getType() {
        return type;
    }
}
