package in.OnGrid.QuizPortal.model.dto;

public class QuizSubmitAnswer {
    private Integer id;
    private Boolean answer;

    public QuizSubmitAnswer(Integer id, Boolean answer) {
        this.id = id;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }
}
