import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Classe pour gérer le labyrinthe
public class Labyrinthe {
    // Constantes pour les différents types de cellules
    public static final char MUR = '#';
    public static final char PASSAGE = '=';
    public static final char DEPART = 'S';
    public static final char ARRIVEE = 'E';
    public static final char CHEMIN = '+';
    public static final char VISITE = '-';

    private char[][] grille; // Grille du labyrinthe
    private int[] depart = new int[2]; // Coordonnées du départ
    private int[] arrivee = new int[2]; // Coordonnées de l'arrivée

    // Constructeur
    public Labyrinthe(char[][] grille) {
        this.grille = grille;
        trouverPointsExtremites();
    }

    // Charger un labyrinthe depuis un fichier
    public static Labyrinthe chargerDepuisFichier(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        int lignes = 0;
        int colonnes = 0;

        // Compter lignes et colonnes
        while (scanner.hasNextLine()) {
            lignes++;
            String ligne = scanner.nextLine();
            colonnes = ligne.length();
        }
        scanner.close();

        // Créer la grille
        char[][] grille = new char[lignes][colonnes];
        scanner = new Scanner(file);
        for (int i = 0; i < lignes; i++) {
            String ligne = scanner.nextLine();
            grille[i] = ligne.toCharArray();
        }
        scanner.close();

        return new Labyrinthe(grille);
    }

    // Générer un labyrinthe aléatoire
    public static Labyrinthe genererAleatoire(int lignes, int colonnes) {
        char[][] grille = new char[lignes][colonnes];
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j] = (Math.random() > 0.3) ? PASSAGE : MUR;
            }
        }
        grille[0][0] = DEPART;
        grille[lignes - 1][colonnes - 1] = ARRIVEE;
        return new Labyrinthe(grille);
    }

    // Trouver les positions du départ et de l'arrivée
    private void trouverPointsExtremites() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                if (grille[i][j] == DEPART) {
                    depart[0] = i;
                    depart[1] = j;
                } else if (grille[i][j] == ARRIVEE) {
                    arrivee[0] = i;
                    arrivee[1] = j;
                }
            }
        }
    }

    // Getters
    public char[][] getGrille() {
        return grille;
    }

    public int[] getDepart() {
        return depart;
    }

    public int[] getArrivee() {
        return arrivee;
    }

    // Vérifier si une position est valide
    public boolean isValide(int x, int y) {
        return x >= 0 && x < grille.length && y >= 0 && y < grille[0].length && grille[x][y] != MUR;
    }

    // Réinitialiser le labyrinthe
    public void reinitialiser() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                if (grille[i][j] == CHEMIN || grille[i][j] == VISITE) {
                    grille[i][j] = PASSAGE;
                }
            }
        }
    }
}
