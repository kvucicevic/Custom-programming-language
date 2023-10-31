package lexer;

import lexer.line.*;

public class LineFactory {

    public static LineFactory instance = null;

    private LineFactory() {

    }

    public static LineFactory getInstance() {
        if(instance == null)
            instance = new LineFactory();
        return instance;
    }

    public Line determineLine(String inputLine){
        if(inputLine.contains("Declare")){
            return new Declaration(inputLine);
        } else if(inputLine.contains("The")){
            return new Assignment(inputLine);
        } else if(inputLine.contains("For")){
            return new For(inputLine);
        } else if(inputLine.contains("While")){
            return new While(inputLine);
        } else if(inputLine.contains("If")){
            return new If(inputLine);
        } else if(inputLine.contains("Else")){
            return new Else(inputLine);
        } else if(inputLine.contains("Function")){
            return new Function(inputLine);
        } else if(inputLine.contains("Return")){
            return new Return(inputLine);
        } else if(inputLine.contains("End of")){
            return new EndOf(inputLine);
        } else if(inputLine.contains("Load")){
            return new Load(inputLine);
        } else if(inputLine.contains("Write")){
            return new Write(inputLine);
        }
        return null;
    }

}
