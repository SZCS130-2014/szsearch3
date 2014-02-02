szsearch3
=========

CS 130 Winter 2014 Search Engine Group 3

Instructions to index dataset into Solr:
    -Move the dataset.xml file from Chris into the solr-4.5.0/example directory
    -In that same folder, start Solr by running "java -Xmx1024m -jar start.jar"
    -Either using curl or a web browser, hit http://localhost:8983/solr/dataimport?command=full-import
    -If Solr didn't throw any exceptions when that command was run, you should be done!

Instructions to reindex:
    -Start Solr with the command above
    -Delete all the documents in the index by running the command "curl http://localhost:8983/solr/update?commit=true --data '<delete><query>*:*</query></delete>'"
    -Now that everything is deleted, follow the instructions above to index the data again
