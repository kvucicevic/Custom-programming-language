package statements;

public class DeclareLine extends CodeLine{

    public DeclareLine(String text) {
        super(text);
    }

    @Override
    protected String makeJavaLine(String text) {
        String[] words = text.split(" ");
        String varType = "int";
        String varName = words[words.length-2];
        return "\t\t" + varType + " " + varName + ";" + "\n";
    }
}
