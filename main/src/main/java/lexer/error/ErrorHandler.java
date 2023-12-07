package lexer.error;

public class ErrorHandler {

    public static ErrorHandler instance = null;
    private ErrorHandler() {
    }

    public static ErrorHandler getInstance() {
        if(instance == null)
            instance = new ErrorHandler();
        return instance;
    }

    public void printError(ErrorType errorType, String word){
        if(errorType == ErrorType.WordMissing){
            System.out.println("Missing words: " + word);
        } else if (errorType == ErrorType.WrongWordOrder){
            System.out.println("Wrong order of the words");
        } else if (errorType == ErrorType.IncorrectWord){
            System.out.println("The word is incorrect: " + word);
        }
    }
}
