$(document).ready(function() {

	$("#alertSuccess").hide();
	$("#alertError").hide();
	$('#Datable').DataTable();
});
// adding a Datatable
$('#fund-table').DataTable({
	language : {
		paginate : {
			next : '<i class="ni ni-bold-right"></i>',
			previous : '<i class="ni ni-bold-left"></i>'
		}
	},
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear status msges-------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation----------------
	var status = validateFundForm();
	// If not valid-------------------
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid-----------------------
	var type = ($("#hidIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "FundsAPI",
		type : type,
		data : $("#formFund").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);

		}
	});

});

$(document).on("click",".btnUpdate",function(event) {
			$("#hidIDSave").val($(this).data("ID"));
			$("#funderID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#researchID").val($(this).closest("tr").find('td:eq(1)').text());
			$("#price").val($(this).closest("tr").find('td:eq(2)').text());
			$("#comments").val($(this).closest("tr").find('td:eq(3)').text());
			
		});


//remove data
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "FundsAPI",
		type : "DELETE",
		data : "ID=" + $(this).data("ID"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});
function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divFundsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}


function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divFundsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidIDSave").val("");
	$("#formFund")[0].reset();
}

function validateFundForm() {

	if ($("#funderID").val().trim() == "") {
		return "Please add funder ID.";
	}

	if ($("#researchID").val().trim() == "") {
		return "Please add research ID.";
	}

	if ($("#price").val().trim() == "") {
		return "Please add donating price.";
	}

	if ($("#comments").val().trim() == "") {
		return "Please add any comments.";
	}

	

	return true;
}