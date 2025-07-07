public class CaesarCode {

    static char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static void main(String[] args) {
        char[] myMSG = {'Z', 'D', 'O', 'G'};
        char[] myMSG2 = {'O', 'H', 'E', 'N', 'X', 'F', 'K', 'H', 'Q', 'K', 'D', 'X', 'V'};

        System.out.println(decode(myMSG2, 3));
    }

    public static char[] decode(char[] input, int shift) {
        char[] msg = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            int letter = (input[i] - 65 - shift) % alphabet.length;
            if (letter < 0) {
                letter = alphabet.length + letter;
            }

            msg[i] = alphabet[letter];
        }

        return msg;
    }

    static char[] stringToCharArray(String text) {
        for (int i = 0; i < text.length(); i++) {

        }
        return null;
    }

    static String charArrayToString(char[] text) {
        for (int i = 0; i < text.length; i++) {

        }
        return null;
    }
}

