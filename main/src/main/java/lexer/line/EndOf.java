package lexer.line;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class EndOf extends Line {

    /**
     * End of loop .
     * End of function .
     * TODO - SEE HOW TO HANDLE
     */

    private final LineType type = LineType.EndOf;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";

    public EndOf(String inputLine) {
        super(inputLine);
        words = inputLine.split(" ");
    }

    @Override
    public void analyzeLine(String inputLine) {
        this.map = new HashMap<>();

        this.map.put(words[0], TokenType.End);
        this.map.put(words[3], TokenType.Punctuation);
    }

    @Override
    public boolean syntaxChecker() {
        if(wordMissing()){
            ErrorHandler.getInstance().printError(ErrorType.WordMissing, null);
            return false;
        }
        if(incorrectWord()) {
            ErrorHandler.getInstance().printError(ErrorType.WordMissing, missing);
            return false; // postoji greska
        }
        if(invalidWordOrder()) {
            ErrorHandler.getInstance().printError(ErrorType.WrongWordOrder, null);
            return false;
        }
        return true;
    }

    @Override
    public boolean incorrectWord() {
        if (!words[0].equals("End")) {
            missing = missing.concat(words[0]);
            return true;
        }
        if (!words[1].equals("of")) {
            missing = missing.concat(words[1]);
            return true;
        }
        if (!words[2].equals("loop") && !words[2].equals("function")) {
            missing = missing.concat(words[2]);
            return true;
        }
        return false;
    }

    @Override
    public boolean wordMissing() {
        return words.length < 3;
    }

    @Override
    public boolean invalidWordOrder() {
        return lineContains("End of") && (lineContains("function")
                    || lineContains("loop")) && lineContains(".");
    }

    public boolean lineContains(String word){
        for(String str : words){
            if(str.equals(word))
                return true;
        }
        return false;
    }

    public LineType getType() {
        return type;
    }
}
