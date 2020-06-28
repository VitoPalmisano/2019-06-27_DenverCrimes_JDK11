package it.polito.tdp.crimes.model;

public class Adiacenza {

	private String a1;
	private String a2;
	private int peso;
	
	public Adiacenza(String a1, String a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}

	public String getA1() {
		return a1;
	}

	public String getA2() {
		return a2;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return a1 + ", "+a2 + ", peso=" + peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		result = prime * result + peso;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		if (peso != other.peso)
			return false;
		return true;
	}
	
	
}
