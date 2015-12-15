package de.akr.game.secretsanta.publish;

/**
 * Created by akr on 11.12.15.
 */
public interface SecretPublisher {
    void publish(String participant, String title, String content, Object... contentArgs);
}
