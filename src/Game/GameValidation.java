package Game;

import cards.ActionCards;
import cards.Cards;
import cards.NumberCard;
import exceptions.WrongCardPlayed;

public class GameValidation {
    public boolean validPlayNumberCard(NumberCard card,GameSession gameSession) throws WrongCardPlayed {
        if(card.color==gameSession.color || card.number==gameSession.discardpile.score){
            return true;
        }
        else{
            throw new WrongCardPlayed("Cannot play");
        }
    }
    public boolean validPlayActionsCards(ActionCards card, GameSession gameSession) throws WrongCardPlayed {
        if(card.color==gameSession.color ){
            return true;
        }
        else{
            throw new WrongCardPlayed("Cannot play this card");
        }
    }


}
