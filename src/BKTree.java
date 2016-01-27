/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aiproject;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * @author Mike
 */
public class BKTree<E> {

	private Node root;
	 Distance distance;

	public BKTree(Distance distance) {
		root = null;
		this.distance = distance;
	}

	public void add(E term) {
		if (root != null) {
			root.add(term);
		} else {
			root = new Node(term);
		}
	}

	public Map<E, Integer> query(E searchObject, int threshold) {
		Map<E, Integer> matches = new HashMap<E, Integer>();
		boolean ttt = root.query(searchObject, threshold, matches, false);
                if(ttt == true)matches.clear();
		return matches;
	}

	private class Node {

		E term;
		Map<Integer, Node> children;

		public Node(E term) {
			this.term = term;
			children = new TreeMap<>();
		}

		public void add(E termm) {
			int score = distance.getDistance((String)termm,(String)this.term);
			Node child = children.get(score);
			if (child != null) {
				child.add(termm);
			} else {
				children.put(score, new Node(termm));
			}
		}

		public boolean query(E termm, int threshold, Map<E, Integer> collected ,boolean rrr) {
                   
                   boolean found = rrr;
                    
                    
			int distanceAtNode = distance.getDistance((String) termm, (String) this.term);
                        
                        
			if (distanceAtNode <= threshold) {
                            String firt = (String)term;
                            String second = (String)termm;
                            
                            if(firt.charAt(0)==second.charAt(0)){
                                if(firt.equals(second)){
                                    
                                    found = true;
                                    return found;
                                   
                                   
                               }
				collected.put(this.term, distanceAtNode);
                            }
                               }
			
                        
                            
                                
                        
			for (int score = distanceAtNode - threshold; score <= distanceAtNode + threshold; score++) {
				 if (score > 0) {
					Node child = children.get(score);
					if (child != null) {
						child.query(termm, threshold, collected, rrr); 
                                                if(child.query(termm, threshold, collected, rrr))return true;
					}
				}
			}
                                
                         
                        
               return found;
                }

        }}