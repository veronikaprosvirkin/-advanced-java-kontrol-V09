public class AppException extends RuntimeException{
    //base
    public AppException(String message){
        super(message);
    }
    public AppException(String message, Throwable cause){
        super(message, cause);
    }
}
class FraudCheckFailedException extends AppException {
    public FraudCheckFailedException(String message){
        super(message);
    }
}
