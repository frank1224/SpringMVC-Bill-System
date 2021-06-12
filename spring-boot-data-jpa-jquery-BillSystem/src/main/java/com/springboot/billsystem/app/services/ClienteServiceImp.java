package com.springboot.billsystem.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.billsystem.app.models.dao.FacturaDao;
import com.springboot.billsystem.app.models.dao.IClienteDao;
import com.springboot.billsystem.app.models.dao.IproductoDao;
import com.springboot.billsystem.app.models.entity.Cliente;
import com.springboot.billsystem.app.models.entity.Factura;
import com.springboot.billsystem.app.models.entity.Producto;

@Service("clienteServiceJPA")
public class ClienteServiceImp implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	@Autowired
	private IproductoDao productoDao;
	@Autowired
	private FacturaDao facturaDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Transactional  
	@Override
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Transactional(readOnly = true)
	@Override
	public Cliente findOne(Long id) {		
		//return clienteDao.findOne(id);
		return clienteDao.findById(id).orElse(null); //retorna el objeto cliente, de lo contrario null.
	}
	
	@Transactional(readOnly = true)
	@Override
	public Cliente fetchByIdWithBills(Long id) {
		return clienteDao.fetchByIdWithBills(id);
	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	
	@Transactional(readOnly = true)
	@Override
	public List<Producto> findByproductName(String term) {
		
		//return productoDao.findByProductName(term);
		return productoDao.findByProductNameLikeIgnoreCase("%"+term+"%");
	}

	@Transactional
	@Override
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
		
	}

	@Transactional(readOnly = true)
	@Override
	public Producto findProductByID(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Factura findBillById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void deleteBill(Long id) {
		facturaDao.deleteById(id);
		
	}

	@Transactional(readOnly = true)
	@Override
	public Factura fetchByIdWithClientWithItemsBillWithProduct(Long id) {
		return facturaDao.fetchByIdWithClientWithItemsBillWithProduct(id);
	}

}
