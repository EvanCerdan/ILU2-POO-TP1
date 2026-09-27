package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		try {
			etal.acheterProduit(10, new Gaulois(null, 0));
		} catch (IllegalStateException | IllegalArgumentException e) {
			System.err.println(e);
		}
		System.out.println("Fin du test");
		
	}
}
