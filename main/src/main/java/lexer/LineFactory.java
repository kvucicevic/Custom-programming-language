package lexer;

import lexer.line.*;

public class LineFactory {

    private String inputLine;

    public static LineFactory instance = null;

    private LineFactory(String inputLine) {
        this.inputLine = inputLine;
        determineLine();
    }

    public static LineFactory getInstance() {
        if(instance == null)
            instance = new LineFactory(instance.inputLine);
        return instance;
    }

    private Line determineLine(){
        if(this.inputLine.contains("Declare")){
            return new Declaration(this.inputLine);
        } else if(this.inputLine.contains("The")){
            return new Assignment(this.inputLine);
        } else if(this.inputLine.contains("For")){
            return new For(this.inputLine);
        } else if(this.inputLine.contains("While")){
            return new While(this.inputLine);
        } else if(this.inputLine.contains("If")){
            return new If(this.inputLine);
        } else if(this.inputLine.contains("Else")){
            return new Else(this.inputLine);
        } else if(this.inputLine.contains("Function")){
            return new Function(this.inputLine);
        } else if(this.inputLine.contains("Return")){
            return new Return(this.inputLine);
        } else if(this.inputLine.contains("End of")){
            return new EndOf(this.inputLine);
        } else if(this.inputLine.contains("Load")){
            return new Load(this.inputLine);
        } else if(this.inputLine.contains("Write")){
            return new Write(this.inputLine);
        }
        return null;
    }

}
