package eu.tjenwellens.lessenroosterchecker.klasprinter;

import eu.tjenwellens.lessenroosterchecker.compare.KlasComparer;
import eu.tjenwellens.lessenroosterchecker.elements.Duration;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import eu.tjenwellens.lessenroosterchecker.gui.VisualLessenRooster;
import java.util.Collection;

/**
 *
 * @author Tjen
 */
public class KlasPrinter extends KlasComparer {
    private static final int ROOSTER_START = 8;
    private static final int ROOSTER_END = 18;

    public KlasPrinter() {
        chatty = false;
    }

    @Override
    protected void succes(Collection<Collection<Klas>> succes) {
        for (Collection<Klas> klassen : succes) {
            int[][] rooster = new int[7][(ROOSTER_END - ROOSTER_START) * 4];
            for (Klas klas : klassen) {
                for (Les les : klas) {
                    addLesToRooster(rooster, les);
                }
            }
            VisualLessenRooster.create(rooster);
        }
    }

    void addLesToRooster(int[][] rooster, Les les) {
        int dag = les.getDag().ordinal();
        Duration duur = les.getDuur();
        int beginKwart = calcKwart(duur.getBegin().getUur(), duur.getBegin().getMinuten());
        int eindKwart = calcKwart(duur.getEind().getUur(), duur.getEind().getMinuten());
        for (int kwart = beginKwart; kwart < eindKwart; kwart++) {
            rooster[dag][kwart]++;
        }
    }

    int calcKwart(int uur, int minuten) {
        return (uur - ROOSTER_START) * 4 + minuten / 25;
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
