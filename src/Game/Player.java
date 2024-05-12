package Game;

import cards.ActionCards;
import cards.Cards;
import cards.NumberCard;
import exceptions.WrongCardPlayed;

import java.util.ArrayList;

public class Player {


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
    public void selectCard(Cards card, GameSession gameSession,String color) throws WrongCardPlayed {
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
    public void playNumberCard(NumberCard card, GameSession gameSession) throws WrongCardPlayed {
        GameValidation gameValidation = new GameValidation();
        if(gameValidation.validPlayNumberCard(card,gameSession)){
            gameSession.discardpile = card;
            cards_in_hand.remove(card);
        }
    }
    public void playActionCard(ActionCards card, GameSession gameSession) throws WrongCardPlayed {
        GameValidation gameValidation = new GameValidation();
        if(gameValidation.validPlayActionsCards(card,gameSession)){
            gameSession.discardpile = card;
            cards_in_hand.remove(card);
            if(card.skill=="reverse"){
                gameSession.clockwise = !gameSession.clockwise;

            }
            else if(card.skill=="skip"){
                gameSession.clockwise = !gameSession.clockwise;
            }

        }
    }
    public void playWildCard(Cards card, GameSession gameSession, String color) {
        if (gameSession.discardpile instanceof NumberCard) {
            NumberCard numberCard = (NumberCard) gameSession.discardpile;
            gameSession. = color;
        } else if (gameSession.discardpile instanceof ActionCards) {
            ActionCards actionCard = (ActionCards) gameSession.discardpile;
            actionCard.color = color;
        }
        gameSession.discardpile.color = color;
        gameSession.discardpile = card;
        cards_in_hand.remove(card);

    }




    public ArrayList<Cards> getCards_in_hand() {
        return cards_in_hand;
    }

    public void setCards_in_hand(ArrayList<Cards> cards_in_hand) {
        this.cards_in_hand = cards_in_hand;
    }


}
