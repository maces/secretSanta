package de.akr.game.secretsanta;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akr on 15.12.15.
 */
public class SecretSantaTest {
    private SecretSanta santa;

    @Test(timeout = 3000)
    public void testSecretSanta() {
        for(int i = 0; i < 100; i++) {
            List<Pair<String, String>> participants = new ArrayList<>(3);
            participants.add(new Pair<>("Alex", "alex@alex"));
            participants.add(new Pair<>("Hans", "hans@hans"));
            participants.add(new Pair<>("Foo", "foo@foo"));

            Map<String, String> foundPossibilities = new HashMap<>(3);


            santa = new SecretSanta((participant, title, content, contentArgs)
                    -> foundPossibilities.put(participant, contentArgs[0].toString()),
                    participants);
            santa.startGame();

            foundPossibilities.entrySet().parallelStream()
                    .forEach((entry) ->
                            Assert.assertNotEquals(entry.getKey(), foundPossibilities.get(entry.getValue())));
        }
    }
}
