package in.OnGrid.QuizPortal.model.dto;

public class SubmitQuizResponse {
    int score;
    int wrong;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }
}
