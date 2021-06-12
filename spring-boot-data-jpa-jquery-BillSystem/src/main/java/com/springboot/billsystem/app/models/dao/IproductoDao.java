package com.springboot.billsystem.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.billsystem.app.models.entity.Producto;

public interface IproductoDao extends CrudRepository<Producto, Long> {

	@Query("select p from Producto p where p.productName like %?1%")
	public List<Producto> findByProductName(String term);
	
	public List<Producto> findByProductNameLikeIgnoreCase(String term);
}
