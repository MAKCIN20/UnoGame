package GUI;

import Game.Bot;
import Game.GameSession;
import Game.Player;
import cards.ActionCards;
import cards.Cards;
import cards.NumberCard;
import cards.WildCard;
import exceptions.WrongCardPlayed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePage extends JFrame {
    private JPanel mainPanel;
    private JLabel sessionNameLabel;
    private DefaultListModel<String> playerHandModel;
    private JList<String> playerHandList;
    private JButton playCardButton;
    private JButton drawCardButton;
    private JLabel[] botLabels;
    private JLabel discardPileLabel;
    private JLabel gameDirectionLabel;
    private GameSession gameSession;

    public GamePage(GameSession gameSession) throws WrongCardPlayed {
        this.gameSession = gameSession;
        setTitle("Uno Game Page");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        setVisible(true);
        gameSession.startGame();
        updateGameState();
    }

    private void initializeComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);

        // Session Name
        sessionNameLabel = new JLabel("Session: " + gameSession.sessionID, SwingConstants.CENTER);
        sessionNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sessionNameLabel.setForeground(Color.WHITE);
        mainPanel.add(sessionNameLabel, BorderLayout.NORTH);

        // Bots Information
        JPanel botsPanel = new JPanel(new GridLayout(1, 3));
        botsPanel.setBackground(Color.DARK_GRAY);
        botLabels = new JLabel[3];

        for (int i = 0; i < gameSession.players.size() - 1; i++) {
            Bot bot = (Bot) gameSession.players.get(i);
            botLabels[i] = new JLabel("Bot: " + bot + " - Cards: " + bot.cardCount(), SwingConstants.CENTER);
            botLabels[i].setFont(new Font("Arial", Font.PLAIN, 18));
            botLabels[i].setForeground(Color.WHITE);
            botsPanel.add(botLabels[i]);
        }
        mainPanel.add(botsPanel, BorderLayout.NORTH);

        // Discard Pile
        discardPileLabel = new JLabel("Discard Pile: " + getTopDiscardCard(), SwingConstants.CENTER);
        discardPileLabel.setFont(new Font("Arial", Font.BOLD, 18));
        discardPileLabel.setForeground(Color.WHITE);
        mainPanel.add(discardPileLabel, BorderLayout.CENTER);

        // Game Direction
        gameDirectionLabel = new JLabel("Game Direction: " + (gameSession.clockwise ? "Clockwise" : "Counter-Clockwise"), SwingConstants.CENTER);
        gameDirectionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gameDirectionLabel.setForeground(Color.WHITE);
        mainPanel.add(gameDirectionLabel, BorderLayout.SOUTH);

        // Player Hand
        playerHandModel = new DefaultListModel<>();
        playerHandList = new JList<>(playerHandModel);
        playerHandList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerHandList.setBackground(Color.LIGHT_GRAY);
        playerHandList.setForeground(Color.BLACK);
        JScrollPane playerHandScrollPane = new JScrollPane(playerHandList);

        JLabel playerLabel = new JLabel("Your Hand:", SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerLabel.setForeground(Color.WHITE);

        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBackground(Color.DARK_GRAY);
        playerPanel.add(playerLabel, BorderLayout.NORTH);
        playerPanel.add(playerHandScrollPane, BorderLayout.CENTER);

        playCardButton = new JButton("Play Selected Card");
        playCardButton.setBackground(Color.BLACK);
        playCardButton.setForeground(Color.WHITE);
        playCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    playSelectedCard();
                } catch (WrongCardPlayed ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        playerPanel.add(playCardButton, BorderLayout.SOUTH);

        drawCardButton = new JButton("Draw Card");
        drawCardButton.setBackground(Color.BLACK);
        drawCardButton.setForeground(Color.WHITE);
        drawCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    drawCard();
                } catch (WrongCardPlayed ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        playerPanel.add(drawCardButton, BorderLayout.NORTH);

        mainPanel.add(playerPanel, BorderLayout.SOUTH);

        // Adding mainPanel to Frame
        add(mainPanel);

        loadPlayerHand();
    }

    private String getTopDiscardCard() {
        if (gameSession.discardpile instanceof NumberCard) {
            NumberCard numberCard = (NumberCard) gameSession.discardpile;
            return numberCard.number + " " + numberCard.color;
        } else if (gameSession.discardpile instanceof ActionCards) {
            ActionCards actionCard = (ActionCards) gameSession.discardpile;
            return actionCard.skill + " " + actionCard.color;
        } else {
            WildCard wildCard = (WildCard) gameSession.discardpile;
            return wildCard.skill;
        }
    }

    private void loadPlayerHand() {
        Player player = (Player) gameSession.players.get(gameSession.players.size() - 1);
        playerHandModel.clear();

        for (Cards card : player.getCards_in_hand()) {
            if (card instanceof NumberCard) {
                NumberCard numberCard = (NumberCard) card;
                playerHandModel.addElement(numberCard.number + " " + numberCard.color);
            } else if (card instanceof ActionCards) {
                ActionCards actionCard = (ActionCards) card;
                playerHandModel.addElement(actionCard.skill + " " + actionCard.color);
            } else if (card instanceof WildCard) {
                WildCard wildCard = (WildCard) card;
                playerHandModel.addElement(wildCard.skill);
            }
        }
    }

    private void playSelectedCard() throws WrongCardPlayed {
        int selectedIndex = playerHandList.getSelectedIndex();
        Player player = (Player) gameSession.players.get(gameSession.players.size() - 1);
        if (selectedIndex != -1) {
            Cards selectedCard = player.getCards_in_hand().get(selectedIndex);
            System.out.println("Playing card: " + selectedCard);

            if (selectedCard instanceof WildCard) {
                String[] colors = {"Red", "Green", "Blue", "Yellow"};
                String selectedColor = (String) JOptionPane.showInputDialog(
                        this,
                        "Select a color:",
                        "Wild Card Color Selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        colors,
                        colors[0]
                );
                if (selectedColor != null) {
                    player.selectCard(selectedCard, gameSession, selectedColor.toLowerCase());
                }
            } else {
                player.selectCard(selectedCard, gameSession, ""); // No color for non-wild cards
            }
            discardPileLabel.setText("Discard Pile: " + getTopDiscardCard()); // Update discard pile
            updateGameState(); // Update state after playing card
        } else {
            JOptionPane.showMessageDialog(this, "Please select a card to play.");
        }
    }

    private void drawCard() throws WrongCardPlayed {
        Player player = (Player) gameSession.players.get(gameSession.players.size() - 1);
        player.drawCard(gameSession.deck);
        loadPlayerHand();
        JOptionPane.showMessageDialog(this, "You drew a card.");
        updateGameState();
    }

    private void updateGameState() throws WrongCardPlayed {
     gameSession.startGame();
     loadPlayerHand();

    }

    public static void main(String[] args) throws WrongCardPlayed {
        // Example GameSession initialization
        GameSession gameSession = new GameSession();

        // Launch GamePage
        new GamePage(gameSession);
    }
}
