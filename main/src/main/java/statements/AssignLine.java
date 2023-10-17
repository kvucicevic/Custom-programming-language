package statements;

public class AssignLine extends CodeLine{

    public AssignLine(String text) {
        super(text);
    }

    @Override
    protected String makeJavaLine(String text) {
        String[] words = text.split(" ");
        String varName = "a";
        String value = words[words.length-2];
        return "\t\t" + varName + " = " + value + ";" + "\n";
    }
}
