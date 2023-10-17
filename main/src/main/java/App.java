import scanner.FileScan;

public class App {

    public static void main(String[] args) {
        System.out.println("Start");
        FileScan fs = new FileScan();
        fs.readFile("main/src/main/java/MyCode.txt");
    }
}
