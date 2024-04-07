package Memo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CardList {
    private final ArrayList<Card> cards;

    public CardList() { cards = new ArrayList<>();
    }

    public CardList(String folderName) throws IllegalArgumentException {
        if (folderName.isEmpty()) {
            throw new IllegalArgumentException();
        }

        cards = new ArrayList<>();
        File folder = new File(folderName);
        File[] files = folder.listFiles();

        int counter = 0;
        if (files != null) {
            for (File file : files) {
                String path = file.getAbsolutePath();
                try {
                    Card card1 = new Card(path, counter);
                    Card card2 = new Card(path, counter);
                    cards.add(card1);
                    cards.add(card2);
                    counter++;
                 } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }
        } else {
            throw new IllegalArgumentException();
        }
        Collections.shuffle(cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
