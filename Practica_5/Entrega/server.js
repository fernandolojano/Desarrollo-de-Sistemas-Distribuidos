var http = require("http");
var url = require("url");
var fs = require("fs");
var socketio = require("socket.io");
var path = require("path");
var mimeTypes = { "html": "text/html", "jpeg": "image/jpeg", "js": "text/javascript", "css": "text/css", "swf": "application/x-shockwave-flash" };

var MongoClient = require('mongodb').MongoClient;
var MongoServer = require('mongo').MongoServer;

var TEMPERATURA_MAX = 36;
var TEMPERATURA_MIN = 10;
var LUMINOSIDAD_MAX = 100;
var LUMINOSIDAD_MIN = 0;
var temperatura = 22;
var luminosidad = 50;
var persianas = "CLOSED";
var aireAcondicionado = "OFF";




var httpServer = http.createServer(
	function(request, response){
		var uri = url.parse(request.url).pathname;
		if(uri=="/") uri ="/client.html";
		var frame = path.join(process.cwd(), uri);
		fs.exists(fname, function(exists){
			if(exists){
				fs.readFile(fname, function(err, data){
					if(!err){
						var extension = path.extname(fname).split(".")[1];
						var mimeType = mimeTypes[exstension];
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

MongoClient.connect("mongodb://localhost:27017/", function(err, db){
	httpServer.listen(8080);

	var io = socketio.listen(httpServer);
	var dbo = db.db("BDSistDomotico");

	dbo.createCollection("Cambiosensores", function(err, collection){
		io.sockets.on('connection',
		function(client){
			client.on('setTemperatura', function(data){
				temperatura = data.temperatura;
				collection.insert(data, {safe:true}, function(err,result){});
					io.sockets.emit("updateTemperatura", temperatura);

					if(temperatura > TEMPERATURA_MAX)
						io.sockets.emit("TemperaturaNotificacion", "La temperatura insertada supera los valores maximos soportados");
					else if(temperatura < TEMPERATURA_MIN)
						io.sockets.emit("TemperaturaNotificacion", "La temperatura insertada es inferior a los valores minimos soportados");
					else
						io.sockets.emit("TemperaturaNotifiacion", ""); 
			});

			client.on("setLuminosidad", function(data){
				luminosidad = data.luminosidad;
				collection.insert(data, {safe:true}, function(err,result){});
				io.sockets.emit("updateLuminosidad", luminosidad);

				if(luminosidad > LUMINOSIDAD_MAX)
					io.sockets.emit("LuminosidadNotificacion", "La luminosidad insertada supera los valores maximos soportados");
				else if(luminosidad < LUMINOSIDAD_MIN)
					io.socket.emit("LuminosidadNotificacion", "La luminosidad insertada es inferior a los valores minimos soportados");
				else
					io.sockets.emit("LuminosidadNotificacion", "");
			});

			client.on('changeAire', function(){
				if(aire =='OFF')
					aire = 'ON';
				else if(aire == 'ON')
					aire = 'OFF';

				io.sockets.emit('updateAire', aire);
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
				io.sockets.emit('updateAire', aire);
			});

			client.on('getPersianas', function(){
				io.sockets.emit('updatePersianas', persianas);
			});
		});
	});
});

console.log("Sistema Domotico Iniciado");
