//declaracao de todas as classes do pacote

import JGames2D.*;

public class Principal {

    public static final byte ABERTURA = 0;
    public static final byte MENU = 1;
    public static final byte GAME = 2;
    public static final byte CONTROLES = 3;
    public static final byte CREDITOS = 4;

    public static void main(String[] args) {
        //instanciar um objecto do motor
        JGEngine motor = new JGEngine();

        //configuracoes de opcoes da janela
        motor.windowManager.setResolution(800, 600, 32);
        motor.windowManager.setfullScreen(false);

        motor.windowManager.setWindowPosition(800 - 400, 450 - 300);
        ///regista uma Cena ao motor grafico
        motor.addLevel(new CenaAbertura());//CODIGO 0
        motor.addLevel(new CenaMenu());//CODIGO 1
        motor.addLevel(new CenaGame());//CODIGO 2
        motor.addLevel(new CenaControles());//CODIGO 3
        motor.addLevel(new CenaCreditos());//CODIGO 4

        //inicia a execucao do motor
        motor.start();
    }

}
