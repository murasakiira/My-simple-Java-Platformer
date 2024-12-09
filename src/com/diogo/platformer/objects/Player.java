package com.diogo.platformer.objects;

import com.diogo.platformer.framework.GameObject;
import com.diogo.platformer.framework.ObjectId;
import com.diogo.platformer.window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Player extends GameObject {

    private float width = 48, height = 96;

    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;

    private Handler handler;

    public Player(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
    }

    public void tick(LinkedList<GameObject> objects) {
        x += velX;
        y += velY;

        if (falling || jumping) {
            velY += gravity;

            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }

        Collision(objects);

    }

    private void Collision(LinkedList<GameObject> objects) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Block) {

                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + 32;
                    velY = 0;
                }

                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                }else
                    falling = true;

                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;
                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + 32;
                }

            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) ((int)x+(width/2)-(width/2)/2), (int) ((int)y+(height/2)), (int)width/2, (int)height/2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) ((int)x+(width/2)-((width/2)/2)), (int)y, (int)width/2, (int)height/2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int)x+width-5), (int)y+5, (int)5, (int)height-10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
    }

}
