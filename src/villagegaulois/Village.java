package villagegaulois;

import histoire.VillageSansChefException;
import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		this.villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

//	-------------------
//	Debut classe interne
//	-------------------

	private static class Marche {

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
			for (int i = 0, j = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbProduitEtal[j] = etals[i];
					j++;
				}
			}

			return nbProduitEtal;
		}

		Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				Gaulois vendeur = etals[i].getVendeur();
				if (etals[i].isEtalOccupe() && vendeur != null && vendeur.equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}

		public String afficherMarche() {

			StringBuilder chaine = new StringBuilder();

			int nbEtalVide = 0;

			for (int i = 0; i < etals.length; i++) {

				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					nbEtalVide++;
				}
			}

			if (nbEtalVide > 0) {
				chaine.append("Il reste ");
				chaine.append(nbEtalVide);
				chaine.append(" étals non utilisés dans le marché.\n");
			}

			return chaine.toString();
		}
	}

	// -------------------
	// Fin classe interne
	// -------------------

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

	public String afficherVillageois() throws VillageSansChefException {

		if (chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef.");
		}

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

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {

		StringBuilder chaine = new StringBuilder();

		chaine.append(vendeur.getNom());
		chaine.append(" cherche un endroit pour vendre ");
		chaine.append(nbProduit);
		chaine.append(" ");
		chaine.append(produit);
		chaine.append(".\n");

		int indiceEtal = marche.trouverEtalLibre();

		if (indiceEtal == -1) {
			chaine.append("Il n'y a plus d'étal disponible dans le marché.\n");
		} else {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);

			chaine.append("Le vendeur ");
			chaine.append(vendeur.getNom());
			chaine.append(" vend des ");
			chaine.append(produit);
			chaine.append(" à l'étal n°");
			chaine.append(indiceEtal + 1);
			chaine.append(".\n");
		}

		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {

		StringBuilder chaine = new StringBuilder();

		Etal[] etalsProduit = marche.trouverEtals(produit);

		if (etalsProduit.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\n");
		} else if (etalsProduit.length == 1) {
			chaine.append("Seul le vendeur " + etalsProduit[0].getVendeur().getNom() + " propose des " + produit
					+ " au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");

			for (int i = 0; i < etalsProduit.length; i++) {
				chaine.append("- " + etalsProduit[i].getVendeur().getNom() + "\n");
			}
		}

		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {

		Etal etal = rechercherEtal(vendeur);

		return etal.libererEtal();
	}

	public String afficherMarche() {

		return marche.afficherMarche();
	}

}