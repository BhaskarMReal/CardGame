public class Card {

    private int value;

    //Constructor containing card information
    public Card(int faceValue) {
        this.value = faceValue;
    }

    //Output card value
    public synchronized int getValue() {
        return value;
    }

    //Set the value for each card, checks for negatives
    public synchronized void setValue(int value) {
        if (value > 0) {
            this.value = value;
        }
    }

}
