<?php
/*
 * image_fetch.php
 *
 * Fetches images from BING API and adds to 
 * the merged dataset
 */
// BING API information
$url = "https://api.datamarket.azure.com/Bing/Search";
$key = "csNTimOpOcrKiUDgR5lvdduqaWEHN3Kjnbm0MTjlzBw=";
$auth = base64_encode("$key:$key");
$data = array(
	'http' => array(
		'request_fulluri' => true,
		'ignore_errors' =>	true,
		'header' => "Authorization: Basic $auth")
	);
$context = stream_context_create($data);


// Get the merge file  and output file out of the arguments
$mergeFileName = $argv[1];
echo "opening $mergeFileName\n";
$outputFileName = $argv[2];

// Open the file
$root = simplexml_load_file($mergeFileName);

// Iterate over the products
foreach($root->product as $product) {
	if(!isset($product['ImgUrl'])) {
		// Grab title from product attribute
		$title = (string)"'{$product['ProductTitle']}'";
		$query = urlencode($title);

		// Issue query
		echo "fetching url for: $title ...\n";
		$requestURI = "$url/Image?\$format=json&Query=$query&ImageFilters=%27Size%3AMedium%27";
		$response = file_get_contents($requestURI, 0 , $context);

		// Check if the response code is valid
		if(substr($http_response_header[0], 9, 1) == "4") {
			echo "ERROR OCCURRED/KEY EXPIRED!\n";
			break;
		}
			
		$jsonObj = json_decode($response);

		// Add the url as an attribute
		$imgurl = $jsonObj->d->results[0]->MediaUrl;
		$product->addAttribute('ImgUrl', $imgurl);
		echo "$imgurl added as attribute\n";
	}

}
echO "Writing to file...";
$root->asXML($outputFileName);
echo "Success\n";

?>