package cards;

import java.util.*;

public class Cards {
    public int score;
    public ArrayList<Cards> deck;

    public Cards() {
        this.score = 0;
        this.deck = new ArrayList<>();
    }
    public ArrayList<Cards> seperateCards(ArrayList deck){
        ArrayList cards_to_give=new ArrayList();
        ArrayList<Cards> temp_deck=new ArrayList<Cards>(deck);
        int index=0;
        for (int i=0;i<7;i++){
            cards_to_give.add(temp_deck.get(i));
            deck.remove(temp_deck.get(i));

        }

        return cards_to_give;

    }

    public void initializer() {
        List<NumberCard> numberCards = initializeNumberCard();
        List<ActionCards> actionCards = initializeActionCards();
        List<WildCard> wildCards = initializeWildCard();

        deck.addAll(numberCards);
        deck.addAll(actionCards);
        deck.addAll(wildCards);
        shuffleCards();
    }

    public List<NumberCard> initializeNumberCard() {
        String[] colorOptions = {"red", "blue", "green", "yellow"};
        List<NumberCard> numberCards = new ArrayList<>();
        for (String color : colorOptions) {
            numberCards.add(new NumberCard(0, color));
            for (int i = 1; i < 10; i++) {
                numberCards.add(new NumberCard(i, color));
                numberCards.add(new NumberCard(i, color));
            }
        }
        return numberCards;
    }

    public List<ActionCards> initializeActionCards() {
        String[] colorOptions = {"red", "blue", "green", "yellow"};
        String[] actionOptions = {"skip", "reverse", "draw2"};
        List<ActionCards> actionCards = new ArrayList<>();
        for (String action : actionOptions) {
            for (String color : colorOptions) {
                actionCards.add(new ActionCards(action, color));
                actionCards.add(new ActionCards(action, color));
            }
        }
        return actionCards;
    }

    public List<WildCard> initializeWildCard() {
        List<WildCard> wildCards = new ArrayList<>();
        String[] wildOptions = {"wild", "draw4"};
        for (String option : wildOptions) {
            for (int i = 0; i < 4; i++) {
                wildCards.add(new WildCard(option));
            }
        }
        return wildCards;
    }
    public void shuffleCards() {
        Collections.shuffle(deck);
    }
}
