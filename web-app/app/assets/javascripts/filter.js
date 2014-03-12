/**
 * filter.js
 *
 * A jQuery widget for the filter component
 *
 * @author: Josh Dykstra
 */
Filter = function(facetContainer, facetName, button) {
	this.init(facetContainer, facetName, button);
}

$.extend(Filter.prototype, {

	filterContainer: '',
	filterName: '',
	buttonName: '',

	init: function(facetContainer, facetName, button) {

		this.filterContainer = facetContainer;
		this.filterName = facetName;
		this.buttonName = button;

		// Hide filters if necessary
		var hidden = this.hideFilters(this.filterContainer.children(".radio"));

		// Add a button with event to expand the hidden filters if we hid any
		if(hidden) {
			expandBtn = $('<button data-action="expand" class="btn btn-default btn-block">More ' + this.buttonName + '</button> <br>');
			this.filterContainer.append(expandBtn);
			expandBtn.click({filterContainer: this.filterContainer, buttonName: this.buttonName}, this.toggleFilters);
		}

		// Add event to register any click events
		this.filterContainer.click({filterContainer: this.filterContainer,
									filterName: this.filterName}, this.handleClick);
	},

	/**
	 * Hides filters after the first 10
	 *
	 * @param filterList to hide
	 * @return true/false if filters were hidden
	 */
	hideFilters: function(filterList) {

		// Check if list is shorter than 10
		if(filterList.length < 11)
			return false;

		// Iterate over the filters and hide 11th+
		filterList.each(function(index, filterNode) {
			if (index >= 10)
				$(filterNode).hide();

		});

		return true;
	},

	/**
	 * Determines whether click should trigger filter
	 * and redirects to apply a filter
	 */
	handleClick: function(e) {
		
		var target = $(e.target);
		if(target.is('input') && target.attr('type') == 'radio') {
			if(target.is(':checked')) {
				// Add this new filter
				var newurl = URI(window.location).removeSearch(e.data.filterName).removeSearch("start")
							.addSearch(e.data.filterName, target.attr('data-facet-value'));
				window.location = newurl;
			} else {
				// Remove the filter
				var newurl = URI(window.location).removeSearch(e.data.filterName).removeSearch("start");
				window.location = newurl;
			}
		}
	},

	/**
	 * Toggles the filter window to show/hide additional filters
	 */
	toggleFilters: function(e) {

		var target = $(e.target);
		if(target.attr('data-action') == 'expand') {

			e.data.filterContainer.children(".radio")
			.each(function(index, filterNode) {
				if (index >= 10)
					$(filterNode).fadeIn("slow");
			});

			target.attr('data-action', 'collapse');
			target.text("Less " + e.data.buttonName);

		} else {

			e.data.filterContainer.children(".radio")
			.each(function(index, filterNode) {
				if (index >= 10)
					$(filterNode).fadeOut("slow");
			});

			target.attr('data-action', 'expand');
			target.text("More " + e.data.buttonName);
		}

		
	},
});