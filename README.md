szsearch3
=========

CS 130 Winter 2014 Search Engine Group 3

<h3>Build/Run Instructions</h3>

Solr:
* Move the dataset file into the solr-4.5.0/example folder with the name "merged_dataset.xml"
* Start solr with the command `java -Xmx1024m -jar start.jar`, run from the example folder
* Execute a dataimport in solr 

Dropwizard:
* Navigate to the dropwizard folder
* run `mvn package`
* cd into the dropwizard/service folder
* run `java -jar target/product-search-service-1.0-SNAPSHOT.jar server service.yaml` to start the service

Rails:
* Navigate to the web-app folder. 
* Run `rails server` to start the server

<h2>Apache Solr</h2>

<b>Instructions to index dataset into Solr:</b>
<ul>
    <li>Move the dataset.xml file from Chris into the solr-4.5.0/example directory</li>
    <li>In that same folder, start Solr by running "java -Xmx1024m -jar start.jar"</li>
    <li>Either using curl or a web browser, hit http://localhost:8983/solr/dataimport?command=full-import</li>
    <li>If Solr didn't throw any exceptions when that command was run, you should be done!</li>
</ul>

<b>Instructions to reindex:</b>
<ul>
    <li>Start Solr with the command above</li>
    <li>Delete all the documents in the index by running the command "curl http://localhost:8983/solr/update?commit=true --data '&lt;delete&gt;&lt;query&gt;*:*&lt;/query&gt;&lt;/delete&gt;'    "</li>
    <li>Now that everything is deleted, follow the instructions above to index the data again</li>
</ul>

<b>Useful sites:</b>
<ul>
	<li>http://khaidoan.wikidot.com/solr</li>
	<li>http://wiki.apache.org/solr/AnalyzersTokenizersTokenFilters</li>
	<li>http://lucene.472066.n3.nabble.com/Best-way-to-boost-results-that-starts-with-search-keyword-td4023502.html</li>
	<li>http://grokbase.com/t/lucene/solr-user/137j8g22qx/boost-docs-if-token-matches-happen-in-the-first-5-words</li>
</ul>

<b>Interesting facts:</b>
<ul>
	<li>schema is only loaded when the server starts! So you have to restart to apply changes.</li>
	<li>Instead, look into solr core reload</li>
</ul>

RequestHandlers are: /select is a solr.SearchHandler
/query is also a solr.SearchHandler. Not sure which one we've been using..
Well, if you hit /solr/collection1/select?q=*:* you get the output we've been seeing as straight XML.
And /solr/collection1/query?q=*:* is THE SAME THING EXCEPT JSON
OH YES MY CHILD /browse is CRAY.
Making our own called simpleweighted, using defType="edismax" and qf=A few important things, boosted.
What we have to do is patch Solr with a patch that exposes SpanFirstQuery. Then we'll be able to do
add < str name="sf">ProductTitle~1^4.0 </ str> or whatever.
Not a complete solution, and not the most elegant but there literally seems to be no other way.

<b>Transforming Data to Add Fields:</b> <br>
To strip out and add the "Brand" and "Item" Fields: <br>
`sed 's/ProductTitle=\"\([^ ]*\)/Brand=\"\1\" ProductTitle=\"\1/g' | sed 's/\(ProductTitle=\"\)\(.*\)\( \)\([^ ]\{1,\}\"\)/\1\2\3\4 Item=\"\4/g'` <br>



<h2>Querying the Dropwizard Web Service</h2>
<h4>ProductSearchResource</h4>
* Description: Returns products relevant to the given query. Also supports filtering by Category.
* Endpoint:    `/productsearch`
* Query Parameters:

  * `q`: search query
  * `start`: number of results to skip
  * `rows`: number of results to return
  * `categoryFilter`: category to filter by (if multiple, union is used)
  * `ratingFilter`: rating to filter by. ex: 3 --> [3 TO *], 4 --> [4 TO *]
  * `sort`: true/false. sorts in order of descending ratings.
  * `format`: xml or json (defaults to json)
```json
{
    "productSearchEntry": [
        {
            "category": "Laptop Computers",
            "brand": "Apple",
            "name": "Apple 11 in. Macbook Air 64GB 1.7GHz dual-core Intel Core i5",
            "title": "Apple - 11.6 MacBook Air Notebook - 4 GB Memory and 64 GB Solid State Drive",
            "pid": 4382094588,
            "imgUrl": "http://asdfasdfasdfsdf.jpg"
        }
    ],
    "numFound": 52,
    "facetAttribute": [
        {
            "name": "Category",
            "facet": [
                {
                    "name": "computers",
                    "numFound": 52
                },
                {
                    "name": "laptop",
                    "numFound": 27
                }
            ]
        }
    ]
}
```

<h4>ProductResource</h4>
* Description: Returns all product information including comments
* Endpoint:    `/product/{product_id}`
* URL Parameters:
  * `product_id`: seen above as part of the URL, this is the ID of the product to retrieve
* Query Parameters:
  * `format`: xml or json (defaults to json)
```json
{
    "commentEntry": [
        {
            "rating": 5,
            "title": "Best Laptop I have ever purchased",
            "content": "I love it. Best laptop I have ever purchased. Perfect for school & traveling.\n"
        },
        {
            "rating": 5,
            "title": "By far the best investment for a notebook!",
            "content": "Easy Applications, extremely well made, what more can I say except Bye bye to those awful window based PC's. Where have you been MAC all my life. Seriously, I could never look back, Apple is definately by far the best investment for the money.\n"
        }
    ],
    "category": "Laptop Computers",
    "brand": "Apple",
    "name": "Apple 11 in. Macbook Air 64GB 1.7GHz dual-core Intel Core i5",
    "title": "Apple - 11.6 MacBook Air Notebook - 4 GB Memory and 64 GB Solid State Drive",
    "pid": 4382094588,
    "imgUrl": "http://asdfasdfasdfsdf.jpg"
}
```
