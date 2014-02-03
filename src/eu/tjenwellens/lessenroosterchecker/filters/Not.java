package eu.tjenwellens.lessenroosterchecker.filters;

import eu.tjenwellens.lessenroosterchecker.elements.Les;
import java.util.Objects;

/**
 *
 * @author Tjen
 */
public class Not extends AbstractFilter {
    Filter base;

    public Not(Filter base) {
        this.base = base;
    }

    @Override
    public boolean accept(Les les) {
        return !base.accept(les);
    }

    @Override
    protected boolean equals(Filter otherFilter) {
        if (otherFilter == null) {
            return false;
        }
        if (getClass() != otherFilter.getClass()) {
            return false;
        }
        final Not other = (Not) otherFilter;
        if (!Objects.equals(this.base, other.base)) {
            return false;
        }
        return true;
    }
}
