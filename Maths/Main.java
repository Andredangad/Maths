public class Main {

	/**
	 * On effectue des tests sur de petites matrices (3 x 3 ou 4 x 4).
	 */
	private static void smallTests() {
		Matrice A = new Matrice(new long[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 13 } });
		Matrice B = A.times(A.transpose());
		Matrice C = A.transpose().times(A);
		Matrice D = new Matrice(new long[][] { { 3 }, { 1 }, { 4 } });
		Matrice E = new Matrice(new long[][] { { 21 }, { 57 }, { 94 } });
		Matrice F = new Matrice(new long[][] { { 3 }, { 1 }, { 4 }, { 1 } });
		Matrice G = new Matrice(new long[][] { { 576 }, { 662 }, { 748 }, { 881 } });
		Matrice H = new Matrice(new long[][] { { 76, -25,-50,0,0,0}, { -25,56,-1,-30,0,0 }, { -50,-1,106,-55,0,0 }, { 0,-30,-55,160,-25,-50 }, { 0,0,0,-25,56,-1 },
		 { 0,0,0,-50,-1,106 } });
		Matrice I = new Matrice(new long[][] { { 10 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 } });

		System.out.println("\n\n--- Tests pour des matrices et vecteurs fixes, de petite taille ---\n\n");

		Matrice.assertInvertible(B, true);
	
		Matrice.assertInvertible(C, false);
		
		Matrice.assertSolution(A, D, true);
		Matrice.assertSolution(A, E, true);
		Matrice.assertSolution(B, D, true);
		Matrice.assertSolution(B, E, true);
		Matrice.assertSolution(C, F, false);
		Matrice.assertSolution(C, G, true);

		System.out.println("Tests réussis !");
	}

	/**
	 * On effectue des tests sur de petites matrices (de tailles 20, 40, 60, 80 et
	 * 100).
	 */
	private static void largeTests() {
		for (int i = 1; i <= 5; i++) {
			Matrice.largeTest(20 * i);
		}
	}

	public static void main(String[] args) {
		// On pourra lancer ces tests une fois toutes les fonctions codées.
		smallTests();
		// largeTests();
	}

}
