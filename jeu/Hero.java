package jeu;

import Mondes.Ressources;
import carte.Carte;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import sys.Point;
import sys.Taille;

import java.util.ArrayList;
import java.util.List;

//import sys.EcranJeu;


public class Hero extends Personnage {

    private List<PersonnageNonJoueur> lesPnj;
    private List<Ennemi> lesEnnemis;
    private int experience;
    private int niveau;
    private int currentGold;

    private boolean artEpee;
    private boolean artBouclier;
    private boolean artFeu;
    private boolean artVoler;

    // hero information
    private static final int HEROLIFE = 1120;
    private static final float HEROSPEED = 0.1f;
    private static final int HEROLEVEL = 5;
    private static final int HEROGOLD = 500;

    private boolean nouvellePartie;

    /**
     * Constructeur de la class Hero (projet InterStateComm);
     * @param nom
     * @param positon
     */
    public Hero(String nom, Point positon) {
        super(nom, positon, Taille.LARGE_SIZE, HEROLIFE,  HEROSPEED);
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
        this.experience = 5;
        this.niveau = HEROLEVEL;
        this.currentGold = HEROGOLD;

        this.artEpee = true;
        this.artBouclier = true;
        this.artFeu = false;
        this.artVoler = false;

        nouvellePartie = true;
        // --
        this.chargerImage();
    }

    public Hero(String save) {

        super("?", 0, 0, 0, 0, 0,  HEROSPEED);
        nouvellePartie = false;
    }



    // seul le hero peut être contrôlé
    public void controle(GameContainer container) {
        if(container.getInput().isKeyDown(Input.KEY_UP)) {
            super.setDirection(0);
            super.marcher();
        } else if(container.getInput().isKeyDown(Input.KEY_LEFT)) {
            super.setDirection(1);
            super.marcher();
        } else if(container.getInput().isKeyDown(Input.KEY_DOWN)) {
            super.setDirection(2);
            super.marcher();
        } else if(container.getInput().isKeyDown(Input.KEY_RIGHT)) {
            super.setDirection(3);
            super.marcher();
        } else {
            super.stop();
        }
    }

    /**
     *
     * @param lesPnj
     */
    public void addPnj(List<PersonnageNonJoueur> lesPnj)
    {
        this.lesPnj = lesPnj;
    }


    public void removePnj() {
        this.lesPnj = new ArrayList<PersonnageNonJoueur>();
    }
    /**
     *
     * @param lesEnnemis
     */
    public void addEnnemis(List<Ennemi> lesEnnemis) {
        this.lesEnnemis = lesEnnemis;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isCollisionPnj(float x, float y) {
       for(PersonnageNonJoueur unPnj : lesPnj) {
           boolean collision = new Rectangle(x - 16, y - 20, 32, 32).intersects(unPnj.getBoundingBox());
           if(collision) {
               unPnj.setParle();
               return true;
           }
       }
       return false;
    }

    private boolean isCollisionEnnemi(float x, float y) {
        for(Ennemi unEnnemi : lesEnnemis) {
            boolean collision = new Rectangle(x - 16, y - 20, 32, 32).intersects(unEnnemi.getBoundingBox());
            if(collision) {
                // --
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    public Color detectAreaChanging(Carte uneCarte) {
        int tileW = uneCarte.getMap().getTileWidth();
        int tileH = uneCarte.getMap().getTileHeight();

        int gateLayer = uneCarte.getMap().getLayerIndex("porte");
        Image tile = uneCarte.getMap().getTileImage((int) this.x / tileW, (int) this.y / tileH, gateLayer);

        boolean collision = tile != null;
        if(collision)
        {
            Color color = tile.getColor((int) x % tileW, (int) y % tileH);
            System.out.println(color.getRed() + "," + color.getGreen() + "," + color.getBlue());
            return color;
        }
        return new Color(0, 0, 0);
    }

    @Override
    public boolean iscollisionLogic(TiledMap map, float x, float y) {
        int tileW = map.getTileWidth();
        int tileH = map.getTileHeight();

        int logicLayer = map.getLayerIndex("solide");
        Image tile = map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
        boolean collision = tile != null;
        if(collision) {
            Color color = tile.getColor((int) x % tileW, (int) y % tileH);
            collision = color.getAlpha() > 0;
        }
        return collision || isCollisionPnj( x, y)/* || isCollisionEnnemi( x, y)*/;
    }

    /**
     *
     */
    private void chargerImage() {
        this.loadAnimation(Ressources.spriteSheet, 6, 7,  11);
        this.loadAnimation(Ressources.spriteSheet, 6, 7,  9);
        this.loadAnimation(Ressources.spriteSheet, 6, 7,  8);
        this.loadAnimation(Ressources.spriteSheet, 6, 7,  10);
        this.loadAnimation(Ressources.spriteSheet, 6, 9,  11);
        this.loadAnimation(Ressources.spriteSheet, 6, 9,  9);
        this.loadAnimation(Ressources.spriteSheet, 6, 9,  8);
        this.loadAnimation(Ressources.spriteSheet, 6, 9,  10);
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getCurrentGold() {
        return currentGold;
    }

    public void setCurrentGold(int currentGold) {
        this.currentGold = currentGold;
    }

    public int getExperience() {
        return this.experience;
    }

    public void setExperience(int experienceGagne) {
        experience += experienceGagne;
    }

    public int getLevel() {
        return this.niveau;
    }

    public void setLevel(int levelGagne) {
        this.niveau += levelGagne;
    }

    public boolean getArtEpee() { return artEpee ; }

    public boolean getArtBouclier() { return artBouclier; }

    public boolean getArtFeu() {
        return artFeu;
    }

    public boolean getArtVoler() {
        return artVoler;
    }



}
