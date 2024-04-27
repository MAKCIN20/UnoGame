package cards;

import java.util.Random;

public class Cards {
    public int score;

    public NumberCard[] intiliazeNumberCard() {
        String[] colorOptions= {"red", "blue", "green", "yellow"};
        NumberCard[] numberCards = new NumberCard[76];
        int index=0;
        for (String colors: colorOptions) {
            for (int i = 0; i < 10; i++) {
                if(i == 0) {
                    numberCards[index] = new NumberCard(i, colors);
                    index++;
                }
                else {
                    numberCards[index] = new NumberCard(i, colors);
                    index++;
                    numberCards[index] = new NumberCard(i, colors);
                    index++;
                }
            }
        }
        return numberCards;
    }
    public ActionCards[] initiliazeActionCards() {
        String[] colorOptions= {"red", "blue", "green", "yellow"};
        String[] actionOptions= {"skip", "reverse", "draw2"};
        ActionCards[] actionCards = new ActionCards[24];
        int index=0;
        for (String options: actionOptions) {
            for (String colors : colorOptions) {
                for (int i = 0; i < 2; i++) {
                    actionCards[index] = new ActionCards(options, colors);
                    index++;
                }
            }
        }
        return actionCards;
    }

    public WildCard[] initializeWildCard() {
        WildCard[] wildCards = new WildCard[8];
        String[] wildOptions = {"wild", "draw4"};
        int index = 0;
        for (String options: wildOptions) {
            for (int i = 0; i < 4; i++) {
                wildCards[index] = new WildCard(options);
                index++;
            }
        }
        return wildCards;
    }
    public void shuffleCards(NumberCard[] numberCards, ActionCards[] actionCards, WildCard[] wildCards){
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int randomIndex1 = random.nextInt(76);
            int randomIndex2 = random.nextInt(76);
            NumberCard temp = numberCards[randomIndex1];
            numberCards[randomIndex1] = numberCards[randomIndex2];
            numberCards[randomIndex2] = temp;
        }
        for (int i = 0; i < 1000; i++) {
            int randomIndex1 = random.nextInt(24);
            int randomIndex2 = random.nextInt(24);
            ActionCards temp = actionCards[randomIndex1];
            actionCards[randomIndex1] = actionCards[randomIndex2];
            actionCards[randomIndex2] = temp;
        }
        for (int i = 0; i < 1000; i++) {
            int randomIndex1 = random.nextInt(8);
            int randomIndex2 = random.nextInt(8);
            WildCard temp = wildCards[randomIndex1];
            wildCards[randomIndex1] = wildCards[randomIndex2];
            wildCards[randomIndex2] = temp;
        }
    }

}