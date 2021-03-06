import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SkipListTest {
    @Test
    public void test() {
        SkipList skipList = new SkipList();
        int[] values = new int[]{23, 54, 34, 12, 3, 8, 39, 25, 13, 24, 7, 89, 90};
        int[] result = new int[]{3, 8, 12, 13, 23, 24, 25, 34, 39, 54, 89, 90};

        for (int i : values) {
            skipList.add(i);
        }
        skipList.remove(7);
        skipList.print();


        Assert.assertEquals(skipList.size(), result.length);
        Assert.assertArrayEquals(result, skipList.toArray());
        Assert.assertTrue(skipList.check(23));
        Assert.assertFalse(skipList.check(33));
        skipList.add(33);
        Assert.assertTrue(skipList.check(33));

        skipList.add(344);
        Assert.assertEquals(skipList.findMax(), 344);

        skipList.remove(3);
        skipList.remove(8);
        Assert.assertEquals(skipList.findMin(), 12);
    }
}
