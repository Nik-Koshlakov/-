package com.vaadin.service.utils;

import org.apache.http.params.HttpProtocolParams;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nik on 24.07.2017.
 */
public class ModifyString {
    public static String modify(String input) {
        String output = "";

        if (input.contains(" ")) {
            String[] sub = input.split(" ");

            if (sub.length > 0) {
                for (int i = 0; i < sub.length - 1; i++)
                    output += sub[i] + "_";

                output += sub[sub.length - 1];
            }
        }

        return output.isEmpty() ? input : output;
    }

    public static String[] parseButtonSecretMessage(String input) {
        String[] array = new String[2];
        String[] str = input.split(" ");

        array[0] = str[0];
        array[1] = "";
        for (int i = 1; i < str.length - 1; i++)
            array[1] += str[i] + " ";
        array[1] += str[str.length - 1];

        return array;
    }

    @Test
    public void testParseButtonSecretMessage() {
        String[] expected = {"aaa", "bbb"};

        String acual1 = "aaa";
        String acual2 = " aaa";
        String acual3 = "aaa bbb";
        String acual4 = "aaa ";

        assertArrayEquals(expected, parseButtonSecretMessage(acual3));
//        assertArrayEquals(expected, parseButtonSecretMessage(acual1));
//        assertArrayEquals(expected, parseButtonSecretMessage(acual2));
//        assertArrayEquals(expected, parseButtonSecretMessage(acual4));

        assertNotNull(parseButtonSecretMessage(acual1));
        assertNotNull(parseButtonSecretMessage(acual2));
        assertNotNull(parseButtonSecretMessage(acual4));
    }

    @Test
    public void testModify() {
        String actual1 = " ";
        String actual2 = " someText";
        String actual3 = " some Text";
        String actual4 = "someText ";
        String actual5 = "";
        String actual6 = "someText";

        String expect1 = "";
        String expect2 = "someText";
        String expect4 = " ";
        String expect5 = "_someText";
        String expect6 = "_some_Text";

        assertNotNull(modify(actual1));
        assertNotNull(modify(actual5));

        // expect actual
        assertEquals(expect4, modify(actual1));
        assertEquals(expect5, modify(actual2));
        assertEquals(expect6, modify(actual3));
        assertEquals(expect2, modify(actual4));
        assertEquals(expect2, modify(actual6));
        assertEquals(expect1, modify(actual5));
    }
}
