package co.edu.uniquindio.billeteradigitalapp.Utils;

public interface RabbitMessageListener {
    void onMessageReceived(String message);
}
