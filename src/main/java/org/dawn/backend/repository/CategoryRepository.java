package org.dawn.backend.repository;

import org.dawn.backend.entity.Category;
import org.dawn.backend.repository.base.BaseRepository;

import java.util.Optional;

public interface CategoryRepository extends BaseRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
