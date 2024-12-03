import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pack {
    String name;
    ArrayList<Card> packList;
    
    public Pack(String fileName, ArrayList<Card> packCards) {
        this.name = fileName;
        this.packList = packCards;
    }

    //Get file name from input
   public String getName() {
       return name;
   }

    //Set file name into variable
   public void setName(String name) {
       this.name = name;
   }

    //Get list of cards from pack file
   public ArrayList<Card> getPackList() {
       return packList;
   }

    //Put cards array into pack
   public void setPackList(ArrayList<Card> packList) {
       this.packList = packList;
   }

    //Add cards from pack
    public ArrayList<Card> packToList(String packFile) {
        ArrayList<Card> packCardList = new ArrayList<>();
        try {
            File file_ = new File("logs/"+packFile);
            Scanner scan = new Scanner(file_);

            //Loop for scanning integers in the files until there are no integers left
            while (scan.hasNextInt()) {
                int data = scan.nextInt();
                Card cardIter = new Card(data);
                packCardList.add(cardIter);
            }
            scan.close();
            return packCardList;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File: "+packFile+" not found");
        }
        return packCardList;
    
    }
}

