package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		try {
			etalOccupe = false;
			StringBuilder chaine = new StringBuilder();
			chaine.append("Le vendeur " + vendeur.getNom() + " quitte son étal, ");

			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append("il a vendu " + produitVendu + " " + produit + " parmi les " + quantiteDebutMarche + " qu'il voulait vendre.\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
			return chaine.toString();
		} catch (NullPointerException e) {
			return new StringBuilder("Il n'y a pas de vendeur à cet étal.").toString();
		}
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite + " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) throws IllegalStateException {

		if (quantiteAcheter < 1) {
			throw new IllegalArgumentException("La quantité doit être supérieur à 0.");
		}
		
		if (!this.isEtalOccupe()) {
			throw new IllegalStateException("L'étal n'est pas occupé");
		}


		try {
			StringBuilder chaine = new StringBuilder();
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter + " " + produit + " à "
					+ this.vendeur.getNom());

			if (this.quantite == 0) {
			    chaine.append(", malheureusement il n'y en a plus !\n");
			    return chaine.toString();
			}
			
			if (quantiteAcheter > this.quantite) {
				chaine.append(", comme il n'y en a plus que " + this.quantite + ", " + acheteur.getNom()
						+ " vide l'étal de " + this.vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (this.quantite != 0) {
				this.quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom() + ", est ravi de tout trouver sur l'étal de "
						+ this.vendeur.getNom() + "\n");
			}
			return chaine.toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean contientProduit(String produit) {
		return produit != null && produit.equals(this.produit);
	}

}
