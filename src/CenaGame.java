
import JGames2D.JGLayer;
import JGames2D.JGLevel;
import JGames2D.JGMusic;
import JGames2D.JGSoundEffect;
import JGames2D.JGSoundManager;
import JGames2D.JGSprite;
import JGames2D.JGTimer;
import JGames2D.JGVector2D;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class CenaGame extends JGLevel {

    JGLayer camada = null;
    JGSprite aviao = null;

    ArrayList<JGSprite> vetTiros = null;
    ArrayList<JGSprite> vetInimigos = null;
    ArrayList<JGSprite> vetExplosoes = null;

    Random sorteio = new Random();
    JGTimer tempoInimigo = null;
    JGTimer tempoTiro = null;
    JGTimer tempoDeRecuperacao=null;
    
    JGSoundEffect tiro=null;
    JGSoundEffect explosao=null;
    JGMusic musica=null;
    
    int totalVidas = 2;
    
    boolean gameOver=false;
    
    public CenaGame(){
        musica=JGSoundManager.loadMusic(criarURL("Sounds/DangerZone.mp3"));
        tiro= JGSoundManager.loadSoundEffect(criarURL("Sounds/SHOT.wav"));
        explosao= JGSoundManager.loadSoundEffect(criarURL("Sounds/boom.wav"));
    }
    
    void colisaoJogadorInimigo(){
        
        tempoDeRecuperacao.update();
        
        if(!tempoDeRecuperacao.isTimeEnded()){
        
            aviao.visible=!aviao.visible;
            return;
        }else{
        
            aviao.visible=true;
        }
    
        for(JGSprite inimigo: vetInimigos){
        
            if(inimigo.visible&& inimigo.collide(aviao)){
                
                tempoDeRecuperacao.restart(3000);
                inimigo.visible = false;
                totalVidas--;
                if(totalVidas==0)
                    gameOver=true;
                break;
            }
        }
    }

    //metodo actualiza explosoes
    private void actualizaExplosoes() {

        for (JGSprite explosao : vetExplosoes) {

            if (explosao.getCurrentAnimation().isEnded()) {

                explosao.visible = false;
            }
        }
    }

    //metodo de explozao na tala
    private void criaExplosao(int posX, int posY) {
        

        JGSprite novaExplosao = null;
        explosao.play();
        for (JGSprite explosao : vetExplosoes) {

            if (explosao.visible == false) {
                novaExplosao = explosao;
                explosao.visible = true;
                explosao.restartAnimation();
                break;
            }
        }

        if (novaExplosao == null) {
            novaExplosao = createSprite(criarURL("Images/spr_bigexplosion.png"), 2, 4);
            novaExplosao.addAnimation(10, false, 0, 7);
            vetExplosoes.add(novaExplosao);
        }

        novaExplosao.position.setXY(posX, posY);
    }

    private void colisaoTitosInimigos() {

        for (JGSprite inimigo : vetInimigos) {
            for (JGSprite tiro : vetTiros) {
                if (tiro.visible && inimigo.visible && tiro.collide(inimigo)) {
                    tiro.visible = false;
                    inimigo.visible = false;
                    criaExplosao((int) inimigo.position.getX(), (int) inimigo.position.getY());
                    break;
                }
            }
        }
    }

    private void actualizaInimigos() {

        //lope que passa por todos inimigos
        for (JGSprite inimigo : vetInimigos) {

            inimigo.position.setY(inimigo.position.getY() + 10);
            inimigo.position.setX(inimigo.position.getX()
                    + 10 * inimigo.direction.getX());
            if (inimigo.position.getY() > gameManager.windowManager.height + inimigo.frameHeight / 2) {
                inimigo.visible = false;
            }
        }
    }

    private void criaInimigo() {

        tempoInimigo.update();

        if (tempoInimigo.isTimeEnded() == false) {
            return;
        }

        tempoInimigo.restart();

        JGSprite novoInimigo = null;

        for (JGSprite inimigo : vetInimigos) {

            if (inimigo.visible == false) {
                novoInimigo = inimigo;
                novoInimigo.visible = true;
                break;
            }
        }

        //reciclar algum inimigo
        if (novoInimigo == null) {

            novoInimigo = createSprite(criarURL("Images/spr_enemy.png"), 1, 4);
            novoInimigo.addAnimation(10, true, 0, 2);
            vetInimigos.add(novoInimigo);
        }

        novoInimigo.position.setY(-novoInimigo.frameHeight / 2);

        int posicao = sorteio.nextInt(3);
        if (posicao == 0) {
            novoInimigo.position.setX(novoInimigo.frameWidth / 2);
            novoInimigo.direction.setX(1);
        } else if (posicao == 1) {
            novoInimigo.position.setX(gameManager.windowManager.width / 2);
            novoInimigo.direction.setX(0);
        } else if (posicao == 2) {
            novoInimigo.position.setX(gameManager.windowManager.width
                    - novoInimigo.frameWidth / 2);
            novoInimigo.direction.setX(-1);
        }
    }

    //actualiza posicao de tirus na posicao de y addicinado ao vector
    private void actualizaTiros() {

        for (JGSprite tiro : vetTiros) {

            if (tiro.position.getY() < -tiro.frameHeight / 2) {
                tiro.visible = false;
            } else {
                tiro.position.setY(tiro.position.getY() - 10);
            }
        }
    }

    //metodo para criar um novo tiro
    private void criaTiro() {

        JGSprite novoTiro = null;
        
        tiro.play();

        //verificar se existe um tiro com visibilidade falsa
        for (JGSprite tiro : vetTiros) {

            if (tiro.visible == false) {

                novoTiro = tiro;
                novoTiro.visible = true;
                break;
            }
        }

        if (novoTiro == null) {
            System.out.println("teste primeito tiro");
            novoTiro = createSprite(criarURL("Images/spr_shot.png"), 1, 1);
            vetTiros.add(novoTiro);

        }

        novoTiro.position.setX(aviao.position.getX());
        novoTiro.position.setY(aviao.position.getY()
                - aviao.frameHeight / 2 - novoTiro.frameHeight / 2);

        //System.err.println(vetTiros.size());
    }

    private URL criarURL(String caminhoDeImagem) {

        return getClass().getResource(caminhoDeImagem);
    }

    @Override
    public void execute() {

        camada.speed.setX(0);

        if (gameManager.inputManager.keyTyped(KeyEvent.VK_ESCAPE)) {
            gameManager.setCurrentLevel(Principal.MENU);
            musica.stop();
            return;
        }
        

        actualizaTiros();

        criaInimigo();

        actualizaInimigos();

        colisaoTitosInimigos();

        actualizaExplosoes();
        
        

        if(gameOver==true){
        musica.stop();
            return;
        }
        
        controlaJogador();
        
        colisaoJogadorInimigo();

        
    }

    @Override
    public void init() {
        
        totalVidas=3;
        gameOver=false;
        
        musica.setNumberOfLoops(-1);
        musica.play();
        

        //atra o tiro
        tempoTiro = new JGTimer(500);
        tempoInimigo = new JGTimer(3000);
        tempoDeRecuperacao=new JGTimer(0);
        //caregar a imagem do rito
        createSprite(criarURL("Images/spr_shot.png"), 1, 1).visible = false;
        createSprite(criarURL("Images/spr_enemy.png"), 1, 4).visible = false;
        createSprite(criarURL("Images/spr_bigexplosion.png"), 2, 4).visible = false;

        //instanciar o vector para armazenar os tiros
        vetTiros = new ArrayList<JGSprite>();
        vetInimigos = new ArrayList<JGSprite>();
        vetExplosoes = new ArrayList<JGSprite>();

        aviao = createSprite(criarURL("Images/spr_airplane.png"), 1, 4);
        aviao.addAnimation(10, true, 0, 2);
        aviao.position.setX(gameManager.windowManager.width / 2);
        aviao.position.setY(500);

        gameManager.windowManager.setBackgroundColor(Color.GRAY);

        camada = this.createLayer(criarURL("Images/spr_elements.png"),
                new JGVector2D(25, 19),
                new JGVector2D(32, 32), true);

        //configura os blocos da camada com seus respectivos quadros da tileimage
        for (int linha = 0; linha < 19; linha++) {

            for (int coluna = 0; coluna < 25; coluna++) {

                camada.setFrameIndex(linha, coluna, 2);
            }
        }
        camada.setFrameIndex(2, 7, 8);
        camada.setFrameIndex(6, 5, 9);
        camada.setFrameIndex(10, 3, 10);
        camada.setFrameIndex(16, 20, 8);
        camada.setFrameIndex(10, 18, 8);

        camada.setFrameIndex(12, 20, 4);
        camada.setFrameIndex(12, 21, 5);
        camada.setFrameIndex(13, 20, 6);
        camada.setFrameIndex(13, 21, 7);

        camada.speed.setY(5);

    }

    public void controlaJogador() {

        tempoTiro.update();
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_SPACE)) {
            if (tempoTiro.isTimeEnded()) {
                criaTiro();
                tempoTiro.restart();
            }

        }

        //deslocar aviai por x
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_LEFT)) {
            aviao.position.setX(aviao.position.getX() - 10);
        }

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_RIGHT)) {
            aviao.position.setX(aviao.position.getX() + 10);
        }

        //Movimentar o aviao por y
        if (gameManager.inputManager.keyPressed(KeyEvent.VK_UP)) {
            aviao.position.setY(aviao.position.getY() - 10);
        }

        if (gameManager.inputManager.keyPressed(KeyEvent.VK_DOWN)) {
            aviao.position.setY(aviao.position.getY() + 10);
        }

        //verificar se o sprite de aviao ultrapaou os limites nas laterais
        if (aviao.position.getX() < aviao.frameWidth / 2) {
            aviao.position.setX(aviao.frameWidth / 2);
        }

        if (aviao.position.getX() > gameManager.windowManager.width - aviao.frameWidth / 2) {
            aviao.position.setX(gameManager.windowManager.width - aviao.frameWidth / 2);
        }

        //verificar se o sprite ultrapaou os limites nas laterais
        if (aviao.position.getY() < aviao.frameHeight / 2) {
            aviao.position.setY(aviao.frameHeight / 2);
        }

        if (aviao.position.getY() > gameManager.windowManager.height - aviao.frameHeight / 2) {
            aviao.position.setY(gameManager.windowManager.height - aviao.frameHeight / 2);
        }

    }

}
