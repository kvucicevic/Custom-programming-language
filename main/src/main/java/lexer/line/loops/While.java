package lexer.line.loops;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class While extends Line {

    /**
     *  While it is true , do :
     *  While it is true that a is higher than b , do :
     *  While it is false that a is equal to 0 , do :
     */

    private final LineType type = LineType.While;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";
    private String inputLine;

    public While(String inputLine) {
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
        this.map.put(words[3], TokenType.Boolean);
        if(getOptionFlag() == 1 || getOptionFlag() == 2){
            this.map.put(words[5], TokenType.VarName);
            this.map.put(words[7], TokenType.Relation);
            this.map.put(words[10], TokenType.Punctuation);
            this.map.put(words[12], TokenType.Punctuation);
        } else {
            this.map.put(words[6], TokenType.Punctuation);
        }
    }

    public void setOption(String inputLine) {
        if(inputLine.contains("equal")) {
            setOptionFlag(1);
            return;
        }
        if(inputLine.contains("higher") || inputLine.contains("lower")) {
            setOptionFlag(2);
            return;
        }
        setOptionFlag(3);
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
        if (!words[0].equals("While")) {
            missing = missing.concat(words[0]);
            return true;
        }
        if (!words[1].equals("it")) {
            missing = missing.concat(words[1]);
            return true;
        }
        if (!words[2].equals("is")) {
            missing = missing.concat(words[2]);
            return true;
        }
        if(getOptionFlag() == 3){
            if (!words[3].equals("true")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (!words[4].equals(",")) {
                missing = missing.concat(words[4]);
                return true;
            }
            if (!words[5].equals("do")) {
                missing = missing.concat(words[5]);
                return true;
            }
            if (!words[6].equals(":")) {
                missing = missing.concat(words[6]);
                return true;
            }
        }
        if(getOptionFlag() == 1 || getOptionFlag() == 2){
            if (!words[3].equals("true") && !words[3].equals("false")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (!words[4].equals("that")) {
                missing = missing.concat(words[4]);
                return true;
            }
            if(words[5].isEmpty() || words[5].equals(".") || words[5].equals(",")) // var name
                return true;
            if (!words[6].equals("is")) {
                missing = missing.concat(words[6]);
                return true;
            }
            if(getOptionFlag() == 1) {
                if (!words[7].equals("equal")) {
                    missing = missing.concat(words[7]);
                    return true;
                }
                if (!words[8].equals("to")) {
                    missing = missing.concat(words[8]);
                    return true;
                }
                if(words[9].isEmpty() || words[9].equals(".") || words[9].equals(",")) // var name
                    return true;
                if (!words[10].equals(",")) {
                    missing = missing.concat(words[10]);
                    return true;
                }
                if (!words[11].equals("do")) {
                    missing = missing.concat(words[11]);
                    return true;
                }
                if (!words[12].equals(":")) {
                    missing = missing.concat(words[12]);
                    return true;
                }
            }
            if (getOptionFlag() == 2) {
                if (!words[7].equals("higher") && !words[7].equals("lower")) {
                    missing = missing.concat(words[7]);
                    return true;
                }
                if (!words[8].equals("than")) {
                    missing = missing.concat(words[8]);
                    return true;
                }
                if(words[9].isEmpty() || words[9].equals(".") || words[9].equals(",")) // var name
                    return true;
                if (!words[10].equals(",")) {
                    missing = missing.concat(words[10]);
                    return true;
                }
                if (!words[11].equals("do")) {
                    missing = missing.concat(words[11]);
                    return true;
                }
                if (!words[12].equals(":")) {
                    missing = missing.concat(words[12]);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean wordMissing() {
        if(getOptionFlag() == 2 || getOptionFlag() == 3) {
            return words.length < 12;
        } else {
            return words.length < 6;
        }
    }

    @Override
    public boolean invalidWordOrder() {
        if(getOptionFlag() == 1){
            return lineContains("While it is true , do :");
        }
        if(getOptionFlag() == 2){
            return lineContains("While it is") && (lineContains("true that") ||
                    lineContains("false that")) && lineContains("is equal to") &&
                    lineContains(", do :");
        } else {
            return lineContains("While it is") && (lineContains("true that") ||
                    lineContains("false that")) && (lineContains("is higher than") ||
                    lineContains("is lower than")) && lineContains(", do :");
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

    public LineType getType() {
        return type;
    }
}
