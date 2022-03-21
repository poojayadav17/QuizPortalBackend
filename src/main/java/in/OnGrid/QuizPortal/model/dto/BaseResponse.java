package in.OnGrid.QuizPortal.model.dto;

public class BaseResponse<T> extends BaseErrorResponse {
    private T data;

    public BaseResponse(int code, String message) {
        super(code, message);
    }

    public BaseResponse(int code, T data) {
        super(code);
        this.data = data;
    }

    public BaseResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
