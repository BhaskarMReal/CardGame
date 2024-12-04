import java.util.ArrayList;

public class Deck {
    private int id;
    private ArrayList<Card> deck;
    // constructor which contains the deck id and the deck cards
    public Deck(int deckId, ArrayList<Card> cards) {
        this.id = deckId;
        this.deck = cards;
    }

    //Set an array for decks
    public synchronized void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    //Assign an ID for each Deck
    public void setId(int id) {
        this.id = id;
    }

    //Get and output ID for decks
    public int getId() {
        return id;
    }

    //Output cards in a deck
    public ArrayList<Card> getDeck() {
        return deck;
    }




}
