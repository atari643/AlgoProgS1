package biosphere7;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IAlearning {

    final double alpha = 0.1; // taux d'apprentissage
    final double gamma = 1; // facteur de discount

    private static final int NUM_STATES = 110000;
    private static final int NUM_ACTIONS = 196;

    public double[][] qTable;
    public Random random;

    final String PLATEAU_VIDE
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";

    JoueurBiosphere7 j = new JoueurBiosphere7();
    IABiosphere7 ia2 = new IABiosphere7(null, -1, 'R');
    IABiosphere7 ia3 = new IABiosphere7(null, -1, 'R');
    state s = new state();

    Case[][] plateautest = Utils.plateauDepuisTexte(PLATEAU_VIDE);

    Vitalites tout() {
        plateautest = Utils.plateauDepuisTexte(PLATEAU_VIDE);
        for (int i = 0; i < 20; i++) {
            partie();
        }
        String[] actionsPossiblesDepuisPlateau
                = j.actionsPossibles(plateautest, 'R', 5);
        ActionsPossibles actionsPossibles
                = new ActionsPossibles(actionsPossiblesDepuisPlateau);
        actionsPossibles.afficher();
        Vitalites vita = JoueurBiosphere7.vitalitesPlateau(plateautest);
        return new Vitalites(vita.vitalitesRouge, vita.vitalitesBleu);
    }

    void partie() {
        ia2.mettreAJour(plateautest, jeu(plateautest), 'R');
        String[] actionsB = j.actionsPossibles(plateautest, 'B', 5);
        random = new Random();
        int aléatoire = random.nextInt(196);
        ia3.mettreAJour(plateautest, ActionsPossibles.enleverVitalites(actionsB[aléatoire]), 'B');
    }

    String jeu(Case[][] plateau) {
        state s = new state();
        int valeur = 0;
        int state = s.hashMatrice(plateau);
        String[] actions = j.actionsPossibles(plateau, 'R', 5);
        valeur = bestValues[state][0];
        return ActionsPossibles.enleverVitalites(actions[valeur]);
    }
    String actionString;
    Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU_VIDE);

    void start() {
        state s = new state();
        int compteur = 0;
        while (compteur < NUM_STATES) {
            plateau = Utils.plateauDepuisTexte(PLATEAU_VIDE);
            int compteur2 = 0;
            while (compteur2 < 20) {
                int state = s.hashMatrice(plateau);
                String[] actions = j.actionsPossibles(plateau, 'R', 5);
                int actionInt = chooseAction(state, actions);
                ia2.mettreAJour(plateau, actionString, 'R');
                double reward = qReward(plateau);
                int nextState = s.hashMatrice(plateau);
                double max = learn(state, actionInt, nextState, reward, plateau);
                qTable[state][actionInt] = max;
                bestValues[state][0] = argMaxQ(state);
                String[] actionsB = j.actionsPossibles(plateau, 'B', 5);
                state = s.hashMatrice(plateau);
                int actionBInt = random.nextInt(196);
                ia3.mettreAJour(plateau, ActionsPossibles.enleverVitalites(actionsB[actionBInt]), 'B');
                double reward2 = qReward(plateau);
                int nextState2 = s.hashMatrice(plateau);
                max = learn(state, actionInt, nextState2, reward2, plateau);
                qTable[state][actionBInt] = max;
                bestValues[state][0] = argMaxQ(state);
                compteur2++;
            }
            compteur++;
        }
    }

    public IAlearning(int numEtat, int numActions) {
        qTable = new double[numEtat][numActions];
        random = new Random();
    }

    public double learn(int state, int action, int nextState, double reward, Case[][] plateau) {
        int actionInt = action;
        double currentQ = qTable[state][actionInt];
        double nextMaxQ = maxQ(nextState);
        double newQ = currentQ + alpha * (reward + gamma * nextMaxQ - currentQ);
        return newQ;
    }
    int[][] bestValues = new int[NUM_STATES][1];

    public static void main(String[] args) {
        IAlearning ia = new IAlearning(NUM_STATES, NUM_ACTIONS);
        ia.start();
        int compteurVictoireR = 0;
        int compteurVictoireB = 0;
        int égaliter = 0;
        for (int i = 0; i < 1000; i++) {
            ia.tout();
            if (ia.tout().vitalitesRouge > ia.tout().vitalitesBleu) {
                compteurVictoireR++;
            } else if (ia.tout().vitalitesRouge < ia.tout().vitalitesBleu) {
                compteurVictoireB++;
            } else {
                égaliter++;
            }
        }
        System.out.println(compteurVictoireR);
        System.out.println(compteurVictoireB);
        System.out.println(égaliter);
    }

    public void serialiser(int state, int action) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("matrix.txt", true))) {
            writer.write(String.valueOf(state));
            writer.write(" ");
            writer.write(String.valueOf(action));
            writer.newLine();

        } catch (IOException ex) {
            Logger.getLogger(IAlearning.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int chooseAction(int state, String[] actions) {
        int actionInt = 0;
        actionInt = bestValues[state][0];
        actionString = ActionsPossibles.enleverVitalites(actions[actionInt]);
        return actionInt;
    }

    int argMaxQ(int state) {
        int maxAction = 0;
        double maxQ = qTable[state][0];
        for (int action = 1; action < NUM_ACTIONS; action++) {
            double q = qTable[state][action];
            if (q > maxQ) {
                maxQ = q;
                maxAction = action;
            }
        }
        return maxAction;
    }

    double maxQ(int state) {
        double maxQ = qTable[state][0];
        for (int action = 1; action < NUM_ACTIONS; action++) {
            double q = qTable[state][action];
            if (q > maxQ) {
                maxQ = q;
            }
        }
        return maxQ;
    }

    public double qReward(Case[][] plateau) {
        Vitalites vita = JoueurBiosphere7.vitalitesPlateau(plateau);
        double score = 0;
        if (vita.vitalitesRouge > vita.vitalitesBleu) {
            score = 1;
        } else if (vita.vitalitesRouge == vita.vitalitesBleu) {
            score = 0;
        } else if (vita.vitalitesRouge < vita.vitalitesBleu) {
            score = -1;
        }
        return score;
    }
}
