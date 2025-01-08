
public class Action {

    private final ActionType actionType;
    private final ActionType deviation;
    private final int trueCount;
    private final boolean sur;
    private final boolean above;
    public Action(String actionType){
        this(actionType, "DEFAULT", 0, "");
    }
    public Action(String actionType, String deviation, int trueCount, String sign){ //sign + or - (above or bellow that count)

        this(actionType, deviation, trueCount, sign, false);
    }
    public Action(String actionType, String deviation, int trueCount, String sign, boolean sur){
        if(sign.equals("+")){
            above = true;
        }
        else{
            above = false;
        }
        this.actionType = ActionType.valueOf(actionType);
        this.deviation = ActionType.valueOf(deviation);
        this.trueCount = trueCount;
        this.sur = sur;
    }

    public ActionType getActionType(){
        return actionType;
    }
    public ActionType tryGetDeviation(Deck deck){
        return actionType;
//        if(deviation.equals(ActionType.DEFAULT)){
//            return actionType;
//        }
//        if(trueCount == 0){
//            if(above && deck.getRunningCount() > 0){
//                return deviation;
//            }
//            if(!above && deck.getRunningCount() < 0){
//                return deviation;
//            }
//            return actionType;
//        }
//        if((above && trueCount >= getTrueCount()) || (!above && trueCount <= getTrueCount())){
//            return deviation;
//        }
//        return actionType;
    }
    public int getTrueCount(){
        return trueCount;
    }
    public boolean isSur(){
        return sur;
    }

    public String toString(){
        return "Action: " + actionType.toString() + " Deviation: " + deviation.toString() + " True Count: " + trueCount;
    }
}

