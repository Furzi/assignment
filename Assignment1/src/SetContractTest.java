import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SetContractTest {

    @Test
    void testNewSetIsEmpty() {
        SetInterface<String> set = new ResizableArraySet<>();
        assertTrue(set.isEmpty());
        assertEquals(0, set.getCurrentSize());
    }
}