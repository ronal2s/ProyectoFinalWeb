<!doctype html>
<html lang="en" manifest="/templates/sinconexion.appcache">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v4.0.1">
    <link href="/templates/css/bootstrap.css" rel="stylesheet">
    <link href="/templates/login/signin.css" rel="stylesheet">

    <script type="text/javascript" src="/templates/js/bootstrap.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <link href="/templates/css/globalStyles.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.5/examples/sign-in/">

    <!-- Bootstrap core CSS -->
    <!--<link href="../assets/dist/css/bootstrap.css" rel="stylesheet">-->

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
    <!-- Custom styles for this template -->
    <!--<link href="/public/templates/login/signin.css" rel="stylesheet">-->


    <script>

        var indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB

        var dataBase = indexedDB.open("Parcial2Web", 1);

        dataBase.onupgradeneeded = function (e) {
            console.log("Creando la estructura de la tabla");

            active = dataBase.result;

            var usuario = active.createObjectStore("usuario", {keyPath: 'user', autoIncrement: false});
            usuario.createIndex('por_user', 'user', {unique: true});
            var formularios = active.createObjectStore("formularios", { keyPath : 'id', autoIncrement : true });

            formularios.createIndex('por_id', 'id', {unique: true});

        };

        dataBase.onsuccess = function (e) {
            console.log('Proceso ejecutado de forma correctamente');
        };

        dataBase.onerror = function (e) {
            console.error('Error en el proceso: ' + e.target.errorCode);
        };

        function agregarUsuario() {
            var dbActiva = dataBase.result;

            var transaccion = dbActiva.transaction(["usuario"], "readwrite");


            transaccion.onerror = function (e) {
                alert(request.error.name + '\n\n' + request.error.message);
            };

            transaccion.oncomplete = function (e) {
                document.querySelector("#user").value = '';

            };

            var usuario = transaccion.objectStore("usuario");

            var request = usuario.put({
                user: document.querySelector("#user").value,
                name: document.querySelector("#name").value,
                password: document.querySelector("#password").value,

            });

            request.onerror = function (e) {
                var mensaje = "Error: " + e.target.errorCode;
                console.error(mensaje);
                alert(mensaje)
            };

            request.onsuccess = function (e) {
                console.log(e);
            };


        }

        function loginUserPassword() {
            var user = document.querySelector("#user").value;
            var data = dataBase.result.transaction(["usuario"]);
            var usuario = data.objectStore("usuario");

            usuario.get("" + user).onsuccess = function (e) {

            
                var resultado = e.target.result;
                
                var form = document.getElementById("login")

                if (resultado !== undefined) {
                    var myHeaders = new Headers();

                    console.log(JSON.stringify(resultado));
                    myHeaders.append('usuario', "" + resultado.user);
                    if (resultado.user === document.querySelector("#user").value && resultado.password === document.querySelector("#password").value) {
                        if (navigator.onLine === false){
                            window.location.href = "/formulario";
                        }else{
                            form.submit();
                        }

                    } else {
                        form.submit();
                    }

                } else {
                    console.log("Usuario no encontrado...");

                    form.submit();
                }
            };

        }

    </script>

</head>
<body class="text-center body">

<form class="form-signin" action="/login" method="post" id="login">
    <h1 class="h3 mb-3 font-weight-normal">Iniciar Sesi&#243n</h1>
    <label for="user" class="sr-only">Usuario</label>
    <input type="text" id="user" name="user" class="form-control" placeholder="Usuario" required autofocus>
    <label for="password" class="sr-only">Contrase&#241a</label>
    <input type="password" id="password" name="password" class="form-control" placeholder="Contrase&#241a" required>
    <button class="btn btn-lg btn-light btn-block" type="button" onclick="loginUserPassword()" form="login">Iniciar</button>
    <a class="btn btn-lg btn-block btn-secondary" type="submit" href="/register">Registrar</a>
    <label>
        <input class="checkbox mb-3" type="checkbox" value="remember-me" name="statu"> Recordarme
    </label>
</form>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>



