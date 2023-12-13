package lexer.line.loops;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class For extends Line {

    /**
     * For values of counter i from 0 to len , do :
     * For values of counter i from 0 to len , step -1 , do :
     * For values of counter k from j until currentLet is higher than 5 , do :
     * For values of counter k from j until currentLet is less than 5 , step 2 , do :
     */

    private final LineType type = LineType.For;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing = "";
    private String inputLine;

    public For(String inputLine) {
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
        this.map.put(words[3], TokenType.DataType);
        this.map.put(words[4], TokenType.VarName);
        this.map.put(words[6], TokenType.VarValue);

        if(inputLine.contains("until")){
            this.map.put(words[8], TokenType.VarName);
            this.map.put(words[11], TokenType.Relation);
            this.map.put(words[13], TokenType.VarValue);
            this.map.put(words[14], TokenType.Punctuation);
            if(inputLine.contains("step")){
                this.map.put(words[15], TokenType.KeyWord);
                this.map.put(words[16], TokenType.VarValue);
                this.map.put(words[17], TokenType.Punctuation);
                this.map.put(words[19], TokenType.Punctuation);
            } else {
                this.map.put(words[16], TokenType.Punctuation);
            }
        } else {
            this.map.put(words[8], TokenType.VarName);
            this.map.put(words[9], TokenType.Punctuation);
            if(inputLine.contains("step")){
                this.map.put(words[10], TokenType.KeyWord);
                this.map.put(words[11], TokenType.VarValue);
                this.map.put(words[12], TokenType.Punctuation);
                this.map.put(words[14], TokenType.Punctuation);
            } else {
                this.map.put(words[11], TokenType.Punctuation);
            }
        }

    }
    public void setOption(String inputLine) {
        if(inputLine.contains("until")){
            if(inputLine.contains("step")){
                setOptionFlag(1);
                return;
            }
            setOptionFlag(2);
        } else {
            if(inputLine.contains("step")){
                setOptionFlag(3);
                return;
            }
            setOptionFlag(4);
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
        if (!words[0].equals("For")) {
            missing = missing.concat(words[0]);
            return true;
        }
        if (!words[1].equals("values")) {
            missing = missing.concat(words[1]);
            return true;
        }
        if (!words[2].equals("of")) {
            missing = missing.concat(words[2]);
            return true;
        }
        if (!words[3].equals("counter")) {
            missing = missing.concat(words[3]);
            return true;
        }
        if(words[4].isEmpty() || words[4].equals(".") || words[4].equals(",")) // var name
            return true;
        if (!words[5].equals("from")) {
            missing = missing.concat(words[5]);
            return true;
        }
        if(words[6].isEmpty() || words[6].equals(".") || words[6].equals(",")) // var name
            return true;

        if(getOptionFlag() == 1 || getOptionFlag() == 2){
            if (!words[7].equals("to")) {
                missing = missing.concat(words[7]);
                return true;
            }
            if(words[8].isEmpty() || words[8].equals(".") || words[8].equals(",")) // var name
                return true;
            if (!words[9].equals(",")) {
                missing = missing.concat(words[9]);
                return true;
            }
            if(getOptionFlag() == 1){
                if (!words[10].equals("do")) {
                    missing = missing.concat(words[10]);
                    return true;
                }
                if (!words[11].equals(":")) {
                    missing = missing.concat(words[11]);
                    return true;
                }

            } else {
                if (!words[10].equals("step")) {
                    missing = missing.concat(words[10]);
                    return true;
                }if (!words[12].equals(",")) {
                    missing = missing.concat(words[12]);
                    return true;
                }if (!words[13].equals("do")) {
                    missing = missing.concat(words[13]);
                    return true;
                }if (!words[14].equals(":")) {
                    missing = missing.concat(words[14]);
                    return true;
                }
            }
        } else {
            if (!words[7].equals("until")) {
                missing = missing.concat(words[7]);
                return true;
            }
            if(words[8].isEmpty() || words[8].equals(".") || words[8].equals(",")) // var name
                return true;
            if (!words[9].equals("is")) {
                missing = missing.concat(words[9]);
                return true;
            }
            if (!words[10].equals("less") && !words[10].equals("higher")) {
                missing = missing.concat(words[10]);
                return true;
            }
            if (!words[11].equals("than")) {
                missing = missing.concat(words[11]);
                return true;
            }
            if(words[12].isEmpty() || words[12].equals(".") || words[12].equals(",")) // var name
                return true;
            if (!words[13].equals(",")) {
                missing = missing.concat(words[13]);
                return true;
            }
            if(getOptionFlag() == 3){
                if (!words[14].equals("step")) {
                    missing = missing.concat(words[14]);
                    return true;
                }
                if(words[15].isEmpty() || words[15].equals(".") || words[15].equals(",")) // var name
                    return true;
                if (!words[16].equals("do")) {
                    missing = missing.concat(words[16]);
                    return true;
                }
                if (!words[17].equals(":")) {
                    missing = missing.concat(words[17]);
                    return true;
                }

            } else {
                if (!words[14].equals("do")) {
                    missing = missing.concat(words[14]);
                    return true;
                }
                if (!words[15].equals(":")) {
                    missing = missing.concat(words[15]);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean wordMissing() {
        if(getOptionFlag() == 1) {
            return words.length < 11;
        }
        if(getOptionFlag() == 2){
            return words.length < 14;
        }
        if(getOptionFlag() == 3) {
            return words.length < 17;
        } else {
            return words.length < 15;
        }
    }

    @Override
    public boolean invalidWordOrder() {
        if(getOptionFlag() == 1){
            return lineContains("For values of counter") && lineContains("from") &&
                    lineContains("to") && lineContains(", do :");
        }
        if(getOptionFlag() == 2){
            return lineContains("For values of counter") && lineContains("from") &&
                    lineContains("to") && lineContains(", step") && lineContains(", do :");
        }
        if(getOptionFlag() == 3){
            return lineContains("For values of counter") && lineContains("from") &&
                    lineContains("until") && (lineContains("is higher than") ||
                    lineContains("is lower than") || lineContains("is equal to")) && lineContains(", do :");
        }
        else {
            return lineContains("For values of counter") && lineContains("from") &&
                    lineContains("until") && (lineContains("is higher than") ||
                    lineContains("is lower than") || lineContains("is equal to")) &&
                    lineContains(", step") && lineContains(", do :");
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
