package com.example.blackjack;
 enum Suit {
    Clubs, Diamonds, Hearts, Spades
        }
 enum Face {
     Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
 }

public class Card {
    private int face;
    private int suit;
    public Card(int face, int suit) {
        this.face = face;
        this.suit = suit;
    }

    public Face getFace() {
        return Face.values()[face];
    }

    public Suit getSuit() {
        return Suit.values()[suit];
    }
}
