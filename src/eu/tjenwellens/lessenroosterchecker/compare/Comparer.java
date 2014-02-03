package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Tjen
 */
public class Comparer {
    Collection<KlassenHolder> holders = new LinkedList<>();

    public Comparer() {
        Waiter w = new Waiter(this);
        holders.add(new Ugent());
        holders.add(new Jaar1(w));
        holders.add(new Jaar2(w));
        w.setHolders(holders);
        w.start();
    }

    public void doneWaiting() {
        Collection<String> totalSelection = new LinkedList();
        for (KlassenHolder holder : holders) {
            totalSelection.addAll(LessenroosterChecker.extractVakken(holder.getKlassen()));
        }
        for (String vakNaam : totalSelection) {
            System.out.println(vakNaam);
        }
        System.exit(0);
    }
}
