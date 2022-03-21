package in.OnGrid.QuizPortal.model.dto;

public class CreateUpdateQuestionRequest {
    private Integer id;
    private String description;
    private Boolean answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public Boolean getAnswer() {
        return answer;
    }
}
