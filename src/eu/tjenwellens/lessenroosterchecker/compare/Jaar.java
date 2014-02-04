package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import java.util.List;

/**
 *
 * @author Tjen
 */
public interface Jaar {
    boolean isReady();

    List<Klas> getKlassen();

    void start();
}
