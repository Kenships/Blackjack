
public class HandleBasicStrategy {

    private static Action[][] hardValues = {
            /*08*/{null,new Action("H"),new Action("H"),new Action("H"),new Action("H"),new Action("H"),new Action("H","D",2,"+"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*09*/{null,new Action("H"),new Action("H","D",1,"+"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("H","D",3,"+"),new Action("H"),new Action("H"),new Action("H")},
            /*10*/{null,new Action("H","D",3,"+"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("H","D",4,"+")},
            /*11*/{null,new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("D"), Settings.HIT_ON_SEVENTEEN ? new Action("D"): new Action("H")},
            /*12*/{null,new Action("H"),new Action("H","S",3,"+"),new Action("H","S",2,"+"),new Action("S","H",0,"-"),new Action("S"),new Action("S"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*13*/{null,new Action("H"),new Action("S","H",-1,"-"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*14*/{null,new Action("H"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*15*/{null,new Action("H","S",5,"+"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("H"),new Action("H"),new Action("H"),new Action("H","S",4,"+")},
            /*16*/{null,new Action("H","S",3,"+"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("H"),new Action("H"),new Action("H","S",3,"+"),new Action("H","S",0,"+")},
            /*17*/{null,new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S")}};
    private static Action[][] softValues = {
            /*0,0*/{null, null, null, null, null, null, null, null, null, null},
            /*A,A*/{null,new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y")},
            /*A,2*/{null,new Action("H"),new Action("H"),new Action("H"),new Action("H"),new Action("D"),new Action("D"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*A,3*/{null,new Action("H"),new Action("H"),new Action("H"),new Action("H"),new Action("D"),new Action("D"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*A,4*/{null,new Action("H"),new Action("H"),new Action("H"),new Action("D"),new Action("D"),new Action("D"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*A,5*/{null,new Action("H"),new Action("H"),new Action("H"),new Action("D"),new Action("D"),new Action("D"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*A,6*/{null,new Action("H"),new Action("H"),new Action("D"),new Action("D"),new Action("D"),new Action("D"),new Action("H"),new Action("H"),new Action("H"),new Action("H")},
            /*A,7*/{null,new Action("H"),new Action("Ds"),new Action("Ds"),new Action("Ds"),new Action("Ds"),new Action("Ds"),new Action("S"),new Action("S"),new Action("H"),new Action("H")},
            /*A,8*/{null,new Action("S"),new Action("S"),new Action("S"),new Action("S","Ds",3,"+"),new Action("S","Ds",1,"+"),new Action("Ds","S",0,"-"),new Action("S"),new Action("S"),new Action("S"),new Action("S")},
            /*A,9*/{null,new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S")},
            /*A,10*/{null,new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S"),new Action("S")}};
    private static Action[][] split = {
            /*0,0*/{null, null, null, null, null, null, null, null, null, null},
            /*A,A*/{null,new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y")},
            /*2,2*/{null,new Action("N"),new Action("Yn"),new Action("Yn"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("N"),new Action("N"),new Action("N")},
            /*3,3*/{null,new Action("N"),new Action("Yn"),new Action("Yn"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("N"),new Action("N"),new Action("N")},
            /*4,4*/{null,new Action("N"),new Action("N"),new Action("N"),new Action("N"),new Action("Yn"),new Action("Yn"),new Action("N"),new Action("N"),new Action("N"),new Action("N")},
            /*5,5*/{null,new Action("N"),new Action("N"),new Action("N"),new Action("N"),new Action("N"),new Action("N"),new Action("N"),new Action("N"),new Action("N"),new Action("N")},
            /*6,6*/{null,new Action("N"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("N"),new Action("N"),new Action("N"),new Action("N")},
            /*7,7*/{null,new Action("N"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("N"),new Action("N"),new Action("N")},
            /*8,8*/{null,new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y")},
            /*9,9*/{null,new Action("N"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("Y"),new Action("N"),new Action("Y"),new Action("Y"),new Action("N")},
            /*T,T*/{null,new Action("N"),new Action("N"),new Action("N"),new Action("N","Y",6,"+"),new Action("N","Y",5,"+"),new Action("N","Y",4,"+"),new Action("N"),new Action("N"),new Action("N"),new Action("N")}};
    public static ActionType getAction (Hand playerHand, int dealerUpCard, Deck deck){
        if(playerHand.isBlackJack()){
            return ActionType.BJ;
        }
        if(playerHand.hasAce()){
            return Settings.IS_COUNTING_CARDS ?
                    softValues[playerHand.getNonAceValue()][dealerUpCard].tryGetDeviation(deck):
                    softValues[playerHand.getNonAceValue()][dealerUpCard].getActionType();
        }
        if(playerHand.isPair()){
            ActionType action = Settings.IS_COUNTING_CARDS ?
                    split[playerHand.getHandValue()/2][dealerUpCard].tryGetDeviation(deck) :
                    split[playerHand.getHandValue()/2][dealerUpCard].getActionType();
            if(!action.equals(ActionType.N)){
                return action;
            }
        }
        int handIndex = clampIndex(playerHand.getHandValue(), 8, 17);
        return Settings.IS_COUNTING_CARDS?
                hardValues[handIndex][dealerUpCard].tryGetDeviation(deck):
                hardValues[handIndex][dealerUpCard].getActionType();
    }

    private static int clampIndex(int value, int min, int max){
        if(value < min){
            value = min;
        }
        if (value > max){
            value = max;
        }
        return value - 8;
    }
}
