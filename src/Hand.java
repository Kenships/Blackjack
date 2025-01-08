import java.util.ArrayList;

public class Hand {
    private int value;
    private ArrayList<Integer> cards;
    private boolean ace;
    private boolean canSplit;
    private boolean canHit;
    private int upCard;
    private int handMultiplier;
    public Hand (){
        cards = new ArrayList<>();
        canSplit = true;
        canHit = true;
        handMultiplier = 1;
    }
    public Hand (int card1, int card2){
        this();
        addCard(card1);
        addCard(card2);
    }
    public void addCard(int cardValue){
        if(cards.isEmpty()){
            upCard = cardValue;
        }
        if(!ace && cardValue == 1){
            if(getHandValue() <= 10){
                ace = true;
            }
            else{
                cards.add(cardValue);
            }
        }
        else{
            cards.add(cardValue);
            if(ace && getHandValue() > 21){
                ace = false;
                cards.add(1);
            }
            if(getHandValue() >= 21){
                lockHand();
            }
        }
    }
    public int getNonAceValue(){
        int value = 0;
        for(Integer i : cards){
            value += i;
        }
        return value;
    }
    public int getHandValue(){
        int value = 0;
        if(ace){
            value += 11;
        }
        value += getNonAceValue();
        return value;
    }
    public int getHandCount(){
        if(ace) return cards.size() + 1;
        return cards.size();

    }
    public boolean hasAce(){
        return ace;
    }
    public boolean isPair(){
        return canSplit && getHandCount() == 2 && cards.get(0).equals(cards.get(1));
    }
    public void lockSplit(){
        canSplit = false;
    }
    public int getHandMultiplier(){
        return handMultiplier;
    }
    public void doubleHand(int card){
        addCard(card);
        handMultiplier = 2;
        lockSplit();
        canHit = false;
    }
    public void lockHand(){
        lockSplit();
        canHit = false;
    }
    public boolean isBlackJack(){
        return getHandCount() == 2 && getHandValue() == 21;
    }
    public boolean isHandDone(){
        return !canHit;
    }
    public int getUpCard(){
        return upCard;
    }

    public String toString(){
        if(ace){
            return cards.toString() + ", Ace Value:" + getHandValue() + " or " + getNonAceValue();
        }
        return cards.toString() + " Value: " + getHandValue();
    }
}
