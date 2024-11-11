package store.exception;

public enum FileManagerExceptionMassage {
    FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
    FILE_WRITE_ERROR("파일을 쓰는 도중 오류가 발생 하였습니다."),
    FILE_READ_ERROR("파일을 읽는 도중 오류가 발생 하였습니다.");

    private final String message;

    FileManagerExceptionMassage(String message) {
        this.message = "[ERROR] " + message;
    }

    public String getMessage() {
        return this.message;
    }
}
