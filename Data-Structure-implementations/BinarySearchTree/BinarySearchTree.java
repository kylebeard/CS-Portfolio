
/**
 * Binary search tree implementation.
 * 
 * We do not allow duplicates.
 * 
 * @author Kyle Beard
 */
import java.util.Iterator;

import bridges.base.BSTElement;
import bridges.base.BinTreeElement;

public class BinarySearchTree<K extends Comparable<? super K>> implements SearchTreeInterface<K> {
	// the root of the binary search tree
	private BSTElement<K, String> root;

	private int numberOfElements = 0;

	/**
	 * Create an empty binary search tree
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * This method has nothing to do with a binary search tree, but is necessary to provide the
	 * bridges visualization.
	 */
	public BSTElement<K, String> getRoot() {
		return root;
	}

	public boolean isEmpty() {
		if (numberOfElements == 0)
			return true;
		else
			return false;
	}

	/**
	 * Solution that uses recursive helper method. We disallow duplicate elements in the tree.
	 */
	public K add(K key) {
		if (contains(key))
			return null;
		else {
			numberOfElements++;
			root = add(key, root);
			return key;
		}
	}

	/**
	 * private helper method for adding a node to the binary search tree
	 * 
	 * @param key
	 * @param subtree
	 * @return the root of the tree
	 */
	private BSTElement<K, String> add(K key, BSTElement<K, String> subtree) {
		if (subtree == null) {
			// we have found the spot for the addition

			// create the new node
			// parameters are (1) label (2) key (3) empty string [not used]
			BSTElement<K, String> newNode = new BSTElement<K, String>(key.toString(), key, "");

			// we also set the color of a new node to red
			newNode.getVisualizer().setColor("red");

			return newNode;
		}

		int direction = key.compareTo(subtree.getKey());

		if (direction < 0) {
			subtree.setLeft(add(key, subtree.getLeft()));
		} else if (direction > 0) {
			subtree.setRight(add(key, subtree.getRight()));
		}

		return subtree;
	}

	/**
	 * Non-recursive algorithm for addition This only serves the purpose of demonstrating the
	 * differences between the recursive and non-recursive approaches.
	 */
	/*
	 * public K add(K key) { // we disallow duplicates if (contains(key)) return null;
	 * 
	 * // create the new node // parameters are (1) label (2) key (3) null [not used] BSTElement<K,
	 * String> newNode = new BSTElement<K, String>(key.toString(), key, "");
	 * newNode.getVisualizer().setColor("red");
	 * 
	 * // if the tree is empty, set the root to the new node if (isEmpty()) { root = newNode; } else
	 * { // else treat it like an unsuccessful search BSTElement<K, String> node = root; boolean
	 * keepLooking = true;
	 * 
	 * while (keepLooking) { int direction = key.compareTo(node.getKey());
	 * 
	 * if (direction < 0) { // go left if (node.getLeft() == null) { // we found the place for the
	 * insert node.setLeft(newNode); keepLooking = false; } else node = node.getLeft(); } else if
	 * (direction > 0) { // go right if (node.getRight() == null) { // we found the place for the
	 * insert node.setRight(newNode); keepLooking = false; } else node = node.getRight(); } } }
	 * 
	 * return key; }
	 */

	public K getLargest() {
		if (root == null)
			return null;
		else
			return getLargestHelper(root);

	}

	public K getLargestHelper(BSTElement<K, String> node) {
		
		if (node.getRight() != null) {  // have not reached right most(largest) node												
			return getLargestHelper(node.getRight());
		} else {	
			node.getVisualizer().setColor("red"); // found largest node and return the data
			return node.getKey();
		}

	}

	public K getSmallest() {
		if (root == null)
			return null;
		else {
			return getSmallestHelper(root);
		}

	}

	public K getSmallestHelper(BSTElement<K, String> node) {
		
		if (node.getLeft() != null) { // have not reached the left most (smallest) node
			return getSmallestHelper(node.getLeft());
		} else {
			node.getVisualizer().setColor("yellow"); // found smallest node and return the value
			return node.getKey();
		}
	}

	public boolean contains(K key) {
		if (root == null) {
			return false;
		} else {
			return containsHelper(key, root);
		}
	}

	private boolean containsHelper(K key, BSTElement<K, String> node) {
		if (key == null)
			return false;
		else if (node == null)
			return false;
		else {
			int compareNum = key.compareTo(node.getKey());
			if (compareNum < 0) {
				return containsHelper(key, node.getLeft());
			} else if (compareNum > 0) {
				return containsHelper(key, node.getRight());
			} else {
				node.getVisualizer().setColor("pink");
				return true;
			}
		}
	}

	public K remove(K key) {
		if(contains(key)){ // only remove if the key is in the tree
		root = removeHelper(key, root);
		numberOfElements--;
	}
		return key;
	}

	@SuppressWarnings("unchecked")
	public BSTElement<K, String> removeHelper(K key, BSTElement<K, String> subtree) {
		BSTElement<K, String> right;
		BSTElement<K, String> left;
		BSTElement<K, String> successor;

		if (subtree == null) {
			return null;
		} else {
			int direction = key.compareTo(subtree.getKey());
			if (direction < 0) { // go left
				left = removeHelper(key, subtree.getLeft());

				subtree.setLeft(left);

			} else if (direction > 0) { // go right
				right = removeHelper(key, subtree.getRight());

				subtree.setRight(right);

				// 2 children
			} else if (subtree.getLeft() != null && subtree.getRight() != null) {
				successor = getLeftMostHelper(subtree.getRight());

				subtree.setKey((successor.getKey()));
				right = removeHelper(successor.getKey(), subtree.getRight());

				subtree.setRight(right);
				subtree.setLabel(successor.getKey() + "");
			} else { // 1 or 0 children
				if (subtree.getLeft() != null) {
					subtree = subtree.getLeft();
					
				} else {
					subtree = subtree.getRight();
				}
			}
			return subtree;
		}
	}
	

	/**
	 * Highlights the left most node to green and returns the value associated with the node.
	 * @return 
	 * 
	 * @return
	 */
	public  BSTElement<K, String> getLeftMostData() {
		if (root == null)
			return null;
		else
			return getLeftMostHelper(root);
	}

	/**
	 * Helper method to get the value and highlight the node to green.
	 * 
	 * @param node
	 * @return
	 */
	private BSTElement<K, String> getLeftMostHelper(BSTElement<K, String> node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		node.getVisualizer().setColor("green");
		//K data = node.getKey();
		return node;
	}
	

	public int size() {
		return numberOfElements;
	}

	public Iterator<K> iterator() {
		return new InOrderIterator();
	}

	@SuppressWarnings("unchecked")
	private class InOrderIterator implements Iterator<K> {
		private K[] elements;
		private int next;

		private InOrderIterator() {
			// create an array large enough to hold all elements in the tree
			elements = (K[]) new Object[size()];
			next = 0;

			// now perform an preorder traversal
			inOrder(root);

			// reset next back to the beginning of the array
			next = 0;
		}

		private void inOrder(BSTElement<K, String> node) {
			if (node != null) {
				inOrder(node.getLeft());
				elements[next++] = node.getKey();
				inOrder(node.getRight());
			}
		}

		public boolean hasNext() {
			return (next < size());
		}

		public K next() {
			return elements[next++];
		}
	}
}
