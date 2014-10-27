/**
 * Created by luisma on 25/10/2014.
 */

import java.awt.*;

public class PartesDelCuerpo {

    private int xCord, yCord, width, height;

    public PartesDelCuerpo(int xCord, int yCord, int tileSize){
        this.xCord = xCord;
        this.yCord = yCord;
        this.width = tileSize;
        this.height = tileSize;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void tick(){

    }

    //la funcion que dibuja la parte de la serpiente

    public void dibujar(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(xCord * width, yCord * height, width, height);
    }
}
