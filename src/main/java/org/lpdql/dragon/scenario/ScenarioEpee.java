package org.lpdql.dragon.scenario;


import org.lpdql.dragon.carte.Carte;
import org.lpdql.dragon.ecrans.EcranJeu;
import org.lpdql.dragon.effet.Effet;
import org.lpdql.dragon.objets.ObjetMessage;
import org.lpdql.dragon.personnages.PersonnageNonJoueur;
import org.lpdql.dragon.personnages.ennemis.Goblin;
import org.lpdql.dragon.personnages.ennemis.Squelette;
import org.lpdql.dragon.singleton.InterStateComm;
import org.lpdql.dragon.system.*;
import org.newdawn.slick.SlickException;

import static org.lpdql.dragon.monde.Ressources.*;

/**
 * class ScenarioEpee
 *
 * @author: Diuxx
 */
public class ScenarioEpee extends Scenario {

    private final Taille BASIC_SIZE = new Taille(16, 16);

    /**
     * Class constructor
     */
    public ScenarioEpee() throws SlickException {
        super();
    }

    @Override
    protected void chargerMondePouvoir(Carte map) {

    }

    @Override
    protected void chargerMondeFeu(Carte map) {

    }

    @Override
    protected void chargerMondeEpee(Carte map) {

    }

    @Override
    protected void chargerMondeBouclier(Carte map) {

    }

    /**
     *
     */
    @Override
    protected void chargerMaison(Carte map) {
        Point pLettre = findObjetPosition(map, "lettre");
        if(pLettre != null) {
            // we should do a letter object instead of (ObjetMessage) !
            ObjetMessage lettre = new ObjetMessage("Lettre", pLettre, BASIC_SIZE, 1);
            lettre.setMessage(
                "Si tu lis cette lettre c'est que les 4 royaumes font faces à une grande menace.\nTu vas devoir les explorer et obtenir l'artéfact présent dans chacuns d'eux...#" +
                "Une fois cela fait rends toi au château et élimines le vil Roi Dragon qui menace ce monde\n"
            );
            lettre.loadAnimation(spriteSheet_letter, 0, 6, 0);

            Effet importantLettre = new Effet("iLettre",
                    new Point((int)lettre.getPosition().getX() /*- 16*/, (int)lettre.getPosition().getY() - lettre.getTaille().getHauteur() - 24), new Taille(16, 24));
            importantLettre.loadAnimation(spriteSheet_important, 0, 4, 0);
            this.getLesEffets().add(importantLettre);

            // set a story element who should be donne if interact
            lettre.setStoryElement(Story.LIRELETTRE);
            super.getLesObjets().add(lettre);
        }
    }

    /**
     *
     */
    @Override
    protected void chargerMainMap(Carte map) {
        super.chargerMainMap(map);

        Point pServante = findPnjPosition(map, "servante");
        if(null == pServante)
            return;

        PersonnageNonJoueur pnjServante = new PersonnageNonJoueur("Servante", pServante, 32, 32);
        pnjServante.loadAnimation(org.lpdql.dragon.monde.Ressources.spriteSheet_PNJ, 3, 4,  4);

        pnjServante.addDialogue(
           "Tu devrais commencer par te rendre au pays de l'Epée.. Leur trésor te sera utile pour obtenir les 3 autres.\n" +
                "#tu veux savoir ou trouver le pays de l'épée ?" +
                "#c'est la porte qui se trouve la plus à l'est d'ici. Mais fais attention, des monstres rôdes, d'après moi, une épée\n" +
                "devrait suffire pour les terrasser. Bonne chance <3"
        );
        if(!Story.TUTOPARLEROLDMAN.getState()) {

            pnjServante.addDialogue(
                    "Bonjour " + InterStateComm.getLeHero().getNom() + ", Bienvenu chez toi. Il y a un vieil homme la bas... Il veut te parler !\n" +
                    "Apparemment c'est important.."
            );
        }
        Effet importantServante = new Effet("iservante", new Point((int)pnjServante.getX() - 8, (int)pnjServante.getY() - pnjServante.getHeight() - 24), new Taille(16, 24));
        importantServante.loadAnimation(spriteSheet_important, 0, 4, 0);
        this.getLesEffets().add(importantServante);

        super.getLesPnj().add(pnjServante);

        Point pChasseur = findPnjPosition(map, "old_man");// 362 870
        PersonnageNonJoueur pnjOldMan = new PersonnageNonJoueur("Old man", pChasseur, 32, 32);
        pnjOldMan.loadAnimation(spriteSheet_vieilHomme, 0, 1,  0);

        pnjOldMan.addDialogue("aaahhh ! il y a plein de monstres.. tues les tu gagnera en puissance !");
        if(!Story.TUTOFIRSTENNEMIWASKILLED.getState()) {

            Effet importantOldMan = new Effet("ivieuxMonsieur", new Point((int)pnjOldMan.getX() - 8, (int)pnjOldMan.getY() - pnjOldMan.getHeight() - 24), new Taille(16, 24));
            importantOldMan.loadAnimation(spriteSheet_important, 0, 4, 0);
            this.getLesEffets().add(importantOldMan);

            pnjOldMan.addDialogue(
                "Yo " + InterStateComm.getLeHero().getNom() + ", Prends cette épée et vas tuer ce monstre pour t'entrainer.. \nUtilises la touche \"a\" pour lancer une attaque, la touche \"f\" sert à fuir le combat..#" +
                "Saches qu'un vrais héro ne fuit jamais....#" + ".................#" + "Jamais"
            );
        }
        pnjOldMan.setStoryElement(Story.TUTOPARLEROLDMAN);
        super.getLesPnj().add(pnjOldMan);


        if(!Story.TUTOEND.getState()) {
            // --
            MyStdOut.write(MyStdColor.GREEN, "<" + getClass().getSimpleName() + ">" + map.getLastMapName());

            if(!Story.TUTOPARLEROLDMAN.getState() && map.getLastMapName().equals("maison"))
                pnjServante.setParle();

            if(!Story.TUTOFIRSTENNEMIWASKILLED.getState()) {
                Story.TUTOPARLEROLDMAN.setState(false);
                Story.TUTOSPAWNENNEMI.setState(false);
                Story.OWNBASICSWORD.setState(false);
            }
        }
    }

