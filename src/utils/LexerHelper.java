package utils;

public class LexerHelper {

    private static final String letters = "أبتثجحخدذرزسشصضطظعغفقكلمنهويى";

    public static boolean isLetter(char ch)
    {

        boolean isLetter=false;
        for(int i=0;i<letters.length();i++)
            if(ch==letters.charAt(i)) {
                isLetter = true;
                break;
            }

        return isLetter;
    }

    public static String reverse(String line){
        String reverse="";
        for(int i = line. length() - 1; i >= 0; i--)
        {
            reverse += line.charAt(i);
        }

        return reverse;
    }
}
