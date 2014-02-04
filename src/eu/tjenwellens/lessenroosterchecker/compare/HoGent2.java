package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.LessenroosterChecker;
import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tjen
 */
public class HoGent2 extends VakSelector {
    public HoGent2(Object notify) {
        super(notify);
    }

    @Override
    protected List<Klas> loadVakken() {
        List<String> paths = new LinkedList<>();
        paths.add("jaar2.html");
        return LessenroosterChecker.getKlassen(paths);
    }
}