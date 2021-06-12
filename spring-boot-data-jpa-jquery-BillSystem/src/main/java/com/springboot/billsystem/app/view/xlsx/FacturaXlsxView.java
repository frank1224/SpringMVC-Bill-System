package com.springboot.billsystem.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.springboot.billsystem.app.models.entity.Factura;
import com.springboot.billsystem.app.models.entity.ItemFactura;



@Component("factura/ver_factura.xlsx")
public class FacturaXlsxView extends AbstractXlsxView{
	

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		Factura factura = (Factura) model.get("factura");
		//Translate
		MessageSourceAccessor messages =  getMessageSourceAccessor();
	
		Sheet sheet = workbook.createSheet("Factura Spring");
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(messages.getMessage("text.factura.ver.datos.cliente"));
	
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getFirstName().concat(" ").concat(factura.getCliente().getLastName()));
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue(messages.getMessage("text.factura.ver.datos.factura"));
		sheet.createRow(5).createCell(0).setCellValue(messages.getMessage("text.factura.factura")+ factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(messages.getMessage("text.factura.descripcion").concat(factura.getDescription()));
		sheet.createRow(7).createCell(0).setCellValue(messages.getMessage("text.factura.fecha")+ factura.getCreateAt());
	
		//styles
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillBackgroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		
		//headers
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue("Producto");
		header.createCell(1).setCellValue("Precio");
		header.createCell(2).setCellValue("Cantidad");
		header.createCell(3).setCellValue("Total");
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		int rownum = 10;
		for(ItemFactura item: factura.getItemsFactura()){
			Row quue = sheet.createRow(rownum ++);
			cell = quue.createCell(0);
			cell.setCellValue(item.getProducto().getProductName());
			cell.setCellStyle(tbodyStyle);

			cell = quue.createCell(1);
			cell.setCellValue(item.getProducto().getPrice());
			cell.setCellStyle(tbodyStyle);
			
			cell = quue.createCell(2);
			cell.setCellValue(item.getAmount());
			cell.setCellStyle(tbodyStyle);
			
			cell = quue.createCell(3);
			cell.setCellValue(item.calculateAmount());
			cell.setCellStyle(tbodyStyle);
			
		}
		
		Row totalQuue = sheet.createRow(rownum);
		cell = totalQuue.createCell(2);
		cell.setCellValue("Gran Total");
		cell.setCellStyle(tbodyStyle);
		
		cell = totalQuue.createCell(3);
		cell.setCellValue(factura.getTotal());
	}
	


}
