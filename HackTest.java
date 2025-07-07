import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HackTest {

    static Caesar abc;
    Caesar wald = new Caesar("wald", "zdog", 3);
    Caesar lebkuchenhaus = new Caesar("lebkuchenhaus", "ohenxfkhqkdxv", 3);
    Caesar email = new Caesar("E-Mail", "I-Qemp", 4);

    @BeforeAll
    static void createAlphabet() {
        String ori = "";
        for (int i = 0; i < CaesarConstants.alphabetSize; i++) {
            ori += (char) (i + 'a');
        }

        abc = new Caesar(ori, ori, 0);
    }

    @Test
    void testBrutForce() {
        //System.out.println(new Hack(abc).brutForce(CodeEnum.CODE).toString());
        //System.out.println(new Hack(abc).brutForce(CodeEnum.DECODE).toString());

        System.out.println(new Hack(wald).brutForce(CodeEnum.DECODE).toString());
    }
}