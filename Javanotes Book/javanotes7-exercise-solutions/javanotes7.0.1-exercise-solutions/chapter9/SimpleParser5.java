/*
    This program reads standard expressions typed in by the user. 
    The program constructs an expression tree to represent the
    expression.  It computes the derivative of the expression and
    prints out the derivative and the value of the derivative at
    several values of x.  It also prints out a list of commands 
    that could be used on a stack machine to evaluate the derivative.
    The expressions can use the variable "x", positive real numbers, and
    the binary operators +, -, *, and /.  The unary minus operation
    is supported.  The expressions are defined by the BNF rules:

            <expression>  ::=  [ "-" ] <term> [ [ "+" | "-" ] <term> ]...

            <term>  ::=  <factor> [ [ "*" | "/" ] <factor> ]...

            <factor>  ::=  <number>  |  <x-variable> | "(" <expression> ")"

    A number must begin with a digit (i.e., not a decimal point).
    A line of input must contain exactly one such expression.  If extra
    data is found on a line after an expression has been read, it is
    considered an error.

    In addition to the main program class, SimpleParser5, this program
    defines a set of five nested classes for implementing expression trees.

 */

public class SimpleParser5 {

//   -------------------- Nested classes for Expression Trees ------------------------------


   /**
    *  An abstract class representing any node in an expression tree.
    *  The four concrete node classes are concrete subclasses.
    *  Two instance methods are specified, so that they can be used with
    *  any ExpNode.  The value() method returns the value of the
    *  expression for a specified value of the variable, x.  
    *  The printStackCommands() method prints a list
    *  of commands that could be used to evaluate the expression on
    *  a stack machine (assuming that the value of the expression is
    *  to be left on the stack).
    *  The derivative() method returns an expression tree for the derivative 
    *  of the expression (with no attempt at simplification).  Actually,
    *  this might not be a tree, but it is a "directed acyclic graph",
    *  with no loops, so it's OK for our purposes.  The printInfix()
    *  method prints the expression in fully parenthesized form.
    */
   abstract private static class ExpNode {
      abstract double value(double xValue); 
      abstract void printStackCommands();
      abstract void printInfix();       
      abstract ExpNode derivative();
   }

