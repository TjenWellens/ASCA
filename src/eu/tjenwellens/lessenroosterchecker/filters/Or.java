package eu.tjenwellens.lessenroosterchecker.filters;

import eu.tjenwellens.lessenroosterchecker.elements.Les;
import java.util.Objects;

/**
 *
 * @author Tjen
 */
public class Or extends AbstractFilter {
    Filter base1, base2;

    public Or(Filter base1, Filter base2) {
        this.base1 = base1;
        this.base2 = base2;
    }

    @Override
    public boolean accept(Les les) {
        return base1.accept(les) || base2.accept(les);
    }

    @Override
    protected boolean equals(Filter otherFilter) {
        if (otherFilter == null) {
            return false;
        }
        if (getClass() != otherFilter.getClass()) {
            return false;
        }
        final Or other = (Or) otherFilter;
        if (!Objects.equals(this.base1, other.base1)) {
            return false;
        }
        if (!Objects.equals(this.base2, other.base2)) {
            return false;
        }
        return true;
    }
}
