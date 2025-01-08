import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GameHandler {

    private final Deck deck;
    private final Queue<Hand> playerQueue;
    private final ArrayList<Hand> finishedHands;
    public static double money;
    public static double progressiveMultiplier;
    public GameHandler(){
        money = 0;
        progressiveMultiplier = 1;
        deck = new Deck();
        playerQueue = new LinkedList<>();
        finishedHands = new ArrayList<>();
    }
    public void playRounds(int rounds){
        for(int i = 0; i < rounds; i++){
            playRound();
            deck.isTimeToShuffle();
        }
    }
    public void TestAllHands(){
        for(int i = 1; i <= 10; i++){
            for(int j = i; j <= 10; j++){
                Hand player = new Hand(i,j);
                for(int k = 1; k <= 10; k++){
                    Hand dealer = new Hand(k, 1);
                    ActionType action = HandleBasicStrategy.getAction(player, dealer.getUpCard(),deck);
                    System.out.println("Player: " + player + " Dealer: " + dealer.getUpCard() + " Action: " + action);

                }
            }
        }
    }

    public void playRound(){
        playerQueue.clear();
        finishedHands.clear();

        Hand initialPlayer = new Hand(deck.dealCard(), deck.dealCard());
        Hand dealer = new Hand(deck.dealCard(), deck.dealCard());
        playRound(initialPlayer, dealer);
    }
    public void playRound(Hand initialPlayer, Hand dealer){
        playerQueue.clear();
        finishedHands.clear();
        output("Player Start: "+ initialPlayer.toString());
        output("Dealer Start: " + dealer.toString());
        if(handleInsurance(dealer)){
            return;
        }
        if(checkForBlackJack(initialPlayer, dealer)){
            return;
        }
        output("Dealer UpCard: " + dealer.getUpCard());



        playerQueue.add(initialPlayer);

        while (!playerQueue.isEmpty()){
            Hand player = playerQueue.poll();

            int handsInPlay = 1 + playerQueue.size() + finishedHands.size();

            boolean split = false;
            while(!player.isHandDone()){
                output(player.toString());

                ActionType action = HandleBasicStrategy.getAction(player, dealer.getUpCard(),deck);
                action = getFilteredAction(action, player, dealer, handsInPlay);

                output("Unfiltered: " + action);
                output("" + action);
                playHand(action, player, dealer, handsInPlay);
                if(action.equals(ActionType.Y)){
                    split = true;
                    break;
                }
            }
            output("Final Hand: " + player);
            if(!split){
                finishedHands.add(player);
            }
        }
        removeBustedHands();
        playDealerHand(dealer);
        calculateResults(dealer);
    }

    private boolean handleInsurance(Hand dealer) {
        if(!Settings.IS_COUNTING_CARDS || !dealer.hasAce()) return false;
        if(deck.getTrueCount() >= 3){
            if( dealer.isBlackJack()){
                output("Push");
                output("Money: " + money);
                return true;
            }
            else{
                deposit(-Settings.STARTING_BET*progressiveMultiplier/2.0);
                output("No Blackjack");
                output("Money: " + money);
                return false;
            }
        }
        return false;
    }
    public void deposit(double money){
        if(deck.getTrueCount() > 0){
            GameHandler.money += money;
        }

    }
    public boolean checkForBlackJack(Hand player, Hand dealer){
        if(player.isBlackJack() && dealer.isBlackJack()){
            output("Push");
            output("Money: " + money);
            return true;
        }
        if(player.isBlackJack()){
            deposit(Settings.STARTING_BET * Settings.BLACKJACK_PAYOUT * progressiveMultiplier);
            output("Player Blackjack");
            output("Money: " + money);
            return true;
        }
        if(dealer.isBlackJack()){
            deposit(-Settings.STARTING_BET * progressiveMultiplier);
            output("Dealer Blackjack");
            output("Money: " + money);
            return true;
        }
        return false;
    }
    private void playDealerHand(Hand dealer) {
        output("DEALER STARTS");

        while(!dealer.isHandDone()){
            int handValue = dealer.getHandValue();
            if((!dealer.hasAce() && handValue >= 17) || (dealer.hasAce() && handValue >= 18)){
                dealer.lockHand();
                break;
            }
            dealer.addCard(deck.dealCard());
            output("" + dealer);
        }
        output("Final Hand: " + dealer);
    }

    public void removeBustedHands(){
        Iterator<Hand> iterator = finishedHands.iterator();
        while (iterator.hasNext()) {
            Hand player = iterator.next();
            if (player.getHandValue() > 21) {
                deposit(-Settings.STARTING_BET*progressiveMultiplier);
                iterator.remove();
            }
        }
    }
    private void calculateResults(Hand dealer) {
        int dealerValue = dealer.getHandValue();
        for(Hand player : finishedHands){
            int playerValue = player.getHandValue();
            if(dealerValue > 21){
                deposit(Settings.STARTING_BET*player.getHandMultiplier()*progressiveMultiplier);
                output("Dealer Bust");
            }
            else if(playerValue > dealerValue){
                deposit(Settings.STARTING_BET*player.getHandMultiplier()*progressiveMultiplier);
                output("Player Wins");
            }
            else if(playerValue < dealerValue){
                deposit(-Settings.STARTING_BET*player.getHandMultiplier()*progressiveMultiplier);
                output("Dealer Wins");
            }
            else{
                output("Push");
            }
        }
        output("Money: " + money);
    }

    public void playHand(ActionType a, Hand player, Hand dealer, int handsInPlay){
        switch(a){
            case Y:
                split(player);
                break;
            case D:
                player.doubleHand(deck.dealCard());
                break;
            case H:
                player.addCard(deck.dealCard());
                break;
            case S:
            case BJ:
                player.lockHand();
                break;
        }
    }

    private void split(Hand player) {
        int value = player.getUpCard();
        Hand player1 = new Hand(value, deck.dealCard());
        Hand player2 = new Hand(value, deck.dealCard());
        if(value == 1){
            player1.lockHand();
            player2.lockHand();
        }
        playerQueue.add(player1);
        playerQueue.add(player2);
    }

    private ActionType getFilteredAction(ActionType a, Hand player, Hand dealer, int handsInPlay){
        ActionType action = a;

        switch(a){
            case Ds:
                if(player.getHandCount() > 2){
                    action = ActionType.S;
                }
                else{
                    action = ActionType.D;
                }
                break;
            case Yn:
                if(Settings.DOUBLE_AFTER_SPLIT){
                    action = ActionType.Y;
                }
                else{
                    player.lockSplit();
                    action = HandleBasicStrategy.getAction(player, dealer.getUpCard(), deck);
                }
                break;
        }
        if (action.equals(ActionType.D) && player.getHandCount() > 2){
            action = ActionType.H;
        }
        if(action.equals(ActionType.Y)){

            if(handsInPlay >= Settings.MAX_SPLIT_HANDS){
                player.lockSplit();
                action = HandleBasicStrategy.getAction(player, dealer.getUpCard(), deck);
            }
        }
        return action;
    }
    private void output(String s){
        //System.out.println(s);
    }
}
