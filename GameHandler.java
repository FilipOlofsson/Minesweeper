public class GameHandler {

    static GUI gui;

    public static void main(String[] args) {
        initGame();
    }

    public static void initGame() {
        gui = new GUI("Minesweeper");
    }

}