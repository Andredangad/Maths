import java.math.BigInteger;

public class Rational {
	private final BigInteger p; /* numérateur */
	private final BigInteger q; /* dénominateur */
	public final static Rational ZERO = new Rational(0);
	public final static Rational ONE = new Rational(1);

	/**
	 * Création du rationnel a/b (sous forme irréductible)
	 * 
	 * @param a numérateur
	 * @param b dénominateur
	 */
	private Rational(BigInteger a, BigInteger b) {
		BigInteger d = a.gcd(b);
		if (b.signum() == d.signum()) {
			p = a.divide(d);
			q = b.divide(d);
		} else {
			p = a.divide(d).negate();
			q = b.divide(d).negate();
		}
	}

	/**
	 * Création du rationnel n
	 * 
	 * @param n numérateur
	 */
	public Rational(long n) {
		this(BigInteger.valueOf(n), BigInteger.ONE);
	}

	@Override
	public String toString() {
		return p + (q.equals(BigInteger.ONE) ? "" : "/" + q);
	}

	/**
	 * Calcul du rationnel this + r
	 * 
	 * @param r rationnel à ajouter
	 * @return somme this + r
	 */
	public Rational plus(Rational r) {
		return new Rational(p.multiply(r.q).add(q.multiply(r.p)), q.multiply(r.q));
	}

	/**
	 * Calcul du rationnel - r
	 * 
	 * @return opposé -this
	 */
	public Rational minus() {
		return new Rational(p.negate(), q);
	}

	/**
	 * Calcul du rationnel this - r
	 * 
	 * @param r rationnel à soustraire
	 * @return somme this - r
	 */
	public Rational minus(Rational r) {
		return plus(r.minus());
	}

	/**
	 * Calcul du rationnel this * r
	 * 
	 * @param r rationnel à multiplier
	 * @return somme this * r
	 */
	public Rational times(Rational r) {
		return new Rational(p.multiply(r.p), q.multiply(r.q));
	}

	/**
	 * Calcul du rationnel 1 / this (si this est non nul)
	 * 
	 * @return inverse 1 / this
	 */
	public Rational inverse() {
		if (p.equals(BigInteger.ZERO)) {
			throw new ArithmeticException("Division par zéro");
		}
		return new Rational(q, p);
	}

	/**
	 * Calcul du rationnel this / r (si r est non nul)
	 * 
	 * @param r rationnel par lequel on divise this
	 * @return rationnel this / r
	 */
	public Rational divide(Rational r) {
		return times(r.inverse());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Rational) {
			Rational r = (Rational) o;
			return p.equals(r.p) && q.equals(r.q);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 7373 * p.hashCode() + 65537 * q.hashCode();
	}

}
