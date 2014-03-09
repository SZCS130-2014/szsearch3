    
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
        @results = Product.search(params[:query], 
                                  params[:start], 
                                  params[:rows], 
                                  params[:categoryFilter], 
                                  params[:ratingFilter], 
                                  params[:brandFilter],
                                  params[:sort])

        @searchTerm = params[:query]
        @resultsPerPage = params[:rows].nil? ? 21 : params[:rows].to_i;

        if !params[:categoryFilter].nil? || !params[:ratingFilter].nil? || !params[:brandFilter].nil?
            @activeFilters = {"Category" => params[:categoryFilter], "Rating" => params[:ratingFilter],
                                "Brand" => params[:brandFilter]}
        else
            @activeFilters = nil
        end

        # PAGINATION

        if !@results['numFound'].nil?
            totalPages = (@results['numFound'].to_f / @resultsPerPage).ceil
            recordToStartFrom = params[:start].to_f + 1 
            # If we start from the first record, recordToStartFrom has value 1.

            if recordToStartFrom <= (3*@resultsPerPage) 
                @current = (recordToStartFrom / @resultsPerPage).ceil
                @startPage = 1
                @endPage = totalPages > 5 ? 5 : totalPages
            
            elsif @results['numFound'] - recordToStartFrom +1 < (3*@resultsPerPage)
             # LHS is number of records left till the end. RHS is number of records on 3 pages.
             # Therefore if number of records left till the end fit on 3 pages, our starting record is somewhere on the last 3 pages.
                @current = (recordToStartFrom / @resultsPerPage).ceil
                @endPage = totalPages;
                @startPage = totalPages > 5 ? @endPage - 4 : 1;   #Need this logic in case current = 4, totalpages = 4 or 5.
            
            else    
                @current = (recordToStartFrom / @resultsPerPage).ceil 
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
