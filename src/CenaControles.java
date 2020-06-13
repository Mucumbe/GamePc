
import JGames2D.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;

public class CenaControles extends JGLevel {

    JGSprite controles = null;

    private URL criarURL(String caminhoDeImagem) {

        return getClass().getResource(caminhoDeImagem);
    }

    @Override
    public void execute() {
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {
            gameManager.setCurrentLevel(Principal.MENU);
            return;
        }
    }

    @Override
    public void init() {

        gameManager.windowManager.setBackgroundColor(Color.blue);

        controles = createSprite(criarURL("Images/controls.png"), 1, 1);
        controles.position.setX(gameManager.windowManager.width / 2);
        controles.position.setY(gameManager.windowManager.height / 2);
        controles.zoom.setXY(0.5, 0.5);

    }

}