   /**
    * Represents an expression node that holds a number.
    */
   private static class ConstNode extends ExpNode {
      double number;  // The number.
      ConstNode(double val) {
             // Construct a ConstNode containing the specified number.
         number = val;
      }
      double value(double xValue) {
             // The value of the node is the number that it contains.
         return number;
      }
      void printStackCommands() {
             // On a stack machine, just push the number onto the stack.
         System.out.println("  Push " + number); 
      }
      void printInfix() {
         System.out.print(number);
      }
      ExpNode derivative() {
             // The derivative of a constant is zero.
         return new ConstNode(0);
      }
   }

   
   /**
    * An expression node representing a binary operator,
    */
   private static class BinOpNode extends ExpNode {
      char op;        // The operator.
      ExpNode left;   // The expression for its left operand.
      ExpNode right;  // The expression for its right operand.
      BinOpNode(char op, ExpNode left, ExpNode right) {
             // Construct a BinOpNode containing the specified data.
         assert op == '+' || op == '-' || op == '*' || op == '/';
         assert left != null && right != null;
         this.op = op;
         this.left = left;
         this.right = right;
      }
      double value(double xValue) {
             // The value is obtained by evaluating the left and right
             // operands and combining the values with the operator.
         double x = left.value(xValue);
         double y = right.value(xValue);
         switch (op) {
         case '+':  return x + y;
         case '-':  return x - y;
         case '*':  return x * y;
         case '/':  return x / y;
         default:   return Double.NaN;  // Bad operator!
         }
      }
      void  printStackCommands() {
             // To evaluate the expression on a stack machine, first do
             // whatever is necessary to evaluate the left operand, leaving
             // the answer on the stack.  Then do the same thing for the
             // second operand.  Then apply the operator (which means popping
             // the operands, applying the operator, and pushing the result).
         left.printStackCommands();
         right.printStackCommands();
         System.out.println("  Operator " + op);
      }
      void printInfix() {              
             // Print the expression, in parentheses.
         System.out.print('(');
         left.printInfix();
         System.out.print(" " + op + " ");
         right.printInfix();
         System.out.print(')');
      }
      ExpNode derivative() {
             // Apply the derivative formulas.
         switch (op) {
         case '+':
            return new BinOpNode('+', left.derivative(), right.derivative());
         case '-':
            return new BinOpNode('-', left.derivative(), right.derivative());
         case '*':
            return new BinOpNode( '+',
                  new BinOpNode('*', left, right.derivative()),
                  new BinOpNode('*', right, left.derivative()) );
         case '/':
            return new BinOpNode( '/',
                  new BinOpNode('-', 
                        new BinOpNode('*', right, left.derivative()),
                        new BinOpNode('*', left, right.derivative())),
                        new BinOpNode('*', right, right) );
         default:
            return null;
         }
      }
   }

   
   /**
    * An expression node to represent a unary minus operator.
    */
   private static class UnaryMinusNode extends ExpNode {
      ExpNode operand;  // The operand to which the unary minus applies.
      UnaryMinusNode(ExpNode operand) {
             // Construct a UnaryMinusNode with the specified operand.
         assert operand != null;
         this.operand = operand;
      }
      double value(double xValue) {
             // The value is the negative of the value of the operand.
         double neg = operand.value(xValue);
         return -neg;
      }
      void printStackCommands() {
             // To evaluate this expression on a stack machine, first do
             // whatever is necessary to evaluate the operand, leaving the
             // operand on the stack.  Then apply the unary minus (which means
             // popping the operand, negating it, and pushing the result).
         operand.printStackCommands();
         System.out.println("  Unary minus");
      }
      void printInfix() {             
         // Print the expression, in parentheses.
         System.out.print("(-");
         operand.printInfix();
         System.out.print(')');
      }
      ExpNode derivative() {
         // The derivative of -A is -(derivative of A).
         return new UnaryMinusNode(operand.derivative());
      }
   }


   /**
    * An expression node that represents a reference to the variable, x.
    */
   private static class VariableNode extends ExpNode {
      VariableNode() {
             // Construct a VariableNode. (There is nothing to do!)
      }
      double value(double xValue) {
            // The value of the node is the value of x.
         return xValue;
      }
      void printStackCommands() {
            // On a stack machine, just push the value of X onto the stack.
         System.out.println("  Push X"); 
      }
      void printInfix() {         
         System.out.print("x");
      }
      ExpNode derivative() {
            // The derivative of x is the constant 1.
         return new ConstNode(1);
      }
   }

   
   //   -------------------------------------------------------------------------------
   

   /**
    * An object of type ParseError represents a syntax error found in 
    * the user's input.
    */
   private static class ParseError extends Exception {
      ParseError(String message) {
         super(message);
      }
   } // end nested class ParseError


   public static void main(String[] args) {

      while (true) {
         System.out.println("\n\nEnter an expression, or press return to end.");
         System.out.print("\n?  ");
         TextIO.skipBlanks();
         if ( TextIO.peek() == '\n' )
            break;
         try {
            ExpNode exp = expressionTree();
            TextIO.skipBlanks();
            if ( TextIO.peek() != '\n' )
               throw new ParseError("Extra data after end of expression.");
            TextIO.getln();
            ExpNode deriv = exp.derivative();
            System.out.println("\nA fully parenthesized expression for the derivative is:");
            System.out.print("   ");
            deriv.printInfix();
            System.out.println();
            System.out.println("\nValue of derivative at x = 0 is " + deriv.value(0));
            System.out.println("Value of derivative at x = 1 is " + deriv.value(1));
            System.out.println("Value of derivative at x = 2 is " + deriv.value(2));
            System.out.println("Value of derivative at x = 3 is " + deriv.value(3));
            System.out.println("\nOrder of postfix evaluation for derivative is:\n");
            deriv.printStackCommands();
         }
         catch (ParseError e) {
            System.out.println("\n*** Error in input:    " + e.getMessage());
            System.out.println("*** Discarding input:  " + TextIO.getln());
         }
      }

      System.out.println("\n\nDone.");

   } // end main()


