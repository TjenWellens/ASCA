package eu.tjenwellens.lessenroosterchecker.compare;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Tjen
 */
public class Jaar2 extends HoGent {
    public Jaar2(Object notify) {
        super(notify);
    }

    @Override
    protected Collection<String> getPaths() {
        Collection<String> paths = new LinkedList<>();
        paths.add("jaar2.html");
        return paths;
    }
}