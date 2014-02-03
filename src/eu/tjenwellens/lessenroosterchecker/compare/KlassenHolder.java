package eu.tjenwellens.lessenroosterchecker.compare;

import eu.tjenwellens.lessenroosterchecker.elements.Klas;
import java.util.Collection;

/**
 *
 * @author Tjen
 */
public interface KlassenHolder {
    boolean isReady();

    Collection<Klas> getKlassen();
}
