package com.accenture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.dao.ClienteDao;
import com.accenture.models.Cliente;
@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	ClienteDao clienteDao;

	@Override
	public Cliente createCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.createCliente(cliente);
	}

	@Override
	public List<Cliente> listarClientes() {
		// TODO Auto-generated method stub
		return clienteDao.listarClientes();
	}

	@Override
	public Cliente getClienteById(int id) {
		// TODO Auto-generated method stub
		return clienteDao.getClienteById(id);
	}

	@Override
	public void eliminarCliente(int id) {
		// TODO Auto-generated method stub
		clienteDao.eliminarCliente(id);
	}

	@Override
	public Cliente updateProject(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.updateProject(cliente);
	}

	@Override
	public Cliente findByCedulaAndDireccion(String cedula, String direccion) {
		// TODO Auto-generated method stub
		return clienteDao.findByCedulaAndDireccion(cedula, direccion);
	}

	@Override
	public Cliente findByCedula(String cedula) {
		// TODO Auto-generated method stub
		return clienteDao.findByCedula(cedula);
	}

	

}
