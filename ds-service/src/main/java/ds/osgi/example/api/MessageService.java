package ds.osgi.example.api;

public interface MessageService {
    public void startMessageService();
    public void stopMessageService();
    public void setMessage(String message);
    public String getMessage();
}
