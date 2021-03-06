package org.lpdql.dragon.bigBataille;

import org.lpdql.dragon.effet.Effet;
import org.lpdql.dragon.monde.Ressources;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.system.Point;
import org.lpdql.dragon.system.Taille;
import org.newdawn.slick.*;

/**
 * class EnnemiBataille
 *
 * @author: Diuxx
 */
public class EnnemiBataille {

    private Ennemi ennemi;
    private Point position;

    private Image image;

    private Point mouvement;

    private long timer;
    private long timerRetour;
    private int pas = -10;

    public boolean aAttaqueStart = false;

    public static int DEFANCE;
    private int defenseBonus;

    /**
     * Class constructor
     */
    public EnnemiBataille(Ennemi e, GameContainer gc) {
        this.ennemi = e;
        this.position = new Point(gc.getWidth() * 3 / 4, gc.getHeight() / 2);
        this.image = e.getEnnemiImages();
        this.mouvement = new Point(0, 0);
        this.defenseBonus = 0;
    }

    public EnnemiBataille(Ennemi e) {
        this.ennemi = e;
        this.position = new Point(0, 0);
        this.image = null;
        this.mouvement = new Point(0, 0);
        this.defenseBonus = 0;
    }

    public void attaque(HeroBataille e) {
        aAttaqueStart = true;
        this.timer = System.currentTimeMillis();
        this.timerRetour = System.currentTimeMillis();

        if (pas > 0) pas *= -1;
    }

    // --
    public void attaqueAnimation(HeroBataille hero) {
        if (System.currentTimeMillis() - this.timerRetour >= 500 && this.pas < 0) {
            this.damageTo(hero);
            this.pas *= -1;
        }
        if (System.currentTimeMillis() - this.timerRetour >= 1000 && this.pas > 0) {
            this.mouvement.setX(0);
            aAttaqueStart = false; // do damage here..
        }
        if (System.currentTimeMillis() - this.timer > 50) {
            this.mouvement.setX(mouvement.getX() + this.pas);
            this.timer = System.currentTimeMillis();
        }
    }

    public boolean isAnnimationEnd() {
        return !this.aAttaqueStart;
    }

    public void update(HeroBataille e) {
        if (aAttaqueStart)
            attaqueAnimation(e);
    }

    public void damageTo(HeroBataille e) {
        e.takeDamage(this.getATK() - this.getDefenseBonus());
        printEnnemiAtkLog(this.getATK(), (int) (e.getHero().getPointDeVieActuel()), this.getDefenseBonus());
        this.setDefenseBonus(0);
    }

    public void takeDamage(int damage) {
        this.ennemi.setPointDeVieActuel(this.ennemi.getPointDeVieActuel() - damage);
    }

    public int getATK() {
        return (int) this.ennemi.getATK();
    }

    public void draw(Graphics g, GameContainer gc) {
        this.drawBarHp(g, gc);
        this.drawCurrentHp(g, gc);
        image.draw(mouvement.getX() + position.getX(), mouvement.getY() + position.getY(), this.getEnnemi().getHeight(),
                this.getEnnemi().getWidth());
    }

    private void drawBarHp(Graphics g, GameContainer gc) {
        g.setColor(Color.white);
        g.drawRect(gc.getWidth() * 3 / 4 - 40, gc.getHeight() / 2 - this.image.getHeight() / 2 - 15, 130, 20);
        g.setColor(Color.red);
        g.fillRect(gc.getWidth() * 3 / 4 - 40 + 1, gc.getHeight() / 2 - this.image.getHeight() / 2 - 15 + 1,
                Math.max(0, (this.ennemi.getPointDeVieActuel() / this.ennemi.getPointDeVie())) * 130, 20);

    }

    private void drawCurrentHp(Graphics g, GameContainer gc) {
        g.setColor(Color.white);
        g.drawString("" + (int) Math.max(0, (int) this.ennemi.getPointDeVieActuel()), gc.getWidth() * 3 / 4 + 10,
                gc.getHeight() / 2 - this.ennemi.getEnnemiImages().getHeight() / 2 - 13);
    }

    public Ennemi getEnnemi() {
        return ennemi;
    }

    public void setEnnemi(Ennemi ennemi) {
        this.ennemi = ennemi;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getMouvement() {
        return mouvement;
    }

    public void setMouvement(Point mouvement) {
        this.mouvement = mouvement;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    public void setDefenseBonus(int defense) {
        this.defenseBonus = defense;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public void printEnnemiAtkLog(int atk, int pv, int def) {
        System.out.println();
        System.out.println("<Bataille> Ennemi turn");
        System.out.println("Ennemi ATK power  ----> " + atk + " Defance - " + def);
        System.out.println("hero restant vie  ----> " + pv);
    }

    public Effet swingEffet(HeroBataille h) {
        Effet e = new Effet("swingEnnemi", h.getPosition(), new Taille(59, 68));
        e.loadAnimation(Ressources.spriteSheet_swordHit, 0, 2, 0, new int[] { 200, 200 });
        e.getAnimation().stopAt(1);
        return e;
        // for movible effet extend a new class MovibleEffet with Effet, and add depart
        // position & endPosition..
    }

}
