    
# ProductController
# A simple controller for viewing and searching for products
# Author: Joshua Dykstra
# Date: 2/27/14
class ProductsController < ApplicationController
  
    #
    # Action for the product search home page
    #
    def search
    end

    #
    # Action for the product search results page
    # param: query - keywords to search for
    # optional param: start - offset to begin returning records
    # optional param: rows - number of rows to return
    #
    def results
        # Use Product model to fetch results in JSON format
        @results = Product.search(params[:query], params[:start], params[:rows], params[:categoryFilter], params[:ratingFilter])
        @searchTerm = params[:query]

        if !params[:categoryFilter].nil? || !params[:ratingFilter].nil?
            @activeFilters = {"Category" => params[:categoryFilter], "Rating" => params[:ratingFilter]}
        else
            @activeFilters = nil;
        end

        # if !@results['numFound'].nil?
        #     totalPages = @results['numFound'] / 20

        #     if !params[:start].nil?
        #         #if params[:start] / 20 > 3?
        #         current = params[:start] / 20 
        #         startPage = (params[:start] / 20) - 2
        #         endPage = (params[:start] / 20) + 2
        #     else
        #         current = 1
        #         startPage = 1
        #         endPage = totalPages > 5 ? 5 : totalPages
        #     end



        #     #@results['pages'] = {'total' => totalPages, 'current' => current, 'start' => startPage, 'end' => endPage}

        #     puts  @results['pages'] 
        # end
        
    end

	#
	# Action for viewing a single product
	# param: id of product to display
	#
	def view
		# Use Product model to fetch the product's info
        @product = Product.getProduct(params[:id])
		
	end

end
