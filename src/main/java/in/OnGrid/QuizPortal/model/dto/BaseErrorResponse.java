package in.OnGrid.QuizPortal.model.dto;

public class BaseErrorResponse {
    private int code;
    private String message;

    public BaseErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseErrorResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
