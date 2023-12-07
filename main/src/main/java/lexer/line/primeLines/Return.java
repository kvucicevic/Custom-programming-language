package lexer.line.primeLines;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Return extends Line {

    /**
     * Return the number value of min .
     * Return true .
     * Return -1 .
     * Return the number value of multiply ("num" "num" times) .    todo - nismo hendlali
     */

    private final LineType type = LineType.Return;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";
    private int optionFlag = 0;

    public Return(String inputLine) {
        super(inputLine);
        words = inputLine.split(" ");
    }

    @Override
    public void analyzeLine(String inputLine) {
        this.map = new HashMap<>();
        setOption(inputLine);

        if(!syntaxChecker()) // neka rec je izostavljena
            return;

        this.map.put(words[0], TokenType.Return);
        if(optionFlag == 1){
            this.map.put(words[2], TokenType.DataType);
            this.map.put(words[5], TokenType.VarName);
            this.map.put(words[6], TokenType.Punctuation);
        } else if(optionFlag == 2){
            this.map.put(words[1], TokenType.Boolean);
            this.map.put(words[2], TokenType.Punctuation);
        } else {
            this.map.put(words[1], TokenType.VarValue); // tj samo value, broj
            this.map.put(words[2], TokenType.Punctuation);
        }
    }

    public void setOption(String inputLine) {
        if(inputLine.contains("value of")){
            optionFlag = 1;
        } else if(inputLine.contains("true") || inputLine.contains("false")){
            optionFlag = 2;
        } else {
            optionFlag = 3;
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
        if (!words[0].equals("Return")) {
            missing = missing.concat(words[0]);
            return true;
        }

        if(optionFlag == 1) {
            if (!words[1].equals("the")) {
                missing = missing.concat(words[1]);
                return true;
            }
            if (!words[2].equals("number") && !words[2].equals("letter") && !words[2].equals("word")) {
                missing = missing.concat(words[2]);
                return true;
            }
            if (!words[3].equals("value")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (!words[4].equals("of")) {
                missing = missing.concat(words[4]);
                return true;
            }
            if(words[5].isEmpty() || words[5].equals(".") || words[5].equals(",")) // var name
                return true;

            if (!words[6].equals(".")) {
                missing = missing.concat(words[6]);
                return true;
            }

        } else {
            if(words[1].isEmpty() || words[1].equals(".") || words[1].equals(",")) // var name
                return true;
            if (!words[2].equals(".")) {
                missing = missing.concat(words[2]);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean wordMissing() {
        if(optionFlag == 1) {
            return words.length < 6;
        } else {
            return words.length < 3;
        }
    }

    @Override
    public boolean invalidWordOrder() {
        if(optionFlag == 1){
            return lineContains("Return the") && (lineContains("number")
                    || lineContains("letter") || lineContains("word")) && lineContains("of")
                    && lineContains(".");
        } else {
            return lineContains("Return") && lineContains(".");
        }
    }

    public boolean lineContains(String word){
        for(String str : words){
            if(str.equals(word))
                return true;
        }
        return false;
    }
}
