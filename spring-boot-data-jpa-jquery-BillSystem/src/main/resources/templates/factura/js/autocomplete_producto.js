$(document).ready(function(){
			
	$("#search_product").autocomplete({
	
		source: function(request, response){
		$.ajax({
			url:"/factura/cargar_productos/" + request.term,
			dataType:"json",
			data:{ 
				term: request.term
			},
			success: function(data){
				response($.map(data, function(item){
					return{
					value: item.id,
					label: item.productName,
					price: item.price,
					};
				}));
			},
		});
		},
		select: function(event, ui){
			//$("#search_product").val(ui.item.label);
			
			//comprobar el producto
			if(itemHelper.hasProduct(ui.item.value)){
				itemHelper.incrementAmount(ui.item.value, ui.item.price);
				return false;
			}
			
			
			//remplazamos los valores
			var line= $("#itemsBillTemplate").html();
			
			line = line.replace(/{ID}/g, ui.item.value);
			line = line.replace(/{NOMBRE}/g, ui.item.label);
			line = line.replace(/{PRECIO}/g, ui.item.price);
			
			$("#loadItemProduct tbody").append(line);
			itemHelper.calculateCost(ui.item.value, ui.item.price, 1);
			
			return false;
		}  
	});
	
	$("form_factura").submit(function(){
		$("#itemsBillTemplate").remove();
		return;
	});

	}
	
	);
	
	var itemHelper ={
		calculateCost: function(id, price, amount){
				$("#total_cost_"+id).html(parseInt(price) * parseInt(amount));
				this.calculateGreatTotal();
			},
			hasProduct: function(id){
				var result = false;
				
				$('input[name="item_id[]"]').each(function(){
					if(parseInt(id)== parseInt($(this).val())){
						result = true;
					}
				});
				return result;
			},
			incrementAmount: function(id, price){
				var amount = $("#amount_"+id).val() ? parseInt($("#amount_"+id).val()):0;
				$("#amount_"+id).val(++amount);
				this.calculateCost(id, price, amount);
			},
			deleteBillLine: function(id){
				$("#row_"+id).remove();
				this.calculateGreatTotal();
			},
			calculateGreatTotal: function(){
				var total=0;
				$('span[id^="total_cost_"]').each(function(){
					total+=parseInt($(this).html());
				});
				$("#great_total").html(total);
			}
	}