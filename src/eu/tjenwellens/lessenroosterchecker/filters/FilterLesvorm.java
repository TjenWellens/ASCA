package eu.tjenwellens.lessenroosterchecker.filters;

import eu.tjenwellens.lessenroosterchecker.elements.Les;
import java.util.Objects;

/**
 *
 * @author Tjen
 */
public class FilterLesvorm extends AbstractFilter {
    private String lesVorm;

    public FilterLesvorm(String lesVorm) {
        this.lesVorm = lesVorm;
    }

    @Override
    public boolean accept(Les les) {
        return lesVorm.equals(les.getLesvorm());
    }

    @Override
    protected boolean equals(Filter otherFilter) {
        if (otherFilter == null) {
            return false;
        }
        if (getClass() != otherFilter.getClass()) {
            return false;
        }
        final FilterLesvorm other = (FilterLesvorm) otherFilter;
        if (!Objects.equals(this.lesVorm, other.lesVorm)) {
            return false;
        }
        return true;
    }
}
