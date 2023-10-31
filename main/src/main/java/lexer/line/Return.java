package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Return extends Line {

    /**
     * Return the number value of min .
     * Return -1 .
     * Return true .
     * Return the number value of multiply ("num" "num" times) . nismo hendlali
     */

    private final LineType type = LineType.Return;

    private Map<String, TokenType> map;

    public Return(String inputLine) {
        super(inputLine);
        this.map = new HashMap<>();
    }

    @Override
    public void analyzeLine(String inputLine) {
        String[] words = inputLine.split(" ");

        if(inputLine.contains("value of") && !inputLine.contains("(")){
            this.map.put(words[0], TokenType.Return);
            this.map.put(words[2], TokenType.DataType);
            this.map.put(words[5], TokenType.VarName);
            this.map.put(words[6], TokenType.Punctuation);
        } else if(inputLine.contains("true") || inputLine.contains("false")){
            this.map.put(words[0], TokenType.Return);
            this.map.put(words[1], TokenType.Boolean);
            this.map.put(words[2], TokenType.Punctuation);
        } else {
            this.map.put(words[0], TokenType.Return);
            this.map.put(words[1], TokenType.VarValue); // tj samo value, broj
            this.map.put(words[2], TokenType.Punctuation);
        }
    }
}
