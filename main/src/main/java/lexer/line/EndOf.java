package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class EndOf extends Line {

    /**
     * End of loop
     * End of function
     * TODO - SEE HOW TO HANDLE
     */

    private final LineType type = LineType.EndOf;
    private Map<String, TokenType> map;

    public EndOf(String inputLine) {
        super(inputLine);
        this.map = new HashMap<>();
    }

    @Override
    public void analyzeLine(String inputLine) {

        String[] words = inputLine.split(" ");

        this.map.put(words[0], TokenType.End);

    }

    public LineType getType() {
        return type;
    }
}
