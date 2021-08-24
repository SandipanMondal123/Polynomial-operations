package poly;

import java.io.IOException;
import java.util.Scanner;
//SANDIPAN MONDAL

public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		Node norm = null;
		Node one = poly1;
		Node two = poly2;
		
		

		while(!(one == null || two == null))
		{
			
			if(one.term.degree == two.term.degree)
			{
				if((one.term.coeff + two.term.coeff)!= 0)
					if(norm == null)
						norm = new Node(one.term.coeff + two.term.coeff, one.term.degree, null);
					else
						norm = nonEmptyListAddition(norm, one.term.coeff + two.term.coeff, one.term.degree);
				one = one.next;
				two = two.next;
			} else if(one.term.degree > two.term.degree)
			{
				if(norm == null)
					norm = new Node(two.term.coeff, two.term.degree, null);
				else
					norm = nonEmptyListAddition(norm, two.term.coeff, two.term.degree);
				two = two.next;
			} else
			{
				if(norm == null)
					norm = new Node(one.term.coeff, one.term.degree, null);
				else
					norm = nonEmptyListAddition(norm, one.term.coeff, one.term.degree);
				one = one.next;
			} 
		}
		
		if(one == null && two == null) return norm;
		
		if(one== null)
		{
			while(two != null) {
				if(norm == null)
					norm = new Node(two.term.coeff,two.term.degree,null);
				else
					norm = nonEmptyListAddition(norm,two.term.coeff,two.term.degree);
				two=two.next;
			}
		}
		else
		{
			while(one != null) 
			{
				if(norm == null)
					norm = new Node(one.term.coeff,one.term.degree,null);
				else
					norm = nonEmptyListAddition(norm,one.term.coeff,one.term.degree);
				one=one.next;
			}
		}
		return norm;
		
	}
	
	private static Node nonEmptyListAddition(Node node, float sum, int degree)
	{
		Node holder = node;
		while(holder.next!=null)
			holder = holder.next;
		holder.next = new Node(sum,degree,null);
		return node;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node total = null;
		if(poly1==null || poly2 == null) return total;
		for(Node two = poly2; two!=null; two=two.next)
		{	
			Node one = poly1;
			Node product = null;
			while(one!=null)
			{
				if(product == null)
					product = new Node(one.term.coeff*two.term.coeff,one.term.degree + two.term.degree,null);
				else
					product = nonEmptyListAddition(product, one.term.coeff*two.term.coeff,one.term.degree + two.term.degree);
				one=one.next;
			}
			
			total = add(product, total);
			
		}
		
		return total;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		float sum=0;
		for(Node holder = poly; holder!=null; holder=holder.next)
		{
			sum += holder.term.coeff*(float)(Math.pow(x, holder.term.degree));
		}
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
