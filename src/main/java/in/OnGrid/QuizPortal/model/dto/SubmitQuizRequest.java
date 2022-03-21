package in.OnGrid.QuizPortal.model.dto;

import java.util.List;

public class SubmitQuizRequest {
    private List<QuizSubmitAnswer> submitAnswers;

    public SubmitQuizRequest(List<QuizSubmitAnswer> submitAnswers) {
        this.submitAnswers = submitAnswers;
    }
    public SubmitQuizRequest(){

    }
    public List<QuizSubmitAnswer> getSubmitAnswers() {
        return submitAnswers;
    }

    public void setSubmitAnswers(List<QuizSubmitAnswer> submitAnswers) {
        this.submitAnswers = submitAnswers;
    }
}
