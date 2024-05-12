package Game;

import cards.ActionCards;
import cards.Cards;
import cards.NumberCard;
import exceptions.WrongCardPlayed;

import java.util.ArrayList;

public class Player {
    public String username;


    private ArrayList<Cards> cards_in_hand;
    public boolean declearedUNO;

    public Player(ArrayList<Cards> cards_in_hand) {
        this.cards_in_hand = cards_in_hand;
        this.declearedUNO = false;
    }
    public void drawCard(ArrayList deck) {
        ArrayList<Cards> card_deck = new ArrayList(deck);
        cards_in_hand.add(card_deck.get(0));
        deck.remove(card_deck.get(0));
    }
    public void decleareUno(){
        if(cards_in_hand.size() == 1){
            declearedUNO = true;
        }
    }

    public void playCard(Cards card, GameSession gameSession,String color)  {
        System.out.println("Playing card: " + card.getClass());
        if(card instanceof NumberCard){
            playNumberCard((NumberCard) card,gameSession);
        }
        else if(card instanceof ActionCards){
            playActionCard((ActionCards) card,gameSession);
        }
        else{
            playWildCard(card,gameSession,color);
        }
    }
    public void playNumberCard(NumberCard card, GameSession gameSession) {
        GameValidation gameValidation = new GameValidation();
        if(gameValidation.validPlayNumberCard(card,gameSession)){
            gameSession.discardpile = card;
            cards_in_hand.remove(card);
            gameSession.color = card.color;
        }
    }
    public void playActionCard(ActionCards card, GameSession gameSession)  {
        GameValidation gameValidation = new GameValidation();
        if(gameValidation.validPlayActionsCards(card,gameSession)){
            gameSession.discardpile = card;
            cards_in_hand.remove(card);
            if(card.skill=="reverse"){
                gameSession.clockwise = !gameSession.clockwise;
                gameSession.color= card.color;

            } else if (card.skill=="draw2") {
                for (int i = 0; i < 2; i++) {
                    drawCard(gameSession.deck);
                }
                gameSession.color= card.color;

            } else if(card.skill=="skip"){
                gameSession.clockwise = !gameSession.clockwise;
                gameSession.color= card.color;
            }


        }
    }
    public void playWildCard(Cards card, GameSession gameSession, String color) {

        gameSession.color = color;
        gameSession.discardpile = card;
        cards_in_hand.remove(card);

    }
    public float calculateScore(ArrayList cards_in_hand){
        float score = 0;
        for (int i = 0; i < cards_in_hand.size(); i++) {
            if (cards_in_hand.get(i) instanceof NumberCard) {
                NumberCard numberCard = (NumberCard) cards_in_hand.get(i);
                score += numberCard.number;
            } else if (cards_in_hand.get(i) instanceof ActionCards) {
                score += 20;
            } else {
                score += 50;
            }
        }
        return score;

    }




    public ArrayList<Cards> getCards_in_hand() {
        return cards_in_hand;
    }

    public void setCards_in_hand(ArrayList<Cards> cards_in_hand) {
        this.cards_in_hand = cards_in_hand;
    }


}
