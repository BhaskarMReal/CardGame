import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
 
    public static void main(String[] args) {
        
       try {
            // get player input
            Scanner scan = new Scanner(System.in);


            int playerNum = -1;
            while (playerNum <= 0) {
                System.out.println("Please enter the number of players: ");
                if (scan.hasNextInt()) {
                    playerNum = scan.nextInt();

                    if (playerNum <= 0) {
                        System.out.println("Please enter a number greater than 0");
                    }
                } else {
                    System.out.println("Input is not a number");
                    scan.next();
                }
            }

            scan.nextLine();
            String testPackFile;
            Pack testValidPack = new Pack(null, null);
            ArrayList<Card> cards;
            boolean isValid = false;
            String packFile = null;

            while (!isValid) {
                System.out.println("Please enter location of pack to load (.txt):");
                testPackFile = scan.nextLine();

                File file = new File("logs/"+testPackFile);
                if (!file.exists()) {
                    System.out.println("Pack file does not exist");
                    continue;
                }

                testValidPack.setName(testPackFile);
                try {
                    cards = testValidPack.packToList(testValidPack.getName());
                } catch (Exception e) {
                    System.out.println("Error loading pack");
                    continue;
                }

                testValidPack.setPackList(cards);

                if (testValidPack.getPackList().isEmpty()) {
                    System.out.println("Pack file is empty");
                    continue;
                }

                if (testValidPack.getPackList().size() < (8 * playerNum)) {
                    System.out.println("Pack file contains less than required number of cards");
                    continue;
                }

                if (testValidPack.getPackList().size() > (8 * playerNum)) {
                    System.out.println("Pack file contains more than required number of cards");
                    continue;
                }




                if (testValidPack.getPackList().size() == (playerNum * 8)) {
                    ArrayList<Integer> validCards = new ArrayList<>();
                    for (Card card : testValidPack.getPackList()) {
                        int cardValue = card.getValue();
                        if (cardValue >= 1) {
                            validCards.add(cardValue);
                        }
                    }
                    if (validCards.size() == (8 * playerNum)) {
                        packFile = testPackFile;
                        isValid = true;
                    } else {
                        System.out.println("Error: Invalid cards in pack file");
                    }

                }

            }
            // get contents of pack file into a list
            Pack gamePack = new Pack(packFile, null);
            // sets the game's card pack
            gamePack.setPackList(gamePack.packToList(packFile));
           // initialises all overarching arraylists
            ArrayList<Player> playerList = new ArrayList<>();
            ArrayList<Deck> decks = new ArrayList<>();
            ArrayList<ArrayList<Card>> tempDeck = new ArrayList<>();
            ArrayList<ArrayList<Card>> tempHand = new ArrayList<>();
            ControlGame control = new ControlGame();
            // creates new players, decks, ids and finally creates hand and deck lists (stores the 4 card hand and deck) 
            for (int i=0; i < playerNum; i++) {
                playerList.add(new Player(i+1, null, 0, 0, null, null, null, null, control, 0, null));
                decks.add(new Deck(i+1, null));
                tempDeck.add(new ArrayList<>());
                tempHand.add(new ArrayList<>());
            }
            // assign cards to deck, using round-robin, from above halfway point to end of pack
            for (int i = (gamePack.getPackList().size()/2); i < gamePack.getPackList().size(); i++) {
                int index = i % playerNum;
                int value = gamePack.getPackList().get(i).getValue();
                tempDeck.get(index).add(new Card(value));
            }
            // sets the deck to a unique instance of Deck class in order to set player's draw deck
            for (int i = 0; i < playerNum; i++) {
                decks.get(i).setDeck(tempDeck.get(i));
                playerList.get(i).setDrawDeck(decks.get(i));
            }
            // hands out cards, round-robin style. takes from first half of pack
            for (int i=0; i < gamePack.getPackList().size()/2; i++) {
                int index = i % playerNum;
                int value = gamePack.getPackList().get(i).getValue();
                tempHand.get(index).add(new Card(value));
            }
            // sets each players' hand using created hands
            for (int i=0; i < playerNum; i++) {
                playerList.get(i).setHand(tempHand.get(i));
            }
            // gets and sets the left and right players for the current player (i), used for getting correct decks from players
            for (int i=0; i < playerNum; i++) {
                Player currentPlayer = playerList.get(i);
                Player nextPlayer = playerList.get((i+1)%playerNum);
                Player previousPlayer = playerList.get((i-1+playerNum)%playerNum);
                currentPlayer.setLeftPlayer(previousPlayer);
                currentPlayer.setRightPlayer(nextPlayer);
            }
            // gets deck id for draw and discard decks, deck 1 to n
            for (int i=1; i <= playerNum; i++) {
                int currentPlayerId = i;
                int nextPlayerId;
                if ((i+1) > playerNum) {
                    nextPlayerId = 1;
                } else {
                    nextPlayerId = currentPlayerId + 1;
                }
                // sets deck ids for each player
                playerList.get(i-1).setDiscardDeckId(nextPlayerId);
                playerList.get(i-1).setDrawDeckId(currentPlayerId);

            }
            // sets discard deck using current and previous player. takes current player draw and assigns it to previous player discard
            for (int i=0; i < playerNum; i++) {
                Player currentPlayer = playerList.get(i);
                Player previousPlayer = playerList.get((i-1+playerNum)%playerNum);
                previousPlayer.setDiscardDeck(currentPlayer.getDrawDeck());
            }

            // sets players in ArrayList for each player, excluding their own player instance
            ArrayList<ArrayList<Player>> playerListForGameWin = new ArrayList<>();
            for (int i=0; i < playerNum; i++) {
                playerListForGameWin.add(new ArrayList<>());
                for (int j=0; j < playerNum; j++) {
                    if (i != j) {
                        playerListForGameWin.get(i).add(playerList.get(j));
                    }
                }
                playerList.get(i).setPlayers(playerListForGameWin.get(i));

            }

            // loop through player to create threads. runs card game
            for (Player player : playerList) {
                Thread thread = new Thread(player);
                player.outputPlayerInitial(player.getId(), player.getHand());
                thread.start();
            }

            



            

            
            

            
              
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            



    
}
}
