import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class Personnage {

    // animation du personnage
    private List<Animation> animation;

    // information sur le personnage
    private String nom;
    private int pointDeVie;

    // mouvement du personnage
    private float x = 0, y = 0;
    private int direction = 0;
    private boolean moving = false;

    // --
    public Personnage(String nom, float x, float y, int pointDeVie) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.pointDeVie = pointDeVie;
        this.animation = new ArrayList<Animation>();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    // mouvement du personnage
    public void mouvement(int delta, TiledMap map) {
        if (this.moving) {
            float futurX = this.x;
            float futurY = this.y;
            switch (this.direction) {
                case 0: futurY = this.y - .1f * delta; break;
                case 1: futurX = this.x - .1f * delta; break;
                case 2: futurY = this.y + .1f * delta; break;
                case 3: futurX = this.x + .1f * delta; break;
            }

            Image tile = map.getTileImage(
                    (int) futurX / map.getTileWidth(),
                    (int) futurY / map.getTileHeight(),
                    map.getLayerIndex("solide"));

            // il y a collision sur un element solide
            boolean collision = tile != null;
            if(collision) {
                Color color = tile.getColor(
                        (int) futurX % map.getTileWidth(),
                        (int) futurY % map.getTileHeight());
                collision = color.getAlpha() > 0;
            }
            if(collision) {
                this.moving = false;
            } else {
                this.x = futurX;
                this.y = futurY;
            }
        }
    }

    // changement de direction du personnage
    public void setDirection(int direction) {
        this.direction = direction;
    }

    // le personnage est en mouvement
    public void marcher() {
        this.moving = true;
    }

    // le personnage ne bouge pas
    public void stop() {
        this.moving = false;
    }

    // affichage du personnage dans le graphique
    public void afficher(Graphics g) {
        g.drawAnimation(this.animation.get(direction + (moving ? 4 : 0)), x - 16, y - 24);
    }

    // collision logic
    public boolean collisionLogic(TiledMap map) {


        return false;
    }

    // chargement des animations pour un personnage.
    public void loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 150);
        }//return animation;
        this.animation.add(animation);
    }
}
