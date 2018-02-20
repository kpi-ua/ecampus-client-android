package ua.kpi.ecampus.model.dao;

import java.util.Collection;

/**
 * Provides general interface to the specific data access object of the model.
 * Access to data varies depending on the source of the data.
 */
public interface IDataAccessObject<T> {

    /**
     * Get data.
     * @return list of data.
     */
    Collection<T> getData();

    /**
     * Set data.
     * @param data has to be persisted.
     */
    void setData(Collection<T> data);

    /**
     * Update specific object.
     * @param object has to be updated.
     */
    void update(T object);

    /**
     * Delete specific object.
     * @param object has to be deleted.
     */
    void delete(T object);
}
