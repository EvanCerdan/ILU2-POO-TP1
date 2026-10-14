package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

    public static void main(String[] args) {

        Etal etal = new Etal();

        // Test 1 : libérer un étal qui n'a jamais été occupé
        System.out.println("Test libération d'un étal vide :");
        System.out.println(etal.libererEtal());

        // On occupe l'étal pour tester les achats
        Gaulois vendeur = new Gaulois("Bonemine", 7);
        etal.occuperEtal(vendeur, "fleurs", 20);

        // Test 2 : acheteur null
        System.out.println("\nTest acheteur null :");
        System.out.println(etal.acheterProduit(10, null));

        // Test 3 : quantité invalide
        System.out.println("\nTest quantité invalide :");
        try {
            etal.acheterProduit(0, new Gaulois("Obélix", 25));
        } catch (IllegalArgumentException e) {
            System.err.println(e);
        }

        // Test 4 : achat sur un étal vide
        System.out.println("\nTest achat sur un étal vide :");
        Etal autreEtal = new Etal();

        try {
            autreEtal.acheterProduit(10, new Gaulois("Astérix", 8));
        } catch (IllegalStateException e) {
            System.err.println(e);
        }

        System.out.println("Fin du test");
    }
}
