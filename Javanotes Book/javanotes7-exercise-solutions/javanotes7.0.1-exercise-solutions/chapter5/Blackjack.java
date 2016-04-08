/**
 * This program lets the user play Blackjack.  The computer
 * acts as the dealer.  The user has a stake of $100, and
 * makes a bet on each game.  The user can leave at any time,
 * or will be kicked out when he loses all the money.
 * House rules:  The dealer hits on a total of 16 or less
 * and stands on a total of 17 or more.  Dealer wins ties.
 * A new deck of cards is used for each game.
 */
public class Blackjack {

   public static void main(String[] args) {
   
      int money;          // Amount of money the user has.
      int bet;            // Amount user bets on a game.
      boolean userWins;   // Did the user win the game?
      
      System.out.println("Welcome to the game of blackjack.");
      System.out.println();
      
      money = 100;  // User starts with $100.
   
      while (true) {
          System.out.println("You have " + money + " dollars.");
          do {
             System.out.println("How many dollars do you want to bet?  (Enter 0 to end.)");
             System.out.print("? ");
             bet = TextIO.getlnInt();
             if (bet < 0 || bet > money)
                 System.out.println("Your answer must be between 0 and " + money + '.');
          } while (bet < 0 || bet > money);
          if (bet == 0)
             break;
          userWins = playBlackjack();
          if (userWins)
             money = money + bet;
          else
             money = money - bet;
          System.out.println();
          if (money == 0) {
             System.out.println("Looks like you've run out of money!");
             break;
          }
      }
      
      System.out.println();
      System.out.println("You leave with $" + money + '.');
   
   } // end main()
   
 
   /**
    * Let the user play one game of Blackjack, with the computer as dealer.
    * @return true if the user wins the game, false if the user loses.
    */  
   static boolean playBlackjack() {

      Deck deck;                  // A deck of cards.  A new deck for each game.
      BlackjackHand dealerHand;   // The dealer's hand.
      BlackjackHand userHand;     // The user's hand.
      
      deck = new Deck();
      dealerHand = new BlackjackHand();
      userHand = new BlackjackHand();

      /*  Shuffle the deck, then deal two cards to each player. */
      
      deck.shuffle();
      dealerHand.addCard( deck.dealCard() );
      dealerHand.addCard( deck.dealCard() );
      userHand.addCard( deck.dealCard() );
      userHand.addCard( deck.dealCard() );
      
      System.out.println();
      System.out.println();
      
      /* Check if one of the players has Blackjack (two cards totaling to 21).
         The player with Blackjack wins the game.  Dealer wins ties.
      */
      
      if (dealerHand.getBlackjackValue() == 21) {
           System.out.println("Dealer has the " + dealerHand.getCard(0)
                                   + " and the " + dealerHand.getCard(1) + ".");
           System.out.println("User has the " + userHand.getCard(0)
                                     + " and the " + userHand.getCard(1) + ".");
           System.out.println();
           System.out.println("Dealer has Blackjack.  Dealer wins.");
           return false;
      }
      
      if (userHand.getBlackjackValue() == 21) {
           System.out.println("Dealer has the " + dealerHand.getCard(0)
                                   + " and the " + dealerHand.getCard(1) + ".");
           System.out.println("User has the " + userHand.getCard(0)
                                     + " and the " + userHand.getCard(1) + ".");
           System.out.println();
           System.out.println("You have Blackjack.  You win.");
           return true;
      }
      
      /*  If neither player has Blackjack, play the game.  First the user 
          gets a chance to draw cards (i.e., to "Hit").  The while loop ends 
          when the user chooses to "Stand".  If the user goes over 21,
          the user loses immediately.
      */
      
      while (true) {
          
           /* Display user's cards, and let user decide to Hit or Stand. */

           System.out.println();
           System.out.println();
           System.out.println("Your cards are:");
           for ( int i = 0; i < userHand.getCardCount(); i++ )
              System.out.println("    " + userHand.getCard(i));
           System.out.println("Your total is " + userHand.getBlackjackValue());
           System.out.println();
           System.out.println("Dealer is showing the " + dealerHand.getCard(0));
           System.out.println();
           System.out.print("Hit (H) or Stand (S)? ");
           char userAction;  // User's response, 'H' or 'S'.
           do {
              userAction = Character.toUpperCase( TextIO.getlnChar() );
              if (userAction != 'H' && userAction != 'S')
                 System.out.print("Please respond H or S:  ");
           } while (userAction != 'H' && userAction != 'S');

           /* If the user Hits, the user gets a card.  If the user Stands,
              the loop ends (and it's the dealer's turn to draw cards).
           */

           if ( userAction == 'S' ) {
                   // Loop ends; user is done taking cards.
               break;
           }
           else {  // userAction is 'H'.  Give the user a card.  
                   // If the user goes over 21, the user loses.
               Card newCard = deck.dealCard();
               userHand.addCard(newCard);
               System.out.println();
               System.out.println("User hits.");
               System.out.println("Your card is the " + newCard);
               System.out.println("Your total is now " + userHand.getBlackjackValue());
               if (userHand.getBlackjackValue() > 21) {
                   System.out.println();
                   System.out.println("You busted by going over 21.  You lose.");
                   System.out.println("Dealer's other card was the " 
                                                      + dealerHand.getCard(1));
                   return false;  
               }
           }
           
      } // end while loop
      
      /* If we get to this point, the user has Stood with 21 or less.  Now, it's
         the dealer's chance to draw.  Dealer draws cards until the dealer's
         total is > 16.  If dealer goes over 21, the dealer loses.
      */

      System.out.println();
      System.out.println("User stands.");
      System.out.println("Dealer's cards are");
      System.out.println("    " + dealerHand.getCard(0));
      System.out.println("    " + dealerHand.getCard(1));
      while (dealerHand.getBlackjackValue() <= 16) {
         Card newCard = deck.dealCard();
         System.out.println("Dealer hits and gets the " + newCard);
         dealerHand.addCard(newCard);
         if (dealerHand.getBlackjackValue() > 21) {
            System.out.println();
            System.out.println("Dealer busted by going over 21.  You win.");
            return true;
         }
      }
      System.out.println("Dealer's total is " + dealerHand.getBlackjackValue());
      
      /* If we get to this point, both players have 21 or less.  We
         can determine the winner by comparing the values of their hands. */
      
      System.out.println();
      if (dealerHand.getBlackjackValue() == userHand.getBlackjackValue()) {
         System.out.println("Dealer wins on a tie.  You lose.");
         return false;
      }
      else if (dealerHand.getBlackjackValue() > userHand.getBlackjackValue()) {
         System.out.println("Dealer wins, " + dealerHand.getBlackjackValue() 
                          + " points to " + userHand.getBlackjackValue() + ".");
         return false;
      }
      else {
         System.out.println("You win, " + userHand.getBlackjackValue() 
                          + " points to " + dealerHand.getBlackjackValue() + ".");
         return true;
      }

   }  // end playBlackjack()


} // end class Blackjack