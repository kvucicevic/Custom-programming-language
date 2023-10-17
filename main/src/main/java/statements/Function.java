package statements;

import statements.CodeLine;

public class Function extends CodeLine {

    public Function(String text) {
        super(text);
    }

    @Override
    protected String makeJavaLine(String text) {
        String[] words = text.split(" ");
        String functionName = "";
        String arguments;
        String returnType = "";
        int i = 0;
        if(text.contains("main")){
            return "public static void main(String[] args) { \n";
        }
        for(String word : words){
            if(word.contains("function")){
                functionName = words[i-1];
                // arguments
            } else if(word.contains("number")){
                returnType = "int";
                // arguments
            }
            i++;
        }
        return returnType + " " + functionName + "()";
    }


}
