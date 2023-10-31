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
    }

    @Override
    public void analyzeLine(String inputLine) {

        this.map = new HashMap<>();
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

    @Override
    public String toString() {
        String res = "";
        for(String str : map.keySet()){
            res = res.concat("KeyWord: " + "\"" + str + "\"" + " is of type: " + map.get(str) + "\n");
        }
        return res;
    }

    public LineType getType() {
        return type;
    }
}
