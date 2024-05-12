package Game;

import cards.ActionCards;
import cards.Cards;
import cards.NumberCard;
import cards.WildCard;
import exceptions.WrongCardPlayed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Bot {
    private ArrayList<Cards> cards_in_hand;
    public boolean declearedUNO;

    public Bot(ArrayList<Cards> cards_in_hand) {
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
    //Bot get first appropriate card to play for wildcard it calculate the color which is most in hand and play that color and select color that
    //Add declearedUno to bot
    //Add drawCard to bot
    public void selectCard(GameSession gameSession) throws WrongCardPlayed {
        GameValidation gameValidation = new GameValidation();
        boolean canplay = false;
        for (Cards card:cards_in_hand) {
            if (canplay!=false){
                if (card instanceof NumberCard) {
                    canplay = gameValidation.validPlayNumberCard((NumberCard) card, gameSession);
                    if (canplay) {
                        playNumberCard((NumberCard) card, gameSession);
                    }
                } else if (card instanceof ActionCards) {
                    canplay = gameValidation.validPlayActionsCards((ActionCards) card, gameSession);
                    if (canplay) {
                        playActionCard((ActionCards) card, gameSession);
                    }
                } else {
                    canplay = true;
                    HashMap<String, Integer> colorCount = new HashMap<>();
                    for (Cards c : cards_in_hand) {
                        if (c instanceof NumberCard) {
                            NumberCard numberCard = (NumberCard) c;
                            if (colorCount.containsKey(numberCard.color)) {
                                colorCount.put(numberCard.color, colorCount.get(numberCard.color) + 1);
                            } else {
                                colorCount.put(numberCard.color, 1);
                            }


                        } else if (c instanceof ActionCards) {
                            ActionCards actionCards = (ActionCards) c;
                            if (colorCount.containsKey(actionCards.color)) {
                                colorCount.put(actionCards.color, colorCount.get(actionCards.color) + 1);
                            } else {
                                colorCount.put(actionCards.color, 1);
                            }
                        }

                    }
                    String color = Collections.max(colorCount.entrySet(), Map.Entry.comparingByValue()).getKey();
                    playWildCard(card, gameSession, color);
                }
            }
        }
        if (canplay!=false){
            drawCard(gameSession.deck);
        }
        if(cards_in_hand.size()==1){
            decleareUno();
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
        gameSession.color = color;
        gameSession.discardpile = card;
        cards_in_hand.remove(card);

    }
    public Integer cardCount(){
        return cards_in_hand.size();
    }

}
