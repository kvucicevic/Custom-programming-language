package statements;

public class WriteLine extends CodeLine{

    public WriteLine(String text) {
        super(text);
    }
    @Override
    protected String makeJavaLine(String text) {
        String[] words = text.split(" ");
        return "\t\t" + "System.out.println(" + "a" + ");" + "\n\t}";
    }
}
