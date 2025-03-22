import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Classe principale pour lancer l'application
public class Main {
    public static void main(String[] args) {
        // Créer la fenêtre d'accueil
        JFrame fenetreBienvenue = new JFrame("Bienvenue");
        fenetreBienvenue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetreBienvenue.setSize(1500, 1000);
        fenetreBienvenue.setLayout(new BorderLayout());

        // Créer le panneau de contenu
        JPanel panneauContenu = new JPanel();
        panneauContenu.setLayout(new BorderLayout());

        // Ajouter le titre
        JLabel titre = new JLabel("Bienvenue dans l'application de Labyrinthe", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 40));
        titre.setBackground(Color.WHITE);
        titre.setForeground(Color.BLACK);
        titre.setOpaque(true);
        panneauContenu.add(titre, BorderLayout.CENTER);

        // Ajouter le bouton "Commencer"
        JButton boutonAcces = new JButton("Commencer");
        boutonAcces.setFont(new Font("Arial", Font.BOLD, 20));
        boutonAcces.setPreferredSize(new Dimension(150, 50));
        boutonAcces.setBackground(new Color(34, 139, 34)); // Vert foncé
        boutonAcces.setForeground(Color.WHITE);
        boutonAcces.setFocusPainted(false);
        boutonAcces.setOpaque(true);
        boutonAcces.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panneauContenu.add(boutonAcces, BorderLayout.SOUTH);

        // Ajouter le panneau à la fenêtre
        fenetreBienvenue.add(panneauContenu);
        fenetreBienvenue.setLocationRelativeTo(null); // Centrer la fenêtre
        fenetreBienvenue.setVisible(true);

        // Action du bouton "Commencer"
        boutonAcces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetreBienvenue.dispose(); // Ferme la fenêtre d'accueil
                SwingUtilities.invokeLater(() -> new InterfaceGraphique()); // Lance l'application principale
            }
        });
    }
}
