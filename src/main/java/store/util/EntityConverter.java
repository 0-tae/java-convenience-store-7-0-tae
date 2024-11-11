package store.util;

import java.util.List;

public interface EntityConverter<E> {
    <T> List<T> convert(Class<T> entityClass, E source);
}
