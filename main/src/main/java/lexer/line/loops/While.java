package lexer.line.loops;

import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class While extends Line {

    /**
     *  While it's true
     *  While it’s true that a is grater than b do:  STA AKO JE POZIV FUNKCIJI??
     */

    private final LineType type = LineType.While;

    private Map<String, TokenType> map;

    public While(String inputLine) {
        super(inputLine);
    }

    @Override
    public void analyzeLine(String inputLine) {

        this.map = new HashMap<>();
        String[] words = inputLine.split(" ");

        if(inputLine.contains("that")){
            this.map.put(words[2], TokenType.Boolean);
            this.map.put(words[4], TokenType.VarName);
            this.map.put(words[6], TokenType.Relation);
            this.map.put(words[8], TokenType.VarName);
        } else {
            this.map.put(words[2], TokenType.Boolean);
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
