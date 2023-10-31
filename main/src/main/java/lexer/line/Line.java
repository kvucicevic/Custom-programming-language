package lexer.line;

public abstract class Line {

    //private abstract final LineType type;

    private String inputLine;

    public Line(String inputLine) {
        this.inputLine = inputLine;
    }

    public abstract void analyzeLine(String inputLine);
}
