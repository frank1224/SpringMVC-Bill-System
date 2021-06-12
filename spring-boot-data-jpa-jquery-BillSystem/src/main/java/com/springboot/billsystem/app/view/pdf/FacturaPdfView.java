package com.springboot.billsystem.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;


import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.springboot.billsystem.app.models.entity.Factura;
import com.springboot.billsystem.app.models.entity.ItemFactura;


@Component("factura/ver_factura")
public class FacturaPdfView extends AbstractPdfView{
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition","attachment; filename=\"factura.pdf\"");
		Factura factura = (Factura) model.get("factura");
		Locale locale = localeResolver.resolveLocale(request);
		//Translate
		MessageSourceAccessor messages =  getMessageSourceAccessor();
		
		PdfPTable tablePdf = new PdfPTable(1);
		tablePdf.setSpacingAfter(20);
		
		PdfPCell cell = null;
		cell =new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		
		
		tablePdf.addCell(cell);
		tablePdf.addCell(factura.getCliente().getFirstName().concat(" ").concat(factura.getCliente().getLastName()));
		tablePdf.addCell(factura.getCliente().getEmail());
	
		
		PdfPTable tablePdf2 = new PdfPTable(1);
		cell =new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		
		tablePdf2.addCell(cell);
		tablePdf2.addCell(messages.getMessage("text.factura.factura")+ factura.getId());
		tablePdf2.addCell(messages.getMessage("text.factura.descripcion").concat(factura.getDescription()));
		tablePdf2.addCell(messages.getMessage("text.factura.fecha")+ factura.getCreateAt());
		
		document.add(tablePdf);
		document.add(tablePdf2);
		
		PdfPTable tablePdf3 = new PdfPTable(4);
		tablePdf3.setWidths(new float[] {3.5f, 1, 1, 1});//column widths
		tablePdf3.addCell("Producto");
		tablePdf3.addCell("Precio");
		tablePdf3.addCell("Cantidad");
		tablePdf3.addCell("Total");
		
		
		for (ItemFactura items : factura.getItemsFactura()) {
			tablePdf3.addCell(items.getProducto().getProductName());
			tablePdf3.addCell(items.getProducto().getPrice().toString());
			
			cell = new PdfPCell(new Phrase(items.getAmount().toString()));
			//centra los valores
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tablePdf3.addCell(cell);
			tablePdf3.addCell(items.calculateAmount().toString());			
		}
		
		cell = new PdfPCell(new Phrase("Total: ")); 
		cell.setColspan(3);// column value
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tablePdf3.addCell(cell);
		tablePdf3.addCell(factura.getTotal().toString());
		
		document.add(tablePdf3);
	}



}
