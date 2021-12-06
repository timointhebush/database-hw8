package com.example.databasehw8.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.databasehw8.bookstore.domain.Book;
import com.example.databasehw8.bookstore.projection.AvgBookPrice;
import com.example.databasehw8.bookstore.projection.AvgBookPriceByYear;
import com.example.databasehw8.bookstore.projection.CntMinMaxAvgByAuthor;

public interface BookRepository extends JpaRepository<Book, String> {

	@Query(value = "select avg(b.price) avgPrice from Book b ", nativeQuery = true)
	public List<AvgBookPrice> getAvgPrice();

	@Query(value = "select b.year year, avg(b.price) avgPrice from Book b group by b.year ", nativeQuery = true)
	public List<AvgBookPriceByYear> getAvgPriceByYear();

	@Query(value = "select w.name aname, count(b.isbn) cnt, "
		+ "max(b.price) maxPrice, min(b.price) minPrice, avg(b.price) avgPrice "
		+ "from Book b join Written_by w on b.isbn = w.isbn "
		+ "group by w.name ", nativeQuery = true)
	public List<CntMinMaxAvgByAuthor> getCntMinMaxAvgByAuthor();
}