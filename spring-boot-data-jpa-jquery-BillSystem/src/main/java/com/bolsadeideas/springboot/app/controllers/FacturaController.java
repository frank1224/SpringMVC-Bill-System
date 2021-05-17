package com.bolsadeideas.springboot.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import com.bolsadeideas.springboot.app.services.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	@Qualifier("clienteServiceJPA")
	private IClienteService clienteService;

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
}
