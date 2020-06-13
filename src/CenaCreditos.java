
import JGames2D.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;

public class CenaCreditos extends JGLevel {

    JGSprite creditos = null;

    private URL criarURL(String caminhoDeImagem) {

        return getClass().getResource(caminhoDeImagem);
    }

    @Override
    public void execute() {

        if (creditos.isMoveEnded()) {
            gameManager.setCurrentLevel(Principal.MENU);
            return;
        }

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {

            gameManager.setCurrentLevel(Principal.MENU);
            return;
        }

    }

    @Override
    public void init() {

        gameManager.windowManager.setBackgroundColor(Color.blue);

        creditos = createSprite(criarURL("Images/spr_credits.png"), 1, 1);
        creditos.position.setX(gameManager.windowManager.width / 2);
        creditos.position.setY(gameManager.windowManager.height + creditos.frameHeight / 2);

        //movimenta para nova posicao para 8 segundos
        creditos.moveTo(8000, new JGVector2D(creditos.position.getX(),
                -creditos.frameHeight / 2));

    }

}
