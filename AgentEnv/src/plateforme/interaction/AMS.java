package plateforme.interaction;

public interface AMS {
	void send(Message m) throws SendMessageException;
}
