package Game;

import cards.ActionCards;
import cards.Cards;
import cards.WildCard;
import exceptions.WrongCardPlayed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameSession {
    public int sessionID;
    public ArrayList<Cards> deck;

    public ArrayList players;
    public boolean clockwise;
    public Cards discardpile;


    public GameSession() {
        Random random = new Random();
        this.sessionID = random.nextInt(89999) + 10000;
        this.deck = setDeck();
        this.clockwise = true;
        this.discardpile = deck.get(50);
        this.players=createPlayer(this.deck);
    }

    public void startGame() throws WrongCardPlayed {
        Integer playerTurn=1;
        while (isGameEnd()){
            playerTurnPlay(playerTurn);
            playerTurn = findNextPlayer(playerTurn,clockwise);
            drawCards(discardpile,playerTurn);

        }
    }
    public ArrayList<Cards> setDeck(){
        Cards cards = new Cards();
        cards.initializer();
        return cards.deck;
    }
    public ArrayList createPlayer(ArrayList deck){
        Cards cards= new Cards();
        cards.deck=deck;

        ArrayList players= new ArrayList();
        Bot bot1 = new Bot(cards.seperateCards(deck));
        Bot bot2 = new Bot(cards.seperateCards(deck));
        Bot bot3 = new Bot(cards.seperateCards(deck));
        Player player = new Player(cards.seperateCards(deck));
        players.add(bot1);
        players.add(bot2);
        players.add(bot3);
        players.add(player);
        return players;
    }
    public void drawCards(Cards discardpile, Integer playerTurn){
        if (playerTurn!=4) {
            Bot bot = (Bot) players.get(playerTurn);
            if (discardpile instanceof ActionCards) {
                ActionCards card = (ActionCards) discardpile;
                if (card.skill.equals("draw2")) {
                    bot.drawCard(deck);
                    bot.drawCard(deck);
                }
            }
            if (discardpile instanceof WildCard) {
                WildCard card = (WildCard) discardpile;
                if (card.skill.equals("draw4")) {
                    bot.drawCard(deck);
                    bot.drawCard(deck);
                    bot.drawCard(deck);
                    bot.drawCard(deck);
                }
            }
        }
        else if(playerTurn==4){
            Player player = (Player) players.get(4);
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
    }
    public void playerTurnPlay(Integer playerTurn) throws WrongCardPlayed {
        if (playerTurn != 4) {
            Bot bot = (Bot) players.get(playerTurn);
            bot.selectCard(this);
            if (bot.cardCount() == 1) {
                bot.decleareUno();
            }
            if (bot.cardCount() == 0) {
                System.out.println("Player " + playerTurn + " wins");
            }
        }
        else if (playerTurn == 4){
            Player player = (Player) players.get(playerTurn);
            player.selectCard(deck.get(0),this,"red");
        }
    }



    public Integer findNextPlayer(Integer playerTurn,boolean clockwise){
        if (clockwise){
            playerTurn++;
            if (playerTurn>4){
                playerTurn=1;
            }
        }
        else{
            playerTurn--;
            if (playerTurn<1){
                playerTurn=4;
            }
        }
        return playerTurn;

    }

    public boolean isGameEnd() {
        Bot bot1 = (Bot) players.get(0);
        Bot bot2 = (Bot) players.get(1);
        Bot bot3 = (Bot) players.get(2);
        Player player = (Player) players.get(3);
        if (bot1.cardCount() != 0||bot2.cardCount() != 0||bot3.cardCount() != 0){
            return false;
        } else if (player.getCards_in_hand().size() != 0){
            return false;

        } else {
            return true;
        }


    }


}
