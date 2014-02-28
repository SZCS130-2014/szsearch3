
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
        # Use Product model to fetch results in JSON format
        @results = Product.search(params[:query], params[:start], params[:rows])
        
    end

	#
	# Action for viewing a single product
	# param: id of product to display
	#
	def view
		# Use Product model to fetch the product's info
		
	end

end
