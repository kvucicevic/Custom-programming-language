package lexer.line.functions;

import lexer.error.ErrorHandler;
import lexer.error.ErrorType;
import lexer.line.Line;
import lexer.line.LineType;
import lexer.word.TokenType;

import java.util.HashMap;
import java.util.Map;

public class Function extends Line {

    /**
     * Function main with no arguments and return type number :
     * Function func with no arguments and no return value :
     * Function isHigher with number a , number b as arguments and return type number :
     * Function isLower with number a , number b as arguments and no return value :
     * Function isVowel with letter let as argument and return type number :
     * Function removeDuplicates with word wor as argument and no return value :
     *
     * no args, no return
     * no args, return type
     * 1 arg, no return
     * 1 arg, return type
     * multi args, no return
     * multi args, return type
     */

    private final LineType type = LineType.Function;
    private Map<String, TokenType> map;
    private String[] words;
    private String missing;
    private String inputLine;

    public Function(String inputLine) {
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
        this.map.put(words[1], TokenType.FunctionName);

        if(getOptionFlag() == 1) { // no arguments
            this.map.put(words[7], TokenType.Return);
            this.map.put(words[8], TokenType.Punctuation);
            return;
        } else if (getOptionFlag() == 2){ // return type
            this.map.put(words[6], TokenType.Return);
            this.map.put(words[8], TokenType.DataType);
            this.map.put(words[9], TokenType.Punctuation);
            return;
        }

        this.map.put(words[3], TokenType.DataType);
        this.map.put(words[4], TokenType.VarName);

        if(getOptionFlag() == 3){ // multiple arguments
            this.map.put(words[5], TokenType.Punctuation);
            this.map.put(words[6], TokenType.DataType);
            this.map.put(words[7], TokenType.VarName);
            this.map.put(words[12], TokenType.Return);
            this.map.put(words[13], TokenType.Punctuation);
            return;
        } else if (getOptionFlag() == 4){
            this.map.put(words[5], TokenType.Punctuation);
            this.map.put(words[6], TokenType.DataType);
            this.map.put(words[7], TokenType.VarName);
            this.map.put(words[11], TokenType.Return);
            this.map.put(words[13], TokenType.DataType);
            this.map.put(words[14], TokenType.Punctuation);
            return;
        }

        if (getOptionFlag() == 5){ // one arguemnt
            this.map.put(words[9], TokenType.Return);
            this.map.put(words[10], TokenType.Punctuation);
        } else {
            this.map.put(words[8], TokenType.Return);
            this.map.put(words[10], TokenType.DataType);
            this.map.put(words[11], TokenType.Punctuation);
        }
    }

    public void setOption(String inputLine) {
        if(inputLine.contains("no arguments")){
            if(inputLine.contains("type")) {
                setOptionFlag(1);
            } else {
                setOptionFlag(2);
            }
            return;
        }
        if(inputLine.contains("arguments")){
            if(inputLine.contains("no"))
                setOptionFlag(3);
            else
                setOptionFlag(4);
            return;
        }
        if(inputLine.contains("no"))
            setOptionFlag(5);
        else
            setOptionFlag(6);
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
        return true;
    }

