import java.awt.*;

/**
 * Created by luisma on 25/10/2014.
 */

public class Manzana {

    private int xCord, yCord, width, height;

    public Manzana (int xCord, int yCord, int tilesize){
        this.xCord = xCord;
        this.yCord = yCord;
        this.width = tilesize;
        this.height = tilesize;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }


    public void tick(){

    }

    //La funciona que dibuja la manzana

    public void dibujar(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(xCord * width, yCord * height, width, height);
    }
}
