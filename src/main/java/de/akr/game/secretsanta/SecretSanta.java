package de.akr.game.secretsanta;

import de.akr.game.secretsanta.publish.MailPublisher;
import de.akr.game.secretsanta.publish.SecretPublisher;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by akr on 08.12.15.
 */
public class SecretSanta {

    private SecretPublisher publisher;

    private List<Pair<String, String>> participants;

    private Map<String, String> collected;

    public static void main(String[] args) {
        // like mail.fh-.de
        String serverAddress = args[0];
        // like 587
        String port = args[1];
        // like ak902764@fh-
        String username = args[2];
        // like 123456
        String password = args[3];
        // like ak902764@fh-
        String senderAddress = args[4];
        // Properties-file in style: name=email
        Properties participantsInProps = new Properties();
        try (InputStream is = new FileInputStream(args[5])) {
            participantsInProps.load(is);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        List<Pair<String, String>> participants = new ArrayList<>(participantsInProps.size());
        participantsInProps.forEach((name, mail) -> participants.add(new Pair<>((String) name, (String) mail)));

        MailPublisher publisher = new MailPublisher(serverAddress, port, username, password, senderAddress);
        SecretSanta santa = new SecretSanta(publisher, participants);
        santa.startGame();

    }

    public SecretSanta(final SecretPublisher publisher, final List<Pair<String, String>> participants) {
        this.publisher = publisher;
        this.participants = new ArrayList<>(participants.size());
        participants.forEach(participant -> this.participants.add(participant));
        this.collected = new HashMap<>(participants.size());
    }

    public void startGame() {
        participants.forEach(pair -> ziehen(pair.getKey(), pair.getValue()));
    }

    public void ziehen(final String name, final String address) {
        Random random = new Random(new Date().getTime());

        Pair<String, String> user;
        do {
            int index = random.nextInt(participants.size());
            user = participants.get(index);
        } while (user.getKey().equals(name)
                || this.collected.containsValue(user.getKey())
                || (this.collected.containsKey(user.getKey()) && this.collected.get(user.getKey()).equals(name)));

        this.collected.put(name, user.getKey());

        publisher.publish(address, "Secret Santaaa!", "Du hast %s gezogen! ;D", user.getKey());
    }
// Peter zieht Sasch
// Sasch zieht Peter
}
