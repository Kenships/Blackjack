import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Deck {
    Queue<Integer> deck;
    ArrayList<Integer> unShuffledDeck;

    private int runningCount;
    public Deck(){
        unShuffledDeck = new ArrayList<>();
        deck = new LinkedList<>();
        for(int d = 0; d < Settings.DECKS_IN_PLAY; d++){
            for(int s = 0; s < 4; s++){
                for(int v = 1; v <= 13; v++){
                    unShuffledDeck.add(clamp(v, 1, 10));
                }
            }
        }

        shuffleDeck();
    }

    private void shuffleDeck() {
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < unShuffledDeck.size(); j++){
                 int random = (int) (Math.random()*unShuffledDeck.size());
                 int temp = unShuffledDeck.get(0);
                 unShuffledDeck.set(0, unShuffledDeck.get(random));
                 unShuffledDeck.set(random, temp);
            }
        }
        runningCount = 0;
        deck.clear();
        deck.addAll(unShuffledDeck);
    }

    private int clamp(int value, int min, int max){
        if(value < min){
            value = min;
        }
        if(value > max){
            value = max;
        }
        return value;
    }

    public void isTimeToShuffle(){
        if(Settings.DECKS_IN_PLAY * 52 *  Settings.CUT_CARD_LOCATION > deck.size()){
            shuffleDeck();
        }
    }

    public int getRunningCount(){
        return runningCount;
    }
    public int getTrueCount(){
        //can modify calculation to account for .5 true count
        return (int)(1.0 * getRunningCount()/(getNumberOfCardsLeft()/52.0));
    }
    public int getNumberOfCardsLeft(){
        return deck.size();
    }

    public int dealCard(){
        int value = deck.poll();
        if(value != 1 && value <= 6){
            runningCount++;
        }
        if(value == 1 || value == 10){
            runningCount--;
        }

        if(Settings.IS_COUNTING_CARDS){
            //GameHandler.progressiveMultiplier = clamp(1 + getTrueCount() * Settings.STARTING_BET, Settings.MINIMUM_BET, Settings.MAXIMUM_BET);
        }
//        System.out.println(getNumberOfCardsLeft()+ " : "+ runningCount +" : " + GameHandler.progressiveMultiplier +" : " + getTrueCount());
        return value;
    }
}
