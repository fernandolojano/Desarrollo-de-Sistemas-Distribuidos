var http = require("http");
var url = require("url");
var fs = require("fs");
var socketio = require("socket.io");
var path = require("path");
var mimeTypes = { "html": "text/html", "jpeg": "image/jpeg", "js": "text/javascript", "css": "text/css", "swf": "application/x-shockwave-flash" };

var MongoClient = require('mongodb').MongoClient;
var MongoServer = require('mongodb').MongoServer;

var TEMPERATURA_MAX = 50;
var TEMPERATURA_MIN = 10;
var LUMINOSIDAD_MAX = 100;
var LUMINOSIDAD_MIN = 0;
var temperatura = 20;
var luminosidad = 50;
var persianas = 'CLOSED';
var aireAcondicionado = 'OFF';




var httpServer = http.createServer(
	function(request, response){
		var uri = url.parse(request.url).pathname;
		if (uri=="/") uri = "/client.html";
		var fname = path.join(process.cwd(), uri);
		fs.exists(fname, function(exists){
			if(exists){
				fs.readFile(fname, function(err, data){
					if(!err){
						var extension = path.extname(fname).split(".")[1];
						var mimeType = mimeTypes[extension];
						response.writeHead(200, mimeType);
						response.write(data);
						response.end();
					}
					else{
						response.writeHead(200, {"Content-Type": "text/plain"});
						response.write('Error en la lectura en el fichero: ' +uri);
						response.end();
					}
				});
			}
			else{
				console.log("Peticion invalida: "+uri);
				response.writeHead(200, {"Content-Type": "text/plain"});
				response.write('404 Not Found\n');
				response.end();
			}
		});
	}
);


MongoClient.connect("mongodb://localhost:27017/", {useNewUrlParser: true, useUnifiedTopology: true},function(err, db) {
	httpServer.listen(8080);
	var io = socketio(httpServer);
	var dbo = db.db("BDSistDomotico");

	dbo.createCollection("cambioSensores", function(err, collection){
		io.sockets.on('connection',
		function(client){
			client.on('insertTemperatura', function (data) {
				console.log("temperatura Pasada :" + data.temperatura);
				temperatura = data.temperatura;

					if(temperatura > TEMPERATURA_MAX){
						io.sockets.emit("TemperaturaNotificacion", "La temperatura insertada supera los valores maximos soportados");
					}
					
					else if(temperatura < TEMPERATURA_MIN){
						io.sockets.emit("TemperaturaNotificacion", "La temperatura insertada es inferior a los valores minimos soportados");
					}
					
					else{
						io.sockets.emit("TemperaturaNotificacion", ""); 
						collection.insert( data , {safe:true}, function(err, result) {});
                		io.sockets.emit("updateTemperatura", temperatura);
					}
					
			});

			client.on("insertLuminosidad", function(data){
				luminosidad = data.luminosidad;
				console.log("Luminosidad Pasada :" + data.luminosidad);

				if(luminosidad > LUMINOSIDAD_MAX){
					console.log("Demasiada Luminosidad");

					io.sockets.emit("LuminosidadNotificacion", "La luminosidad insertada supera los valores maximos soportados");
				}
				else if(luminosidad < LUMINOSIDAD_MIN){
					console.log("Insuficiente Luminosidad");
					io.sockets.emit("LuminosidadNotificacion", "La luminosidad insertada es inferior a los valores minimos soportados");
				}
				else{
					io.sockets.emit("LuminosidadNotificacion", "");

                	collection.insert(data, {safe:true}, function(err, result) {});
               		 io.sockets.emit("updateLuminosidad", luminosidad);
				}
			});

			client.on('changeAire', function(){
				if(aireAcondicionado =='OFF')
					aireAcondicionado = 'ON';
				else if(aireAcondicionado == 'ON')
					aireAcondicionado = 'OFF';

				io.sockets.emit('updateAire', aireAcondicionado);
			});

			client.on('changePersianas', function(){
				if(persianas == 'CLOSED')
					persianas = 'OPENED';
				else if(persianas == 'OPENED')
					persianas = 'CLOSED';

				io.sockets.emit('updatePersianas', persianas);
			});

			client.on('getTemperatura', function() {
				io.sockets.emit("updateTemperatura", temperatura);
			});
			client.on('getLuminosidad', function(){
				io.sockets.emit("updateLuminosidad", luminosidad);
			});

			client.on('getAire', function(){
				io.sockets.emit('updateAire', aireAcondicionado);
			});

			client.on('getPersianas', function(){
				io.sockets.emit('updatePersianas', persianas);
			});
		});
	});
});

console.log("Sistema Domotico Iniciado");
