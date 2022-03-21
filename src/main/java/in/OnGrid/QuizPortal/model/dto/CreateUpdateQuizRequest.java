package in.OnGrid.QuizPortal.model.dto;

public class CreateUpdateQuizRequest {
    private String description;
    private String image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
