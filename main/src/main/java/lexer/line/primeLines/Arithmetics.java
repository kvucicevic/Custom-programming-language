package lexer.line.primeLines;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Arithmetics extends Line {

    /**
     *  Add ( 1 to j ) then put it in number a .
     */

    private final LineType type = LineType.Arithmetics;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";

    public Arithmetics(String inputLine) {
        super(inputLine);
        words = inputLine.split(" ");
    }

    @Override
    public void analyzeLine(String inputLine) {
        this.map = new HashMap<>();

        this.map.put(words[0], TokenType.Arithmetics);
        this.map.put(words[2], TokenType.VarValue);
        this.map.put(words[4], TokenType.VarName);
        this.map.put(words[10], TokenType.DataType);
        this.map.put(words[11], TokenType.VarName);
        this.map.put(words[12], TokenType.Punctuation);
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
        if (!words[0].equals("Add")) {
            missing = missing.concat(words[0]);
            return true;
        }
        if (!words[1].equals("(")) {
            missing = missing.concat(words[1]);
            return true;
        }
        if (words[2].isEmpty() || words[2].equals(".") || words[2].equals(",")) {
            return true;
        }
        if (!words[3].equals("to")) {
            missing = missing.concat(words[3]);
            return true;
        }
        if (words[4].isEmpty() || words[4].equals(".") || words[4].equals(",")) {
            return true;
        }
        if (!words[5].equals(")")) {
            missing = missing.concat(words[5]);
            return true;
        }
        if (!words[6].equals("then")) {
            missing = missing.concat(words[6]);
            return true;
        }
        if (!words[7].equals("put")) {
            missing = missing.concat(words[7]);
            return true;
        }
        if (!words[8].equals("it")) {
            missing = missing.concat(words[8]);
            return true;
        }
        if (!words[9].equals("in")) {
            missing = missing.concat(words[9]);
            return true;
        }
        if (!words[10].equals("number")) {
            missing = missing.concat(words[10]);
            return true;
        }
        if(words[11].isEmpty() || words[11].equals(".") || words[11].equals(",")) // var name
            return true;
        if (!words[12].equals(".")) {
            missing = missing.concat(words[12]);
            return true;
        }
        return false;
    }

    @Override
    public boolean wordMissing() {
        return words.length < 13;
    }

    @Override
    public boolean invalidWordOrder() {
        return lineContains("Add (") && lineContains("to")
                    && lineContains(") then put it in") && (lineContains("number")
                    || lineContains("letter") || lineContains("word")) && lineContains(".");
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
