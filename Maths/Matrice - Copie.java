import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public final class Matrice implements Cloneable {

	private final int n; /* nombre de lignes */
	private final int m; /* nombre de colonnes */
	private final Rational[][] coeff; /* liste des coefficients */

	/**
	 * Création d'une matrice
	 * 
	 * @param coeff coefficients de la matrice
	 */
	public Matrice(Rational[][] coeff) {
		Objects.requireNonNull(coeff);
		n = coeff.length;
		m = coeff[0].length;
		this.coeff = coeff;
		for (int i = 0; i < n; i++) {
			if (m != coeff[i].length) {
				throw new IllegalArgumentException("Dimensions incorrectes");
			}
		}
	}

	/**
	 * Création d'une matrice
	 * 
	 * @param coeff coefficients de la matrice, donnés comme long
	 */
	public Matrice(long[][] coeff) {
		Objects.requireNonNull(coeff);
		n = coeff.length;
		m = coeff[0].length;
		for (int i = 0; i < n; i++) {
			if (m != coeff[i].length) {
				throw new IllegalArgumentException("Dimensions incorrectes");
			}
		}
		this.coeff = new Rational[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.coeff[i][j] = new Rational(coeff[i][j]);
			}
		}
	}

	/**
	 * Calcul de la somme matricielle this + M (si les dimensions de this et M
	 * l'autorisent)
	 * 
	 * @param M matrice à ajouter : tableau n x m
	 * @return somme this + M : tableau n x m
	 */
	public Matrice plus(Matrice M) {
		if (n != M.n || m != M.m) {
			throw new IllegalArgumentException("Dimensions incorrectes");
		}

		Rational[][] sum = new Rational[n][m];
		for(int i=0;i<n;i++)
			for(int j=0;j<m;j++)
				sum[i][j] = this.coeff[i][j].plus(M.coeff[i][j]);
		return new Matrice(sum);
	}

	/**
	 * Calcul du produit matriciel this M (si les dimensions de this et M
	 * l'autorisent)
	 * 
	 * @param M matrice à multiplier : tableau m x p
	 * @return produit this M : tableau n x p
	 */
	public Matrice times(Matrice M) {
		if (m != M.n) {
			throw new IllegalArgumentException("Dimensions incorrectes");
		}
		int p = M.m;

		Rational[][] prod = new Rational[n][p];
		for(int i=0;i<n;i++)
			for(int j=0;j<p;j++) {
					prod[i][j] = new Rational(0);
				for(int k=0;k<M.n;k++)
					prod[i][j] = prod[i][j].plus(this.coeff[i][k].times(M.coeff[k][j]));
			}		
		return new Matrice(prod);
	}

	/**
	 * Calcul de la transposée de this
	 * 
	 * @return transposée de this : tableau m x n
	 */
	public Matrice transpose() {
		Rational[][] trans = new Rational[m][n];
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
				trans[i][j] = this.coeff[j][i];
		return new Matrice(trans);
	}

	/**
	 * échange les lignes i et j de la matrice
	 * 
	 * @param i première ligne à échanger
	 * @param j deuxième ligne à échanger
	 */
	private void swapRows(int i, int j) {
		Rational temp;
		for(int k=0;k<m;k++) {
			temp = this.coeff[i][k];
			this.coeff[i][k] = this.coeff[j][k];
			this.coeff[j][k] = temp;
		}
	}

	/**
	 * Ajoute a fois la ligne i de this à sa ligne j
	 * 
	 * @param i ligne à ajouter (multiplée par a)
	 * @param j ligne à laquelle on ajoute a fois la ligne j
	 * @param a scalaire par lequel on multiplie la ligne i quand on l'ajoute
	 */
	private void transvection(int i, int j, Rational a) {
		for(int k=0;k<m;k++)
			this.coeff[j][k] = this.coeff[j][k].plus(this.coeff[i][k].times(a));
	}

	/**
	 * Mutiplie par a la ligne i de this
	 * 
	 * @param i ligne à multiplier par a
	 * @param a scalaire par lequel on multiplie la ligne i
	 */
	private void multiplyRow(int i, Rational a) {
		for(int j=0;j<m;j++)
			this.coeff[i][j] = this.coeff[i][j].times(a);
	}

	/**
	 * Calcul d'une matrice identité de taille x n x
	 * 
	 * @param n taille de la matrice
	 * 
	 * @return matrice identité : tableau n x n
	 */
	public static Matrice identity(int n) {
		Rational[][] id = new Rational[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				if(i == j)
					id[i][j] = new Rational(1);
				else 
					id[i][j] = new Rational(0);
		return new Matrice(id);
	}

	/**
	 * Calcul de la matrice identité de mêmes dimensions que this (si les dimensions
	 * de this l'autorisent)
	 * 
	 * @return matrice identitÃ© : tableau n x n
	 */
	public Matrice identity() {
		if (m != n) {
			throw new IllegalArgumentException("Dimensions incorrectes");
		}
		return identity(n);
	}

	/**
	 * Calcul d'une copie de this
	 * 
	 * @return copie de this : tableau n x m
	 */
	public Matrice clone() {
		Rational[][] clone = new Rational[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				clone[i][j] = coeff[i][j];
			}
		}
		return new Matrice(clone);
	}

	/**
	 * Calcul de l'inverse de this
	 * 
	 * @return inverse de this : tableau n x n
	 */
	public Matrice inverse() {
		if (m != n) {
			throw new IllegalArgumentException("Dimensions incorrectes");
		}
		Matrice clone = clone();
		Matrice id = identity();
		/**
		 * Remplir ici le code manquant. On suggère très fortement d'utiliser
		 * l'algorithme du pivot de Gauss.
		 * 
		 * S'il s'avère que la matrice this n'a pas d'inverse : throw new
		 * ArithmeticException("Division par zéro");
		 **/
		int i,j,k;
		/* Descente du pivot */
		for(i=0;i<clone.n;i++){ 
		    if(clone.coeff[i][i].equals(new Rational(0))){
				k=i;
				while(clone.coeff[k][i].equals(new Rational(0))){
					
			
					
					k++;
				
			
				if(k==clone.n) 
					throw new ArithmeticException("Division par zéro");
				
				else { 
				   	clone.swapRows(i,k);
				    id.swapRows(i,k);
				}
				}
		    }
		    id.multiplyRow(i, clone.coeff[i][i].inverse());
		    clone.multiplyRow(i, clone.coeff[i][i].inverse());
		  	    
		    for (j=i+1;j<clone.n;j++){
				if (!clone.coeff[j][i].equals(new Rational(0))){
				    id.multiplyRow(j, new Rational(-1).times(clone.coeff[j][i].inverse()));
				    clone.multiplyRow(j, new Rational(-1).times(clone.coeff[j][i].inverse()));	

				    id.transvection(i,j,new Rational(1));
				    clone.transvection(i, j, new Rational(1));
				}
		    }
		}
		/* Remontée du pivot de Gauss */
		for(i=clone.n -1;i>=0;i--){
		    id.multiplyRow(i, clone.coeff[i][i].inverse());
		    clone.multiplyRow(i, clone.coeff[i][i].inverse());
		    for (j=i-1;j>=0;j--){
				if (!clone.coeff[j][i].equals(new Rational(0))){					
				    id.multiplyRow(j, new Rational(-1).times(clone.coeff[j][i].inverse()));
				    clone.multiplyRow(j, new Rational(-1).times(clone.coeff[j][i].inverse()));
				    id.transvection(i,j,new Rational(1));
				    clone.transvection(i, j, new Rational(1));
				}
		    }	       
		}
		return id;
	}

	/**
	 * Résoud une équation linéaire matricielle
	 * 
	 * @param this est une matrice n x m
	 * @param b    vecteur n x 1 que l'on veut obtenir
	 * 
	 * @return vecteur colonne a tel que this * a = b : tableau n x 1
	 n = ligne
	 m = colonne
	 tester si carré, 
	 si carré : inverser this et faire this * b pour trouver a
	 sinon pivot de gauss et regarder ce qui a à droite du pivot
	 */
	public Matrice solve(Matrice b) {
		if (n != b.n || b.m != 1) {
			throw new IllegalArgumentException("Dimensions incorrectes");
		}
		Matrice clone = clone();
		Matrice B = b.clone();
		Rational[][] a = new Rational[m][1];
/* 		if(clone.n == clone.m){
			
			clone = clone.inverse();
			 
			clone = clone.times(B);
		
			a = clone.coeff; 
			
			
		}
			
		else{ */
		int i,j,k;
		/* Descente du pivot */
		
		for(i=0;i<clone.n;i++){ 

			/* Supprimer la colonne */
			if(i==clone.n-1) {
                    Rational[][] tempMat = new Rational[clone.n][clone.m-1];
                    for(int f=0;f<clone.n;f++)
                        for(int g=0;g<clone.m-1;g++) {
                            if(g == i){
								tempMat[f][g] = clone.coeff[f][g+1];
								

							}
                                
                            else
                                tempMat[f][g] = clone.coeff[f][g];
                        }
                    clone = new Matrice(tempMat);
					a[i][0] = new Rational(0);
                }

	
		    if(clone.coeff[i][i].equals(new Rational(0))){
				k=i;
				while(clone.coeff[k][i].equals(new Rational(0))){
				
					k++;
				

	
				if(k==clone.n) {
					if(B.coeff[k-1][0].equals(new Rational(0))){
					Rational[][] temp = new Rational[clone.n-1][clone.m];
                    for(int f=0;f<clone.n-1;f++)
                        for(int g=0;g<clone.m;g++) {
                            if(g == i){
								temp[f][g] = clone.coeff[f][g];
								

							}
                                
                            else
                                temp[f][g] = clone.coeff[f][g];
                        }
                    clone = new Matrice(temp);
					System.out.println(clone);
					k=0;
					
					}
								if(i==clone.n-1) {
                    Rational[][] tempMat = new Rational[clone.n][clone.m-1];
                    for(int f=0;f<clone.n;f++)
                        for(int g=0;g<clone.m-1;g++) {
                            if(g == i){
								tempMat[f][g] = clone.coeff[f][g+1];
								

							}
                                
                            else
                                tempMat[f][g] = clone.coeff[f][g];
                        }
                    clone = new Matrice(tempMat);
					a[i][0] = new Rational(0);
                }

					else{
						throw new ArithmeticException("Division par zéro");
					}

					

				}
					
					
				else { 
				   	clone.swapRows(i,k);
				    B.swapRows(i,k);
				}
				}
		    }


		    B.multiplyRow(i, clone.coeff[i][i].inverse());
		    clone.multiplyRow(i, clone.coeff[i][i].inverse());
		  	    
		    for (j=i+1;j<clone.n;j++){
				if (!clone.coeff[j][i].equals(new Rational(0))){
				    B.multiplyRow(j, new Rational(-1).times(clone.coeff[j][i].inverse()));
				    clone.multiplyRow(j, new Rational(-1).times(clone.coeff[j][i].inverse()));	

				    B.transvection(i,j,new Rational(1));
				    clone.transvection(i, j, new Rational(1));
				}
		    }
		}
		/* Remontée du pivot de Gauss */
		for(i=clone.n -1;i>=0;i--){
		    B.multiplyRow(i, clone.coeff[i][i].inverse());
		    clone.multiplyRow(i, clone.coeff[i][i].inverse());
		    for (j=i-1;j>=0;j--){
				if (!clone.coeff[j][i].equals(new Rational(0))){					
				    B.multiplyRow(j, new Rational(-1).times(clone.coeff[j][i].inverse()));
				    clone.multiplyRow(j, new Rational(-1).times(clone.coeff[j][i].inverse()));
				    B.transvection(i,j,new Rational(1));
				    clone.transvection(i, j, new Rational(1));
				}
		    }	       
		}
		
			/* solutions */

			int f = 0;
			int g = 0;
			while(f<m){
				if(a[f][0]==null){
					a[f][0] = B.coeff[g][0];
					f++;
					g++;
				}
				else{
					f++;
				}

			}

			
		// }

		/**
		 * Remplir ici le code manquant.
		 * 
		 * S'il s'avère que l'équation n'a pas de solution : throw new
		 * ArithmeticException("Pas de solution"); Si elle a plusieurs solutions : on
		 * peut renvoyer n'importe quelle solution.
		 **/
		
		return new Matrice(a);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Matrice) {
			Matrice a = (Matrice) o;
			return n == a.n && m == a.m && Arrays.deepEquals(coeff, a.coeff);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return n + 37 * m + 65537 * coeff.hashCode();
	}

	@Override
	public String toString() {
		return Arrays.deepToString(coeff);
	}

	/**
	 * Fonctions utiles pour faire des tests une fois que vous aurez tout codé. Ne
	 * pas modifier cette partie. Vous n'aurez pas besoin de comprendre comment elle
	 * fonctionne.
	 */

	static void assertInvertible(Matrice A, boolean b) {
		try {
			if (!A.times(A.inverse()).equals(A.identity())) {
				throw new RuntimeException("Matrix inversion failed!");
			}
		} catch (ArithmeticException e) {
			if (b) {
				throw new RuntimeException("Matrix inversion failed!");
			}
		}
	}

	static void assertSolution(Matrice A, Matrice B, boolean b) {
		try {
			if (!A.times(A.solve(B)).equals(B)) {
				throw new RuntimeException("System resolution failed!");
			}
		} catch (ArithmeticException e) {
			if (b) {
				throw new RuntimeException("Matrix inversion failed!");
			}
		}
	}

	static void largeTest(int size) {
		System.out.println("\n\n--- Tests pour des matrices et vecteurs aléatoires, de taille " + size + " ---\n\n");
		int swap = (int) Math.sqrt(size);
		Matrice A = randomUnitMatrix(size, size * swap);
		Matrice B = randomNearUnitMatrix(size, size * swap);
		Matrice C = randomSingularMatrix(size, size * swap);
		Matrice D = randomVector(size);
		Matrice E = randomImageVector(C);

		long time = System.currentTimeMillis();

		assertInvertible(A, true);
		assertInvertible(B, true);
		assertInvertible(C, false);

		long time1 = System.currentTimeMillis();

		assertSolution(A, D, true);
		assertSolution(A, E, true);
		assertSolution(B, D, true);
		assertSolution(B, E, true);
		assertSolution(C, D, false);
		assertSolution(C, E, true);

		long time2 = System.currentTimeMillis();

		System.out.println("Tests réussis en " + (time1 - time) / 3 + " millisecondes/inversion et "
				+ (time2 - time1) / 6 + " millisecondes/résolution !");

	}

	private static Matrice randomUnitMatrix(int size, int swaps) {
		Random r = new Random();
		Rational R = Rational.ONE.minus();
		Matrice m = Matrice.identity(size);
		for (int i = 0; i < swaps; i++) {
			int a = r.nextInt(size);
			int b = r.nextInt(size);
			if (a != b) {
				m.transvection(a, b, R);
			}
		}
		return m;
	}

	private static Matrice randomNearUnitMatrix(int size, int swaps) {
		Random r = new Random();
		Rational R = Rational.ONE.minus();
		Matrice m = Matrice.identity(size);
		for (int i = 0; i < swaps; i++) {
			int a = r.nextInt(size);
			int b = r.nextInt(size);
			if (a != b) {
				m.transvection(a, b, R);
			}
		}
		for (int i = 0; i < swaps; i++) {
			int a = r.nextInt(size);
			int b = r.nextInt(size);
			if (r.nextBoolean()) {
				m.coeff[a][b] = m.coeff[a][b].plus(Rational.ONE);
			} else {
				m.coeff[a][b] = m.coeff[a][b].minus(Rational.ONE);
			}
		}
		return m;
	}

	private static Matrice randomSingularMatrix(int size, int swaps) {
		Random r = new Random();
		Rational R = Rational.ONE.minus();
		Matrice m = Matrice.identity(size);
		int j = r.nextInt(size);
		m.coeff[j][j] = Rational.ZERO;
		for (int i = 0; i < swaps; i++) {
			int a = r.nextInt(size);
			int b = r.nextInt(size);
			if (a != b) {
				m.transvection(a, b, R);
			}
		}
		return m;
	}

	private static Matrice randomVector(int size) {
		Random r = new Random();
		Rational[][] tab = new Rational[size][1];
		for (int i = 0; i < size; i++) {
			tab[i][0] = new Rational(r.nextInt(size));
		}
		return new Matrice(tab);
	}

	private static Matrice randomImageVector(Matrice m) {
		return m.times(randomVector(m.m));
	}
}
