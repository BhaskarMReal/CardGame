import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {
 
    public static void main(String[] args) {
        
       try {
            // get player input
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter the number of players:");
            
            try {
                // input validation, checks for an integer
                while (!scan.hasNextInt()) {
                    System.out.println("Input is not a number");
                    System.out.println("Please enter the number of players:");
                    scan.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            int playerNum = scan.nextInt();
            scan.nextLine();
            System.out.println("Please enter location of pack to load (.txt):");
            try {
                // checks the format of input
                while (!scan.hasNext()) {
                    System.out.println("Input is not in format {playerNumber}.txt");
                    System.out.println("Please enter the location of pack to load (.txt):");
                    scan.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String packFile = scan.nextLine();
            try {
                // checks if file exists
                File file = new File("logs/"+packFile);
                // if not..
                while (!file.exists()) {
                    System.out.println("Input is not in format {playerNumber in words}.txt");
                    System.out.println("Please enter the location of pack to load (.txt):");
                  packFile = scan.nextLine();
                  file = new File("logs/"+packFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // closes the scanner to stop leaking memory
            scan.close();
            
            // get contents of pack file into a list
            Pack gamePack = new Pack(packFile, null);
            // sets the game's card pack
            gamePack.setPackList(gamePack.packToList(packFile));
           // initialises all overarching arraylists
            ArrayList<Player> playerList = new ArrayList<>();
            ArrayList<Deck> decks = new ArrayList<>();
            ArrayList<Integer> playerIds = new ArrayList<>();
            ArrayList<ArrayList<Card>> tempDeck = new ArrayList<>();
            ArrayList<ArrayList<Card>> tempHand = new ArrayList<>();
            ControlGame control = new ControlGame();
            // creates new players, decks, ids and finally creates hand and deck lists (stores the 4 card hand and deck) 
            for (int i=0; i < playerNum; i++) {
                playerList.add(new Player(i+1, null, 0, 0, null, null, null, null, control, 0, null));
                playerIds.add(i+1);
                decks.add(new Deck(i+1, null));
                tempDeck.add(new ArrayList<>());
                tempHand.add(new ArrayList<>());
            }
            // assign cards to deck, using round-robin, from above half way point to end of pack
            for (int i = (gamePack.getPackList().size()/2); i < gamePack.getPackList().size(); i++) {
                int index = i % playerNum;
                int value = gamePack.getPackList().get(i).getValue();
                tempDeck.get(index).add(new Card(value));
            }
            // sets the deck to a unique instance of Deck class in orde to set player's draw deck
            for (int i = 0; i < playerNum; i++) {
                decks.get(i).setDeck(tempDeck.get(i));
                playerList.get(i).setDrawDeck(decks.get(i));
            }
            // hands out cards, round robin style. takes from first half of pack
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

      
            

            ArrayList<Thread> threadList = new ArrayList<>();
            // testing for outputs
            for (Player player : playerList) {
                Thread thread = new Thread(player);
                player.outputPlayerInitial(player.getId(), player.getHand());
                threadList.add(thread);
                thread.start();
            }

            



            

            
            

            
              
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            



    
}
}
