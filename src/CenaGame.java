
import JGames2D.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;

public class CenaGame extends JGLevel {

    JGLayer camada = null;
    JGSprite aviao=null;

    private URL criarURL(String caminhoDeImagem) {

        return getClass().getResource(caminhoDeImagem);
    }

    @Override
    public void execute() {
        
        camada.speed.setX(0);

        if (gameManager.inputManager.keyTyped(KeyEvent.VK_ESCAPE)) {
            gameManager.setCurrentLevel(Principal.MENU);
            return;
        }
        
        controlaJogador();
          
    }

    @Override
    public void init() {
        
        aviao=createSprite(criarURL("Images/spr_airplane.png"), 1, 4);
        aviao.addAnimation(10,true, 0, 2);
        aviao.position.setX(gameManager.windowManager.width/2);
        aviao.position.setY(500);
        
        
        gameManager.windowManager.setBackgroundColor(Color.GRAY);

        camada = this.createLayer(criarURL("Images/spr_elements.png"),
                new JGVector2D(25,19),
                new JGVector2D(32, 32), true);
        
        //configura os blocos da camada com seus respectivos quadros da tileimage
        
        for(int linha=0;linha<19;linha++){
            
            for(int coluna =0;coluna<25;coluna++){
            
                camada.setFrameIndex(linha,coluna,2);
            }
        }
        camada.setFrameIndex(2, 7,8);
        camada.setFrameIndex(6, 5,9);
        camada.setFrameIndex(10, 3,10);
        camada.setFrameIndex(16, 20,8);
        camada.setFrameIndex(10,18,8);
        
        camada.setFrameIndex(12, 20,4);
        camada.setFrameIndex(12,21,5);
        camada.setFrameIndex(13, 20,6);
        camada.setFrameIndex(13,21,7);
        
        camada.speed.setY(5);


    }
    
    public void controlaJogador(){
    
        //deslocar aviai por x
        if(gameManager.inputManager.keyPressed(KeyEvent.VK_LEFT))
         aviao.position.setX(aviao.position.getX()-10);
        
        if(gameManager.inputManager.keyPressed(KeyEvent.VK_RIGHT))
            aviao.position.setX(aviao.position.getX()+10);
        
        //Movimentar o aviao por y
         if(gameManager.inputManager.keyPressed(KeyEvent.VK_UP))
            aviao.position.setY(aviao.position.getY()-10);
        
          if(gameManager.inputManager.keyPressed(KeyEvent.VK_DOWN))
            aviao.position.setY(aviao.position.getY()+10);
        
          //verificar se o sprite de aviao ultrapaou os limites nas laterais
          if(aviao.position.getX()<aviao.frameWidth/2)
              aviao.position.setX(aviao.frameWidth/2);
          
          if(aviao.position.getX()>gameManager.windowManager.width-aviao.frameWidth/2)
              aviao.position.setX(gameManager.windowManager.width-aviao.frameWidth/2);
          
          //verificar se o sprite ultrapaou os limites nas laterais
          if(aviao.position.getY()<aviao.frameHeight/2)
              aviao.position.setY(aviao.frameHeight/2);
          
          if(aviao.position.getY()>gameManager.windowManager.height-aviao.frameHeight/2)
              aviao.position.setY(gameManager.windowManager.height-aviao.frameHeight/2);
    
    }

}
