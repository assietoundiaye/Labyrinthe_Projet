import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

// Classe principale pour l'interface graphique
public class InterfaceGraphique extends JFrame {
    private Labyrinthe labyrinthe;
    private PanneauLabyrinthe panneauLabyrinthe;

    public InterfaceGraphique() {
        // Initialisation du labyrinthe et du panneau
        labyrinthe = Labyrinthe.genererAleatoire(15, 15);
        panneauLabyrinthe = new PanneauLabyrinthe();

        // Configuration de la fenêtre
        setTitle("Labyrinthe");
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Ajout du panneau de dessin
        add(panneauLabyrinthe, BorderLayout.CENTER);

        // Panneau pour les boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new GridLayout(5, 1));
        add(panelBoutons, BorderLayout.EAST);

        // Création des boutons
        JButton btnGenerer = new JButton("Générer Labyrinthe");
        JButton btnCharger = new JButton("Charger un Fichier");
        JButton btnAlgoDFS = new JButton("Algo DFS");
        JButton btnAlgoBFS = new JButton("Algo BFS");
        JButton btnComparaison = new JButton("Comparaison des 2 algos");

        panelBoutons.add(btnGenerer);
        panelBoutons.add(btnCharger);
        panelBoutons.add(btnAlgoDFS);
        panelBoutons.add(btnAlgoBFS);
        panelBoutons.add(btnComparaison);

        // Action du bouton "Générer Labyrinthe"
        btnGenerer.addActionListener(e -> {
            labyrinthe = Labyrinthe.genererAleatoire(15, 15);
            panneauLabyrinthe.repaint();
        });

        // Action du bouton "Charger un Fichier"
        btnCharger.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    labyrinthe = Labyrinthe.chargerDepuisFichier(fileChooser.getSelectedFile().getAbsolutePath());
                    panneauLabyrinthe.repaint();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Fichier non trouvé !");
                }
            }
        });

        // Action du bouton "Algo DFS"
        btnAlgoDFS.addActionListener(e -> {
            labyrinthe.reinitialiser();
            ResolveurProfondeurDFS resolveurDFS = new ResolveurProfondeurDFS(labyrinthe);
            long startTime = System.nanoTime();
            boolean solutionTrouvee = resolveurDFS.resoudre();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;

            panneauLabyrinthe.repaint();
        });

        // Action du bouton "Algo BFS"
        btnAlgoBFS.addActionListener(e -> {
            labyrinthe.reinitialiser();
            ResolveurLargeurBFS resolveurBFS = new ResolveurLargeurBFS(labyrinthe);
            long startTime = System.nanoTime();
            boolean solutionTrouvee = resolveurBFS.resoudre();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;

            panneauLabyrinthe.repaint();
        });

        // Action du bouton "Comparaison des 2 algos"
        btnComparaison.addActionListener(e -> {
            labyrinthe.reinitialiser();
            ResolveurProfondeurDFS resolveurDFS = new ResolveurProfondeurDFS(labyrinthe);
            long startTimeDFS = System.nanoTime();
            resolveurDFS.resoudre();
            long endTimeDFS = System.nanoTime();
            long durationDFS = (endTimeDFS - startTimeDFS) / 1000000;

            labyrinthe.reinitialiser();
            ResolveurLargeurBFS resolveurBFS = new ResolveurLargeurBFS(labyrinthe);
            long startTimeBFS = System.nanoTime();
            resolveurBFS.resoudre();
            long endTimeBFS = System.nanoTime();
            long durationBFS = (endTimeBFS - startTimeBFS) / 1000000;

            String message = "<html><body style='font-family:Arial,sans-serif;'>"
                + "<h2 style='color:blue;'>Comparaison des algorithmes :</h2>"
                + "<table border='1' cellpadding='5' cellspacing='0' style='border-collapse:collapse;width:100%;'>"
                + "<tr style='background-color:#f0f0f0;'><th>Algorithme</th><th>Temps d'exécution (ms)</th><th>Noeuds explorés</th><th>Longueur du chemin</th></tr>"
                + "<tr><td style='color:green;'>DFS</td><td>" + durationDFS
                + "</td><td>" + resolveurDFS.getNombreNoeudsExplores()
                + "</td><td>" + resolveurDFS.getLongueurChemin() + "</td></tr>"
                + "<tr><td style='color:red;'>BFS</td><td>" + durationBFS
                + "</td><td>" + resolveurBFS.getNombreNoeudsExplores()
                + "</td><td>" + resolveurBFS.getLongueurChemin() + "</td></tr>"
                + "</table></body></html>";
            JTextPane textPane = new JTextPane();
            textPane.setContentType("text/html");
            textPane.setText(message);
            textPane.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textPane);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(null, scrollPane, "Comparaison des Algorithmes", JOptionPane.PLAIN_MESSAGE);
            panneauLabyrinthe.repaint();
        });

        setVisible(true); // Affiche la fenêtre
    }

    // Panneau pour dessiner le labyrinthe
    private class PanneauLabyrinthe extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            dessinerLabyrinthe(g);
        }

        // Dessine le labyrinthe
        public void dessinerLabyrinthe(Graphics g) {
            char[][] grille = labyrinthe.getGrille();
            int cellSize = 40;
            int rows = grille.length;
            int cols = grille[0].length;

            int labyrintheWidth = cols * cellSize;
            int labyrintheHeight = rows * cellSize;

            int startX = (getWidth() - labyrintheWidth) / 2;
            int startY = (getHeight() - labyrintheHeight) / 2;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Ombre et fond
            g2d.setColor(new Color(0, 0, 0, 113));
            g2d.fillRoundRect(startX + 5, startY + 5, labyrintheWidth, labyrintheHeight, 15, 15);

            g2d.setColor(new Color(255, 255, 255, 255));
            g2d.fillRoundRect(startX, startY, labyrintheWidth, labyrintheHeight, 20, 20);

            // Dessin des cellules
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int x = startX + j * cellSize;
                    int y = startY + i * cellSize;

                    switch (grille[i][j]) {
                        case Labyrinthe.MUR:
                            g2d.setColor(new Color(12, 12, 12, 255));
                            g2d.fillRect(x, y, cellSize, cellSize);
                            break;

                        case Labyrinthe.PASSAGE:
                            g2d.setColor(new Color(255, 255, 255, 255));
                            g2d.fillRect(x, y, cellSize, cellSize);
                            break;

                        case Labyrinthe.DEPART:
                            g2d.setPaint(new Color(35, 115, 13));
                            g2d.fillRect(x, y, cellSize, cellSize);
                            break;

                        case Labyrinthe.ARRIVEE:
                            g2d.setPaint(new Color(209, 15, 15));
                            g2d.fillRect(x, y, cellSize, cellSize);
                            break;

                        case Labyrinthe.CHEMIN:
                            g2d.setPaint(new GradientPaint(x, y, new Color(52, 152, 219), x + cellSize, y + cellSize, new Color(173, 216, 230), true));
                            g2d.fillRect(x, y, cellSize, cellSize);
                            break;

                        case Labyrinthe.VISITE:
                            g2d.setPaint(new GradientPaint(x, y, new Color(243, 156, 18), x + cellSize, y + cellSize, new Color(255, 222, 173), true));
                            g2d.fillRect(x, y, cellSize, cellSize);
                            break;
                    }
                }
            }

            // Bordure du labyrinthe
            int bordureEpaisseur = 10;
            g2d.setColor(new Color(12,12,12));
            g2d.setStroke(new BasicStroke(bordureEpaisseur));
            g2d.drawRoundRect(startX, startY, labyrintheWidth, labyrintheHeight, 20, 20);
        }
    }
}
