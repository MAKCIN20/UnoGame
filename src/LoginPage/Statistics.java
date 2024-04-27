package LoginPage;

public class Statistics extends User{
    public int gamesPlayed;
    public int gamesWon;
    public int gamesLost;
    public int score;


    public Statistics(String[] usersList, String[] passwords, boolean isLogged, int gamesPlayed, int gamesWon, int gamesLost, int score) {
        super(isLogged);
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.score = score;
    }


}
