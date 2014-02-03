package eu.tjenwellens.lessenroosterchecker.filters;

import eu.tjenwellens.lessenroosterchecker.elements.Les;

/**
 *
 * @author Tjen
 */
public abstract class AbstractFilter implements Filter {
    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Les) {
            // Les: check timestamps overlap
//            System.out.println("Filter equals les?");
            final Les other = (Les) obj;
            return !this.accept(other);
        } else if (obj instanceof Filter) {
            // Filter: check if filter applies
//            System.out.println("Filter equals filter?");
            return equals((Filter) obj);
        }
        return false;
    }

    protected abstract boolean equals(Filter filter);
}
