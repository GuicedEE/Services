package com.guicedee.guicedinjection.interfaces;

import java.util.Comparator;
/**
 * Supplies standard set changer and comparable's for services
 *
 * @param <J>
 */
public interface IDefaultService<J extends IDefaultService<J>>
        extends Comparable<J>, Comparator<J> {

    /**
     * Method compare ...
     *
     * @param o1 of type J
     * @param o2 of type J
     * @return int
     */
    @Override
    default int compare(J o1, J o2) {
        if (o1 == null || o2 == null) {
            return -1;
        }
        return o1.sortOrder()
                .compareTo(o2.sortOrder());
    }

    /**
     * Default Sort Order 100
     *
     * @return 100
     */
    default Integer sortOrder() {
        return 100;
    }

    /**
     * Method compareTo ...
     *
     * @param o of type J
     * @return int
     */
    @Override
    default int compareTo( J o) {
        int sort = sortOrder().compareTo(o.sortOrder());
        if (sort == 0) {
            return -1;
        }
        return sort;
    }


}
