package assignment2;
/*
 * @author Selina Gao
 */

import java.util.Random;

public class Deck {
 public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
 public static Random gen = new Random();

 public int numOfCards; // contains the total number of cards in the deck
 public Card head; // contains a pointer to the card on the top of the deck

 /* 
  * TODO: Initializes a Deck object using the inputs provided
  */
 public Deck(int numOfCardsPerSuit, int numOfSuits) {
	 if(((numOfCardsPerSuit < 1) || (numOfCardsPerSuit > 13))  || ((numOfSuits < 1) || (numOfSuits > suitsInOrder.length))) {
		 throw new IllegalArgumentException("Thee first input is not a number\n"
		 		+ "between 1 and 13 (both included) or the second input is not a number between 1 and the size\n"
		 		+ "of the class field suitsInOrder.");
	 }
	 
	 for(int i=0; i<numOfSuits; i++) {
		 for(int j=1; j<=numOfCardsPerSuit; j++) {
			 PlayingCard newCard = new PlayingCard(suitsInOrder[i],j);
			 addCard(newCard);
		 } 
	 }
	 
	 addCard(new Joker("red"));
	 addCard(new Joker("black"));  
 }

 

/* 
  * TODO: Implements a copy constructor for Deck using Card.getCopy().
  * This method runs in O(n), where n is the number of cards in d.
  */
 public Deck(Deck d) {
	 if (d.numOfCards > 0) {
		 head = d.head.getCopy();
		 Card currentCard = d.head;
		 Card copyCard = head;
		 currentCard=currentCard.next;
		 addCard(head);
		 
		 while (currentCard != d.head) {
			 copyCard = currentCard.getCopy();
		     addCard(copyCard);
		     currentCard=currentCard.next;
		   }
		  }
		}


 /*
  * For testing purposes we need a default constructor.
  */
 public Deck() {}

 /* 
  * TODO: Adds the specified card at the bottom of the deck. This 
  * method runs in $O(1)$. 
  */
 public void addCard(Card c) {
	 if (numOfCards == 0) {
		 head = c;
		 head.prev = head;
		 head.next = head;
	 }
	 else {
		 c.next = head;
		 c.prev = head.prev;
		 head.prev.next = c;
		 head.prev = c;
	 }
	 numOfCards +=1;
 }


/*
  * TODO: Shuffles the deck using the algorithm described in the pdf. 
  * This method runs in O(n) and uses O(n) space, where n is the total 
  * number of cards in the deck.
  */
 public void shuffle() {
	 if(numOfCards != 0) {
	 
	 Card[] cards = new Card[numOfCards];
	 Card currentCard = head;
	 
	 for(int i=0;i<cards.length;i++) {
		 cards[i]=currentCard;
		 currentCard = currentCard.next;
	 }
	 
	 if(cards.length > 1) {
		 for(int i=numOfCards-1; i>0; i--) {
			 int j = gen.nextInt(i+1);
		     Card swapCard = cards[i];
		     cards[i]=cards[j];
		     cards[j]=swapCard; 
	 }
		
		 head=cards[0];
	     head.prev = cards[numOfCards-1];
	     head.next = cards[1];
	    
	     for(int i=1;i<numOfCards-1;i++) {
	    	 currentCard=cards[i];
	    	 currentCard.prev=cards[i-1];
	    	 currentCard.next=cards[i+1];
	     }
	     cards[numOfCards-1].prev = cards[numOfCards-2];
	     cards[numOfCards-1].next=head;
	    
	 }
	}
 }

 /*
  * TODO: Returns a reference to the joker with the specified color in 
  * the deck. This method runs in O(n), where n is the total number of 
  * cards in the deck. 
  */
 public Joker locateJoker(String color) {
	 if(color.equalsIgnoreCase("red") || color.equalsIgnoreCase("black")){
		 String newJoker = new Joker(color).toString();
		 Card currentCard = head;
		 for(int i=0; i<numOfCards; i++) {
			 currentCard = currentCard.next;
			 if((currentCard.toString()).equals(newJoker)) {
				 return (Joker) currentCard;
			 } 
	 }
		 
 }
	 return null;
	 
	 
 }

 /*
  * TODO: Moved the specified Card, p positions down the deck. You can 
  * assume that the input Card does belong to the deck (hence the deck is
  * not empty). This method runs in O(p).
  */
 public void moveCard(Card c, int p) {
	 if(c == null) {
		 return;
	 }
	 if(p>0) {
		 Card current = c;
		 if(p>numOfCards) {
			 for(int i=0;i<p;i++) {
				 current = current.next.next; 
	 }
		 }
		 else {
			 for(int i=0;i<p; i++) {
				 current = current.next;
			 }
		 }
		 c.prev.next = c.next;
		 c.next.prev = c.prev;
		 current.next.prev = c;
		 c.next = current.next;
		 current.next = c;
		 c.prev = current;
  }
	 }
 

