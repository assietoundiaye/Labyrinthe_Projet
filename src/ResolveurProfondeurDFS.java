// Classe pour résoudre le labyrinthe avec l'algorithme DFS
public class ResolveurProfondeurDFS {
    private Labyrinthe labyrinthe;
    private boolean[][] visite; // Tableau des cellules visitées
    private int nombreNoeudsExplores; // Compteur des noeuds explorés
    private int longueurChemin; // Longueur du chemin trouvé

    // Constructeur
    public ResolveurProfondeurDFS(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        this.visite = new boolean[labyrinthe.getGrille().length][labyrinthe.getGrille()[0].length];
        this.nombreNoeudsExplores = 0;
        this.longueurChemin = 0;
    }

    // Résoudre le labyrinthe avec DFS
    public boolean resoudre() {
        int[] depart = labyrinthe.getDepart();
        return explorer(depart[0], depart[1]);
    }

    // Explorer une case du labyrinthe
    private boolean explorer(int x, int y) {
        if (!labyrinthe.isValide(x, y) || visite[x][y]) {
            return false; // Case invalide ou déjà visitée
        }

        visite[x][y] = true;
        nombreNoeudsExplores++;

        // Vérifier si on est à l'arrivée
        if (x == labyrinthe.getArrivee()[0] && y == labyrinthe.getArrivee()[1]) {
            labyrinthe.getGrille()[x][y] = Labyrinthe.CHEMIN;
            longueurChemin++;
            return true;
        }

        // Explorer les 4 directions (Bas, Haut, Droite, Gauche)
        if (explorer(x + 1, y) || explorer(x - 1, y) || explorer(x, y + 1) || explorer(x, y - 1)) {
            labyrinthe.getGrille()[x][y] = Labyrinthe.CHEMIN;
            longueurChemin++;
            return true;
        }

        labyrinthe.getGrille()[x][y] = Labyrinthe.VISITE; // Marquer comme visité
        return false;
    }

    // Getters pour les performances
    public int getNombreNoeudsExplores() {
        return nombreNoeudsExplores;
    }

    public int getLongueurChemin() {
        return longueurChemin;
    }
}
