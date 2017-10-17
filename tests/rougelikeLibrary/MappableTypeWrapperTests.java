package rougelikeLibrary;

import static org.junit.Assert.*;

import org.junit.Test;

public class MappableTypeWrapperTests {

    @Test
    public void testQuantityItem() {
        Class [] parameterTypes = {Object.class};
        Object [] parameterValues = {"test"};

        MappableTypeWrapper mappableTypeWrapper =
                new MappableTypeWrapper(
                        String.class,
                        parameterTypes,
                        parameterValues,
                        1,
                        4,
                        50);
        assertNotNull(mappableTypeWrapper);
        assertFalse(mappableTypeWrapper.isMinQuantity());
        assertFalse(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertTrue(mappableTypeWrapper.isMinQuantity());
        assertFalse(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertFalse(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertFalse(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertTrue(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertTrue(mappableTypeWrapper.isMaxQuantity());
    }

    @Test
    public void testQuantityEnemy() {
        Class [] parameterTypes = {Object.class};
        Object [] parameterValues = {"test"};

        MappableTypeWrapper mappableTypeWrapper =
                new MappableTypeWrapper(
                        String.class,
                        parameterTypes,
                        parameterValues,
                        new EnemyAI(21),
                        1,
                        4,
                        50);
        assertNotNull(mappableTypeWrapper);
        assertFalse(mappableTypeWrapper.isMinQuantity());
        assertFalse(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertTrue(mappableTypeWrapper.isMinQuantity());
        assertFalse(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertFalse(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertFalse(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertTrue(mappableTypeWrapper.isMaxQuantity());
        mappableTypeWrapper.addQuantity();
        assertTrue(mappableTypeWrapper.isMaxQuantity());
    }
}
