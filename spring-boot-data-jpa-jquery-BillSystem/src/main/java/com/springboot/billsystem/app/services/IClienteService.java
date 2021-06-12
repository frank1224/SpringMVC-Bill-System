package com.springboot.billsystem.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.billsystem.app.models.entity.Cliente;
import com.springboot.billsystem.app.models.entity.Factura;
import com.springboot.billsystem.app.models.entity.Producto;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente); 
	
	public Cliente fetchByIdWithBills(Long id);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
	
	public List<Producto> findByproductName(String term);
	
	public void saveFactura(Factura factura);
	
	public Producto findProductByID(Long id);
	
	public Factura findBillById(Long id);
	
	public void deleteBill(Long id);
	
	public Factura fetchByIdWithClientWithItemsBillWithProduct (Long id);
	 
}

