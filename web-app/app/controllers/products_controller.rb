
# ProductController
# A simple controller for viewing and searching for products
# Author: Joshua Dykstra
# Date: 2/27/14
class ProductsController < ApplicationController
  
    #
    # Action for the product search home page
    #
    def index
    end

    #
    # Action for the product search results page
    # param: query - keywords to search for
    # optional param: start - offset to begin returning records
    # optional param: rows - number of rows to return
    #
    def results
        # Create a SearchModel to fetch results in JSON format

        # Format the results for the view

    end

	#
	# Action for viewing a single product
	# param: id of product to display
	#
	def view
		# Create a ProductModel to fetch the product's info
		
	end

end
