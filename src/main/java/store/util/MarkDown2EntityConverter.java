package store.util;

import store.annotations.Column;
import store.annotations.EntityConstructor;
import store.annotations.Param;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MarkDown2EntityConverter implements EntityConverter<MarkDownObject> {
    @Override
    public <T> List<T> convert(Class<T> entityClass, MarkDownObject markDownObject) {
        List<String> columnsInClass = getColumnInClass(entityClass);
        List<String> columnsInMarkDown = markDownObject.getColumns();
        List<T> entities = new ArrayList<>();

        for (List<String> raw : markDownObject.getRaws()) {
            HashMap<String, String> columnValueMap = getColumnValueMap(columnsInClass, columnsInMarkDown, raw);
            Constructor<T> defindEntityConstructor = getEntityConstructor(entityClass);
            List<Object> entityParamValues = mappingParams(defindEntityConstructor.getParameters(), columnValueMap);
            entities.add(createNewInstance(defindEntityConstructor, entityParamValues));
        }

        return entities;
    }

    private static <T> T createNewInstance(Constructor<T> defindEntityConstructor, List<Object> entityParamValues) {
        try {
            return defindEntityConstructor.newInstance(entityParamValues.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }


    private List<Object> mappingParams(Parameter[] params, HashMap<String, String> columnValuesMap) {
        List<Object> finalParams = new ArrayList<>();
        for (Parameter param : params) {
            String paramName = getParamName(param);
            String value = columnValuesMap.getOrDefault(paramName, null);
            value = isEmpty(value);
            finalParams.add(mappingParam(param, value));
        }
        return finalParams;
    }

    private String isEmpty(String value) {
        if (value.equals("null")) {
            value = null;
        }
        return value;
    }

    private String getParamName(Parameter param) {
        String paramName = param.getAnnotation(Param.class).name();
        if (paramName.equals("")) {
            paramName = param.getName();
        }
        return paramName;
    }

    private Object mappingParam(Parameter param, String givenValue) {
        Class<?> type = param.getType();

        if (type.equals(Integer.class) || type.equals(int.class)) {
            return Integer.parseInt(givenValue);
        } else if (type.equals(LocalDate.class)) {
            return LocalDate.parse(givenValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (type.equals(String.class)) {
            return givenValue;
        }

        throw new IllegalStateException("지원하지 않는 타입입니다: " + type.getName());
    }

    private <T> Constructor<T> getEntityConstructor(Class<T> entityClass) {
        for (Constructor<?> constructor : entityClass.getConstructors()) {
            if (constructor.isAnnotationPresent(EntityConstructor.class)) {
                return (Constructor<T>) constructor;
            }
        }

        throw new IllegalStateException("EntityConsturctor가 없습니다.");
    }

    private <T> List<String> getColumnInClass(Class<T> entityClass) {
        return getAnnotationValues(entityClass, Column.class);
    }


    private <T, A extends Annotation> List<String> getAnnotationValues(Class<T> entityClass, Class<A> annotationClass) {
        List<Field> fields = List.of(entityClass.getDeclaredFields());
        List<String> result = new ArrayList<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClass)) {
                String name = getClassName(annotationClass, field);
                result.add(name);
            }
        }
        return result;
    }

    private <A extends Annotation> String getClassName(Class<A> annotationClass, Field field) {
        String name = "";

        if (annotationClass == Column.class) {
            name = (field.getAnnotation(Column.class)).name();
        } else if (annotationClass == Param.class) {
            name = (field.getAnnotation(Param.class)).name();
        }

        name = getFieldName(field, name);
        return name;
    }

    private String getFieldName(Field field, String name) {
        if (name.equals("")) {
            name = field.getName();
        }
        return name;
    }

    private HashMap<String, String> getColumnValueMap(List<String> columnsInClass, List<String> columnsInMarkDown, List<String> values) {
        HashMap<String, String> mdColumnValueMap = new HashMap<>();
        HashMap<String, String> classColumnValueMap = new HashMap<>();

        columnsInMarkDown.forEach(column -> mdColumnValueMap.put(column, values.get(mdColumnValueMap.size())));
        columnsInClass.forEach(column -> copyIfPresent(mdColumnValueMap, classColumnValueMap, column));

        return classColumnValueMap;
    }

    private <K, V> void copyIfPresent(HashMap<K, V> src, HashMap<K, V> dest, K key) {
        dest.put(key, src.getOrDefault(key, null));
    }
}
