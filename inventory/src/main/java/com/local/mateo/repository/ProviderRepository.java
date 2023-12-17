package com.local.mateo.repository;

import com.local.mateo.entity.ProviderEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProviderRepository implements PanacheRepository<ProviderEntity>{}
