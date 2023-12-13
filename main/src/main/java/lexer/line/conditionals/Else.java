package lexer.line.conditionals;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Else extends Line {

    /**
     *  else , do :
     *  else if // todo verovatno ce biti zasebna klasa
     */

    private final LineType type = LineType.Else;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";
    private String inputLine;

    public Else(String inputLine) {
        super(inputLine);
    }

    @Override
    public void analyzeLine(String inputLine) {
        this.map = new HashMap<>();
        this.inputLine = inputLine;
        this.missing = "";
        this.words = inputLine.split(" ");

        if(!syntaxChecker()) // neka rec je izostavljena
            return;

        this.map.put(words[0], TokenType.KeyWord);
        this.map.put(words[1], TokenType.Punctuation);
        this.map.put(words[3], TokenType.Punctuation);
    }

    @Override
    public boolean syntaxChecker() {
        if(wordMissing()){
            ErrorHandler.getInstance().printError(ErrorType.WordMissing, null);
            setOptionFlag(-1);
            return false;
        }
        if(!invalidWordOrder() && incorrectWord()) {
            ErrorHandler.getInstance().printError(ErrorType.WrongWordOrder, null);
            setOptionFlag(-1);
            return false;
        }
        if(incorrectWord()) {
            ErrorHandler.getInstance().printError(ErrorType.IncorrectWord, missing);
            setOptionFlag(-1);
            return false; // postoji greska
        }
        setOptionFlag(1);
        return true;
    }

    @Override
    public boolean incorrectWord() {
        if (!words[0].equals("Else")) {
            missing = missing.concat(words[0]);
            return true;
        }
        if (!words[1].equals(",")) {
            missing = missing.concat(words[1]);
            return true;
        }
        if (!words[2].equals("do")) {
            missing = missing.concat(words[2]);
            return true;
        }
        if (!words[3].equals(":")) {
            missing = missing.concat(words[3]);
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
        return lineContains("Else , do :");
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

    public LineType getType() {
        return type;
    }
}
