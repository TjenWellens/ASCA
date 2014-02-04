package eu.tjenwellens.lessenroosterchecker.klasprinter;

import eu.tjenwellens.lessenroosterchecker.compare.KlasComparer;
import eu.tjenwellens.lessenroosterchecker.elements.Duration;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import eu.tjenwellens.lessenroosterchecker.gui.VisualLessenRooster;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tjen
 */
public class KlasPrinter extends KlasComparer {
    public KlasPrinter() {
        chatty = false;
    }

    @Override
    protected void succes(Collection<Collection<Klas>> succes) {
        Map<int[][], String> roosters = createRoosters(succes);
        for (Map.Entry<int[][], String> entry : roosters.entrySet()) {
            int[][] rooster = entry.getKey();
            String title = entry.getValue();
            System.out.println(roosterTotal(rooster));
            VisualLessenRooster.create(rooster, title);
        }
    }

    private Map<int[][], String> createRoosters(Collection<Collection<Klas>> succes) {
        Map<int[][], String> roosters = new HashMap<>();
        for (Collection<Klas> klassen : succes) {
            int[][] rooster = new int[7][(VisualLessenRooster.ROOSTER_END - VisualLessenRooster.ROOSTER_START) * 4];
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

    void addLesToRooster(int[][] rooster, Les les) {
        int dag = les.getDag().ordinal();
        Duration duur = les.getDuur();
        int beginKwart = calcKwart(duur.getBegin().getUur(), duur.getBegin().getMinuten());
        int eindKwart = calcKwart(duur.getEind().getUur(), duur.getEind().getMinuten());
        for (int kwart = beginKwart; kwart < eindKwart; kwart++) {
            rooster[dag][kwart] = 1;
        }
    }

    int roosterTotal(int[][] rooster) {
        int total = 0;
        for (int[] days : rooster) {
            for (int kwartValue : days) {
                total += kwartValue;
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
}
