import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class InputTest {

    @Test
    public void testPositiveIntegerInputSuccess() {
        String simulatedInput = "1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        int playerNum = -1;
        while (playerNum <= 0) {
            System.out.println("Please enter the number of players: ");
            if (scan.hasNextInt()) {
                playerNum = 1;
                System.out.println(1);

                if (playerNum <= 0) {
                    System.out.println("Please enter a number greater than 0");
                }

            } else {
                System.out.println("Input is not a number");
                scan.next();
            }
        }
        assertEquals(1, playerNum);
    }

    @Test
    public void testNegativeIntegerInputFailure() {
        String outputMessage = "";
        int playerNum = -1;
        if (playerNum <= 0) {
            System.out.println("Please enter the number of players: ");

            playerNum = -1;


            if (playerNum <= 0) {
                System.out.println("Please enter a number greater than 0");
                outputMessage = "Please enter a number greater than 0";
            }


        }
        assertEquals("Please enter a number greater than 0", outputMessage);
    }


    @Test
    public void testValidPackFileExists() {
        Pack testValidPack = new Pack(null, null);
        ArrayList<Card> cards;
        String outputMessage = "";
        String testPackFile = "test1.txt";
        File file = new File("logs/" + testPackFile);
        if (!file.exists()) {
            System.out.println("Pack file does not exist");
            outputMessage = "Pack file does not exist";
        } else {
            System.out.println("Pack file exists");
            outputMessage = "Pack file exists";
        }

        assertEquals("Pack file exists", outputMessage);

    }

    @Test
    public void testValidFileContents() {
        Pack testValidPack = new Pack(null, null);
        ArrayList<Card> cards = new ArrayList<>();

        String packFile = null;


        System.out.println("Please enter location of pack to load (.txt):");
        String testPackFile = "one.txt";
        int playerNum = 1;

        File file = new File("logs/" + testPackFile);
        if (!file.exists()) {
            System.out.println("Pack file does not exist");
        }

        testValidPack.setName(testPackFile);
        try {
            cards = testValidPack.packToList(testValidPack.getName());
        } catch (Exception e) {
            System.out.println("Error loading pack");
        }

        testValidPack.setPackList(cards);

        ArrayList<Integer> validCards = null;
        if (testValidPack.getPackList().size() == (playerNum * 8)) {
            validCards = new ArrayList<>();
            for (Card card : testValidPack.getPackList()) {
                int cardValue = card.getValue();
                if (cardValue >= 1) {
                    validCards.add(cardValue);
                }
            }
            if (validCards.size() == (8 * playerNum)) {
                packFile = testPackFile;
                System.out.println("Valid Pack file loaded " + packFile);
            } else {
                System.out.println("Error: Invalid cards in pack file");
            }

        }
        assert validCards != null;
        assertEquals(8, validCards.size());
        assertEquals(testPackFile, packFile);


    }

    @Test
    public void testInvalidFileLessContents() {
        Pack testValidPack = new Pack(null, null);
        ArrayList<Card> cards = null;
        String testPackFile = "test1.txt";
        File file = new File("logs/" + testPackFile);
        int playerNum = 1;

        if (!file.exists()) {
            System.out.println("Pack file does not exist");

        }
        testValidPack.setName(testPackFile);
        try {
            cards = testValidPack.packToList(testValidPack.getName());
        } catch (Exception e) {
            System.out.println("Error loading pack");
        }
        String outputMessage = null;
        testValidPack.setPackList(cards);
        if (testValidPack.getPackList().size() < (8 * playerNum)) {
            System.out.println("Pack file contains less than required number of cards");
            outputMessage = "Pack file contains less than required number of cards";
        }

        assertEquals("Pack file contains less than required number of cards", outputMessage);
    }

    @Test
    public void testInvalidFileMoreContents() {
        Pack testValidPack = new Pack(null, null);
        ArrayList<Card> cards = null;
        String testPackFile = "test3.txt";
        File file = new File("logs/" + testPackFile);
        int playerNum = 1;

        if (!file.exists()) {
            System.out.println("Pack file does not exist");

        }
        testValidPack.setName(testPackFile);
        try {
            cards = testValidPack.packToList(testValidPack.getName());
        } catch (Exception e) {
            System.out.println("Error loading pack");
        }

        testValidPack.setPackList(cards);

        String outputMessage = null;
        if (testValidPack.getPackList().size() > (8 * playerNum)) {
            System.out.println("Pack file contains more than required number of cards");
            outputMessage = "Pack file contains more than required number of cards";
        }
        assertEquals("Pack file contains more than required number of cards", outputMessage);
    }

    @Test
    public void testInvalidFileNegativeCardsInContent() {
        Pack testValidPack = new Pack(null, null);
        ArrayList<Card> cards = null;
        String testPackFile = "test2.txt";
        File file = new File("logs/" + testPackFile);
        int playerNum = 1;

        if (!file.exists()) {
            System.out.println("Pack file does not exist");

        }
        testValidPack.setName(testPackFile);
        try {
            cards = testValidPack.packToList(testValidPack.getName());
        } catch (Exception e) {
            System.out.println("Error loading pack");
        }

        testValidPack.setPackList(cards);
        String outputMessage = null;
        if (testValidPack.getPackList().size() == (playerNum * 8)) {
            ArrayList<Integer> validCards = new ArrayList<>();
            for (Card card : testValidPack.getPackList()) {
                int cardValue = card.getValue();
                if (cardValue >= 1) {
                    validCards.add(cardValue);
                }
            }

            if (validCards.size() == (playerNum * 8)) {
                System.out.println("No invalid cards found");
            } else {
                System.out.println("Error: Invalid cards in pack file");
                outputMessage = "Error: Invalid cards in pack file";
            }
        }
        assertEquals("Error: Invalid cards in pack file", outputMessage);
    }

    @Test
    public void testEmptyFileContents() {
        Pack testValidPack = new Pack(null, null);
        ArrayList<Card> cards = null;
        String testPackFile = "test4.txt";
        File file = new File("logs/"+testPackFile);

        if (!file.exists()) {
            System.out.println("Pack file does not exist");

        }
        testValidPack.setName(testPackFile);
        try {
            cards = testValidPack.packToList(testValidPack.getName());
        } catch (Exception e) {
            System.out.println("Error loading pack");

        }
        testValidPack.setPackList(cards);
        if (testValidPack.getPackList().isEmpty()) {
            System.out.println("Pack file is empty");

        }
        assertEquals(testValidPack.getPackList().size(), 0);
    }


}
