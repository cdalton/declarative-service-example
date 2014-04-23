package ds.osgi.example.api;


public interface ServiceProxyMBean {
    public void startMessageService();
    public void stopMessageService();
    public void setMessage(String message);
    public String getMessage();
}