 /*
  * TODO: Performs a triple cut on the deck using the two input cards. You 
  * can assume that the input cards belong to the deck and the first one is 
  * nearest to the top of the deck. This method runs in O(1)
  */
 public void tripleCut(Card firstCard, Card secondCard) {
	 if(firstCard == null) return;
	 if(firstCard == head && secondCard == head.prev) {
		 return;
	 }
	 else if(firstCard == head) {
		 head = secondCard.next;
	 }
	 else if(secondCard == head.prev) {
		 head = firstCard;
	 }
	 else {
		 Card temp1 = firstCard.prev;
		 Card temp2 = secondCard.next;
		 Card deckTail = head.prev;
		 Card deckHead = head;
		 
		 // 1st pointer 
		 firstCard.prev = deckTail;
		 deckTail.next = firstCard;
		 
		 //2nd pointer
		 secondCard.next = deckHead;
		 deckHead.prev = secondCard;
		 
		 head = temp2;
		 head.prev = temp1;
		 
		 //3rd pointer
		 temp1.next = temp2;
		 temp2.prev = temp1;	 
  }
 }

 /*
  * TODO: Performs a count cut on the deck. Note that if the value of the 
  * bottom card is equal to a multiple of the number of cards in the deck, 
  * then the method should not do anything. This method runs in O(n).
  */
 public void countCut() {
	 if(numOfCards > 0) {
		 int lastCardValue= head.prev.getValue();
		 int CardValue = lastCardValue % numOfCards;
		 Card currentCard = head;
		 
		 if((CardValue > 0) && (numOfCards-1)!=CardValue) {
			 for(int i=0; i<CardValue-1;i++) {
				 currentCard = currentCard.next;
		 }
			 Card oldTail = head.prev; // 6C
			 Card oldHead = head; //5C
			 Card newHead = currentCard.next; // head = 10D
			 Card newTail = currentCard;
			 
			 newHead.prev = currentCard;
			 currentCard.next = newHead;
			 
			 oldTail.prev.next = oldHead;
			 oldHead.prev = oldTail.prev;
			 
			 newTail.next = oldTail;
			 oldTail.prev = newTail;
			 
			 newHead.prev = oldTail;
			 oldTail.next = newHead;
			 
			 head = newHead;
			 
		 }
		 
	 }
 }

 /*
  * TODO: Returns the card that can be found by looking at the value of the 
  * card on the top of the deck, and counting down that many cards. If the 
  * card found is a Joker, then the method returns null, otherwise it returns
  * the Card found. This method runs in O(n).
  */
 public Card lookUpCard() {
	 
	 int cardValue = head.getValue();
	 Card current = head;
	 
	 for (int i = 0; i < cardValue; i++) {
		 current = current.next;
		 }
	 if (current instanceof Joker) {
		 return null;
		 }
	 return current;
	 
 }

 /*
  * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
  * using this deck. This method runs in O(n).
  */
 public int generateNextKeystreamValue() {
	 // for loop 
	 Card locateFirst = null;
	 Card locateSecond = null;
	 moveCard(locateJoker("red"),1);
	 moveCard(locateJoker("black"),2);
	 Card blackJoker = locateJoker("black");
	 Card redJoker = locateJoker("red");
	 Card current = head;
	 
	 for (int i=0;i<numOfCards;i++) {
		 if((current.toString()).equalsIgnoreCase("RJ") || current.toString().equalsIgnoreCase("BJ")) {
			 locateFirst = current;
			 break;
		 }
		 current = current.next;
	 }
	 if(locateFirst != blackJoker) {
		 locateSecond = blackJoker;
	 }
	 else if(locateFirst == blackJoker){
		 locateSecond = redJoker;
	 }
	 
	 tripleCut(locateFirst,locateSecond);
	 countCut();
	 Card keyStream = lookUpCard();
	 
	 if(keyStream == null) {
		 return generateNextKeystreamValue();
	 }
	 else {
		 return keyStream.getValue();
	 }
}

 public abstract class Card { 
  public Card next;
  public Card prev;

  public abstract Card getCopy();
  public abstract int getValue();

 }

 public class PlayingCard extends Card {
  public String suit;
  public int rank;

  public PlayingCard(String s, int r) {
   this.suit = s.toLowerCase();
   this.rank = r;
  }

  public String toString() {
   String info = "";
   if (this.rank == 1) {
    //info += "Ace";
    info += "A";
   } else if (this.rank > 10) {
    String[] cards = {"Jack", "Queen", "King"};
    //info += cards[this.rank - 11];
    info += cards[this.rank - 11].charAt(0);
   } else {
    info += this.rank;
   }
   //info += " of " + this.suit;
   info = (info + this.suit.charAt(0)).toUpperCase();
   return info;
  }

  public PlayingCard getCopy() {
   return new PlayingCard(this.suit, this.rank);   
  }

  public int getValue() {
   int i;
   for (i = 0; i < suitsInOrder.length; i++) {
    if (this.suit.equals(suitsInOrder[i]))
     break;
   }

   return this.rank + 13*i;
  }

 }

 public class Joker extends Card{
  public String redOrBlack;

  public Joker(String c) {
   if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
    throw new IllegalArgumentException("Jokers can only be red or black"); 

   this.redOrBlack = c.toLowerCase();
  }

  public String toString() {
   //return this.redOrBlack + " Joker";
   return (this.redOrBlack.charAt(0) + "J").toUpperCase();
  }

  public Joker getCopy() {
   return new Joker(this.redOrBlack);
  }

  public int getValue() {
   return numOfCards - 1;
  }

  public String getColor() {
   return this.redOrBlack;
  }
 }

}
