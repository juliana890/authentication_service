$(function(){
	var token, id, email, senha, nomeCliente, emailCliente, senhaCliente, telefoneCliente;
	
	$(".entrar").click(function(){
		console.log("Clicou!!!");		
		
		email = $("#email").val();
		senha = $("#senha").val();
		
		var login = JSON.stringify({
			email: email,
			senha: senha	
		});
		
		console.log(login);
		
		$.ajax({
            type: "POST",
            url: "http://localhost:8080/login",
            contentType: "application/json",
            data: login,
            success: function (output, status, xhr) {
				alert("Sucesso ao autenticar");  
				token = xhr.getResponseHeader('Authorization'); 
			    console.log(xhr.getResponseHeader('Authorization'));
            },
            error: function (output, status, xhr) {
				alert("Usuário ou senha inválido");
				console.log("Erro");
				console.log(xhr.getResponseHeader('Authorization'));
            }
        });
	});
	
	$(".get").click(function(){
		//Limpando a tabela
		$(".clientes > tbody").empty();
		
		$.ajax({
            type: "GET",
            url: "http://localhost:8080/clientes",
            contentType: "application/json",
			headers: {
                    "Authorization": token
            },
            success: function (data) {
				console.log("Sucesso " + JSON.stringify(data));
				for(var i in data){
					$('.clientes tbody:last-child').append(
						"<tr>" +
							  "<td>" + data[i].id + "</td>" +
							  "<td>" + data[i].nome + "</td>" +
							  "<td>" + data[i].email + "</td>" +
							  "<td>" + data[i].telefone + "</td>" +
					     "</tr>"
					);
					
				}
            },
            error: function (data) {
				alert("Erro ao buscar clientes");
				console.log("Erro");
				
            }
        });
	});
	
	$(".getId").click(function(){
		//Limpando a tabela
		$(".clientes > tbody").empty();
		id = $("#id").val();
		
		if(id != null && id != ""){
			$.ajax({
	            type: "GET",
	            url: "http://localhost:8080/clientes/" + id,
	            contentType: "application/json",
				headers: {
	                    "Authorization": token
	            },
	            success: function (data) {
					console.log("Sucesso " + JSON.stringify(data));
					$('.clientes tbody:last-child').append(
						"<tr>" +
							  "<td>" + data.id + "</td>" +
							  "<td>" + data.nome + "</td>" +
							  "<td>" + data.email + "</td>" +
							  "<td>" + data.telefone + "</td>" +
						"</tr>"
					);
	            },
	            error: function (data) {
					console.log("Erro");
					alert("Erro ao buscar clientes");
	            }
        	});
		
		}else{
			alert("O campo (Id) precisa ser preenchido");
		}
	});
	
	$(".post").click(function(){
		//Limpando a tabela
		$(".clientes > tbody").empty();
		nomeCliente = $("#nomeCliente").val();
		emailCliente = $("#emailCliente").val();
		telefoneCliente = $("#telefoneCliente").val();
		senhaCliente = $("#senhaCliente").val();
		
		var dados = JSON.stringify({
				nome: nomeCliente,
				email: emailCliente,
				telefone: telefoneCliente,
				senha: senhaCliente 
		});
		
		if((nomeCliente != null && nomeCliente != "") && (emailCliente != null && emailCliente != "") && (telefoneCliente != null && telefoneCliente != "") && (senhaCliente != null && senhaCliente != "")){
			$.ajax({
	            type: "POST",
	            url: "http://localhost:8080/clientes",
	            contentType: "application/json",
				data: dados,
				headers: {
	                    "Authorization": token
	            },
	            success: function (data) {
					console.log("Sucesso");
					alert("Registro inserido com sucesso");
	            },
	            error: function (data) {
					console.log("Erro");
					alert("Erro ao inserir cliente");
	            }
        	});
		
		}else{
			alert("Os campos (Nome, Email, Telefone e Senha) precisam ser preenchidos");
		}
	});
	
	$(".put").click(function(){
		//Limpando a tabela
		$(".clientes > tbody").empty();
		id = $("#id").val();
		nomeCliente = $("#nomeCliente").val();
		emailCliente = $("#emailCliente").val();
		telefoneCliente = $("#telefoneCliente").val();
		senhaCliente = $("#senhaCliente").val();
		
		var dados = JSON.stringify({
				nome: nomeCliente,
				email: emailCliente,
				telefone: telefoneCliente,
				senha: senhaCliente 
		});
		
		if((id != null && id != "") && (nomeCliente != null && nomeCliente != "") && (emailCliente != null && emailCliente != "") && (telefoneCliente != null && telefoneCliente != "") && (senhaCliente != null && senhaCliente != "")){
			$.ajax({
	            type: "PUT",
	            url: "http://localhost:8080/clientes/" + id,
	            contentType: "application/json",
				data: dados,
				headers: {
	                    "Authorization": token
	            },
	            success: function (data) {
					console.log("Sucesso");
					alert("Registro alterado com sucesso");
	            },
	            error: function (data) {
					console.log("Erro");
					alert("Erro ao alterar cliente");
	            }
        	});
		
		}else{
			alert("Os campos (Id, Nome, Email, Telefone e Senha) precisam ser preenchidos");
		}
	});
	
	$(".delete").click(function(){
		//Limpando a tabela
		$(".clientes > tbody").empty();
		id = $("#id").val();
		
		if(id != null && id != ""){
			$.ajax({
	            type: "DELETE",
	            url: "http://localhost:8080/clientes/" + id,
	            contentType: "application/json",
				headers: {
	                    "Authorization": token
	            },
	            success: function (data) {
					console.log("Sucesso");
					alert("Registro deletado com sucesso");
	            },
	            error: function (data) {
					console.log("Erro");
					alert("Erro ao deletar cliente");
	            }
        	});
		
		}else{
			alert("O campos (Id) precisa ser preenchido");
		}
	});
	
});