package ua.kpi.ecampus.util;

import java.util.Collection;

/**
 * Contains methods that validate passed data structure from Java collection class.
 *
 * Created by Administrator on 04.02.2016.
 */
public class CollectionValidator {

    public static void validateOnNull(Collection collection) {
        if (collection == null) {
            throw new IllegalArgumentException("The collection cannot be null");
        }
    }

    public static boolean isEmpty(Collection collection) {
        return collection.size() == 0;
    }
}
