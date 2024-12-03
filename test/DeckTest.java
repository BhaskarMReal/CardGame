import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.*;

public class DeckTest {
    
    @Test
    public void initialiseDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(4));
        Deck deck = new Deck(1, cards);
        assertEquals(1,deck.getId());
        assertEquals(cards, deck.getDeck());
    }   

    @Test 
    public void setDeckId() {
        Deck deck = new Deck(1, new ArrayList<>());
        deck.setId(3);
        assertEquals(3, deck.getId());
    }


    @Test 
    public void setDeckCards() {
        ArrayList<Card> old = new ArrayList<>();
        ArrayList<Card> new_ = new ArrayList<>();
        new_.add(new Card(3));
        Deck deck = new Deck(1, old);
        deck.setDeck(new_);
        assertEquals(new_, deck.getDeck());
    }

    @Test
    public void emptyDeckInitialisation() {
        ArrayList<Card> cards = new ArrayList<>();
        Deck deck = new Deck(1, cards);
        assertEquals(0, deck.getDeck().size());
    }

    @Test
    public void getDeckId() {
        Deck deck = new Deck(5, new ArrayList<>());
        assertEquals(5, deck.getId());
    }

    @Test
    public void checkDeckThreadSafety() throws InterruptedException {
        ArrayList<Card> cards1 = new ArrayList<>();
        ArrayList<Card> cards2 = new ArrayList<>();
        cards1.add(new Card(2));
        cards2.add(new Card(4));
        Deck deck = new Deck(1, new ArrayList<>());
        Thread thread1 = new Thread(() -> {
            deck.setDeck(cards1);
        });

        Thread thread2 = new Thread(() -> {
            deck.setDeck(cards2);
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        ArrayList<Card> res = deck.getDeck();
        assertTrue(res.equals(cards1) || res.equals(cards2));
    }
}
