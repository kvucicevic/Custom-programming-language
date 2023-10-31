package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Write extends Line {

    /**
     * Write "Hello" .
     * Write the number content of res .
     */

    private final LineType type = LineType.ConsoleWrite;

    private Map<String, TokenType> map;

    public Write(String inputLine) {
        super(inputLine);
        this.map = new HashMap<>();
    }

    @Override
    public void analyzeLine(String inputLine) {

        String[] words = inputLine.split(" ");

        if(inputLine.contains("\"")){
            this.map.put(words[1], TokenType.VarValue);
            this.map.put(words[2], TokenType.Punctuation);
        } else {
            this.map.put(words[2], TokenType.DataType);
            if(words[5].contains("\"")) {
                this.map.put(words[5], TokenType.Argument);
            } else {
                this.map.put(words[5], TokenType.VarName);
            }
            this.map.put(words[6], TokenType.Punctuation);
        }
    }

    public LineType getType() {
        return type;
    }
}