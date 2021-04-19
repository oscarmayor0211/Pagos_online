package com.accenture.dao;

import java.util.List;

import com.accenture.models.Cliente;

public interface ClienteDao {
	Cliente createCliente(Cliente cliente);
	
    List<Cliente> listarClientes();

    Cliente getClienteById(int id);

    void eliminarCliente(int id);
    
    Cliente updateProject(Cliente cliente);
    
    Cliente findByCedulaAndDireccion(String cedula, String direccion);

    Cliente findByCedula(String cedula);

}
