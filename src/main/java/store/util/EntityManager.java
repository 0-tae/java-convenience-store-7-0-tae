package store.util;

import store.enums.EntityDataConfig;

import java.util.List;

public class EntityManager {
    public <T> List<T> findAll(Class<T> entityClass) {
        String dataPath = EntityDataConfig.findByEntityType(entityClass).getPath();
        MarkDownObject markDownObject = new MarkDownReader(dataPath).read();

        EntityConverter<MarkDownObject> entityConverter = new MarkDown2EntityConverter();
        return entityConverter.convert(entityClass, markDownObject);
    }
}
