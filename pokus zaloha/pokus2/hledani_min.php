<!DOCTYPE html>
<html>

<head>
	<title>Page Title</title>
	<link rel="stylesheet" type="text/css" href="styl.css">
</head>

<body>
<?php
$ cd ~/public_html;
$ php -S localhost:8080;
// here I use argv for URL, but you can adapt it however you like
$url = "http://localhost:8080/getBoard".$argv[1];
$data = array('var1' => 'value1', 'var2' => 'value2');

$options = array(
    'http' => array(
        'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
        'method'  => 'POST',
        'content' => http_build_query($data)));

$response = file_get_contents($url, false, stream_context_create($options));


?>

</body>

</html>