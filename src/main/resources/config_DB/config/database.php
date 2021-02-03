<?php

// Recuperation du fichier de config .json
$content = utf8_decode(file_get_contents(__DIR__ . './db_config.json', FALSE, NULL));


// Récupère une chaîne encodée JSON et la converti en tableau
$database_config = json_decode($content,true, 512,JSON_THROW_ON_ERROR);

// Définition des constantes PHP
define('DB_HOST', $database_config['database']['host']);
define('DB_PORT', $database_config['database']['port']);
define('DB_USER', $database_config['database']['username']);
define('DB_PASS', $database_config['database']['password']);
define('DB_DSN', 'mysql:dbname=connect_life' . ';host=' . DB_HOST . ';port=' . DB_PORT);

// var_dump(DB_DSN);

// Init du nouveau PDO avec les constantes
try {
    $db = new PDO(DB_DSN, DB_USER, DB_PASS);
    $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
} catch (PDOException $e) {
    echo 'erreur : ' . $e->getMessage() . "\r\n";
}


