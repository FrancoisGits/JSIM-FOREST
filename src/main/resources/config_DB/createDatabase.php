<?php

require ('./config/database.php');

// fonction pour se connecter à la BDD
function connectDb($dbName) {
    try {
        $dsn = !$dbName ? 'mysql:host=' . DB_HOST . ';port=' . DB_PORT : 'mysql:dbname=jsimforestdemo' . ';host=' . DB_HOST . ';port=' . DB_PORT;
        echo $dsn;
        $db = new PDO ($dsn, DB_USER, DB_PASS);
    } catch (PDOException $e) {
        echo 'erreur connectDb() : ' . $e->getMessage() . "\r\n";
    }
    return $db;
}

// fonction pour créer la base de données
function create(){
    // si $dbName est à false, la function connect() se co juste à MySQL
    // si $dbName est à true , la function connect() se co avec le dsn de la config (donc sur la base déjà créé)
    $dbName = false;
    $db = connectDb($dbName);

    echo "Script de création de BDD lancé \r\n";

    try {
        $content = utf8_encode(file_get_contents('.\jsimforestdemo.sql', FALSE, NULL));
        echo "C'est parti ! \r\n";
        $db->exec($content);
        echo "C'est fini ! \r\n";
    } catch (Exception $e) {
        echo 'erreur create() : ' . $e->getMessage() . "\r\n";
    }
    return $db;
}

create();
