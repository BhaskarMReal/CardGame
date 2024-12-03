import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class Player implements Runnable {

    private int id;
    private ArrayList<Card> hand;
    private int drawDeckId;
    private int discardDeckId;
    private Deck drawDeck;
    private Deck discardDeck;
    private Player rightPlayer;
    private Player leftPlayer; 
    private final  ReentrantLock handLock = new ReentrantLock();
    private final ReentrantLock drawDeckLock = new ReentrantLock();
    private final ReentrantLock discardDeckLock = new ReentrantLock();
    private final ReentrantLock idLock = new ReentrantLock();
    private final ReentrantLock playerLock = new ReentrantLock();
    private final ControlGame controlGame;
    private int winnerId;
    private ArrayList<Player> players;
    private final ReentrantLock playerListLock = new ReentrantLock();

    public Player(int playerId, ArrayList<Card> playerHand, int drawId, int discardId, Deck playerDrawDeck, Deck playerDiscardDeck, Player previousPlayer, Player nextPlayer, ControlGame gameControl, int playerWon, ArrayList<Player> playersInGame) {

        this.id = playerId;
        this.hand = playerHand;
        this.discardDeck = playerDiscardDeck;
        this.drawDeck = playerDrawDeck;
        this.drawDeckId = drawId;
        this.discardDeckId = discardId;
        this.leftPlayer = previousPlayer;
        this.rightPlayer = nextPlayer;
        this.controlGame = gameControl;
        this.winnerId = playerWon;
        this.players = playersInGame;
    }

    @Override
    public void run() {
        try {
         
            while (!controlGame.isGameWon()) {
                checkWin(hand);                
                drawAndDiscard(hand, drawDeck, discardDeck);
                outputPlayerCurrent(id, hand);
                
                
                
            }

    
            outputPlayerFinalFile(id, getWinnerId(), hand);
            outputDeckToFile(drawDeck);
            

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkWin(ArrayList<Card> hand) {
        try {
            ArrayList<Card> winCondition = new ArrayList<>();
            winCondition.add(new Card(getId()));
            winCondition.add(new Card(getId()));
            winCondition.add(new Card(getId()));
            winCondition.add(new Card(getId()));
            if (winCondition.equals(hand)) {
                controlGame.setGameWon();
                setControlGame(getControlGame());
            } 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public ControlGame getControlGame() {
        return controlGame;
    }

    public void setControlGame(ControlGame controlGame) {
        controlGame.setGameWon();
    }

    public void setPlayers(ArrayList<Player> players) {
        playerListLock.lock();
        try {
            this.players = players;
        } finally {
            playerListLock.unlock();
        }
        
    }

    public ArrayList<Player> getPlayers() {
        playerListLock.lock();
        try {
            return players;
        } finally {
            playerListLock.unlock();
        }
        
    }

    public void setWinnerId(int winnerId) {
        idLock.lock();
        try {   
        this.winnerId = winnerId;
        } finally {
            idLock.unlock();
        }
      
    }

    

    public int getWinnerId() {
        idLock.lock();
        try {
            return winnerId;
        } finally {
            idLock.unlock();
        }
    }

    //Set the deck which cards to be discard to
    public void setDiscardDeck(Deck discardDeck) {
        discardDeckLock.lock();
        try {
            this.discardDeck = discardDeck;
        } finally {
            discardDeckLock.unlock();
        }
    }

    //Output which discard deck that have been set
    public Deck getDiscardDeck() {
        discardDeckLock.lock();
        try {
            return discardDeck;
        } finally {
            discardDeckLock.unlock();
        }
    }

    //Set ID for discard deck to be identifiable
    public void setDiscardDeckId(int discardDeckId) {
        idLock.lock();
        try {
            this.discardDeckId = discardDeckId;
        } finally {
            idLock.unlock();
        }
    }

    //Output discard deck ID
    public int getDiscardDeckId() {
        idLock.lock();
        try {
            return discardDeckId;
        } finally {
            idLock.unlock();
        }
    }

    //Set player ID
    public void setId(int id) { 
        idLock.lock();
        try {
            this.id = id;
        } finally {
            idLock.unlock();
        }
    }

    //Output Player ID
    public int getId() {
        idLock.lock();
        try {
            return id;
        } finally {
            idLock.unlock();
        }
    }

    //Get the array of cards in hand
    public ArrayList<Card> getHand() {
        handLock.lock();
        try {
            return hand;
        } finally {
            handLock.unlock();
        }
    }

    //Assign cards to hand
    public void setHand(ArrayList<Card> hand) {
        handLock.lock();
        try {
            this.hand = hand;
        } finally {
            handLock.unlock();
        }
    }
  
    //Get the deck to draw from
    public Deck getDrawDeck() {
        drawDeckLock.lock();
        try {
            return drawDeck;
        } finally {
            drawDeckLock.unlock();
        }
    }

    //Set which deck to draw from
    public void setDrawDeck(Deck drawDeck) {
        drawDeckLock.lock();
        try {
            this.drawDeck = drawDeck;
        } finally {
            drawDeckLock.unlock();
        }
    }

    //Get player on the left of current player that are running
    public Player getLeftPlayer() {
        playerLock.lock();
        try {
            return leftPlayer;
        } finally {
            playerLock.unlock();
        }
    }

    //Set player on the left of current player that are running
    public void setLeftPlayer(Player leftPlayer) {
        playerLock.lock();
        try {
            this.leftPlayer = leftPlayer;
        } finally {
            playerLock.unlock();
        }
    }

    //Get player on the right of current player that are running
    public Player getRightPlayer() {
        playerLock.lock();
        try {
            return rightPlayer;
        } finally {
            playerLock.unlock();
        }
    }

    //Set player on the right of current player that are running
    public void setRightPlayer(Player rightPlayer) {
        playerLock.lock();
        try {
            this.rightPlayer = rightPlayer;
        } finally {
            playerLock.unlock();
        }
    }

    //Get deck ID which current player draws from
    public int getDrawDeckId() { 
        idLock.lock();
        try {
            return drawDeckId;
        } finally {
            idLock.unlock();
        }
    }

    //Set deck ID which current player draws from
    public void setDrawDeckId(int drawDeckId) {
        idLock.lock();
        try {
            this.drawDeckId = drawDeckId;
        } finally {
            idLock.unlock();
        }
    }

    //Output into log current player's initial hand
    public synchronized void outputPlayerInitial(int playerId, ArrayList<Card> playerHand) {
        try {
            BufferedWriter playerlog = new BufferedWriter(new FileWriter("logs/player"+id+"_output.txt", true));
            playerlog.write("player "+id+" initial hand: "+hand.get(0).getValue()+" "+hand.get(1).getValue()+" "+hand.get(2).getValue()+" "+hand.get(3).getValue());
            playerlog.newLine();
            playerlog.close();
        } catch (IOException e) {
            System.out.println("Error: Couldn't log player " + id + "'s current hand!");
            e.printStackTrace();
        }
    }

    //Output into log current player's current hand
    public synchronized void outputPlayerCurrent(int id, ArrayList<Card> hand) {
        try {
            BufferedWriter playerLog = new BufferedWriter(new FileWriter("logs/player" + id + "_output.txt", true));
            playerLog.write("player " + id + " current hand is: "+hand.get(0).getValue()+" "+hand.get(1).getValue()+" "+hand.get(2).getValue()+" "+hand.get(3).getValue());
            playerLog.newLine();
            playerLog.close();
        } catch (IOException e) {
            System.out.println("Error: Couldn't log player " + id + "'s current hand!");
            e.printStackTrace();
        }
    }

    //Output into log current player's discarded card
    public synchronized void outputDrawOrDiscardToFile(int id, int deckId, Card selectedCard, String action) {
        try {
            String actionWord = action.equals("draws") ? "from" : "to";
            int card = selectedCard.getValue();
            BufferedWriter playerLog = new BufferedWriter(new FileWriter("logs/player" + id + "_output.txt", true));
            playerLog.write("player " + id + " " + action + " a " + card + " " + actionWord + " deck " + deckId);
            playerLog.newLine();
            playerLog.close();
        } catch (IOException e) {
            System.out.println("Error: Couldn't log player " + id + "'s action (Draw or Discard)!");
            e.printStackTrace();
        }
    }

    //Output into log player that wins and cards in their hand
    public synchronized void outputPlayerFinalFile(int id, int winnerId, ArrayList<Card> hand) {
        try {
            //Output into log final hand of the winned player
            if (id == winnerId) {
                BufferedWriter playerlog = new BufferedWriter(new FileWriter("logs/player"+id+"_output.txt", true));
                playerlog.write("player " +id+ " wins");
                playerlog.newLine();
                playerlog.write("player " +id+ " exits");
                playerlog.newLine();
                playerlog.write("player " +id+ " final hand: "+hand.get(0).getValue()+ " "+ hand.get(1).getValue()+ " " + hand.get(2).getValue() + " " + hand.get(3).getValue());
                playerlog.newLine();
                playerlog.close();
            //Output into log final hand of player that lose
            } else {
                BufferedWriter playerlog = new BufferedWriter(new FileWriter("logs/player"+id+"_output.txt", true));
                playerlog.write("player " +getWinnerId()+ " has informed player "+id+ " that player " + getWinnerId() + " has won");
                playerlog.newLine();
                playerlog.write("player " +id+ " exits");
                playerlog.newLine();
                playerlog.write("player " +id+ " final hand: "+hand.get(0).getValue()+ " "+ hand.get(1).getValue()+ " " + hand.get(2).getValue() + " " + hand.get(3).getValue());
                playerlog.newLine();
                playerlog.close();
        } 
        } catch (Exception e) {
            System.out.println("Error: Couldn't log player " +id+ "'s final output!");
            e.printStackTrace();
        }
    
    }

    //Output into Terminal or screen which player win
    public synchronized void outputPlayerWinToTerminal(int winnerId) {
        System.out.println("Player "+winnerId+" wins");
    }

    //
    public synchronized void outputDeckToFile(Deck deck) {
        try {
            BufferedWriter playerlog = new BufferedWriter(new FileWriter("logs/deck"+drawDeck.getId()+"_output.txt", true));
            playerlog.write("Deck"+drawDeckId+" contents: "+deck.getDeck().get(0).getValue()+" "+deck.getDeck().get(1).getValue()+" "+deck.getDeck().get(2).getValue()+" "+deck.getDeck().get(3).getValue());
            playerlog.newLine();
            playerlog.close();

        } catch (Exception e) {
            System.out.println("Error: Deck "+deck.getId()+" has not outputted to deck file successfully");;
        }
    }

    //Check to discard card that player do not prefer
    public synchronized List<Integer> preferredCardList(int id, ArrayList<Card> hand) {
        int card1 = hand.get(0).getValue();
        int card2 = hand.get(1).getValue();
        int card3 = hand.get(2).getValue();
        int card4 = hand.get(3).getValue();
        List<Integer> discardList = new ArrayList<>();
        if (card1 != id) {
            discardList.add(card1);
        }
        if (card2 != id) {
            discardList.add(card2);
        }
        if (card3 != id) {
            discardList.add(card3);
        }
        if (card4 != id) {
            discardList.add(card4);
        }
        return discardList;
    }

    //Randomize non-preferred card to discard
    public int preferredCard(List<Integer> cardValues) {
        try {
            Random rand = new Random();
           
            int preferredCardValue = cardValues.get(rand.nextInt(cardValues.size()));
            return preferredCardValue; 
        } catch (IllegalArgumentException e) {
            controlGame.setGameWon();
            
        }
        return 0;
       
    }

    //Discard and draw card for current player
    public synchronized void drawAndDiscard(ArrayList<Card> hand, Deck leftDeck, Deck rightDeck) {
        
        try {
            //Draw card from the deck left of current player
            
            if (!leftDeck.getDeck().isEmpty()) {
                drawDeckLock.lock();
                Card cardDrawn = leftDeck.getDeck().get(0);
                drawDeckLock.unlock();
                leftDeck.getDeck().remove(0);
                hand.add(cardDrawn);
                leftPlayer.setDiscardDeck(getDrawDeck());
                outputDrawOrDiscardToFile(getId(), getId(), cardDrawn, "draws");
            }
            
            
        
            //Discard card from current player deck to the right
            Card selectCard = new Card(preferredCard(preferredCardList(getId(), hand)));
            if (selectCard.getValue() != 0) {
                discardDeckLock.lock();
                rightDeck.getDeck().add(selectCard);
                discardDeckLock.unlock();
                rightPlayer.setDrawDeck(getDiscardDeck());
                Iterator<Card> iterateRemove = hand.iterator();
                while (iterateRemove.hasNext()) {
                    Card card = iterateRemove.next();
                    if (card.getValue() == selectCard.getValue()) {
                        iterateRemove.remove();
                        break;
                    }
                }
    
                setHand(hand);
                outputDrawOrDiscardToFile(getId(), getDiscardDeckId(), selectCard, "discards");
               
            } else {
                outputPlayerWinToTerminal(id);
                setWinnerId(id);
                for (int i=0; i < players.size(); i++) {
                    players.get(i).setWinnerId(id);
                }
                
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}