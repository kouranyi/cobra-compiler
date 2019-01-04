package utils;



public class LexerHelper {

    public static final String letters = "اأبتثجحخدذرزسشصضطظعغفقكلمنهويىة";
    public static final String digits = "0123456789";



    /**
     *
     * @param ch a character
     * @return true if it's an alphapet ,otherwise false
     */
    public static boolean isAlpha(char ch) {

        boolean isLetter = false;
        for (int i = 0; i < letters.length(); i++)
            if (ch == letters.charAt(i)) {
                isLetter = true;
                break;
            }

        return isLetter;
    }

    /**
     *
     * @param ch a character
     * @return true if it's a digit ,otherwise false
     */
    public static boolean isDigit(char ch) {

        boolean isDigit = false;
        for (int i = 0; i < digits.length(); i++)
            if (ch == digits.charAt(i)) {
                isDigit = true;
                break;
            }

        return isDigit;
    }


}
