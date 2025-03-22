import java.util.LinkedList;
import java.util.Queue;

// Classe pour résoudre le labyrinthe avec l'algorithme BFS
public class ResolveurLargeurBFS {
    private Labyrinthe labyrinthe;
    private boolean[][] visite; // Tableau des cellules visitées
    private int[][] predecesseurs; // Tableau des prédécesseurs pour reconstruire le chemin
    private int nombreNoeudsExplores; // Compteur des noeuds explorés
    private int longueurChemin; // Longueur du chemin trouvé

    // Constructeur
    public ResolveurLargeurBFS(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
        this.visite = new boolean[labyrinthe.getGrille().length][labyrinthe.getGrille()[0].length];
        this.predecesseurs = new int[labyrinthe.getGrille().length][labyrinthe.getGrille()[0].length];
        this.nombreNoeudsExplores = 0;
        this.longueurChemin = 0;
    }

    // Résoudre le labyrinthe avec BFS
    public boolean resoudre() {
        int[] depart = labyrinthe.getDepart();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(depart);
        visite[depart[0]][depart[1]] = true;
        nombreNoeudsExplores++;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Directions : Haut, Bas, Gauche, Droite

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            // Vérifier si on est à l'arrivée
            if (x == labyrinthe.getArrivee()[0] && y == labyrinthe.getArrivee()[1]) {
                reconstruireChemin(x, y);
                return true;
            }

            // Explorer les directions
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (labyrinthe.isValide(newX, newY) && !visite[newX][newY]) {
                    queue.add(new int[]{newX, newY});
                    visite[newX][newY] = true;
                    predecesseurs[newX][newY] = x * labyrinthe.getGrille()[0].length + y;
                    nombreNoeudsExplores++;
                }
            }
        }

        return false; // Pas de chemin trouvé
    }

    // Reconstruire le chemin depuis l'arrivée jusqu'au départ
    private void reconstruireChemin(int x, int y) {
        while (x != labyrinthe.getDepart()[0] || y != labyrinthe.getDepart()[1]) {
            labyrinthe.getGrille()[x][y] = Labyrinthe.CHEMIN;
            longueurChemin++;
            int pred = predecesseurs[x][y];
            x = pred / labyrinthe.getGrille()[0].length;
            y = pred % labyrinthe.getGrille()[0].length;
        }
    }

    // Getters pour les performances
    public int getNombreNoeudsExplores() {
        return nombreNoeudsExplores;
    }

    public int getLongueurChemin() {
        return longueurChemin;
    }
}
