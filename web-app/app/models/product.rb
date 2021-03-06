
# Product
# A model for managing products
# Author: Joshua Dykstra
# Date: 2/27/14
class Product 

	require 'rubygems'
	require 'json'

	# URIs for resources
	@service_base_uri = URI.parse(URI.encode('http://54.213.229.102:7500/services'))

	#
	# Issues a query to the search service
	# param: keywords - words to search
	# optional param: start - offset to being returning results
	# optional param: rows - number of results ro return
	# return: object conta"shared/ad_banner"ining search results or nil
	#
	def self.search(keywords, start, rows, categoryFilter, ratingFilter, brandFilter, sort)

		# Check if we don't have any keywords
		if keywords.nil?
			return {:productSearchEntry => nil,
					:numFoud => nil,
					:facetAttribute => nil}
		end

		#set defaults
		if start.nil?
			start = 0
		end

		if rows.nil?
			rows = 21
		end

		params = {:q => keywords, :start => start,
					:rows => rows, :format => 'json'}

		if sort.nil?
			params[:sort] = false
		else
			params[:sort] = sort 			
		end

		if !categoryFilter.nil?
			params[:categoryFilter] = categoryFilter
		end

		if !ratingFilter.nil?
			params[:ratingFilter] = ratingFilter
		end

		if !brandFilter.nil?
			params[:brandFilter] = brandFilter
		end

		puts "Issuing search for: #{keywords} start: #{start} rows: #{rows}"

		# Issue a query to the service using HTTParty
		response = HTTParty.get("#{@service_base_uri}/productsearch", { :query =>  params})

		if response.success?
			puts response
			return JSON.parse(response.body)
		else
			puts response
			return {:productSearchEntry => nil,
					:numFoud => nil,
					:facetAttribute => nil}
		end

	end

	#
	# Fetches a single product record from the server
	# param: productID to fetch
	# return: object containing product info or nil
	#
	def self.getProduct(productID)

		# Check that productID is valid
		if productID.nil?
			return nil
		end

		# Issue query to the service using HTTParty
		params = { :query => {:format => 'json'} }
		puts "#{@service_base_uri}/product/#{productID}"
		response = HTTParty.get("#{@service_base_uri}/product/#{productID}", params)

		if response.success?
			return JSON.parse(response.body)
		else
			puts response.response
			return nil
		end

	end

end