    @Override
    public boolean incorrectWord() {
        if (!words[0].equals("Function")) {
            missing = missing.concat(words[0]);
            return true;
        }
        if(words[1].isEmpty() || words[1].equals(".") || words[1].equals(",")) // var name
            return true;
        if (!words[2].equals("with")) {
            missing = missing.concat(words[2]);
            return true;
        }

        if(getOptionFlag() == 1){
            if (!words[3].equals("no")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (!words[4].equals("arguments")) {
                missing = missing.concat(words[4]);
                return true;
            }
            if (!words[5].equals("and")) {
                missing = missing.concat(words[5]);
                return true;
            }
            if (!words[6].equals("return")) {
                missing = missing.concat(words[6]);
                return true;
            }
            if (!words[7].equals("type")) {
                missing = missing.concat(words[7]);
                return true;
            }
            if (!words[8].equals("number") && !words[8].equals("letter") && !words[8].equals("word")) {
                missing = missing.concat(words[8]);
                return true;
            }
            if (!words[9].equals(":")) {
                missing = missing.concat(words[9]);
                return true;
            }

        } else if(getOptionFlag() == 2){
            if (!words[3].equals("no")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (!words[4].equals("arguments")) {
                missing = missing.concat(words[4]);
                return true;
            }
            if (!words[5].equals("and")) {
                missing = missing.concat(words[5]);
                return true;
            }
            if (!words[6].equals("no")) {
                missing = missing.concat(words[6]);
                return true;
            }
            if (!words[7].equals("return")) {
                missing = missing.concat(words[7]);
                return true;
            }
            if (!words[8].equals("value")) {
                missing = missing.concat(words[8]);
                return true;
            }
            if (!words[9].equals(":")) {
                missing = missing.concat(words[9]);
                return true;
            }

        } else if(getOptionFlag() == 3){
            if (!words[3].equals("number") && !words[3].equals("letter") && !words[3].equals("word")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if(words[4].isEmpty() || words[4].equals(".") || words[4].equals(",")) // var name
                return true;
            if (!words[5].equals(",")) {
                missing = missing.concat(words[5]);
                return true;
            }
            if (!words[6].equals("number") && !words[6].equals("letter") && !words[6].equals("word")) {
                missing = missing.concat(words[6]);
                return true;
            }
            if(words[7].isEmpty() || words[7].equals(".") || words[7].equals(",")) // var name
                return true;
            if (!words[8].equals("as")) {
                missing = missing.concat(words[8]);
                return true;
            }
            if (!words[9].equals("arguments")) {
                missing = missing.concat(words[9]);
                return true;
            }
            if (!words[10].equals("and")) {
                missing = missing.concat(words[10]);
                return true;
            }
            if (!words[11].equals("return")) {
                missing = missing.concat(words[11]);
                return true;
            }
            if (!words[12].equals("type")) {
                missing = missing.concat(words[12]);
                return true;
            }
            if (!words[13].equals("number") && !words[13].equals("letter") && !words[13].equals("word")) {
                missing = missing.concat(words[13]);
                return true;
            }
            if (!words[14].equals(":")) {
                missing = missing.concat(words[14]);
                return true;
            }

        } else if(getOptionFlag() == 4) {
            if (!words[3].equals("number") && !words[3].equals("letter") && !words[3].equals("word")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (words[4].isEmpty() || words[4].equals(".") || words[4].equals(",")) // var name
                return true;
            if (!words[5].equals(",")) {
                missing = missing.concat(words[5]);
                return true;
            }
            if (!words[6].equals("number") && !words[6].equals("letter") && !words[6].equals("word")) {
                missing = missing.concat(words[6]);
                return true;
            }
            if (words[7].isEmpty() || words[7].equals(".") || words[7].equals(",")) // var name
                return true;
            if (!words[8].equals("as")) {
                missing = missing.concat(words[8]);
                return true;
            }
            if (!words[9].equals("arguments")) {
                missing = missing.concat(words[9]);
                return true;
            }
            if (!words[10].equals("and")) {
                missing = missing.concat(words[10]);
                return true;
            }
            if (!words[11].equals("no")) {
                missing = missing.concat(words[11]);
                return true;
            }
            if (!words[12].equals("return")) {
                missing = missing.concat(words[12]);
                return true;
            }
            if (!words[13].equals("value")) {
                missing = missing.concat(words[13]);
                return true;
            }
            if (!words[14].equals(":")) {
                missing = missing.concat(words[14]);
                return true;
            }

        } else if (getOptionFlag() == 5) {
            if (!words[3].equals("number") && !words[3].equals("letter") && !words[3].equals("word")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (words[4].isEmpty() || words[4].equals(".") || words[4].equals(",")) // var name
                return true;

            if (!words[5].equals("as")) {
                missing = missing.concat(words[5]);
                return true;
            }
            if (!words[6].equals("argument")) {
                missing = missing.concat(words[6]);
                return true;
            }
            if (!words[7].equals("and")) {
                missing = missing.concat(words[7]);
                return true;
            }
            if (!words[8].equals("return")) {
                missing = missing.concat(words[8]);
                return true;
            }
            if (!words[9].equals("type")) {
                missing = missing.concat(words[9]);
                return true;
            }
            if (!words[10].equals("number") && !words[10].equals("letter") && !words[10].equals("word")) {
                missing = missing.concat(words[10]);
                return true;
            }
            if (!words[11].equals(":")) {
                missing = missing.concat(words[11]);
                return true;
            }

        } else {
            if (!words[3].equals("number") && !words[3].equals("letter") && !words[3].equals("word")) {
                missing = missing.concat(words[3]);
                return true;
            }
            if (words[4].isEmpty() || words[4].equals(".") || words[4].equals(",")) // var name
                return true;

            if (!words[5].equals("as")) {
                missing = missing.concat(words[5]);
                return true;
            }
            if (!words[6].equals("argument")) {
                missing = missing.concat(words[6]);
                return true;
            }
            if (!words[7].equals("and")) {
                missing = missing.concat(words[7]);
                return true;
            }
            if (!words[8].equals("no")) {
                missing = missing.concat(words[8]);
                return true;
            }
            if (!words[9].equals("return")) {
                missing = missing.concat(words[9]);
                return true;
            }
            if (!words[10].equals("value")) {
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
        if(getOptionFlag() == 1 || getOptionFlag() == 2) {
            return words.length < 9;
        } else if (getOptionFlag() == 3 || getOptionFlag() == 4){
            return words.length < 14;
        } else {
            return words.length < 11;
        }
    }

    @Override
    public boolean invalidWordOrder() {
        if(getOptionFlag() == 1){
            return lineContains("Function") && lineContains("with no arugments and return type") &&
                    lineContains(":");
        } else if(getOptionFlag() == 2){
            return lineContains("Function") && lineContains("with no arugments and no return value :");
        } else if(getOptionFlag() == 3){
            return lineContains("Function") && lineContains("with") && (lineContains("number") ||
                    lineContains("letter") || lineContains("word")) && lineContains(",") &&
                    (lineContains("number") || lineContains("letter") || lineContains("word")) &&
                    lineContains("as arugments and return type") && lineContains(":");
        } else if(getOptionFlag() == 4){
            return lineContains("Function") && lineContains("with") && (lineContains("number") ||
                    lineContains("letter") || lineContains("word")) && lineContains(",") &&
                    (lineContains("number") || lineContains("letter") || lineContains("word")) &&
                    lineContains("as arguments and no return value :");
        } else if(getOptionFlag() == 5){
            return lineContains("Function") && lineContains("with") && (lineContains("number") ||
                    lineContains("letter") || lineContains("word")) &&
                    lineContains("as argument and return type") && (lineContains("number") ||
                    lineContains("letter") || lineContains("word")) && lineContains(":");
        } else {
            return lineContains("Function") && lineContains("with") && (lineContains("number") ||
                    lineContains("letter") || lineContains("word")) &&
                    lineContains("as argument and no return value :");

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
