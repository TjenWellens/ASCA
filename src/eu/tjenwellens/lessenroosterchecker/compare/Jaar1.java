package eu.tjenwellens.lessenroosterchecker.compare;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Tjen
 */
public class Jaar1 extends HoGent {
    public Jaar1(Object notify) {
        super(notify);
    }

    @Override
    protected Collection<String> getPaths() {
        Collection<String> paths = new LinkedList<>();
        paths.add("jaar1_a.html");
        paths.add("jaar1_b.html");
        return paths;
    }
}