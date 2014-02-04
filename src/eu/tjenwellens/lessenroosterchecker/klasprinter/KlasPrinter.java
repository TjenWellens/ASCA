package eu.tjenwellens.lessenroosterchecker.klasprinter;

import eu.tjenwellens.lessenroosterchecker.compare.KlasComparer;
import eu.tjenwellens.lessenroosterchecker.elements.Duration;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import eu.tjenwellens.lessenroosterchecker.gui.VisualLessenRooster;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Tjen
 */
public class KlasPrinter extends KlasComparer {
    private Collection<String> selection;

    public KlasPrinter() {
        chatty = false;
        initSelection();
    }

    @Override
    protected void succes(Collection<Collection<Klas>> succes) {
        Map<String[][], String> roosters = createRoosters(succes);
        int total = -1;
        for (Map.Entry<String[][], String> entry : roosters.entrySet()) {
            String[][] rooster = entry.getKey();
            String title = entry.getValue();
            if (selection != null && !selection.contains(title)) {
                continue;
            }
            int newTotal = roosterTotal(rooster);
            if (total >= 0 && newTotal != total) {
                System.err.println("Oops: roosterTotal mismatch!");
            }
            total = newTotal;
//            System.out.println(roosterTotal(rooster));
            VisualLessenRooster.create(rooster, title);
        }
    }

    private Map<String[][], String> createRoosters(Collection<Collection<Klas>> succes) {
        Map<String[][], String> roosters = new HashMap<>();
        for (Collection<Klas> klassen : succes) {
            String[][] rooster = new String[7][(VisualLessenRooster.ROOSTER_END - VisualLessenRooster.ROOSTER_START) * 4];
            StringBuilder title = new StringBuilder("Klas: ");
            for (Klas klas : klassen) {
                title.append(klas.getKlasNaam()).append(' ');
                for (Les les : klas) {
                    addLesToRooster(rooster, les);
                }
            }
            roosters.put(rooster, title.toString());
//            VisualLessenRooster.create(rooster, title.toString());
        }
        return roosters;
    }

    void addLesToRooster(String[][] rooster, Les les) {
        int dag = les.getDag().ordinal();
        Duration duur = les.getDuur();
        int beginKwart = calcKwart(duur.getBegin().getUur(), duur.getBegin().getMinuten());
        int eindKwart = calcKwart(duur.getEind().getUur(), duur.getEind().getMinuten());
        for (int kwart = beginKwart; kwart < eindKwart; kwart++) {
            if (rooster[dag][kwart] != null && !rooster[dag][kwart].equals(les.getCursusNaam())) {
                System.err.println("Oops: overlappende lessen?" + rooster[dag][kwart] + "" + les.getCursusNaam());
            }
            rooster[dag][kwart] = les.getCursusNaam();
        }
    }

    int roosterTotal(String[][] rooster) {
        int total = 0;
        for (String[] days : rooster) {
            for (String kwartValue : days) {
                if (kwartValue != null) {
                    total++;
                }
            }
        }
        return total;
    }

    int calcKwart(int uur, int minuten) {
        return (uur - VisualLessenRooster.ROOSTER_START) * 4 + minuten / 25;
    }

    public static void main(String[] args) {
        new KlasPrinter().start();
    }

    private void printKlas(Klas klas) {
        System.out.println("Klas: " + klas.getKlasNaam());
        for (Vak vak : klas.getAllCourses()) {
            for (Les les : vak) {
                System.out.println(les);
            }
        }
        System.out.println();
    }

    private void initSelection() {
        selection = new HashSet<>();
        selection.add("Klas: BI2 1C3 2B2 ");
        selection.add("Klas: BI2 1D1 2B1 ");
        selection.add("Klas: BI2 1D1 2B2 ");
        selection.add("Klas: BI2 1C3 2B1 ");
    }
}
