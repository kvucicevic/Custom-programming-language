package lexer.line.conditionals;

import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Else extends Line {

    /**
     *  else , do :
     *  else if .....
     */

    private final LineType type = LineType.Else;
    private Map<String, TokenType> map;

    public Else(String inputLine) {
        super(inputLine);
    }

    @Override
    public void analyzeLine(String inputLine) {

        this.map = new HashMap<>();
        String[] words = inputLine.split(" ");

        if(words.length < 5){
            this.map.put(words[1], TokenType.Punctuation);
            this.map.put(words[3], TokenType.Punctuation);
        } else {
            this.map.put(words[4], TokenType.Boolean);
            if(words[6].contains("\"")) {
                this.map.put(words[6], TokenType.Argument);
            } else {
                this.map.put(words[6], TokenType.VarName);
            }

            this.map.put(words[8], TokenType.Relation);
        }

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

    public LineType getType() {
        return type;
    }
}
