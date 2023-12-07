package lexer.line;

import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public abstract class Line {

    //private abstract final LineType type;

    private String inputLine;
    private Map<String, TokenType> map;

    private String[] words;

    private String missingWords;

    public Line(String inputLine) {
        this.inputLine = inputLine;
        this.map = new HashMap<>();
        analyzeLine(inputLine);
    }

    public abstract void analyzeLine(String inputLine);  // popunjava mapu

    public abstract boolean syntaxChecker();
    public abstract boolean incorrectWord();
    public abstract boolean wordMissing();
    public abstract boolean invalidWordOrder();

    @Override
    public String toString() {
        String res = "";
        for(String str : map.keySet()){
            res = res.concat("KeyWord : " + str + " is of type: " + map.get(str) + "\n");
        }

        return res;
    }

    public Map<String, TokenType> getMap() {
        return map;
    }
}
