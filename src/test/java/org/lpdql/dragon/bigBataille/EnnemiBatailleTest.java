package org.lpdql.dragon.bigBataille;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lpdql.dragon.personnages.Ennemi;
import org.lpdql.dragon.personnages.Hero;
import org.lpdql.dragon.system.Point;

import static org.junit.Assert.*;

public class EnnemiBatailleTest {

    private EnnemiBataille ennemiBataille;

    private Ennemi anEnnemi;
    private Hero hero;

    private HeroBataille heroBataille;

    @Before
    public void setUp() throws Exception {
        this.hero = new Hero("test");
        this.anEnnemi = new Ennemi("test", new Point(100, 100), 32, 32);
        this.ennemiBataille = new EnnemiBataille(this.anEnnemi);
        this.heroBataille = new HeroBataille(this.hero);
    }

    @After
    public void tearDown() throws Exception {
        this.anEnnemi = null;
        this.ennemiBataille = null;
        this.heroBataille = null;
        this.hero = null;
    }

    @Test
    public void damageToTest() {
        this.ennemiBataille.damageTo(this.heroBataille);
        assertEquals(80, this.heroBataille.getHero().getPointDeVieActuel(),  0.001);
        assertEquals(0, this.ennemiBataille.getDefenseBonus());
    }

    @Test
    public void takeDamageTest() {
        this.ennemiBataille.takeDamage(10);
        assertEquals(1 * 30 + 150 - 10, this.ennemiBataille.getEnnemi().getPointDeVieActuel(),  0.001);
    }
}