package com.codegym.service.admin.impl;

import com.codegym.model.admin.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.codegym.repository.admin.IStatusRepository;
import com.codegym.service.admin.IStatusService;

public class StatusServiceImpl implements IStatusService {
    @Autowired
    private IStatusRepository iStatusRepository;

    @Override
    public Page<Status> findAllByNameContaining(String search, Pageable pageable) {
        return iStatusRepository.findAllByNameContaining(search, pageable);
    }

    @Override
    public Page<Status> findAll(Pageable pageable) {
        return iStatusRepository.findAll(pageable);
    }

    @Override
    public Status findById(Long id) {
        return iStatusRepository.findOne(id);
    }

    @Override
    public void save(Status model) {
        iStatusRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        iStatusRepository.delete(id);
    }
}