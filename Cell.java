import java.util.Random;

public class Cell {

    boolean bomb;
    boolean revealed = false;

    int x, y;

    int surrounding;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        Random rnd = new Random();
        if(rnd.nextInt(10) == 1) {
            bomb = true;
        } else {
            bomb = false;
        }
    }

    public void reveal() {
        revealed = true;
        for(int i = this.x-1; i <= this.x + 1; i++) {
            for(int j = this.y - 1; j <= this.y + 1; j++) {
                try {
                    if(GameHandler.gui.Grid[i][j].bomb) {
                        surrounding++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
        findNearby();
        GameHandler.gui.repaint();
    }

    public void findNearby() {
        try {
            if(GameHandler.gui.amountRevealed < GameHandler.gui.maxAmountRevealed) {
                if(!GameHandler.gui.Grid[x + 1][y].bomb && !GameHandler.gui.Grid[x + 1][y].revealed) {
                    GameHandler.gui.Grid[x + 1][y].reveal();
                    GameHandler.gui.amountRevealed++;
                }
                if(!GameHandler.gui.Grid[x - 1][y].bomb && !GameHandler.gui.Grid[x - 1][y].revealed) {
                    GameHandler.gui.Grid[x - 1][y].reveal();
                    GameHandler.gui.amountRevealed++;
                }
                if(!GameHandler.gui.Grid[x][y + 1].bomb && !GameHandler.gui.Grid[x][y + 1].revealed) {
                    GameHandler.gui.Grid[x][y + 1].reveal();
                    GameHandler.gui.amountRevealed++;
                }
                if(!GameHandler.gui.Grid[x][y - 1].bomb && !GameHandler.gui.Grid[x][y - 1].revealed) {
                    GameHandler.gui.Grid[x][y - 1].reveal();
                    GameHandler.gui.amountRevealed++;
                }
                GameHandler.gui.repaint();
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }

    }

}
