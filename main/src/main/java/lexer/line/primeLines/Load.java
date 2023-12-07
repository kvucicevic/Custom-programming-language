package lexer.line.primeLines;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Load extends Line {

    /**
     * 1. Option: Load number a .
     * 2. Option: Load numbers a , b .
     */

    private final LineType type = LineType.Load;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";
    private int optionFlag = 0;

    public Load(String inputLine) {
        super(inputLine);
        words = inputLine.split(" ");
    }

    @Override
    public void analyzeLine(String inputLine) {
        this.map = new HashMap<>();

        setOption(inputLine);

        if(!syntaxChecker()) // neka rec je izostavljena
            return;

        this.map.put(words[0], TokenType.KeyWord);

        if(optionFlag == 2){ // 2. option
            this.map.put(words[1], TokenType.DataType);
            this.map.put(words[3], TokenType.VarName);
            this.map.put(words[4], TokenType.Punctuation);
            this.map.put(words[5], TokenType.VarName);
            this.map.put(words[6], TokenType.Punctuation);
        } else { // 1. option
            this.map.put(words[1], TokenType.DataType);
            this.map.put(words[2], TokenType.VarName);
            this.map.put(words[3], TokenType.Punctuation);
        }

    }

    public void setOption(String inputLine) {
        if(inputLine.contains("number")){
            optionFlag = 1;
        } else {
            optionFlag = 2;
        }
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
        if (!words[0].equals("Load")) {
            missing = missing.concat(words[0]);
            return true;
        }

        if(optionFlag == 2) {

            if (!words[1].equals("numbers") && !words[1].equals("letters") && !words[1].equals("words")) {
                missing = missing.concat(words[1]);
                return true;
            }

            if(words[2].isEmpty() || words[2].equals(".") || words[2].equals(",")) // var name
                return true;

            if (!words[3].equals(",")) {
                missing = missing.concat(words[3]);
                return true;
            }

            if (words[4].isEmpty() || words[4].equals(".") || words[4].equals(",")) {
                return true;
            }

            if (!words[5].equals(".")) {
                missing = missing.concat(words[5]);
                return true;
            }
        } else {
            if (!words[1].equals("number") && !words[1].equals("letter") && !words[1].equals("word")) {
                missing = missing.concat(words[1]);
                return true;
            }

            if (words[2].isEmpty() || words[2].equals(".")) {
                missing = missing.concat(words[2]);
                return true;
            }
            if (!words[3].equals(".")) {
                missing = missing.concat(words[3]);
                return true;
            }
        }
        return false; // ne fali ni jedna rec
    }

    @Override
    public boolean wordMissing() {
        if(optionFlag == 2) {
            if (words.length < 7) {
                return true;
            }
        }
        if(optionFlag == 1){
            if (words.length < 4) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean invalidWordOrder() {
        if(optionFlag == 2){ // 2. option
            return lineContains("Load") && (lineContains("numbers") || lineContains("letters")
                    || lineContains("words")) && lineContains(",") && lineContains(".");
        } else { // 1. option
            return lineContains("Load") && (lineContains("number") || lineContains("letter")
                    || lineContains("word")) && lineContains(".");
        }
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
