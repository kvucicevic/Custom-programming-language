package scanner;

import statements.*;

public class LineFactory {

    public static LineFactory instance = null;

    private LineFactory() {
    }

    public CodeLine generateLine(String text){
        if(text.contains("Declare"))
            return new DeclareLine(text);
        if(text.contains("Function"))
            return new Function(text);
        if(text.contains("Write"))
            return new WriteLine(text);
        return new AssignLine(text);
    }

    public static LineFactory getInstance() {
        if(instance == null)
            return new LineFactory();
        return instance;
    }
}
