

public class Main {

    public static void main(String[] args) {
        runSimulation(1000000);
    }

    private static void runSimulation(int iterations) {
        GameHandler gameHandler = new GameHandler();
        gameHandler.playRounds(iterations);
        System.out.println("Money won per game: " + GameHandler.money/iterations);
    }
}
