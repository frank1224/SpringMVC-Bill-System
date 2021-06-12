package com.springboot.billsystem.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.billsystem.app.models.entity.Factura;

public interface FacturaDao extends CrudRepository<Factura, Long>{
	@Query("select f from Factura f join fetch f.cliente c join fetch f.itemsFactura l join fetch l.producto where f.id=?1")
	public Factura fetchByIdWithClientWithItemsBillWithProduct(Long id);
}
