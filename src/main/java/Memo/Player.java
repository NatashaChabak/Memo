package Memo;

public class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name.isEmpty() ? "Player" : name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseScore() {
        score++;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
