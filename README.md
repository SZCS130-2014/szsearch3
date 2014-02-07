szsearch3
=========

CS 130 Winter 2014 Search Engine Group 3

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
    <li>Delete all the documents in the index by running the command "curl http://localhost:8983/solr/update?commit=true --data '&lt;delete&gt;&lt;query&gt;*:*&lt;/query&gt;&lt;/delete&gt;"</li>
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

<b>Transforming Data to Add Fields:</b>
To strip out and add the "Brand" Field: <br>
`sed 's/ProductTitle=\"\([^ ]*\)/Brand=\"\1\" ProductTitle=\"\1/g'` <br>
To strip out and add the "Item" Field: <br>
`sed 's/\(ProductTitle=\"\)\(.*\)\( \)\([^ ]\{1,\}\"\)/\1\2\3\4 Item=\"\4/g'` <br>
