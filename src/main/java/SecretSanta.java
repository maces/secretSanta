import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by akr on 08.12.15.
 */
public class SecretSanta {

    private List<String> participants;

    public SecretSanta(final List<String> participants) {
        this.participants = new ArrayList<String>(participants.size());
        for (String participant : participants) {
            this.participants.add(participant);
        }
    }

    public SecretSanta(String... participants) {
        this.participants = new ArrayList<String>(Arrays.asList(participants));
    }

    public void ziehen(final String participant) {
        Random random = new Random();

        String user;
        do {
            int index = random.nextInt(participants.size());
            user = participants.get(index);
        } while (user.equals(participant));

        System.out.println(user + " hat " + participant + " gezogen! ;D");
        participants.remove(user);
    }

    public static void main(String[] args) {
        List<String> participants = Arrays.asList("Alex", "Torben", "Sandra", "Jenny", "Tim", "Jens", "Marcus");
        SecretSanta santa = new SecretSanta(participants);

        for (String participant : participants) {
            santa.ziehen(participant);
        }
    }

}
