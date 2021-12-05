package com.example.databasehw8.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.databasehw8.domain.Course;
import com.example.databasehw8.repository.CourseRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {
	private CourseRepository courseRepository;

	public List<Course> findAll() {
		return courseRepository.findAll();
	}

	public long count() {
		return courseRepository.count();
	}

	public Course getById(String cno) {
		return courseRepository.getById(cno);
	}
}
