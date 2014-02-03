package eu.tjenwellens.lessenroosterchecker.filters;

import eu.tjenwellens.lessenroosterchecker.elements.Les;

/**
 *
 * @author Tjen
 */
public interface Filter {
    boolean accept(Les les);
}
