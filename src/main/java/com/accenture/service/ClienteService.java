package com.accenture.service;

import java.util.List;

import com.accenture.models.Cliente;

public interface ClienteService {
	
	Cliente createCliente(Cliente cliente);
	
    List<Cliente> listarClientes();

    Cliente getClienteById(int id); 
    
    Cliente updateProject(Cliente cliente);

    void eliminarCliente(int id);
    
    Cliente findByCedulaAndDireccion(String cedula, String direccion);

    Cliente findByCedula(String cedula);
}
