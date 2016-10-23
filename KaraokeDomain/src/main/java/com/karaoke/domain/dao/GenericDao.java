package com.karaoke.domain.dao;

import java.util.List;

public interface GenericDao <T> {
    public List<T> retrieveAll();

    public T create(T t);

    public void delete(Object id);

    public T find(Object id);

    public T update(T t);   
}