package com.accenture.dao;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.accenture.models.Cliente;
import com.accenture.repository.ClienteRepository;
@Service

public class ClienteDaoImpl implements ClienteDao {
	@Autowired
	ClienteRepository clienteRepo;
	
	@Override
	public Cliente createCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteRepo.save(cliente);
	}

	@Override
	public List<Cliente> listarClientes() {
		// TODO Auto-generated method stub
		return clienteRepo.findAll();
	}

	@Override
	public Cliente getClienteById(int id) {
		// TODO Auto-generated method stub
		return clienteRepo.findById(id).orElseThrow(()-> new ResourceAccessException("No existe el cliente con el id:"+ id));
	}


	@Override
	public void eliminarCliente(int id) {
		// TODO Auto-generated method stub
		Cliente cliente = clienteRepo.findById(id).orElseThrow(() -> new ResourceAccessException("No se encontro el cliente con el id:" + id));
		clienteRepo.delete(cliente);
		
	}

	@Override
	public Cliente updateProject(Cliente cliente) {
		// TODO Auto-generated method stub
		Cliente clientetUpdate = clienteRepo.findById(cliente.getId()).orElseThrow(()-> new ResourceAccessException("No se encontro el cliente con el id:" + cliente.getId()));
		
		clientetUpdate.setId(cliente.getId());
		clientetUpdate.setNombre(cliente.getNombre());
		clientetUpdate.setApellido(cliente.getApellido());
		clientetUpdate.setCedula(cliente.getCedula());
		clientetUpdate.setDireccion(cliente.getDireccion());
		clientetUpdate.setCreadoEn(cliente.getCreadoEn());
		
		clienteRepo.save(clientetUpdate);
		return clientetUpdate;
	}

	@Override
	public Cliente findByCedulaAndDireccion(String cedula, String direccion) {
		// TODO Auto-generated method stub
		return clienteRepo.findByCedulaAndDireccion(cedula, direccion);
	}

	@Override
	public Cliente findByCedula(String cedula) {
		// TODO Auto-generated method stub
		return clienteRepo.findByCedula(cedula);
	}



}