    /**
     * permet d'effectuer des changement sans recharger le scenario
     * @param map
     */
    @Override
    public void update(Carte map, Camera camera) {
        if(!Story.FIRSTSTART.getState()) {//  plein de nouvelles aventures t'attendes

            EcranJeu.lesMessages.add(
                    "Bienvenue chez toi " + InterStateComm.getLeHero().getNom() + ", tu n'as plus aucunes nouvelles de ton père depuis près d'un ans.\n" +
                    "Il semble qu'une lettre soit arrivé aujoud'hui chez toi. Lis là elle t'apportera je pense beaucoup !\n" +
                    "J'esperes que tu es pret.. plein de nouvelles aventures t'attendes.."
            );

            Story.FIRSTSTART.done();
        }



        if(Story.TUTOPARLEROLDMAN.getState())
        {
            if(!Story.TUTOSPAWNENNEMI.getState() && !Story.TUTOEND.getState()) {
                Point pFirstEnnemi = findPnjPosition(map, "first_ennemi");
                Squelette firstEnnemi = new Squelette(pFirstEnnemi, Direction.VERTICAL);
                firstEnnemi.setStoryElement(Story.TUTOFIRSTENNEMIWASKILLED);
                firstEnnemi.addCollision(InterStateComm.getLeHero());
                firstEnnemi.marcher();
                super.getLesEnnemis().add(firstEnnemi);

                Story.TUTOSPAWNENNEMI.done();
                Story.OWNBASICSWORD.done();

                // -- play sound here
                sounds.playZik("victory");
                EcranJeu.lesMessages.add(
                        "Vous venez de gagner l'épée basique. Elle va vous permettre de vous defendre contre les ennemis hostiles#" +
                        "Servez-vous-en pour terrasser votre premier ennemi.. Il est là.."
                );
            }
            //--
        }
        if(Story.TUTOEND.getState() && !Story.GAMESTART.getState())
        {
            super.resetOnCurrentMap(map, camera); // teleport sur un point de spawn
            Story.GAMESTART.done();
        }
        // --
    }

    /**
     *
     * @param destMap
     * @return
     */
    @Override
    protected boolean laodMapAuthorization(String originMap, String destMap) {

        boolean autorization = true && super.laodMapAuthorization(originMap, destMap);
        boolean readLetterBeforLeave = (originMap.equals("maison") && destMap.equals("dragon")) ? Story.LIRELETTRE.getState(): true;
        if(!readLetterBeforLeave) {
            EcranJeu.lesMessages.add(Story.LIRELETTRE.getMessage());
        }

        // autorization
        autorization = autorization && readLetterBeforLeave;
        return autorization;
    }

}
