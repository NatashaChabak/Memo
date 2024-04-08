/**
 * This class CardList represents a Card object with attribute cards (ArrayList).
 * The constructor creates new ArrayList from the path to the folder containing jpg files.
 */

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
                String lastThreeSymbols = path.substring(Math.max(0, path.length() - 3));
                if (!lastThreeSymbols.equals("jpg") && !lastThreeSymbols.equals("png") ) {continue;}
                try {
                    Card card1 = new Card(path, counter);
                    Card card2 = new Card(path, counter);
                    cards.add(card1);
                    cards.add(card2);
                    String soundName = path.substring(0, path.length() - 3) + "mp3";
                    card1.setSoundFile(soundName);
                    card2.setSoundFile(soundName);
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
