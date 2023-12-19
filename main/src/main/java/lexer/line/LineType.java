package lexer.line;

public enum LineType {
    Function, // prioritet 1
    Return, // prioritet 1
    EndOf, // prioritet 1,2 zavisi
    For,  // prioritet 2
    While, // prioritet 2
    If, // prioritet 3
    Else, // prioritet 3
    Declaration, // prioritet 4
    Assignment,// prioritet 4
    Load, // prioritet 4
    ConsoleWrite, // prioritet 4
    Arithmetics // prioritet 4
}
