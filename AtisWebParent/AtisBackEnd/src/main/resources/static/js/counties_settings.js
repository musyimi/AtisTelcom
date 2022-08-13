var buttonLoad4Counties;
var dropDownCountry4Counties;
var dropDownCounties;
var buttonAddCounty;
var buttonUpdateCounty;
var buttonDeleteCounty;
var labelCountyName;
var fieldCountyName;

$(document).ready(function() {
	buttonLoad4Counties = $("#buttonLoadCountriesForCounties");
	dropDownCountry4Counties = $("#dropDownCountriesForCounties");
	dropDownCounties = $("#dropDownCounties");
	buttonAddCounty = $("#buttonAddCounty");
	buttonUpdateCounty = $("#buttonUpdateCounty");
	buttonDeleteCounty = $("#buttonDeleteCounty");
	labelCountyName = $("#labelCountyName");
	fieldCountyName = $("#fieldCountyName");
	
	buttonLoad4Counties.click(function() {
		loadCountries4Counties();
	});
	
	dropDownCountry4Counties.on("change", function() {
		loadCounties4Country();
	});

	dropDownCounties.on("change", function() {
		changeFormCountyToSelectedCounty();
	});
		
	buttonAddCounty.click(function() {
		if (buttonAddCounty.val() == "Add") {
			addCounty();
		} else {
			changeFormCountyToNew();
		}
	});
	
	buttonUpdateCounty.click(function() {
		updateCounty();
	});
	
	buttonDeleteCounty.click(function() {
		deleteCounty();
	});
});

function deleteCounty() {
	countyId = dropDownCounties.val();
	
	url = contextPath + "counties/delete/" + countyId;
	
	$.ajax({
		type: 'DELETE',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		$("#dropDownCounties option[value='" + countyId + "']").remove();
		changeFormCountyToNew();
		showToastMessage("The county has been deleted");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});		
}

function updateCounty() {
	if (!validateFormCounty()) return;
	
	url = contextPath + "counties/save";
	countyId = dropDownCounties.val();
	countyName = fieldCountyName.val();
	
	selectedCountry = $("#dropDownCountriesForCounties option:selected");
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {id: countyId, name: countyName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(countyId) {
		$("#dropDownCounties option:selected").text(countyName);
		showToastMessage("The county has been updated");
		changeFormCountyToNew();
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});	
}

function addCounty() {
	if (!validateFormCounty()) return;
	
	url = contextPath + "counties/save";
	countyName = fieldCountyName.val();
	
	selectedCountry = $("#dropDownCountriesForCounties option:selected");
	countryId = selectedCountry.val();
	countryName = selectedCountry.text();
	
	jsonData = {name: countyName, country: {id: countryId, name: countryName}};
	
	$.ajax({
		type: 'POST',
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(countyId) {
		selectNewlyAddedCounty(countyId, countyName);
		showToastMessage("The new county has been added");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
		
}

function validateFormCounty() {
	formCounty = document.getElementById("formCounty");
	if (!formCounty.checkValidity()) {
		formCounty.reportValidity();
		return false;
	}	
	
	return true;
}

function selectNewlyAddedCounty(countyId, countyName) {
	$("<option>").val(countyId).text(countyName).appendTo(dropDownCounties);
	
	$("#dropDownCounties option[value='" + countyId + "']").prop("selected", true);
	
	fieldCountyName.val("").focus();
}

function changeFormCountyToNew() {
	buttonAddCounty.val("Add");
	labelCountyName.text("County/Province Name:");
	
	buttonUpdateCounty.prop("disabled", true);
	buttonDeleteCounty.prop("disabled", true);
	
	fieldCountyName.val("").focus();	
}

function changeFormCountyToSelectedCounty() {
	buttonAddCounty.prop("value", "New");
	buttonUpdateCounty.prop("disabled", false);
	buttonDeleteCounty.prop("disabled", false);
	
	labelCountyName.text("Selected County/Province:");
	
	selectedCountyName = $("#dropDownCounties option:selected").text();
	fieldCountyName.val(selectedCountyName);
	
}

function loadCounties4Country() {
	selectedCountry = $("#dropDownCountriesForCounties option:selected");
	countryId = selectedCountry.val();
	url = contextPath + "counties/list_by_country/" + countryId;
	
	$.get(url, function(responseJSON) {
		dropDownCounties.empty();
		
		$.each(responseJSON, function(index, county) {
			$("<option>").val(county.id).text(county.name).appendTo(dropDownCounties);
		});
		
	}).done(function() {
		changeFormCountyToNew();
		showToastMessage("All countys have been loaded for country " + selectedCountry.text());
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});	
}

function loadCountries4Counties() {
	url = contextPath + "countries/list";
	$.get(url, function(responseJSON) {
		dropDownCountry4Counties.empty();
		
		$.each(responseJSON, function(index, country) {
			$("<option>").val(country.id).text(country.name).appendTo(dropDownCountry4Counties);
		});
		
	}).done(function() {
		buttonLoad4Counties.val("Refresh Country List");
		showToastMessage("All countries have been loaded");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
}