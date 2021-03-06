//$('#apartment-table-body').load('/apartments');


$('#city-form').on('submit', function(event) {

    var link = $(this).attr('action');
    
    $(".ui-autocomplete").hide();

    $.post(link,$(this).serialize(),function(data, status) {
    	data = JSON.parse(data);
    	aOrig = data;
    	a = data.slice(0);
    	updateFilters();
    });

    return false; // dont post it automatically
});

$('.sortable-header').on('click', function(event) {
	var id = $(this).attr('id');
	var cookie = $.cookie('sorted');
	if(!cookie || cookie != id) {
		sortApartments(id, true);
	} else {
		var asc = $.cookie('sorted-asc');
		if(asc == "true") {
			sortApartments(id, false);
		} else {
			sortApartments(id, true);
		}
	}
});

$('#student-apartment').on('change', function(event) {
	updateFilters();
});

$('#non-student-apartment').on('change', function(event) {
	updateFilters();
});