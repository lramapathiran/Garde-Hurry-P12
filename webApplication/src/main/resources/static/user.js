$(document).ready(function () {

				var i = 0;
		        $('#childrenDtosData').multiInput({

		            json: true,
		            input: $('<div class="row inputElement">\n' +
		                '<div class="multiinput-title col-sm-3">Liste de Vos enfants<span class="number">1</span></div>\n' +
		                '<div class="form-group col-sm-4">\n' +
		                '<input class="form-control age" name="childrenDtos[].age" placeholder="Age" type="text">\n' +
		                '</div>\n'
//		                '<div class="form-group col-sm-4">\n' +
//		                '<input class="form-control routesNumber" name="areas[].routesNumber" placeholder="Nombre de Voies" type="number" min="0">\n' +
//		                '</div>\n' +
//		                '</div>\n'
                    ),
		            limit: 10,

		            onElementAdd: function (el, plugin) {

		                $('.age', $(el)).attr('name','childrenDtos['+i+'].age');
//		                $('.routesNumber', $(el)).attr('name','areas['+i+'].routesNumber');

		               i++;

		            },
		            onElementRemove: function (el, plugin) {
		                i--;
		            }
		        });
});