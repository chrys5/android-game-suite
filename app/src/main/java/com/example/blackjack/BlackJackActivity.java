package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;

public class BlackJackActivity extends AppCompatActivity {
    private Button Exit_Button;
    private Button Deal_Button;
    private Button Bet_Button;
    private Button Call_Button;
    private ArrayList<Card>[] l;
    private ArrayList<Card> deck;
    private int totalBet;
    private boolean started;
    private boolean gameFin;
    private int[] totalWinnings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_jack);
        gameFin = false;
        started = false;
        totalBet = 0;
        l = new ArrayList[2];
        for (int i = 0; i < l.length; i++) {
            l[i] = new ArrayList<>(2);
        }
        totalWinnings = new int[l.length - 1];
        ((TextView) findViewById(R.id.textView3)).setText("Your Bet is: $0");
        deck = new ArrayList<>(52);
        shuffle();
        Exit_Button = (Button) findViewById(R.id.button);
        Exit_Button.setOnClickListener(v -> returnMain());
        Deal_Button = (Button) findViewById(R.id.button2);
        Deal_Button.setOnClickListener(v -> {
            if (l[0].size() > 0 && !gameFin) {
                hit(l[1]);
                addCard(1, l[1].get(l[1].size()-1));
            } else if (!gameFin) {
                for (int i = 0; i < l.length * 2; i++) {
                    deal(l[(i % l.length)]);
                    addCard((i % l.length), l[(i % l.length)].get(l[(i % l.length)].size()-1));
                }
                Deal_Button.setText("Hit");
            }
        });
        Bet_Button = (Button) findViewById(R.id.button3);
        Bet_Button.setOnClickListener(v -> makeBet());
        Call_Button = (Button) findViewById(R.id.button4);
        Call_Button.setOnClickListener(v -> call());
    }

    public void returnMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deal(ArrayList<Card> hand) {
        started = true;
        hand.add(draw());
    }

    public Card draw() {
        Card drawn = deck.remove((int) (Math.random() * (deck.size()-1)));
        System.out.println(drawn.getFace() + " of " + drawn.getSuit());
        return drawn;
    }

    public void shuffle() {
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
    public void makeBet() {
        String bet = ((EditText) findViewById(R.id.editTextNumber)).getText().toString();
        if (bet.length() != 0 && !gameFin) {
            totalBet += Integer.parseInt(bet);
            ((TextView) findViewById(R.id.textView3)).setText("Your Bet is: $" + totalBet);
        }
        System.out.println(bet);
    }

    public String checkCardValues(ArrayList<Card> hand) {
        StringBuilder total = new StringBuilder();
        for (int i = 0; i < hand.size(); i++) {
            int e = Arrays.asList(Face.values()).indexOf(hand.get(i).getFace());
            String val;
            if (0 != e && e < 9) {
                e++;
                val = e + "";
            } else if (hand.get(i).getFace() == Face.Ace) {
                val = "1 or 11";
            } else {
                val = 10 + "";
            }
            total.append(" ").append(val);
        }
        return total.toString();
    }

    public int checkValue(ArrayList<Card> hand) {
        String val = checkCardValues(hand);
        int total = 0;
        if (val.contains("or")) {
            int o = val.split("or").length - 1;
            String[] s = val.trim().split("1 or 11");
            for (String value : s) {
                if (value.trim().length() > 0) {
                    for (String s1 : value.trim().split(" ")) {
                        if (s1.trim().length() > 0) {
                            System.out.println(s1.trim());
                            total += Integer.parseInt(s1.trim());
                        }
                    }
                }
            }
            if (total > (21 - 11 - (o - 1))) {
                total += o;
            } else {
                total += 11 + (o - 1);
            }

        } else {
            String[] s = val.trim().split(" ");
            for (String value : s) {
                System.out.println(value.trim());
                total += Integer.parseInt(value.trim());
            }
        }
        return total;
    }

    public Boolean isBusted(ArrayList<Card> hand) {
        return (checkValue(hand) > 21);
    }

    public void hit(ArrayList<Card> hand) {
        deal(hand);
    }

    public void call() {
        if (!started) {
            return;
        }
        if (Call_Button.getText().toString().equals("Stand")) {
            ((Button) findViewById(R.id.button4)).setText("Call");
            int hasSingleAce = 0;
            for (Card c : l[0]) {
                if (c.getFace() == Face.Ace) {
                    hasSingleAce = 1;
                }
            }
            while ((checkValue(l[0]) < 17 || checkValue(l[0]) - 10 * hasSingleAce < 17) && checkValue(l[0]) != 21) {
                deal(l[0]);
                addCard(0, l[0].get(l[0].size() - 1));
                if (hasSingleAce == 1 && l[0].get(l[0].size() - 1).getFace() == Face.Ace) {
                    hasSingleAce = 0;
                }
            }
            gameFin = true;
            return;
        } else if (Call_Button.getText().toString().equals("Reset")) {
            shuffle();
            for (ArrayList<Card> c : l) {
                c.clear();
            }
            LinearLayout l = (LinearLayout) findViewById(R.id.Linear1);
            LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayout);
            l.removeAllViewsInLayout();
            l1.removeAllViewsInLayout();
            ((TextView) findViewById(R.id.textView4)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.textView3)).setText("Your Bet is: $0");
            Deal_Button.setText("Deal");
            Call_Button.setText("Stand");
            gameFin = false;
            return;
        }
        TextView text = ((TextView) findViewById(R.id.textView4));
        TextView text12 = (TextView) findViewById(R.id.textView5);
        if (isBusted(l[1])) {
            String bet = ((TextView) findViewById(R.id.textView3)).getText().toString().split("Your Bet is: ")[1];
            text.setText("You Busted! You Lose " + bet +"!");
            totalWinnings[0] -= Integer.parseInt(((TextView) findViewById(R.id.textView3)).getText().toString().split("\\$")[1]);

        } else if ((checkValue(l[0]) < checkValue(l[1]) || isBusted(l[0])) && !isBusted(l[1])) {
            System.out.println(isBusted(l[1]));
            System.out.println(checkValue(l[0]));
            System.out.println(checkValue(l[1]));
            System.out.println("You Win!");
            String bet = ((TextView) findViewById(R.id.textView3)).getText().toString().split("Your Bet is: ")[1];
            text.setText("You Win " + bet + "!");
            totalWinnings[0] += Integer.parseInt(((TextView) findViewById(R.id.textView3)).getText().toString().split("\\$")[1]);
        } else if (checkValue(l[0]) == checkValue(l[1])) {
            System.out.println(isBusted(l[1]));
            System.out.println(checkValue(l[0]));
            System.out.println(checkValue(l[1]));
            System.out.println("You Win!");
            text.setText("You Tied! Keep Your Bet.");
        } else {
            System.out.println(isBusted(l[1]));
            System.out.println(checkValue(l[0]));
            System.out.println(checkValue(l[1]));
            System.out.println("You Lose!");
            String bet = ((TextView) findViewById(R.id.textView3)).getText().toString().split("Your Bet is: ")[1];
            text.setText("You Lose " + bet + "!");
            totalWinnings[0] -= Integer.parseInt(((TextView) findViewById(R.id.textView3)).getText().toString().split("\\$")[1]);
        }
        text12.setText("Total Winnings: $" + totalWinnings[0]);
        System.out.println(totalWinnings);
        text.setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.button4)).setText("Reset");
    }

    public void addCard(int hand, Card card) {
        String s = card.getFace() + " of " + card.getSuit();
        ImageView imageView = new ImageView(this);
        switch (s) {
            case "Ace of Clubs":
                imageView.setImageResource(R.drawable.ace_of_clubs);
                break;
            case "Ace of Diamonds":
                imageView.setImageResource(R.drawable.ace_of_diamonds);
                break;
            case "Ace of Hearts":
                imageView.setImageResource(R.drawable.ace_of_hearts);
                break;
            case "Ace of Spades":
                imageView.setImageResource(R.drawable.ace_of_spades);
                break;
            case "Two of Clubs":
                imageView.setImageResource(R.drawable.a2_of_clubs);
                break;
            case "Two of Diamonds":
                imageView.setImageResource(R.drawable.a2_of_diamonds);
                break;
            case "Two of Hearts":
                imageView.setImageResource(R.drawable.a2_of_hearts);
                break;
            case "Two of Spades":
                imageView.setImageResource(R.drawable.a2_of_spades);
                break;
            case "Three of Clubs":
                imageView.setImageResource(R.drawable.a3_of_clubs);
                break;
            case "Three of Diamonds":
                imageView.setImageResource(R.drawable.a3_of_diamonds);
                break;
            case "Three of Hearts":
                imageView.setImageResource(R.drawable.a3_of_hearts);
                break;
            case "Three of Spades":
                imageView.setImageResource(R.drawable.a3_of_spades);
                break;
            case "Four of Clubs":
                imageView.setImageResource(R.drawable.a4_of_clubs);
                break;
            case "Four of Diamonds":
                imageView.setImageResource(R.drawable.a4_of_diamonds);
                break;
            case "Four of Hearts":
                imageView.setImageResource(R.drawable.a4_of_hearts);
                break;
            case "Four of Spades":
                imageView.setImageResource(R.drawable.a4_of_spades);
                break;
            case "Five of Clubs":
                imageView.setImageResource(R.drawable.a5_of_clubs);
                break;
            case "Five of Diamonds":
                imageView.setImageResource(R.drawable.a5_of_diamonds);
                break;
            case "Five of Hearts":
                imageView.setImageResource(R.drawable.a5_of_hearts);
                break;
            case "Five of Spades":
                imageView.setImageResource(R.drawable.a5_of_spades);
                break;
            case "Six of Clubs":
                imageView.setImageResource(R.drawable.a6_of_clubs);
                break;
            case "Six of Diamonds":
                imageView.setImageResource(R.drawable.a6_of_diamonds);
                break;
            case "Six of Hearts":
                imageView.setImageResource(R.drawable.a6_of_hearts);
                break;
            case "Six of Spades":
                imageView.setImageResource(R.drawable.a6_of_spades);
                break;
            case "Seven of Clubs":
                imageView.setImageResource(R.drawable.a7_of_clubs);
                break;
            case "Seven of Diamonds":
                imageView.setImageResource(R.drawable.a7_of_diamonds);
                break;
            case "Seven of Hearts":
                imageView.setImageResource(R.drawable.a7_of_hearts);
                break;
            case "Seven of Spades":
                imageView.setImageResource(R.drawable.a7_of_spades);
                break;
            case "Eight of Clubs":
                imageView.setImageResource(R.drawable.a8_of_clubs);
                break;
            case "Eight of Diamonds":
                imageView.setImageResource(R.drawable.a8_of_diamonds);
                break;
            case "Eight of Hearts":
                imageView.setImageResource(R.drawable.a8_of_hearts);
                break;
            case "Eight of Spades":
                imageView.setImageResource(R.drawable.a8_of_spades);
                break;
            case "Nine of Clubs":
                imageView.setImageResource(R.drawable.a9_of_clubs);
                break;
            case "Nine of Diamonds":
                imageView.setImageResource(R.drawable.a9_of_diamonds);
                break;
            case "Nine of Hearts":
                imageView.setImageResource(R.drawable.a9_of_hearts);
                break;
            case "Nine of Spades":
                imageView.setImageResource(R.drawable.a9_of_spades);
                break;
            case "Ten of Clubs":
                imageView.setImageResource(R.drawable.a10_of_clubs);
                break;
            case "Ten of Diamonds":
                imageView.setImageResource(R.drawable.a10_of_diamonds);
                break;
            case "Ten of Hearts":
                imageView.setImageResource(R.drawable.a10_of_hearts);
                break;
            case "Ten of Spades":
                imageView.setImageResource(R.drawable.a10_of_spades);
                break;
            case "Jack of Clubs":
                imageView.setImageResource(R.drawable.jack_of_clubs2);
                break;
            case "Jack of Diamonds":
                imageView.setImageResource(R.drawable.jack_of_diamonds2);
                break;
            case "Jack of Hearts":
                imageView.setImageResource(R.drawable.jack_of_hearts2);
                break;
            case "Jack of Spades":
                imageView.setImageResource(R.drawable.jack_of_spades2);
                break;
            case "Queen of Clubs":
                imageView.setImageResource(R.drawable.queen_of_clubs2);
                break;
            case "Queen of Diamonds":
                imageView.setImageResource(R.drawable.queen_of_diamonds2);
                break;
            case "Queen of Hearts":
                imageView.setImageResource(R.drawable.queen_of_hearts2);
                break;
            case "Queen of Spades":
                imageView.setImageResource(R.drawable.queen_of_spades2);
                break;
            case "King of Clubs":
                imageView.setImageResource(R.drawable.king_of_clubs2);
                break;
            case "King of Diamonds":
                imageView.setImageResource(R.drawable.king_of_diamonds2);
                break;
            case "King of Hearts":
                imageView.setImageResource(R.drawable.king_of_hearts2);
                break;
            case "King of Spades":
                imageView.setImageResource(R.drawable.king_of_spades2);
                break;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200,290);
        imageView.setLayoutParams(params);
        LinearLayout layout;
        if (hand == 0) {
            layout = (LinearLayout) findViewById(R.id.Linear1);
        } else {
            layout = (LinearLayout) findViewById(R.id.linearLayout);
        }
        layout.addView(imageView);
    }
}