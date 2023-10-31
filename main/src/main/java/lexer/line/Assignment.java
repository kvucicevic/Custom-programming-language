package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Assignment extends Line {

    /**
     *  The number len takes value of length (of "arr") .
     *  The number min takes value of 0 .
     *  The letter "word" (of k) takes value of letter "word" (of k+1) . TODO - VIDECEMO
     */

    private final LineType type = LineType.Assignment;
    private Map<String, TokenType> map;

    public Assignment(String inputLine) {
        super(inputLine);
        this.map = new HashMap<>();
    }

    @Override
    public void analyzeLine(String inputLine) {

        String[] words = inputLine.split(" ");

        this.map.put(words[1], TokenType.DataType);
        this.map.put(words[2], TokenType.VarName);
        this.map.put(words[6], TokenType.VarValue);
        this.map.put(words[7], TokenType.Punctuation);

    }
}
