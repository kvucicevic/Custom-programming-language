package lexer.line.primeLines;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Declaration extends Line {

    /**
     *  Declare number len .
     *  Declare bigDecimalNumber result . TODO - hendlati ove dablove
     */

    private final LineType type = LineType.Declaration;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";

    public Declaration(String inputLine) {
        super(inputLine);
        words = inputLine.split(" ");
    }

    @Override
    public void analyzeLine(String inputLine) {
        this.map = new HashMap<>();

        if(!syntaxChecker()) // neka rec je izostavljena
            return;

        this.map.put(words[0], TokenType.KeyWord);
        this.map.put(words[1], TokenType.DataType);
        this.map.put(words[2], TokenType.VarName);
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
        if (!words[0].equals("Declare")) {
            missing = missing.concat(words[0]);
            return true;
        }
        if (!words[1].equals("numbers") && !words[1].equals("letters") && !words[1].equals("words")) {
            missing = missing.concat(words[1]);
            return true;
        }
        if (words[2].isEmpty() || words[2].equals(".") || words[2].equals(",")) {
            missing = missing.concat(words[2]);
            return true;
        }
        if (!words[3].equals(".")) {
            missing = missing.concat(words[3]);
            return true;
        }
        return false;
    }

    @Override
    public boolean wordMissing() {
        if(words.length < 4)
            return true;
        return false;
    }

    @Override
    public boolean invalidWordOrder() {
        return lineContains("Declare") && (lineContains("number")
                || lineContains("letter") || lineContains("word"))
                && lineContains(".");
    }

    public boolean lineContains(String word){
        for(String str : words){
            if(str.equals(word))
                return true;
        }
        return false;
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
