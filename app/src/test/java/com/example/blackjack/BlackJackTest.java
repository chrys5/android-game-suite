package com.example.blackjack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class BlackJackTest {
    private ArrayList<Card>[] l;
    private ArrayList<Card> deck;
    private int totalBet;
    private int totalWinnings;

    @Before
    public void init() {
        deck = new ArrayList<>(52);
        l = new ArrayList[2];
        l[0] = new ArrayList<>();
        l[1] = new ArrayList<>();
        totalBet = 0;
        totalWinnings = 0;
        ArrayList<Card> tempDeck = new ArrayList<>(52);
        deck.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <13; j++) {
                tempDeck.add(new Card(j, i));
            }
        }
        while(deck.size() < 52) {
            deck.add(tempDeck.remove((int) (Math.random() * (tempDeck.size()-1))));
        }
    }

    @Test
    public void testDeal() {
        int oldDeckLen = deck.size();
        int oldHandLen = l[0].size();
        Card drawn = deck.remove((int) (Math.random() * (deck.size()-1)));
        l[0].add(drawn);
        Assert.assertEquals(oldDeckLen - 1, deck.size());
        Assert.assertEquals(oldHandLen + 1, l[0].size());
        Assert.assertFalse(deck.contains(drawn));
    }

    @Test
    public void testDraw() {
        Card drawn = deck.remove((int) (Math.random() * (deck.size()-1)));
        Assert.assertFalse(deck.contains(drawn));
    }

    @Test
    public void testShuffle() {
        ArrayList<Card> tempDeck = new ArrayList<>(52);
        deck.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <13; j++) {
                tempDeck.add(new Card(j, i));
            }
        }
        while(deck.size() < 52) {
            deck.add(tempDeck.remove((int) (Math.random() * (tempDeck.size()-1))));
        }
        HashSet<Card> cards = new HashSet<>();
        for (Card card : deck) {
            Assert.assertTrue(cards.add(card));
        }
        Assert.assertEquals(52, deck.size());
    }
}