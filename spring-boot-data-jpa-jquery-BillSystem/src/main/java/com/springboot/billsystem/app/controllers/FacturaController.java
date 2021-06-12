package com.springboot.billsystem.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.billsystem.app.models.entity.Cliente;
import com.springboot.billsystem.app.models.entity.Factura;
import com.springboot.billsystem.app.models.entity.ItemFactura;
import com.springboot.billsystem.app.models.entity.Producto;
import com.springboot.billsystem.app.services.IClienteService;

import net.bytebuddy.implementation.bind.annotation.BindingPriority;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	@Qualifier("clienteServiceJPA")
	private IClienteService clienteService;

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	
	
	@GetMapping("/ver_factura/{id}")
	public String verFactura(@PathVariable(name = "id") Long id, Model model, RedirectAttributes flashMessage) {
		//Factura factura = clienteService.findBillById(id);
		Factura factura = clienteService.fetchByIdWithClientWithItemsBillWithProduct(id);
		
		if (factura==null) {
			flashMessage.addFlashAttribute("error", "La factura no existe en la base de datos");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "factura: ".concat(factura.getDescription()));
		model.addAttribute("factura", factura);
		
		return "factura/ver_factura";
	}

	@GetMapping("/form_factura/{clienteId}")
	public String crear(@PathVariable(name = "clienteId") Long clienteId, Factura factura, Model model,
			RedirectAttributes flashMessage) {

		Cliente cliente = clienteService.findOne(clienteId);

		if (cliente == null) {

			flashMessage.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/listar";
		}

		factura.setCliente(cliente);
		model.addAttribute("titulo", "Crear factura");
		model.addAttribute("factura", factura);
		return "/factura/form_factura";
	}

	@GetMapping(value = "/cargar_productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {

		return clienteService.findByproductName(term);

	}

	@PostMapping("/form_factura")
	public String guardar(@Valid Factura factura, BindingResult result,Model model, @RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "amount[]", required = false) Integer[] amount, RedirectAttributes flashMessange,
			SessionStatus status) {
		Producto product = null;
		ItemFactura line = new ItemFactura();
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear factura");
			
			return "factura/form_factura";
		}
		
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo","Crear factura");
			model.addAttribute("error","Error: La factura no puede tener lineas");
		}

		for (int i = 0; i < itemId.length; i++) {
			product = clienteService.findProductByID(itemId[i]);
			line.setAmount(amount[i]);
			line.setProducto(product);
			factura.addItemFactura(line);

			log.info("ID:  " + itemId[i].toString() + ", cantidad: " + amount[i].toString());

		}
		clienteService.saveFactura(factura);
		status.setComplete();
		flashMessange.addAttribute("success", "Factura creada con éxito");

		return "redirect:/ver_detalle/" + factura.getCliente().getId();

	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(name = "id") Long id, RedirectAttributes flashMessage) {
		
		Factura factura = clienteService.findBillById(id);
		if (factura != null) {
			clienteService.deleteBill(id);
			flashMessage.addFlashAttribute("success", "Factura eliminada con éxito");
			return "redirect:/ver_detalle/" + factura.getCliente().getId();
		}
		flashMessage.addFlashAttribute("error", "La factura no existe en la base de datos, no se pudo eliminar");
		return "redirect:/listar";
	}
}
