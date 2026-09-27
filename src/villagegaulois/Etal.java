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
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder();
		
		try {
			chaine.append("Le vendeur " + vendeur.getNom() + " quitte son étal, ");
		} 
		catch (NullPointerException e) {
			return new StringBuilder("Il n'y a pas de vendeur à cet étal.").toString();
		}
		
		int produitVendu = quantiteDebutMarche - quantite;
		if (produitVendu > 0) {
			chaine.append(
					"il a vendu " + produitVendu + " parmi " + produit + ".\n");
		} else {
			chaine.append("il n'a malheureusement rien vendu.\n");
		}
		return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) throws IllegalStateException {
		
		if(!this.isEtalOccupe()) {
			throw new IllegalStateException("L'étal n'est pas occupé");
		}
		
		
		if(quantiteAcheter < 1) {
			throw new IllegalArgumentException("La quantité doit être supérieur à 0.");
		}
		
		StringBuilder chaine = new StringBuilder();
		try {
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
					+ " " + produit + " à " + this.vendeur.getNom());
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "";
		}
		
		if (this.quantite == 0) {
			chaine.append(", malheureusement il n'y en a plus !");
			quantiteAcheter = 0;
		}
		if (quantiteAcheter > this.quantite) {
			chaine.append(", comme il n'y en a plus que " + this.quantite + ", "
					+ acheteur.getNom() + " vide l'étal de "
					+ this.vendeur.getNom() + ".\n");
			quantiteAcheter = quantite;
			quantite = 0;
		}
		if (this.quantite != 0) {
			this.quantite -= quantiteAcheter;
			chaine.append(". " + acheteur.getNom()
					+ ", est ravi de tout trouver sur l'étal de "
					+ this.vendeur.getNom() + "\n");
		}
		return chaine.toString();
	}

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}
