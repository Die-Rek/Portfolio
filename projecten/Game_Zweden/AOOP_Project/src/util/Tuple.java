package util;

import java.io.Serializable;

/**
 * Utility class for Storing immutable data pairs
 * 
 * @author micha
 *
 * @param <T>	contained type of the tuple
 */
public final class Tuple<T> implements Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7180684197001381157L;
	/**
	 * immutable value 1
	 */
	private final T val1;
	/**
	 * immutable value 2
	 */
	private final T val2;
	
	/**
	 * Constructor of class
	 * Creates the tuple with the passed parameter
	 * 
	 * @param v1 value 1
	 * @param v2 value 2
	 */
	public Tuple(T v1, T v2){
		this.val1 = v1;
		this.val2 = v2;
	}
	
	/**
	 * 
	 * @return the key value of the tuple
	 */
	public T getKey() {
		return val1;
	}
	
	/**
	 * 
	 * @return returns the value of the tuple
	 */
	public T getVal() {
		return val2;
	}
	
	/**
	 * Custom equality testing method to compare two Tuples
	 * it compares the values of the tuples.
	 * 
	 * @param o the object to compare this to
	 * @return true if the objects are equal otherwise false
	 */
	public boolean equal(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(getClass() != o.getClass())
			return false;
		
		@SuppressWarnings("unchecked")
		Tuple<T> other = (Tuple<T>)o;
		return this.val1 == other.val1 && this.val2 == other.val2;
	}
	
	/**
	 * cloning method for deep-copy
	 * 
	 * @return a clone of the tuple
	 */
	public Tuple<T> clone() {
		try {
			@SuppressWarnings("unchecked")
			Tuple<T> cloned = (Tuple<T>)super.clone();
			return cloned;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
