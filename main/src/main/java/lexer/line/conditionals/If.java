package lexer.line.conditionals;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class If extends Line {

    /**
     * If it is true that a is higher than b , then :
     * If it is true that a is higher than b and b is lower than c , then :
     * If it is false that num is equal to 0 , then :
     */

    private final LineType type = LineType.If;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";
    private String inputLine;

    public If(String inputLine) {
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
        this.map.put(words[5], TokenType.VarName);
        this.map.put(words[7], TokenType.Relation);
        this.map.put(words[9], TokenType.VarName);
        if(getOptionFlag() == 1) {
            this.map.put(words[10], TokenType.Logic);
            this.map.put(words[11], TokenType.VarName);
            this.map.put(words[13], TokenType.Relation);
            this.map.put(words[15], TokenType.VarName);
            this.map.put(words[16], TokenType.Punctuation);
            this.map.put(words[18], TokenType.Punctuation);
        } else {
            this.map.put(words[10], TokenType.Punctuation);
            this.map.put(words[12], TokenType.Punctuation);
        }
    }

    public void setOption(String inputLine) {
        if(inputLine.contains("and") || inputLine.contains("or")){
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
        if (!words[0].equals("if")) {
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
        if (!words[7].equals("higher") && !words[7].equals("lower") && !words[7].equals("equal")) {
            missing = missing.concat(words[7]);
            return true;
        }
        if (!words[8].equals("to") && !words[8].equals("than")) {
            missing = missing.concat(words[8]);
            return true;
        }

        if(getOptionFlag() == 1) {
            if (!words[9].equals("and") && !words[9].equals("or")) {
                missing = missing.concat(words[9]);
                return true;
            }
            if(words[10].isEmpty() || words[10].equals(".") || words[10].equals(",")) // var name
                return true;

            if (!words[11].equals("is")) {
                missing = missing.concat(words[11]);
                return true;
            }
            if (!words[12].equals("higher") && !words[12].equals("lower") && !words[12].equals("equal")) {
                missing = missing.concat(words[12]);
                return true;
            }
            if (!words[13].equals("to") && !words[13].equals("than")) {
                missing = missing.concat(words[13]);
                return true;
            }
            if (!words[14].equals(",")) {
                missing = missing.concat(words[14]);
                return true;
            }
            if (!words[15].equals("then")) {
                missing = missing.concat(words[15]);
                return true;
            }
            if (!words[16].equals(":")) {
                missing = missing.concat(words[16]);
                return true;
            }
        } else {
            if (!words[9].equals(",")) {
                missing = missing.concat(words[9]);
                return true;
            }
            if (!words[10].equals("then")) {
                missing = missing.concat(words[10]);
                return true;
            }
            if (!words[11].equals(":")) {
                missing = missing.concat(words[11]);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean wordMissing() {
        if(getOptionFlag() == 1) {
            return words.length < 16;
        } else {
            return words.length < 11;
        }
    }

    @Override
    public boolean invalidWordOrder() {
        if(getOptionFlag() == 1){
            return lineContains("If it is") && (lineContains("true") || lineContains("false")) &&
                    lineContains("that") && (lineContains("is higher than") ||
                    lineContains("is lower than") || lineContains("is equal to")) &&
                    lineContains(", then :");
        } else {
            return lineContains("If it is") && (lineContains("true") || lineContains("false")) &&
                    lineContains("that") && (lineContains("is higher than") ||
                    lineContains("is lower than") || lineContains("is equal to")) &&
                    (lineContains("and") || lineContains("or")) &&
                    lineContains(", then :");
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
}
