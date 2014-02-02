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
