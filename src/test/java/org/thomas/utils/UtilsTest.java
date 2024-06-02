package org.thomas.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @Test
    void testRound_roundUp() {
        //execute
        int result = Utils.round(2.5);
        ///verify
        assertEquals(3, result);
    }

    @Test
    void testRound_roundDown() {
        //execute
        int result = Utils.round(2.4);
        ///verify
        assertEquals(2, result);
    }

    @Test
    void testConstrain_in_range() {
        //execute
        int result = Utils.constrain(5, 0, 10);
        //verify
        assertEquals(5, result);
    }

    @Test
    void testConstrain_out_of_lower_bound() {
        //execute
        int result = Utils.constrain(-5, 0, 10);
        //verify
        assertEquals(0, result);
    }

    @Test
    void testConstrain_out_of_upper_bound() {
        //execute
        int result = Utils.constrain(15, 0, 10);
        //verify
        assertEquals(10, result);
    }

    @Test
    void testAnalogRead() {
        //execute
        int result = Utils.analogRead(1);
        //verify
        assertEquals(-1, result);
    }

    @Test
    void testResolveMaxPages() {
        //execute
        int result = Utils.resolveMaxPages(10, 2);
        //verify
        assertEquals(6, result);
    }
}