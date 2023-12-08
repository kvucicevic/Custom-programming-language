package lexer.line.primeLines;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Assignment extends Line {

    /**
     *  The number len takes value of length ( of "arr" ) .
     *  The number min takes value of 0 .
     *  The letter "word" ( of k ) takes value of letter "word" ( of k+1 ) . TODO - VIDECEMO
     */

    private final LineType type = LineType.Assignment;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing;
    private String inputLine;

    public Assignment(String inputLine) {
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

        if(getOptionFlag() == 1) {
            this.map.put(words[1], TokenType.DataType);
            this.map.put(words[2], TokenType.VarName);
            this.map.put(words[6], TokenType.FunctionName);
            this.map.put(words[9], TokenType.VarName);
            this.map.put(words[11], TokenType.Punctuation);
        } else {
            this.map.put(words[1], TokenType.DataType);
            this.map.put(words[2], TokenType.VarName);
            this.map.put(words[6], TokenType.VarValue);
            this.map.put(words[7], TokenType.Punctuation);
        }
    }

    public void setOption(String inputLine) {
        if(inputLine.contains("(")){
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
        if (!words[0].equals("The")) {
            missing = missing.concat(words[0]);
            return true;
        }
        if (!words[1].equals("number") && !words[1].equals("letter") && !words[1].equals("word")) {
            missing = missing.concat(words[1]);
            return true;
        }
        if(words[2].isEmpty() || words[2].equals(".") || words[2].equals(",")) // var name
            return true;

        if (!words[3].equals("takes")) {
            missing = missing.concat(words[3]);
            return true;
        }
        if (!words[4].equals("value")) {
            missing = missing.concat(words[4]);
            return true;
        }
        if (!words[5].equals("of")) {
            missing = missing.concat(words[5]);
            return true;
        }
        if(words[6].isEmpty() || words[6].equals(".") || words[6].equals(",")) // var name
            return true;

        if(getOptionFlag() == 1) {
            if (!words[7].equals("(")) {
                missing = missing.concat(words[7]);
                return true;
            }
            if (!words[8].equals("of")) {
                missing = missing.concat(words[8]);
                return true;
            }
            if (words[9].isEmpty() || words[9].equals(".") || words[9].equals(",")) {
                return true;
            }
            if (!words[10].equals(")")) {
                missing = missing.concat(words[10]);
                return true;
            }

            if (!words[11].equals(".")) {
                missing = missing.concat(words[11]);
                return true;
            }

        } else {
            if (!words[7].equals(".")) {
                missing = missing.concat(words[7]);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean wordMissing() {
        if(getOptionFlag() == 1) {
            return words.length < 11;
        } else {
            return words.length < 7;
        }
    }

    @Override
    public boolean invalidWordOrder() {
        if(getOptionFlag() == 1){
            return lineContains("The") && (lineContains("number") || lineContains("letter")
                    || lineContains("word")) && lineContains("takes value of") && lineContains("(")
                    && lineContains(")") && lineContains(".");
        } else {
            return lineContains("The") && (lineContains("number") || lineContains("letter")
                    || lineContains("word")) && lineContains("takes value of") && lineContains(".");
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