   /**
    * Reads an expression from the current line of input and builds
    * an expression tree that represents the expression.
    * @return an ExpNode which is a pointer to the root node of the 
    *    expression tree
    * @throws ParseError if a syntax error is found in the input
    */
   private static ExpNode expressionTree() throws ParseError {
      TextIO.skipBlanks();
      boolean negative;  // True if there is a leading minus sign.
      negative = false;
      if (TextIO.peek() == '-') {
         TextIO.getAnyChar();
         negative = true;
      }
      ExpNode exp;       // The expression tree for the expression.
      exp = termTree();  // Start with the first term.
      if (negative)
         exp = new UnaryMinusNode(exp);
      TextIO.skipBlanks();
      while ( TextIO.peek() == '+' || TextIO.peek() == '-' ) {
             // Read the next term and combine it with the
             // previous terms into a bigger expression tree.
         char op = TextIO.getAnyChar();
         ExpNode nextTerm = termTree();
         exp = new BinOpNode(op, exp, nextTerm);
         TextIO.skipBlanks();
      }
      return exp;
   } // end expressionTree()


   /**
    * Reads a term from the current line of input and builds
    * an expression tree that represents the expression.
    * @return an ExpNode which is a pointer to the root node of the 
    *    expression tree
    * @throws ParseError if a syntax error is found in the input
    */
   private static ExpNode termTree() throws ParseError {
      TextIO.skipBlanks();
      ExpNode term;  // The expression tree representing the term.
      term = factorTree();
      TextIO.skipBlanks();
      while ( TextIO.peek() == '*' || TextIO.peek() == '/' ) {
             // Read the next factor, and combine it with the
             // previous factors into a bigger expression tree.
         char op = TextIO.getAnyChar();
         ExpNode nextFactor = factorTree();
         term = new BinOpNode(op,term,nextFactor);
         TextIO.skipBlanks();
      }
      return term;
   } // end termValue()


   /**
    * Reads a factor from the current line of input and builds
    * an expression tree that represents the expression.
    * @return an ExpNode which is a pointer to the root node of the 
    *    expression tree
    * @throws ParseError if a syntax error is found in the input
    */

   private static ExpNode factorTree() throws ParseError {
      TextIO.skipBlanks();
      char ch = TextIO.peek();
      if ( Character.isDigit(ch) ) {
             // The factor is a number.  Return a ConstNode.
         double num = TextIO.getDouble();
         return new ConstNode(num);
      }
      else if ( ch == 'x' || ch == 'X' ) { 
             // The factor is the variable x.
            TextIO.getAnyChar();   // Read the X.
            return new VariableNode();
         }
      else if ( ch == '(' ) {
             // The factor is an expression in parentheses.
             // Return a tree representing that expression.
         TextIO.getAnyChar();  // Read the "("
         ExpNode exp = expressionTree();
         TextIO.skipBlanks();
         if ( TextIO.peek() != ')' )
            throw new ParseError("Missing right parenthesis.");
         TextIO.getAnyChar();  // Read the ")"
         return exp;
      }
      else if ( ch == '\n' )
         throw new ParseError("End-of-line encountered in the middle of an expression.");
      else if ( ch == ')' )
         throw new ParseError("Extra right parenthesis.");
      else if ( ch == '+' || ch == '-' || ch == '*' || ch == '/' )
         throw new ParseError("Misplaced operator.");
      else
         throw new ParseError("Unexpected character \"" + ch + "\" encountered.");
   }  // end factorTree()


} // end class SimpleParser5
