
import JGames2D.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;

public class CenaAbertura extends JGLevel {

    JGTimer tempo = null;
    JGSprite empresa = null;
    int estado = 0;

    private URL criarURL(String caminhoDeImagem) {

        return getClass().getResource(caminhoDeImagem);
    }

    @Override
    public void execute() {
        //onde sera implementado a logica da sena
        //chamado n vezes por segundo
        //fps=frames per second
        if (estado == 0) {
            empresa.zoom.setX(empresa.zoom.getX() - 0.05);
            empresa.zoom.setY(empresa.zoom.getX() - 0.05);

            if (empresa.zoom.getX() <= 0.5) {
                estado = 1;
            }
        } else {

            tempo.update();

            if (tempo.isTimeEnded()) {
                gameManager.setCurrentLevel(Principal.MENU);
                return;
            }
        }

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {
            gameManager.finish();
        }

        if (gameManager.inputManager.mouseClicked()) {
            gameManager.setCurrentLevel(1);
            return;
        }
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_SPACE)) {
            gameManager.setCurrentLevel(1);
            return;
        }

    }

    @Override
    public void init() {

        empresa = this.createSprite(criarURL("Images/spr_produtors.png"), 1, 1);
        empresa.position.setX(gameManager.windowManager.width / 2);
        empresa.position.setY(gameManager.windowManager.height / 2);
        empresa.zoom.setXY(3, 3);

        //configura a cor do fundo da Cena
        gameManager.windowManager.setBackgroundColor(Color.yellow);

        tempo = new JGTimer(2000);
        //chamado quendo uma sena é escolhido 
        //para ser mostrado na janela
        //mostrado uma unica vez quando a sena inicia
        //serve para carreagamento dos recursos da sena
    }

}
