import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {

    private BufferedReader input;

    private final String letters = "أبتثجحخدذرزسشصضطظعغفقكلمنهويى";

    public Lexer(String fileName){

        try {
            input = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            System.exit(1);
        }

    }

    //do tokenization....
    private Token nextToken(){

        String line="";


        try {

          // while(input.read() ==' ' || input.read()=='\t' || input.read()=='\r' ) input.read();
           line = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        line = reverse(line);

        for(int i = 0 ;i<line.length();i++)
            System.out.println(isLetter(line.charAt(i)));





       // System.out.println(reverse(line));


        return new Token(TokenType.Eof,"asd");

    }

    //utils

    private boolean isLetter(char c)
    {

        boolean isLetter=false;
        for(int i=0;i<letters.length();i++)
            if(c==letters.charAt(i)) {
                isLetter = true;
                break;
            }

            return isLetter;
    }
     private String reverse(String line){
        String reverse="";
        for(int i = line. length() - 1; i >= 0; i--)
        {
            reverse += line.charAt(i);
        }

        return reverse;
    }

    public static void main(String[] argv){

        Lexer lexer = new Lexer("input.txt");
        Token token = lexer.nextToken();

        while(token.type != TokenType.Eof){
            System.out.println(token);
            token = lexer.nextToken();
        }


    }
}
