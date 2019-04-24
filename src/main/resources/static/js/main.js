$(document).ready(function () {

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        deleteRow();

    });

});

function deleteRow(elt) {

	 var row = elt.parentNode.parentNode.parentNode.parentNode;
	  row.parentNode.removeChild(row);
	  //document.getElementById("ingredient_table").deleteRow(3);

}