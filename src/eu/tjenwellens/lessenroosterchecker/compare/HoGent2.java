package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Tjen
 */
public class HoGent2 extends VakSelector {
    public HoGent2(Object notify) {
        super(notify);
    }

    @Override
    protected Collection<Klas> loadVakken() {
        Collection<String> paths = new LinkedList<>();
        paths.add("jaar2.html");
        return LessenroosterChecker.getKlassen(paths);
    }
}