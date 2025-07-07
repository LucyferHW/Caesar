import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaesarTest {

    Caesar wald = new Caesar("wald", "zdog", 3);
    Caesar lebkuchenhaus = new Caesar("lebkuchenhaus", "ohenxfkhqkdxv", 3);
    Caesar email = new Caesar("E-Mail", "I-Qemp", 4);

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCode() {
        Assertions.assertEquals(wald.coded, wald.code(3));
        Assertions.assertEquals(lebkuchenhaus.coded, lebkuchenhaus.code(3));
        Assertions.assertEquals(email.coded, email.code(4));
        Assertions.assertNotEquals(email.coded.toLowerCase(), email.code(4));
    }

    @Test
    void testDecode() {
        Assertions.assertEquals(wald.original, wald.decode(3));
        Assertions.assertEquals(lebkuchenhaus.original, lebkuchenhaus.decode(3));
        Assertions.assertEquals(email.original, email.decode(4));
        Assertions.assertNotEquals(email.original.toLowerCase(), email.decode(4));
    }

    @Test
    void testToString() {
        Assertions.assertEquals("Original: " + wald.original + "\n" + "Coded: " + wald.coded + "\n" + "shift: " + wald.shift, wald.toString());
    }
}