package com.example.databasehw8.university.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.databasehw8.university.domain.Enroll;
import com.example.databasehw8.university.domain.EnrollId;
import com.example.databasehw8.university.domain.Student;
import com.example.databasehw8.university.projection.CntSnoByGrade;
import com.example.databasehw8.university.projection.MeanExamByCno;
import com.example.databasehw8.university.projection.SumCreditMeanExam;
import com.example.databasehw8.university.repository.CourseRepository;
import com.example.databasehw8.university.repository.EnrollRepository;
import com.example.databasehw8.university.repository.StudentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EnrollService {
	private EnrollRepository enrollRepository;
	private StudentRepository studentRepository;
	private CourseRepository courseRepository;

	public List<Enroll> findAll() {
		return enrollRepository.findAll();
	}

	public long count() {
		return enrollRepository.count();
	}

	/**
	 * 각 성적 별 잘못 채점된 것들을 찾아 합쳐 반환.
	 * @return
	 */
	public List<Enroll> findWrongGraded() {
		List<Enroll> wrongGradedEnrollList = new ArrayList<>();
		wrongGradedEnrollList.addAll(enrollRepository.findWrongGraded("A", 90, 100));
		wrongGradedEnrollList.addAll(enrollRepository.findWrongGraded("B", 80, 89));
		wrongGradedEnrollList.addAll(enrollRepository.findWrongGraded("C", 70, 79));
		wrongGradedEnrollList.addAll(enrollRepository.findWrongGraded("D", 60, 69));
		wrongGradedEnrollList.addAll(enrollRepository.findWrongGraded("F", 0, 60));
		return wrongGradedEnrollList;
	}

	@Transactional
	public void correctWrongGrade(Integer sno, String cno) {
		EnrollId enrollId = new EnrollId(
			studentRepository.getById(sno),
			courseRepository.getById(cno)
		);
		Enroll wrongEnroll = enrollRepository.getById(enrollId);
		wrongEnroll.correctGrade();
	}

	public List<Enroll> findByStudent(Student student) {
		return enrollRepository.findAllByStudent(student);
	}

	public List<Enroll> searchBySnoAndCname(Integer sno, String cname) {
		return enrollRepository.findAllByStudentAndCname(sno, cname);
	}

	public List<Enroll> searchBySnoAndCredit(Integer sno, Integer credit) {
		return enrollRepository.findAllByStudentAndCredit(sno, credit);
	}

	public List<Enroll> searchBySnoAndExam(Integer sno, Integer exam) {
		return enrollRepository.findAllByStudentAndExam(sno, exam);
	}

	public List<Enroll> searchByMinMaxExamStudent() {
		List<Enroll> enrollList = new ArrayList<>();
		enrollList.addAll(enrollRepository.findAllMinExam());
		enrollList.addAll(enrollRepository.findAllMaxExam());
		return enrollList;
	}

	public List<SumCreditMeanExam> searchBySumCreditMeanExam() {
		return enrollRepository.findStudentSumCreditMeanExam();
	}

	public List<MeanExamByCno> searchMeanExamByCno() {
		return enrollRepository.findMeanExamByCno();
	}

	public List<CntSnoByGrade> searchCntSnoByGrade(String cno) {
		return enrollRepository.findCntSnoByGrade(cno);
	}
}
