/**
 * Created by luisma on 25/10/2014.
 */

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame{

    Principal(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Snake por Luisma");
        setResizable(false);

        init();
    }

    private void init() {
        //Aplico un layout de 1 fila po una columna para mas tarde añadirle el jpanel que manejara el cotarro
        setLayout(new GridLayout(1,1));
        //Me creo una nueva pantalla donde se ejecutara la pantalla de juego que hereda de JPanel
        Screen pantalla = new Screen();
        //Añado a Principal (Que es un frame al heredar de esta clase) la pantalla
        add(pantalla);

        pack();

        //Posiciono la pantalla en el centro
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        new Principal();
    }
}
