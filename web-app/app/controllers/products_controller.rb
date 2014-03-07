    
# ProductController
# A simple controller for viewing and searching for products
# Author: Joshua Dykstra
# Date: 2/27/14

RESULTS_PER_PAGE = 21
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
        @results = Product.search(params[:query], params[:start], params[:rows], params[:categoryFilter], params[:ratingFilter], params[:brandFilter])
        @searchTerm = params[:query]

        if !params[:categoryFilter].nil? || !params[:ratingFilter].nil? || !params[:brandFilter].nil?
            @activeFilters = {"Category" => params[:categoryFilter], "Rating" => params[:ratingFilter],
                                "Brand" => params[:brandFilter]}
        else
            @activeFilters = nil
        end

         if !@results['numFound'].nil?
            totalPages = @results['numFound'] / RESULTS_PER_PAGE

             if params[:start].nil? || params[:start].to_i < (3*RESULTS_PER_PAGE) 
                @current = (params[:start].to_f / RESULTS_PER_PAGE).ceil
                @startPage = 1
                @endPage = totalPages > 5 ? 5 : totalPages
            
             elsif @results['numFound'] - (params[:start].to_i) < (3*RESULTS_PER_PAGE)
                @current = (params[:start].to_f / RESULTS_PER_PAGE).ceil
                @endPage = totalPages;
                @startPage = totalPages > 5 ? @endPage - 4 : 1;   #Need this logic in case current = 4, totalpages = 4 or 5.
            
              else    
                 @current = (params[:start].to_f / RESULTS_PER_PAGE).ceil 
                 @startPage = @current - 2
                 @endPage = @current + 2
             end
         end
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
