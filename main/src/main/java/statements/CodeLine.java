package statements;

public abstract class CodeLine {

    String text;

    public CodeLine(String text) {
        this.text = text;
    }

    protected abstract String makeJavaLine(String text);


    public String getJavaText() {
        return makeJavaLine(text);
    }
}
