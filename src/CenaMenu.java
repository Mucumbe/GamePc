
import JGames2D.JGLevel;
import JGames2D.JGSprite;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Spring_Boot
 */
public class CenaMenu extends JGLevel {

    JGSprite titulo = null;
    JGSprite teste = null;
    JGSprite ponteiro = null;
    JGSprite[] botoes = new JGSprite[4];
    String[] nomes = {"btn_play", "btn_controls", "btn_credits", "btn_exit"};

    private URL criarURL(String caminhoDeImagem) {

        return getClass().getResource(caminhoDeImagem);
    }

    @Override
    public void execute() {

        ponteiro.position.setX(gameManager.inputManager.getMousePosition().getX());
        ponteiro.position.setY(gameManager.inputManager.getMousePosition().getY());

        //Verifica se o mouse esta sobre um botao
        for (int indece = 0; indece < botoes.length; indece++) {
            if (ponteiro.collide(botoes[indece])) {
                botoes[indece].setCurrentAnimation(1);
                if (gameManager.inputManager.mouseClicked()) {
                    switch (indece) {
                        case 0:
                            gameManager.setCurrentLevel(Principal.GAME);
                            return;
                        case 1:
                            gameManager.setCurrentLevel(Principal.CONTROLES);
                            return;
                        case 2:
                            gameManager.setCurrentLevel(Principal.CREDITOS);
                            return;
                        case 3:
                            gameManager.finish();
                            return;
                    }
                }

            } else {
                botoes[indece].setCurrentAnimation(0);
            }
        }
        /*
        if (gameManager.inputManager.keyTyped(KeyEvent.VK_SPACE)) {
            if (teste.getCurrentAnimationIndex() == 0) {
                teste.setCurrentAnimation(1);
            } else {
                teste.setCurrentAnimation(0);
            }
        }*/

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_ESCAPE)) {
            gameManager.finish();
        }

    }

    @Override
    public void init() {

        for (int indice = 0; indice < botoes.length; indice++) {
            botoes[indice] = createSprite(criarURL("Images/" + nomes[indice] + ".png"), 2, 1);
            botoes[indice].addAnimation(1, false, 0);
            botoes[indice].addAnimation(1, false, 1);
            botoes[indice].zoom.setXY(0.6, 0.6);
            botoes[indice].position.setX(gameManager.windowManager.width / 2);
            botoes[indice].position.setY(180 + indice * 120);

        }

        titulo = createSprite(criarURL("Images/gametitle.png"), 1, 1);
        titulo.position.setX(gameManager.windowManager.width / 2);
        titulo.position.setY(titulo.frameHeight / 2);

        //Carega sprinte de mouse
        ponteiro = createSprite(criarURL("Images/mira.png"), 1, 1);
        /*
        teste = createSprite(criarURL("Images/spr_hidro.png"), 4, 8);
        teste.position.setX(gameManager.windowManager.width / 2);
        teste.position.setY(gameManager.windowManager.height / 2);
        teste.addAnimation(15, true, 0, 18);
        teste.addAnimation(15, true, 19, 29);*/

        gameManager.windowManager.setBackgroundColor(Color.GREEN);

    }

}
