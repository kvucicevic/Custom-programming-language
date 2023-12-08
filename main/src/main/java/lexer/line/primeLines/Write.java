package lexer.line.primeLines;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Write extends Line {

    /**
     * Write "Hello" .
     * Write the number content of res .
     */

    private final LineType type = LineType.ConsoleWrite;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing;
    private String inputLine;

    public Write(String inputLine) {
        super(inputLine);
    }

    @Override
    public void analyzeLine(String inputLine) {
        this.map = new HashMap<>();
        this.inputLine = inputLine;
        this.missing = "";
        this.words = inputLine.split(" ");
        setOption(inputLine);

        if(!syntaxChecker()) // neka rec je izostavljena
            return;

        this.map.put(words[0], TokenType.KeyWord);
        if(getOptionFlag() == 1){
            this.map.put(words[1], TokenType.VarValue);
            this.map.put(words[2], TokenType.Punctuation);
        } else {
            this.map.put(words[2], TokenType.DataType);
            this.map.put(words[5], TokenType.VarName);
            this.map.put(words[6], TokenType.Punctuation);
        }
    }

    public void setOption(String inputLine) {
        if(inputLine.contains("\"")){
            setOptionFlag(1);
        } else {
            setOptionFlag(2);
        }
    }

    @Override
    public boolean syntaxChecker() {
        if(wordMissing()){
            ErrorHandler.getInstance().printError(ErrorType.WordMissing, null);
            setOptionFlag(-1);
            return false;
        }
        if(incorrectWord()) {
            ErrorHandler.getInstance().printError(ErrorType.IncorrectWord, missing);
            setOptionFlag(-1);
            return false; // postoji greska
        }
        if(!invalidWordOrder()) {
            ErrorHandler.getInstance().printError(ErrorType.WrongWordOrder, null);
            setOptionFlag(-1);
            return false;
        }

        return true;
    }

    @Override
    public boolean incorrectWord() {
        if (!words[0].equals("Write")) {
            missing = missing.concat(words[0]);
            return true;
        }

        if(getOptionFlag() == 2) {
            if (!words[1].equals("the")) {
                missing = missing.concat(words[1]);
                return true;
            }
            if (!words[2].equals("number") && !words[2].equals("letter") && !words[2].equals("word")) {
                missing = missing.concat(words[2]);
                return true;
            }
            if (!words[3].equals("content")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (!words[4].equals("of")) {
                missing = missing.concat(words[4]);
                return true;
            }
            if (words[5].isEmpty() || words[5].equals(".") || words[5].equals(",")) {
                missing = missing.concat(words[5]);
                return true;
            }
            if (!words[6].equals(".")) {
                missing = missing.concat(words[6]);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean wordMissing() {
        if(getOptionFlag() == 2) {
            if (words.length < 7) {
                return true;
            }
        }
        if(getOptionFlag() == 1){
            return words.length < 3;
        }
        return false;
    }

    @Override
    public boolean invalidWordOrder() {
        if(getOptionFlag() == 2){ // 2. option
            return lineContains("Write the") && (lineContains("number")
                    || lineContains("letter") || lineContains("word"))
                    && lineContains("content of") && lineContains(".");
        } else { // 1. option
            return lineContains("Write") && lineContains(".");
        }
    }

    public boolean lineContains(String word){
        return inputLine.contains(word);
        /*
        for(String str : words){
            if(str.equals(word))
                return true;
        }
         */
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
