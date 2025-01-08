public class Settings {
    /**
     * Player Settings
     */
    public static final int STARTING_BANKROLL = 0;
    public static final int STARTING_BET = 1;
    public static final int MINIMUM_BET = 1;
    public static final int MAXIMUM_BET = 10;

    /**
     * Card Counting settings
     */
    public static final boolean IS_COUNTING_CARDS = false;
    /**
     * General Play
     */
    public static final int DECKS_IN_PLAY = 4;
    public static final float CUT_CARD_LOCATION = 0.25f;
    public static final float BLACKJACK_PAYOUT = 1.5f; //1.5 = 2:1 or 1.2 = 6:5
    public static final boolean HIT_ON_SEVENTEEN = true;
    public static final boolean SURRENDER = false;
    /**
     * SPLIT RULES
     */
    public static final boolean SPLIT = true;
    public static final int MAX_SPLIT_HANDS = 4;

    public static final boolean SPLIT_ACES = true;
    public static final boolean HIT_AFTER_SPLIT_ACES = false;
    public static final int MAX_SPLIT_ACES = 4;
    /**
     * DOUBLE RULES
     */
    public static final boolean DOUBLE_ALL_VALUES = true;
    public static final boolean DOUBLE_AFTER_SPLIT = true;
    public static final int[] VALID_DOUBLE_VALUES = {9, 10, 11};



}
