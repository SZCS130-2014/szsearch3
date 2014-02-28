
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
	# return object containing 
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
			raise response.response
		end

	end

end
