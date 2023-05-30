package com.example.book.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.book.Model.Category;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}