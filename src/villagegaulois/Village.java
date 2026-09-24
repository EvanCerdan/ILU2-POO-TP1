package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		this.villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public class Marche {

		private Etal[] etals;

		public Marche(int nbEtals) {
			etals = new Etal[nbEtals]; // On crée un tableau qui contient que des Etals

			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal(); // Dans chaque case de ce tableau, on crée un Etal (via son constructeur)
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		public int trouverEtalLibre() {

			for (int i = 0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe())) {
					return i;
				}
			}

			return -1;
		}

		Etal[] trouverEtals(String produit) {

			int nbEtalProduit = 0;

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalProduit++;
				}
			}

			Etal[] nbProduitEtal = new Etal[nbEtalProduit];

			for (int j = 0; j < etals.length; j++) {
				if (etals[j].contientProduit(produit)) {
					nbProduitEtal[j] = etals[j];
				}
				j++;
			}
			return nbProduitEtal;
		}

		Etal trouverVendeur(Gaulois gaulois) {

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}

			return null;

		}

		public String afficherMarche() {

			String chaine = ""; // init de la chaîne

			int nbEtalVide = 0;

			for (int i = 0; i < etals.length; i++) { // pour toutes les étals

				if (etals[i].isEtalOccupe()) { // occupé ?

					chaine += etals[i].afficherEtal(); // si oui, on l'ajoute dans la chaîne

				} else {

					nbEtalVide++; // sinon, on incrémente nbEtalVide
				}
			}

			if (nbEtalVide > 0) { // s'il y'a au moins une vide, dernier affichage

				chaine += "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
			}

			return chaine;
		}
	}
}