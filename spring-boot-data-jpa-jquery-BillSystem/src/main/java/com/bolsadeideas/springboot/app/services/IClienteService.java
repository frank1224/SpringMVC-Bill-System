package com.bolsadeideas.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.Producto;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente); 
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
	
	public List<Producto> findByproductName(String term);
	
	public void saveFactura(Factura factura);
	
	public Producto findProductByID(Long id);
}

