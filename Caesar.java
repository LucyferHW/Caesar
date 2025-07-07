public class Caesar {
    String original;
    String coded;
    int shift;

    public Caesar() {
        this.original = null;
        this.coded = null;
        this.shift = 0;
    }

    public Caesar(String original, String coded, int shift) {
        this.original = original;
        this.coded = coded;
        this.shift = shift;
    }

    public String code(int shift) {
        return codeEngine(this.original, shift, CodeEnum.CODE);
    }

    public String decode(int shift) {
        return codeEngine(this.coded, shift, CodeEnum.DECODE);
    }

    private String codeEngine(String text, int shift, CodeEnum codeEnum) {
        String msg = "";

        if (codeEnum == CodeEnum.DECODE) shift *= -1;

        for (int i = 0; i < text.length(); i++) {
            boolean isUppercase = isUppercase(text.charAt(i));

            char charAtI = text.toLowerCase().charAt(i);

            if (!inRange(charAtI)) {
                msg += charAtI;
                continue;
            }

            int offset = charAtI - 'a';
            int shifted = (offset + shift + CaesarConstants.alphabetSize) % CaesarConstants.alphabetSize;
            int letter = 'a' + shifted;

            String stringLetter = "" + (char) letter;

            if (isUppercase) {
                msg += stringLetter.toUpperCase();
            } else {
                msg += stringLetter;
            }
        }

        return msg;
    }

    private static char[] stringToCharArray(String text) {
        char[] result = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = text.charAt(i);
        }
        return result;
    }

    private static String charArrayToString(char[] text) {
        String result = "";
        for (int i = 0; i < text.length; i++) {
            result += text[i];
        }
        return result;
    }

    private boolean inRange(char letter) {
        return letter >= 'a' && letter <= 'z';
    }

    private boolean isUppercase(char letter) {
        return letter >= 'A' && letter <= 'Z';
    }

    private int abs(int num) {
        if (num >= 0) {
            return num;
        } else {
            return num * -1;
        }
    }

    @Override
    public String toString() {
        return "Caesar:\n"+"Original: " + this.original + "\n" + "Coded: " + this.coded + "\n" + "shift: " + this.shift;
    }
}
