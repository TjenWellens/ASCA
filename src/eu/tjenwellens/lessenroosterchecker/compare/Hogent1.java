package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Tjen
 */
public class HoGent1 extends VakSelector {
    public HoGent1(Object notify) {
        super(notify);
    }

    @Override
    protected Collection<Klas> loadVakken() {
        Collection<String> paths = new LinkedList<>();
        paths.add("jaar1_a.html");
        paths.add("jaar1_b.html");
        return LessenroosterChecker.getKlassen(paths);
    }
}