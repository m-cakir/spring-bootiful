package com.bootiful.framework.service;

import com.bootiful.framework.domain.BaseModel;
import com.bootiful.framework.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<E extends BaseModel, ID, T extends BaseRepository<E, ID>> {

    @Autowired
    T repository;

    //    @Cacheable(value = )
    public List<E> findAll() {
        return repository.findAll();
    }

    //    @Cacheable
    public List<E> findAll(Sort sort) {
        return repository.findAll();
    }

    //    @Cacheable(unless = "#result != null")
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    //    @CachePut(key = "#result.id", condition = "#result != null")
    @Transactional(readOnly = true)
    public E save(E entity) {
        return repository.save(entity);
    }

    //    @Cacheable
    public List<E> findAll(Specification<E> spec) {
        return repository.findAll(spec);
    }

    //    @Cacheable
    public Page<E> findAll(Specification<E> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    //    @Cacheable
    public List<E> findAll(Specification<E> spec, Sort sort) {
        return repository.findAll(spec, sort);
    }

    //    @Cacheable
    public <K> Page<K> findAll(Specification<E> spec, Class<K> type, Pageable pageable) {
        return repository.findAll(spec, type, pageable);
    }

    //    @CacheEvict
    public void delete(E entity) {
        repository.delete(entity);
    }

}
