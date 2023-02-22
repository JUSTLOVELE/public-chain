import org.junit.Test;

public class MainTest {

    @Test
    public void test() {

        Long a = (long) 1.0;
        long l = a << 252;
        System.out.println(l);

    }
}
