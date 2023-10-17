package statements;

public class AddLine extends CodeLine {
    public AddLine(String text) {
        super(text);
    }

    @Override
    protected String makeJavaLine(String text) {
        String[] words = text.split(" ");
        for(String word : words){
            if(word.contains("\"")){
                return "System.out.println(" + word + ")";
            }
        }
        return "";
    }
}
