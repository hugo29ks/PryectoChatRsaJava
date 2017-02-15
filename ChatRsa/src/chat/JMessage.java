package chat;
import java.io.Serializable;

public class JMessage implements Serializable {

    String text;
    public JMessage(String input) {
        text = input;
    }

}
