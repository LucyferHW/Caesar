public class Hack {
    private Caesar caesar;

    public Hack(Caesar caesar) {
        this.caesar = caesar;
    }

    public HackTable brutForce(CodeEnum codeEnum) {

        String[] table = new String[CaesarConstants.alphabetSize + 1];

        for (int i = 0; i <= CaesarConstants.alphabetSize; i++) {
            if (codeEnum == CodeEnum.DECODE) table[i] = caesar.decode(i);
            else if (codeEnum == CodeEnum.CODE) table[i] = caesar.code(i);
            else throw new RuntimeException("CodeEnum can not be: " + codeEnum);
        }

        return new HackTable(table);
    }
}

class HackTable {
    String[] table;

    public HackTable(String[] strings) {
        table = strings;
    }

    @Override
    public String toString() {
        String result = "Table:\n";
        for (int i = 0; i <= CaesarConstants.alphabetSize; i++) {
            result += addZero(i) + " : " + table[i] + "\n";
        }
        return result.substring(0, result.length() - 1);
    }

    private String addZero(int num) {
        if (num < 10 && num >= 0) {
            return "0" + num;
        } else {
            return "" + num;
        }
    }
}
