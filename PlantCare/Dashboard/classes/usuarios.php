<?php

    if ($_SERVER['METHOD'] == 'POST') {
        //$conexion = new Modelos_Conexion();

        //datos de login
        $_host =        'localhost';
        $_usuario =     'czebra_admin';
        $_contrasena =  'czebrasql2030';
        $_nombre =      'czebra_usuarios';
        $_db =          null;
        $_db = new PDO("mysql:host=$_host;dbname=$_nombre;charset=utf8", $_usuario, $_contrasena);
        $_db->exec("SET NAMES UTF8");
        $arregloDatos = array($_POST['name'], $_POST['lastname'], $_POST['phone'], $_POST['email'], $_POST['company'], $_POST['role'], $_POST['password']);
        $sth = $_db->prepare("INSERT INTO usuarios (name, lastname, phone, email, company, role, password, username) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        if (!$sth->execute($arregloDatos)) {

        }
    }
?>