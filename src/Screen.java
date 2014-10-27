/**
 * Created by luisma on 25/10/2014.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.*;

public class Screen extends JPanel implements Runnable{

    public static final int WIDTH=800, HEIGHT=600, TILESIZE=20;
    public Thread thread;
    public boolean running = false;

    private int xCord=10, yCord=10;
    private int size=5;

    private Random r;

    private PartesDelCuerpo parte;
    private ArrayList<PartesDelCuerpo> snake;

    private Manzana manzana;
    private ArrayList<Manzana> manzanas;

    private boolean dcha=true, izda=false, arriba=false, abajo=false;

    private int ticks=0;

    private Teclado tecla;

    public Screen(){
        //Añadimos el control por teclado con la clase privada de abajo llamada Teclado
        setFocusable(true);
        tecla = new Teclado();
        addKeyListener(tecla);

        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        snake = new ArrayList<PartesDelCuerpo>();
        manzanas = new ArrayList<Manzana>();
        r = new Random();

        start();
    }

    public void tick() {

        //Se genera la cabeza, esto no vuelve a ejecutarse ya que la serpiente no volvera a ser de 0 elementos.

        if (snake.size() == 0){
            parte = new PartesDelCuerpo(xCord, yCord, TILESIZE);
            snake.add(parte);
        }

        //Se genera una manza si no hay ninguna mas

        if (manzanas.size() == 0){
            int xCord = r.nextInt(39);
            int yCord = r.nextInt(29);
            manzana = new Manzana(xCord, yCord, TILESIZE);
            manzanas.add(manzana);
        }

        //Controlamos la direccion y la variable la cambiamos mediante el listener de teclado

        if (dcha) xCord++;
        if (izda) xCord--;
        if (arriba) yCord--;
        if (abajo) yCord++;

        //Controlamos que la serpiente al salir de los limites aparezca al otro lado

        if (xCord < 0 )
            xCord = 39;
        else if (xCord == 40)
            xCord = 0;
        if (yCord < 0)
            yCord = 29;
        else if (yCord == 30)
            yCord = 0;

        System.out.println(xCord + " " + yCord);

        //Creo una nueva parte del cuerpo cada vez para que parezca que se mueva, pero cuando llega al
        //límite del Size(osea el tamaño de la serpiente que es 5), empiezo a borrar la primera parte que se puso
        //y continua así con lo que siempre va a ser 5 de tamaño, a no ser que lo aumentemos con las manzanas.

        parte = new PartesDelCuerpo(xCord, yCord, TILESIZE);
        snake.add(parte);

        if (snake.size() > size){
            snake.remove(0);
        }

        for (int i = 0; i < manzanas.size(); i++){
            //Comprobamos si hay colision entre la cabeza de la serpiete (Que son siempre las coordenadas xCord e yCord)
            //con las manzanas. Si la hay aumentamos el tamaño de la serpiente y borramos la manzana. Al volver a ser 0
            //el tamaño del array de manzanas se generara una nueva.
            if (xCord == manzanas.get(i).getxCord() && yCord == manzanas.get(i).getyCord()){
                size++;
                manzanas.remove(i);
            }
        }

        for (int i = 0; i < snake.size(); i++){
            //Hacemos la colision de la serpiente con ella misma, si ocurre para el hilo y fin del juego
            if (xCord == snake.get(i).getxCord() && yCord == snake.get(i).getyCord()){
                //Aqui le decimos que si es la cabeza la que choca consigo misma (Que es lo que pasaria siempre) no ocurra nada
                //Ya que la cabeza siempre esta la ultima del arraylist, por eso lo del snake.size()-1
                if (i != snake.size()-1){
                   stop();
                }
            }
        }
    }

    //El metodo paint es llamado automaticamente por el método repaint

    public void paint(Graphics g){
        //Limpiamos la pantalla
        g.clearRect(0,0,WIDTH,HEIGHT);

        //DIBUJO LA CUADRICULA (Primero las verticales y luego las horizontales)
        //Verticales
        g.setColor(Color.BLACK);
        for (int i = 0; i < WIDTH/TILESIZE ; i++){
            g.drawLine(i * TILESIZE, 0, i * TILESIZE, HEIGHT);
        }
        //Horizontales
        for (int i = 0; i < HEIGHT/TILESIZE; i++){
            g.drawLine(0, i * TILESIZE, WIDTH, i * TILESIZE);
        }
        //Pintamos las partes del cuerpo
        for (int i = 0; i < snake.size(); i++){
            snake.get(i).dibujar(g);
        }
        //Pintamos las manzanas
        for (int i = 0; i < manzanas.size(); i++){
            manzanas.get(i).dibujar(g);
        }
    }

    //Comienza el hilo del juego

    public void start(){
        running = true;
        thread = new Thread(this, "Game Loop");
        thread.start();
    }

    //Paramos el hilo del juego

    public void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //El hilo que se ejecuta en cada momento y llama a las funciones donde los objetos interactuan y se pintan

    public void run() {
        while (running){
            try {
                thread.sleep(100L - size );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tick();//Donde interactuarían los objetos
            repaint();//Para pintarlos llamando a la función paint de tu clase gozosa
        }
    }

    //Aqui me he quedado loco, pero se crea una clase dentro de esta por comodidad, ya que hace faltar implementar el
    //listener y para no hacer una clase nueva solo para los inputs, se crea aqui.

    private class Teclado implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_RIGHT && !izda) {
                dcha = true;
                arriba = false;
                abajo = false;
            }
            if (key == KeyEvent.VK_LEFT && !dcha) {
                izda = true;
                arriba = false;
                abajo = false;
            }
            if (key == KeyEvent.VK_UP && !abajo) {
                dcha = false;
                izda = false;
                arriba = true;
            }
            if (key == KeyEvent.VK_DOWN && !arriba) {
                dcha = false;
                izda = false;
                abajo = true;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
