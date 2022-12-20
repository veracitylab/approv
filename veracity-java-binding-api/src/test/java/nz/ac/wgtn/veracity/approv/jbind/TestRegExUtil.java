package nz.ac.wgtn.veracity.approv.jbind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRegExUtil {

    private void assertMatch(String pattern,String input) {
        assertTrue(RegExUtil.glob2regex(pattern).matcher(input).matches());
    }

    private void assertNoMatch(String pattern,String input) {
        assertFalse(RegExUtil.glob2regex(pattern).matcher(input).matches());
    }

    @Test
    public void test1() {
        assertMatch("foo","foo");
    }

    @Test
    public void test2() {
        assertNoMatch("foo","fo");
    }

    @Test
    public void test3() {
        assertNoMatch("foo","fooo");
    }

    @Test
    public void test4() {
        assertMatch("foo.Boo","foo.Boo");
    }

    @Test
    public void test5() {
        assertMatch("foo.Boo$Inner","foo.Boo$Inner");
    }

    @Test
    public void test6() {
        assertMatch("([Ljava/sql/Driver;I)[[J","([Ljava/sql/Driver;I)[[J");
    }

    @Test
    public void test7() {
        assertMatch("foo*","foo");
    }

    @Test
    public void test8() {
        assertMatch("foo*","fooo");
    }

    @Test
    public void test9() {
        assertMatch("foo*","fooohoo");
    }

    @Test
    public void test10() {
        assertNoMatch("foo?","foo");
    }

    @Test
    public void test11() {
        assertMatch("foo?","fooo");
    }

    @Test
    public void test12() {
        assertNoMatch("foo?","foooo");
    }

    @Test
    public void test13() {
        assertMatch("fo?o","fooo");
    }

    @Test
    public void test14() {
        assertMatch("fo?o","fo_o");
    }

    @Test
    public void test15() {
        assertMatch("fo*o","fooo");
    }

    @Test
    public void test16() {
        assertMatch("fo*o","foo");
    }
    @Test
    public void test17() {
        assertMatch("fo*o","fololoo");
    }

}
