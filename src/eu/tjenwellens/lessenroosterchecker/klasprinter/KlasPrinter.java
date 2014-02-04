package eu.tjenwellens.lessenroosterchecker.klasprinter;

import eu.tjenwellens.lessenroosterchecker.compare.Jaar;
import eu.tjenwellens.lessenroosterchecker.compare.KlasComparer;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Les;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Tjen
 */
public class KlasPrinter extends KlasComparer {
    private Collection<String> klassen;

    public KlasPrinter(Collection<String> klassen) {
        this.klassen = klassen;
    }

    @Override
    public void doneWaiting() {
        printSelectedVakken();
        printJaarList(jaren);
        for (Jaar jaar : jaren) {
            for (Klas klas : jaar.getKlassen()) {
                if (klassen.contains(klas.getKlasNaam())) {
                    printKlas(klas);
                }
            }
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        new KlasPrinter(Arrays.asList(args)).start();
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
