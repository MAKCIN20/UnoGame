package Game;

import cards.ActionCards;
import cards.Cards;
import cards.NumberCard;
import cards.WildCard;
import exceptions.WrongCardPlayed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameSession {
    public String username;
    public int sessionID;
    public ArrayList<Cards> deck;
    public String color;

    public ArrayList<Player> players;
    public boolean clockwise;
    public Cards discardpile;
    public Integer playerTurn;


    public GameSession(String username) {
        Random random = new Random();
        this.sessionID = random.nextInt(89999) + 10000;
        this.deck = setDeck();
        this.clockwise = true;
        this.discardpile = deck.get(50);
        this.username=username;
        this.players=createPlayer(this.deck);
        if (discardpile instanceof ActionCards){
            ActionCards card = (ActionCards) discardpile;
            this.color=card.color;
            }
        else if (discardpile instanceof NumberCard){
            NumberCard card = (NumberCard) discardpile;
            this.color=card.color;
        }
        else if (discardpile instanceof WildCard){
            this.color="red";
        }
        this.playerTurn=3;
    }


    public ArrayList<Cards> setDeck(){
        Cards cards = new Cards();
        cards.initializer();
        return cards.deck;
    }
    public ArrayList<Player> createPlayer(ArrayList<Cards> deck){
        Cards cards= new Cards();
        cards.deck=deck;

        ArrayList<Player> players= new ArrayList();
        Bot bot1 = new Bot(cards.seperateCards(deck));
        Bot bot2 = new Bot(cards.seperateCards(deck));
        Bot bot3 = new Bot(cards.seperateCards(deck));
        Player player = new Player(cards.seperateCards(cards.deck));
        players.add(bot1);
        players.add(bot2);
        players.add(bot3);
        players.add(player);
        return players;
    }
    public void drawCards(Cards discardpile){
            Player player = players.get(playerTurn);
            if (discardpile instanceof ActionCards) {
                ActionCards card = (ActionCards) discardpile;
                if (card.skill.equals("draw2")) {
                    player.drawCard(deck);
                    player.drawCard(deck);
                }
            }
            if (discardpile instanceof WildCard) {
                WildCard card = (WildCard) discardpile;
                if (card.skill.equals("draw4")) {
                    player.drawCard(deck);
                    player.drawCard(deck);
                    player.drawCard(deck);
                    player.drawCard(deck);

            }
        }
    }
    public void playerTurnPlay(String color) throws WrongCardPlayed {
        if (playerTurn != 3) {
            Bot bot = (Bot) players.get(playerTurn);
            bot.findCard(this);
            if (bot.cardCount() == 1) {
                bot.decleareUno();
            }
            if (bot.cardCount() == 0) {
                System.out.println("Player " + playerTurn + " wins");
            }
        }
        else if (playerTurn == 3){
            Player player = (Player) players.get(playerTurn);
            player.playCard(deck.get(0),this,color);
        }
    }



    public Integer findNextPlayer(boolean clockwise){
        if (clockwise){
            playerTurn++;
            if (playerTurn>3){
                playerTurn=0;
            }
        }
        else{
            playerTurn--;
            if (playerTurn<0){
                playerTurn=3;
            }
        }
        return playerTurn;

    }

    public boolean isGameEnd() {
        Bot bot1 = (Bot) players.get(0);
        Bot bot2 = (Bot) players.get(1);
        Bot bot3 = (Bot) players.get(2);
        Player player = (Player) players.get(3);
        if (bot1.cardCount() == 0 || bot2.cardCount() == 0 || bot3.cardCount() == 0 || player.getCards_in_hand().size()== 0) {
            return true;
        } else if (deck.size()==0) {
            return true;
        } else {
            return false;
        }


    }


}
