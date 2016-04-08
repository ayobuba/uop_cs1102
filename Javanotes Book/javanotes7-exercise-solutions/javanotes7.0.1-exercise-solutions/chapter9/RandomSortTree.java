/**  
  * This program makes a random binary sort tree containing 1023 random
  * real numbers.  It then computes the height of the tree and the
  * average depth of the leaves of the tree.  Hopefully, the average
  * depth will tend to be close to 9, which is what it would be
  * if the tree were perfectly balanced.  The height of the tree,
  * which is the same as the maximum depth of any leaf, can be
  * significantly larger.
  */
public class RandomSortTree {


   static TreeNode root;   // Pointer to the binary sort tree.

   
   /**
    * An object of type TreeNode represents one node in a binary tree of real numbers.
    */
   static class TreeNode {
       double item;      // The data in this node.
       TreeNode left;    // Pointer to left subtree.
       TreeNode right;   // Pointer to right subtree.
       TreeNode(double x) {
              // Constructor.  Make a node containing x.
          item = x;
       }
   } // end class TreeNode


   /**
    * Add x to the binary sort tree to which the global variable "root" refers.
    */
   static void treeInsert(double x) {
      if ( root == null ) {
              // The tree is empty.  Set root to point to a new node 
              // containing the new item.
          root = new TreeNode( x );
          return;
       }
       TreeNode runner; // Runs down the tree to find a place for newItem.
       runner = root;   // Start at the root.
       while (true) {
          if ( x < runner.item ) {
                   // Since the new item is less than the item in runner,
                   // it belongs in the left subtree of runner.  If there
                   // is an open space at runner.left, add a node there.
                   // Otherwise, advance runner down one level to the left.
             if ( runner.left == null ) {
                runner.left = new TreeNode( x );
                return;  // New item has been added to the tree.
             }
             else
                runner = runner.left;
          }
          else {
                   // Since the new item is greater than or equal to the 
                   // item in runner, it belongs in the right subtree of
                   // runner.  If there is an open space at runner.right, 
                   // add a new node there.  Otherwise, advance runner
                   // down one level to the right.
             if ( runner.right == null ) {
                runner.right = new TreeNode( x );
                return;  // New item has been added to the tree.
             }
             else
                runner = runner.right;
           }
       } // end while
   }  // end treeInsert()


   /**
    * Return the number of leaves in the tree to which node points.
    */
   static int countLeaves(TreeNode node) {
       if (node == null)
          return 0;
       else if (node.left == null && node.right == null)
          return 1;  // Node is a leaf.
       else
          return countLeaves(node.left) + countLeaves(node.right);
   } // end countNodes()
   

   /**
    * When called as sumOfLeafDepths(root,0), this will compute the
    * sum of the depths of all the leaves in the tree to which root
    * points.  When called recursively, the depth parameter gives
    * the depth of the node, and the routine returns the sum of the
    * depths of the leaves in the subtree to which node points.
    * In each recursive call to this routine, depth goes up by one.
    */   
   static int sumOfLeafDepths( TreeNode node, int depth ) {
       if ( node == null ) {
             // Since the tree is empty and there are no leaves,
             // the sum is zero.
          return 0;
       }
       else if ( node.left == null && node.right == null) {
             // The node is a leaf, and there are no subtrees of node, so
             // the sum of the leaf depth is just the depths of this node.
          return depth;
       }
       else {
             // The node is not a leaf.  Return the sum of the
             // the depths of the leaves in the subtrees.
          return sumOfLeafDepths(node.left, depth + 1) 
                      + sumOfLeafDepths(node.right, depth + 1);
       }
   } // end sumOfLeafDepths()
   
   
   /**
    * When called as maximumLeafDepth(root,0), this will compute the
    * max of the depths of all the leaves in the tree to which root
    * points.  When called recursively, the depth parameter gives
    * the depth of the node, and the routine returns the max of the
    * depths of the leaves in the subtree to which node points.
    * In each recursive call to this routine, depth goes up by one.
    */
   static int maximumLeafDepth( TreeNode node, int depth ) {
       if ( node == null ) {
            // The tree is empty.  Return 0.
          return 0;
       }
       else if ( node.left == null && node.right == null) {
             // The node is a leaf, so the maximum depth in this
             // subtree is the depth of this node (the only leaf 
             // that it contains).
          return depth;
       }
       else {
             // Get the maximum depths for the two subtrees of this
             // node.  Return the larger of the two values, which
             // represents the maximum in the tree overall.
          int leftMax = maximumLeafDepth(node.left, depth + 1);
          int rightMax =  maximumLeafDepth(node.right, depth + 1);
          if (leftMax > rightMax)
             return leftMax;
          else
             return rightMax;
       }
   } // end maximumLeafDepth()
   
   
   /**
    * The main routine makes the random tree and prints the statistics.
    */
   public static void main(String[] args) {
         
      root = null;  // Start with an empty tree.  Root is a global
                    // variable, defined at the top of the class.
         
      // Insert 1023 random items.
         
      for (int i = 0; i < 1023; i++)
          treeInsert(Math.random()); 
          
      // Get the statistics.
          
      int leafCount = countLeaves(root);
      int depthSum = sumOfLeafDepths(root,0);
      int depthMax = maximumLeafDepth(root,0);
      double averageDepth = ((double)depthSum) / leafCount;
      
      // Display the results.
      
      System.out.println("Number of leaves:         " + leafCount);
      System.out.println("Average depth of leaves:  " + averageDepth);
      System.out.println("Maximum depth of leaves:  " + depthMax);

   }  // end main()


} // end class RandomSortTree
