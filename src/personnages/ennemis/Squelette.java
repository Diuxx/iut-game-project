package personnages.ennemis;


import org.newdawn.slick.Image;

import personnages.Ennemi;
import sys.Direction;
import sys.Point;
import sys.Taille;

/**
 * class Squelette
 *
 * @author: Ez
 */
public class Squelette extends Ennemi {

    private static final Taille SIZE = new Taille(32, 32);
    private static final int TIMER = 1500;
    private static final float VITESSE = 0.04f;
    private static final Image ENNEMI_IMAGE = Mondes.Ressources.spriteSheet_Ennemis.getSubImage(3, 1).getScaledCopy(2);
    private static final int NIVEAU = 2;
    
    /**
     * Class constructor
     */
    public Squelette(int x, int y, Taille taille, Direction direction, int timer, float vitesse, Image ennemiImages, int niveau) {
        super("Squelette", x, y, taille.getLargeur(), taille.getLongeur(),
                50, direction, timer, vitesse, ennemiImages, niveau);
        this.chargerImage();
    }
    
    public Squelette(Point positon, Taille taille, Direction direction, int timer, float vitesse , Image ennemiImages, int niveau) {
        this(positon.getX(), positon.getY(), taille, direction, timer, vitesse, ennemiImages, niveau);
    }

    public Squelette(Point p, Direction direction) {
        this(p.getX(), p.getY(), Squelette.SIZE, direction, Squelette.TIMER, Squelette.VITESSE, Squelette.ENNEMI_IMAGE, Squelette.NIVEAU);
    }

	private void chargerImage() {
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  3);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  1);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  0);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 4,  2);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  3);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  1);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  0);
        this.loadAnimation(Mondes.Ressources.spriteSheet_Ennemis, 3, 5,  2);
    }
}