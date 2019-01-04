import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static utils.LexerHelper.*;

public class Lexer {

    private final char eofCh = '\004';
    private final char eolnCh = '\n';
    private BufferedReader input;
    private char ch = ' ';
    private String line = "";
    private int lineno = 0;
    private int col = 1;


    public Lexer(String fileName) {

        try {
            input = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            System.exit(1);
        }

    }

    public static void main(String[] argv) {

        Lexer lexer = new Lexer("input.txt");
        Token token;
        try {
            token = lexer.nextToken();
        while (token.type != TokenType.Eof) {
                System.out.println(token.value + "  " + token.type);
                token = lexer.nextToken();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end of file ");


    }

    private char nextChar() { // Return next char
        if (ch == eofCh)
            error("Attempt to read past end of file");
        col++;
        if (col >= line.length()) {
            try {
                line = input.readLine();
            } catch (IOException e) {
                System.err.println(e);
                System.exit(1);
            } // try
            if (line == null) // at end of file
                line = "" + eofCh;
            else {
                // System.out.println(lineno + ":\t" + line);
                lineno++;
                line += eolnCh;
            } // if line
            col = 0;
        } // if col
        return line.charAt(col);
    }//nextChar


    private Token nextToken() throws IOException {

        do {
            if (isAlpha(ch)) {

                String lexeme = concat(letters + digits);
                return identifyLexeme(lexeme);
            } else if (isDigit(ch)) { // int  literal
                String number = concat(digits);

                // int Literal
                return new Token(TokenType.IntLiteral, number);

            } else switch (ch) {
                case ' ':
                case '\t':
                case '\r':
                case eolnCh:
                    ch = nextChar();

                    break;

                case eofCh:
                    return new Token(TokenType.Eof, "eof");

                case '+':
                    ch = nextChar();
                    return new Token(TokenType.Minus, "+");


                case '-':
                    ch = nextChar();
                    return new Token(TokenType.Assign, "-");

                case '*':
                    ch = nextChar();
                    return new Token(TokenType.Multiply, "*");

                case '/':
                    ch = nextChar();
                    return new Token(TokenType.Divide, "/");


                case '(':
                    ch = nextChar();
                    return new Token(TokenType.LeftParen, "(");

                case ')':
                    ch = nextChar();
                    return new Token(TokenType.RightParen, ")");


                case '=':
                    ch = nextChar();
                    return new Token(TokenType.Assign, "=");

                case '>':
                    return chkOpt('=', new Token(TokenType.Greater, "<"),
                            new Token(TokenType.GreaterEqual, "=<"));

                case '<':
                    return chkOpt('=', new Token(TokenType.Less, ">"),
                            new Token(TokenType.LessEqual, "=>"));


                default:
                    error("Illegal character " + ch);
            } // switch
        } while (true);

    }

    private Token chkOpt(char c, Token one, Token two) {

        ch = nextChar();
        if (ch != c)
            return one;
        ch = nextChar();
        return two;
    }

    private String concat(String set) {
        String r = "";
        do {
            r += ch;
            ch = nextChar();
        } while (set.indexOf(ch) >= 0);
        return r;
    }

    Token identifyLexeme(String lexeme) {

             if (lexeme.equals("صحيح") ) return new Token(TokenType.Int, lexeme);
        else if (lexeme.equals("نصى")  ) return new Token(TokenType.String, lexeme);
        else if (lexeme.equals("منطقى")) return new Token(TokenType.Bool, lexeme);
        else if (lexeme.equals("ادخل") ) return new Token(TokenType.Enter, lexeme);
        else if (lexeme.equals("اطبع") ) return new Token(TokenType.Print, lexeme);
        else if (lexeme.equals("نفذ")  ) return new Token(TokenType.Execute, lexeme);
        else if (lexeme.equals("اذا")  ) return new Token(TokenType.If, lexeme);
        else if (lexeme.equals("من")   ) return new Token(TokenType.From, lexeme);
        else if (lexeme.equals("حتى")  ) return new Token(TokenType.To, lexeme);
        else if (lexeme.equals("خطوة") ) return new Token(TokenType.Step, lexeme);
        else return new Token(TokenType.Identifier, lexeme);
    }

    public void error(String msg) {
        System.err.print(line);
        System.err.println("Error: column " + col + " " + msg);
        System.exit(1);
    }
}
