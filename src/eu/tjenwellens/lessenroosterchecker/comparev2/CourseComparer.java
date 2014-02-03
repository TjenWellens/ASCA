package eu.tjenwellens.lessenroosterchecker.comparev2;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import eu.tjenwellens.lessenroosterchecker.compare.HoGent1;
import eu.tjenwellens.lessenroosterchecker.compare.HoGent2;
import eu.tjenwellens.lessenroosterchecker.compare.Jaar;
import eu.tjenwellens.lessenroosterchecker.compare.UGent2;
import eu.tjenwellens.lessenroosterchecker.compare.Waiter;
import eu.tjenwellens.lessenroosterchecker.compare.WaiterWaiter;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import eu.tjenwellens.lessenroosterchecker.elements.Vak;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Tjen
 */
public class CourseComparer implements WaiterWaiter {
    private static final boolean goCrazy = false;
    private Collection<Jaar> jaren = new LinkedList<>();
    private Waiter w;

    public CourseComparer() {
        w = new Waiter(this);
        jaren.add(new UGent2(w));
        jaren.add(new HoGent1(w));
        jaren.add(new HoGent2(w));
        w.setHolders(jaren);
    }

    public void start() {
        w.start();
    }

    @Override
    public void doneWaiting() {
        printSelectedVakken();
        System.out.println("\n========================\n");
        // convert jaren to courses
        Courses courses = new Courses();
        for (Jaar jaar : jaren) {
            for (Klas klas : jaar.getKlassen()) {
                courses.addAll(klas);
            }
        }
        System.out.println("\n========================\n");
        Collection<Collection<Vak>> succes = courses.getMatchingCourses();
        if (succes.isEmpty()) {
            System.out.println("Geen mogelijke oplossingen gevonden");
            System.exit(0);
        }
        System.out.println("Aantal oplossingen: " + succes.size());
        if (!goCrazy) {
            System.exit(0);
        }
        int teller = 1;
        for (Collection<Vak> vakkenMatch : succes) {
            System.out.println("Oplossing " + teller + ": ");
            for (Vak vak : vakkenMatch) {
                System.out.print(vak.getKlasNaam());
                System.out.print('\t');
            }
            System.out.println();
            teller++;
        }
        System.exit(0);
    }

    private void printSelectedVakken() {
        Collection<String> totalSelection = new LinkedList();
        for (Jaar jaar : jaren) {
            totalSelection.addAll(LessenroosterChecker.extractVakken(jaar.getKlassen()));
        }
        for (String vakNaam : totalSelection) {
            System.out.println(vakNaam);
        }
    }

    public static void main(String[] args) {
        new CourseComparer().start();
    }
}
