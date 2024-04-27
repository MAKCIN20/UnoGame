package test;
import LoginPage.User;
import cards.ActionCards;
import cards.Cards;
import cards.NumberCard;
import cards.WildCard;

public class Main {
    public static void main(String[] args) {
        Cards card = new Cards();
        NumberCard[] deck=card.intiliazeNumberCard();
        ActionCards[] actionDeck=card.initiliazeActionCards();
        WildCard[] wildDeck=card.initializeWildCard();
        User x=new User(false);
        System.out.println(x);


    }

}