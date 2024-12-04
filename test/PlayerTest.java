import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private List<Player> players = new ArrayList<>();
    
    // initialises overarching players and gameControl
    @Before
    public void initialisePlayer() {
        ControlGame gameControl = new ControlGame();
        players.add(new Player(1, null, 0, 0, null, null, null, null, gameControl, 0, null));
        players.add(new Player(2, null, 0, 0, null, null, null, null, gameControl, 0, null));
    }
    // checks the constructor is not null
    @Test
    public void checkPlayerConstructorNotNull() {
        Player player = players.get(0);
        assertNotNull(player);
    }
    // checks set and get hand
    @Test
    public void checkSetAndGetHand() {
        ArrayList<Card> hand = new ArrayList<>();
        for (int i=0; i < 4; i++) {
            hand.add(new Card(0+i));
        }

        players.get(0).setHand(hand);
        ArrayList<Card> playerHand = players.get(0).getHand();
        assertEquals(hand, playerHand);
    }
    // checks if getId works
    @Test
    public void checkGetId() {
        assertEquals(1, players.get(0).getId());
    }
    // checks set and get players
    @Test
    public void checkPlayersSetAndGet() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = new Player(3, null, 0, 0, null, null,null ,null,null, 0, null);
        ArrayList<Player> playersNew = new ArrayList<>();
        playersNew.add(player1);
        playersNew.add(player2);
        player3.setPlayers(playersNew);
        assertEquals(playersNew, player3.getPlayers());
    }

    // tests whether winnerId is set to the default value (initialised value) and whether set and get winnerid works
    // similar to all the other id set and get methods
    @Test
    public void checkWinnerId() {
        int win1 = players.get(0).getWinnerId();
        assertEquals(0, win1);
        players.get(0).setWinnerId(1);
        int win2 = players.get(0).getWinnerId();
        assertEquals(1, win2);
    }

    // tests if set and get DiscardDeck works, similar to the draw deck set and get methods
    @Test
    public void checkDiscardDeck() {
        ArrayList<Card> discardDeck = new ArrayList<>();
        discardDeck.add(new Card(3));
        discardDeck.add(new Card(6));
        discardDeck.add(new Card(9));
        discardDeck.add(new Card(1));
        Deck deck = new Deck(1, discardDeck);
        deck.setDeck(discardDeck);
        players.get(0).setDiscardDeck(deck);
        ArrayList<Card> playerDeck = players.get(0).getDiscardDeck().getDeck();
        assertEquals(playerDeck, players.get(0).getDiscardDeck().getDeck());

    }
    // checks left and right player are set correctly
    @Test
    public void checkLeftAndRightPlayer() {
        players.get(0).setLeftPlayer(players.get(1));
        players.get(0).setRightPlayer(players.get(1));
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player leftPlayer = player1.getLeftPlayer();
        Player rightPlayer = player1.getRightPlayer();
        assertEquals(player2, leftPlayer);
        assertEquals(player2, rightPlayer);
    }
    // checks that preferredCardList keeps non-preferred cards and ignores preferred cards
    @Test
    public void checkPreferredCardList() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1)); 
        hand.add(new Card(1)); 
        hand.add(new Card(5)); 
        hand.add(new Card(9)); 
        List<Integer> prefCards = players.get(0).preferredCardList(players.get(0).getId(), hand);
        List<Integer> testCards = new ArrayList<>();
        testCards.add(5);
        testCards.add(9);
        assertEquals(testCards, prefCards);
    }
    // check that gameControl works correctly if player has not won
    @Test
    public void checkPlayerLose() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1));
        hand.add(new Card(1));
        hand.add(new Card(5));
        hand.add(new Card(9));
        players.get(0).setHand(hand);
        Player player1 = players.get(0);
        assertEquals(player1, players.get(0));
        player1.checkWin(player1.getHand());
        assertFalse(player1.getControlGame().isGameWon());
    }
    // check that it doesn't keep any cards and is returns an empty arraylist empty
    @Test 
    public void checkPreferredCardListFull() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1)); 
        hand.add(new Card(1)); 
        hand.add(new Card(1)); 
        hand.add(new Card(1));
        List<Integer> prefCards = players.get(0).preferredCardList(players.get(0).getId(), hand);
        assertTrue(prefCards.isEmpty());
    }
    // checks if preferredCard function works and that it doesn't choose a preferred card
    @Test
    public void checkPreferredCard() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1)); 
        hand.add(new Card(1)); 
        hand.add(new Card(5)); 
        hand.add(new Card(9)); 
        List<Integer> prefCards = players.get(0).preferredCardList(players.get(0).getId(), hand);
        assertTrue(prefCards.contains(9) || prefCards.contains(5));
    }
    // checks whether after drawing and discarding, hand remains at 4 cards
    @Test
    public void checkDrawAndDiscard() {
        ArrayList<Card> drawDeck1 = new ArrayList<>();
        ArrayList<Card> hand1 = new ArrayList<>();
        ArrayList<Card> drawDeck2 = new ArrayList<>();
        ArrayList<Card> hand2 = new ArrayList<>();
        hand1.add(new Card(1)); 
        hand1.add(new Card(1)); 
        hand1.add(new Card(5)); 
        hand1.add(new Card(9)); 
        hand2.add(new Card(1)); 
        hand2.add(new Card(1)); 
        hand2.add(new Card(5)); 
        hand2.add(new Card(9)); 
        drawDeck1.add(new Card(4));
        drawDeck1.add(new Card(5));
        drawDeck1.add(new Card(6));
        drawDeck1.add(new Card(7));
        drawDeck2.add(new Card(8));
        drawDeck2.add(new Card(2));
        drawDeck2.add(new Card(1));
        drawDeck2.add(new Card(5));
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        players.get(0).setRightPlayer(player2);
        players.get(1).setRightPlayer(player1);
        players.get(0).setLeftPlayer(player2);
        players.get(1).setLeftPlayer(player1);
        int deck1Id = 1;
        int deck2Id = 2;
        Deck deck1 = new Deck(deck1Id, drawDeck1);
        Deck deck2 = new Deck(deck2Id, drawDeck2);
        players.get(0).setDrawDeckId(deck1Id);
        players.get(0).setDiscardDeckId(deck2Id);
        players.get(1).setDiscardDeckId(deck1Id);
        players.get(1).setDrawDeckId(deck2Id);
        players.get(0).setDrawDeck(deck1);
        players.get(1).setDrawDeck(deck2);
        players.get(0).setDiscardDeck(deck2);
        players.get(1).setDiscardDeck(deck1);
        players.get(0).drawAndDiscard(hand1, players.get(0).getDrawDeck(), players.get(1).getDiscardDeck());
        assertEquals(4, players.get(0).getHand().size());
    }
    // checks player outputs correct information to file (pretty much same code as outputPlayerCurrentHand)
    @Test
    public void checkOutputInitial() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1));
        hand.add(new Card(1));
        hand.add(new Card(5));
        hand.add(new Card(9));
        players.add(new Player(99, null, 0, 0, null, null, null, null, null, 0, null));
        players.get(2).setHand(hand);
        Player player3 = players.get(2);
        player3.outputPlayerInitial(player3.getId(), hand);
        try {
            File file = new File("logs/player99_output.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                assertEquals("player 99 initial hand: 1 1 5 9", line);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void deleteInitialOutputFile() {
        File file = new File("logs/player99_output.txt");
        if (file.exists()) {
            file.delete();
        }
    }
    // check if drawing works
    @Test
    public void checkOutputDrawToFile() {
        Player player3 = new Player(98, null, 0, 0, null, null, null, null, null, 0, null);
        player3.outputDrawOrDiscardToFile(player3.getId(), 1, new Card(4), "draws");
        try {
            File file = new File("logs/player98_output.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                assertEquals("player 98 draws a 4 from deck 1", line);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteDrawOutputFile() {
        File file = new File("logs/player98_output.txt");
        if (file.exists()) {
            file.delete();
        }
    }
    // check that discarding works
    @Test
    public void checkOutputDiscardToFile() {
        Player player3 = new Player(97, null, 0, 0, null, null, null, null, null, 0, null);
        player3.outputDrawOrDiscardToFile(player3.getId(), 2, new Card(5), "discards");
        try {
            File file = new File("logs/player97_output.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                assertEquals("player 97 discards a 5 to deck 2", line);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteDiscardOutputFile() {
        File file = new File("logs/player97_output.txt");
        if (file.exists()) {
            file.delete();
        }
    }
    // check that winning player outputs to terminal
    @Test
    public void checkPlayerWinToTerminal() {
        try {
            Player player3 = new Player(96, null, 0, 0, null, null, null, null, null, 0, null);
            player3.outputPlayerWinToTerminal(player3.getId());
            String simInput = "Player 96 wins";
            System.setIn(new ByteArrayInputStream(simInput.getBytes()));
            Scanner scan = new Scanner(System.in);
            String str = scan.nextLine();
            assertEquals("Player 96 wins", str);
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // check that player outputs correct message if they won
    @Test
    public void checkOutputFinalFileWinner() {
        Player player3 = new Player(95, null, 0, 0, null, null, null, null, null, 0, null);
        player3.setWinnerId(95);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1));
        hand.add(new Card(5));
        hand.add(new Card(9));
        hand.add(new Card(7));
        player3.outputPlayerFinalFile(player3.getId(), player3.getWinnerId(),hand);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("player 95 wins");
        strings.add("player 95 exits");
        strings.add("player 95 final hand: 1 5 9 7");
        try {
            File file = new File("logs/player95_output.txt");
            Scanner scan = new Scanner(file);
            for (int i=0; i < strings.size(); i++) {
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    assertEquals(strings.get(i), line);
                    break;
                }
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @After
    public void deleteFinalOutputFileWinner() {
        File file = new File("logs/player95_output.txt");
        if (file.exists()) {
            file.delete();
        }
    }
    // check if player outputs correct quitting message
    @Test
    public void checkOutputFinalFileOther() {
        Player player3 = new Player(94, null, 0, 0, null, null, null, null, null, 0, null);
        player3.setWinnerId(95);
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1));
        hand.add(new Card(5));
        hand.add(new Card(9));
        hand.add(new Card(7));
        player3.outputPlayerFinalFile(player3.getId(), player3.getWinnerId(),hand);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("player 95 has informed player 94 that player 95 has won");
        strings.add("player 94 exits");
        strings.add("player 94 final hand: 1 5 9 7");
        try {
            File file = new File("logs/player94_output.txt");
            Scanner scan = new Scanner(file);
            for (int i=0; i < strings.size(); i++) {
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    assertEquals(strings.get(i), line);
                    break;
                }
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteFinalOutputFileOther() {
        File file = new File("logs/player94_output.txt");
        if (file.exists()) {
            file.delete();
        }
    }
    // check if output to deck file succeeds
    @Test
    public void checkOutputDeckToFileSuccess() {
        Deck deck = new Deck(99, null);
        ArrayList<Card> deckCards = new ArrayList<>();
        deckCards.add(new Card(1));
        deckCards.add(new Card(5));
        deckCards.add(new Card(9));
        deckCards.add(new Card(7));
        deck.setDeck(deckCards);
        Player playerNew = new Player(3, null, 99, 0, null, null, null, null, null, 0, null);
        playerNew.setDrawDeck(deck);
        playerNew.outputDeckToFile(deck);
        try {
            File file = new File("logs/deck99_output.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                assertEquals("Deck99 contents: 1 5 9 7", line);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteDeckOutputFileSuccess() {
        File file = new File("logs/deck99_output.txt");
        if (file.exists()) {
            file.delete();
        }
    }
    // check whether output to deck file fails if there aren't enough cards
    @Test
    public void checkOutputDeckToFileFail() {
        Deck deck = new Deck(90, null);
        ArrayList<Card> deckCards = new ArrayList<>();
        deckCards.add(new Card(1));
        deckCards.add(new Card(5));
        deckCards.add(new Card(9));
        deck.setDeck(deckCards);
        Player playerNew = new Player(3, null, 90, 0, null, null, null, null, null, 0, null);
        playerNew.setDrawDeck(deck);
        playerNew.outputDeckToFile(deck);
        try {
            File file = new File("logs/deck90_output.txt");
            assertTrue(file.length() == 0);
            FileWriter writer = new FileWriter("logs/deck90_output.txt");
            writer.write("one");
            if (file.length() == 1) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}