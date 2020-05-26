package it.polimi.ingsw.PSP14.client.view.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CLIHelperTest {
    CLIHelper helper;

    UICache cache = new UICache();

    @BeforeEach
    void setUp() {
        helper = new CLIHelper(cache);
    }

//    @Test
//    void testOneRect() {
//        helper.addRect(0,0,10,10);
//        helper.print();
//    }
//
//    @Test
//    void testManyRect() {
//        helper.addRect(0,0,10,10);
//        helper.addRect(5,5,7,7);
//        helper.addRect(3,3,20, 4);
//        helper.print();
//    }
//
//    @Test
//    void testLines() {
//        helper.addLine(5, 0, 5, 10);
//        helper.addLine(10, 0, 10, 10);
//        helper.addLine(0, 3, 20, 3);
//        helper.addLine(0, 8, 20, 8);
//        helper.addLine(10, 1, 15, 1);
//        helper.print();
//    }
//
//    @Test
//    void testDrawTest() {
////        helper.addText(24, 0, "Hello world!");
//        helper.addText(0, 0, "Hello world!", CLIColor.BLUE);
//        helper.addText(2, 2, "Hello world!", CLIColor.GREEN);
//        helper.addText(4, 4, "Hello world!", CLIColor.BLUE);
//
//        helper.print();
//    }

    @Test void color() {
        ColorChar x = new ColorChar('X', CLIColor.RED);
        System.out.println(x);
    }
}
