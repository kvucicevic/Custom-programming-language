package statements;

public class AssignLine extends CodeLine{

    public AssignLine(String text) {
        super(text);
    }

    @Override
    protected String makeJavaLine(String text) {
        String[] words = text.split(" ");
        for(String word : words)
            System.out.println(word + " " + words.length);
        String varName = words[words.length-6];
        String value = words[words.length-2];
        return "\t\t" + varName + " = " + value + ";" + "\n";
    }
}
