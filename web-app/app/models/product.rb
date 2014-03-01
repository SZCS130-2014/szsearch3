
# Product
# A model for managing products
# Author: Joshua Dykstra
# Date: 2/27/14
class Product 

	require 'rubygems'
	require 'json'
	include HTTParty

	base_uri '54.213.229.102:7500/services/'

	#
	# Issues a query to the search service
	# param: keywords - words to search
	# optional param: start - offset to being returning results
	# optional param: rows - number of results ro return
	# return: object containing search results or nil
	#
	def self.search(keywords, start = 0, rows = 20)

		# Check if we don't have any keywords
		if keywords.nil?
			return nil
		end

		# Issue a query to the service using HTTParty
		params = { :query => {:q => keywords, :start => start,
							  :rows => rows, :format => 'json'} }
		response = get("#{@base_uri}/productsearch", params)

		if response.success?
			return JSON.parse(response.body)
		else
			puts response.response
			return nil
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
		puts "#{@base_uri}/product/#{productID}"
		response = get("#{@base_uri}/product/#{productID}", params)

		if response.success?
			return JSON.parse(response.body)
		else
			puts response.response
			return nil
		end

	end

end
