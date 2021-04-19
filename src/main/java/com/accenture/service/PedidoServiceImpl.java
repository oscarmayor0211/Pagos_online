package com.accenture.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.dao.ClienteDao;
import com.accenture.exception.ErrorDetails;
import com.accenture.models.Cliente;
import com.accenture.models.Factura;
import com.accenture.models.Pedido;
import com.accenture.models.Producto;
import com.accenture.repository.ClienteRepository;
import com.accenture.repository.FacturaRepository;
import com.accenture.repository.PedidoRepository;
import com.accenture.repository.ProductoRepository;


@Service
public class PedidoServiceImpl implements PedidoService {


    private PedidoRepository pedidoRepository;
    private ProductoRepository productoRepository;
    private ClienteRepository clienteRepository;
    private FacturaRepository facturaRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, ProductoRepository productoRepository,
                             ClienteRepository clienteRepository, FacturaRepository facturaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.facturaRepository = facturaRepository;
    }


    @Override
    @Transactional
    public Map<String, Object> generarFactura(Pedido pedido, Long[] idProductos, Integer[] cantidad, Map<String, Object> model) {

        try {

            Cliente cliente = obtenerClientePorCedulaYDireccion(pedido.getCliente().getCedula(),
                                                                pedido.getCliente().getDireccion());
            if (cliente.equals(null)) {
                model.put("respuesta", "El Cliente no existe en la base de datos");
                return model;
            }
            LocalDateTime fecha = LocalDateTime.now();
            pedido.setFechaPedido(fecha);
            pedido.setCliente(cliente);

            model.put("Empresa", Pedido.NOMBRE_EMPRESA);
            model.put("fecha", fecha);
            model.put("nombre del cliente", cliente.getNombre()+ " " + cliente.getApellido());
            model.put("cedula", cliente.getCedula());
            model.put("dirección", cliente.getDireccion());

            Double subTotal = 0.0;
            Double valorBasico = 0.0;

            for (int i = 0; i < cantidad.length; i++) {
                Producto producto = productoRepository.findById(idProductos[i]).get();

                model.put("producto" + (i + 1), producto.getNombreProducto()
                        + " valor: " + producto.getPrecio() + " cantidad: " + cantidad[i]);

                if (producto.equals(null)) {
                    model.put("respuesta", "El Producto no existe en la base de datos!");
                    return model;
                }
                // precion con el iva
                Double precioConIva = producto.getPrecio() + (producto.getPrecio() * producto.getIva()) / 100;
                subTotal += precioConIva * cantidad[i];

                valorBasico += producto.getPrecio() * cantidad[i];

                if (valorBasico <= 70000) {
                    pedido.setCostoPedido(valorBasico);
                    pedido.setServicioDomicilio(0.0);
                } else if (subTotal > 70000 && subTotal <= 100000) {
                    pedido.setCostoPedido(subTotal + Pedido.VALOR_DOMICILIO);
                    pedido.setServicioDomicilio(Pedido.VALOR_DOMICILIO);
                } else {
                    pedido.setCostoPedido(subTotal);
                    pedido.setServicioDomicilio(0.0);
                }

                Factura factura = new Factura();
                factura.setCantidad(cantidad[i]);
                factura.setProducto(producto);
                facturaRepository.save(factura);

                pedido.agregarFactura(factura);

            }

            pedidoRepository.save(pedido);

            model.put("costo domicilio", pedido.getServicioDomicilio());
            model.put("costo pedido", pedido.getCostoPedido());

        }catch (Exception e){
            model.put("mensaje", "Ocurrio un error");
            model.put("error", e.getMessage());
            return model;
        }

        return model;
    }


    @Override
    @Transactional
    public Map<String, Object> actualizarFactura(Pedido pedido,
                                                 Long[] idProductos,
                                                 Integer[] cantidad) {

       Map<String, Object> model = new LinkedHashMap<>();

        try {
            if (!pedidoRepository.existsById(pedido.getId())) { // se necesita el pedido y el cliente en la base de datos para poder actualizar el pedido.
                model.put("mensaje", "El pedido o el cliente no existe en la base de datos.");
                return model;
            }

            Cliente clienteDB = clienteRepository.findByCedula(pedido.getCliente().getCedula());
            Pedido dataBPedido = pedidoRepository.findById(pedido.getId()).orElse(null);
            dataBPedido.setCliente(clienteDB);

            Float hora = ChronoUnit.SECONDS.between(dataBPedido.getFechaPedido(), LocalDateTime.now()) / 3600.0f;

            if (hora >= 5f) {
                model.put("mensaje", "El sistema  no permite actualizar su Pedido");
                return model;
            }

            List<Long> idsProductos = facturaRepository.obtenerProductosPorIdPedido(dataBPedido.getId());
            List<Producto> productoDB = productoRepository.findAllById(idsProductos);
            List<Factura> pedidoFacturas = dataBPedido.getFacturas();

            for (int i = 0; i < pedidoFacturas.size(); i++) {
                pedidoFacturas.get(i).setProducto(productoDB.get(i));
            }

            dataBPedido.setFacturas(pedidoFacturas);

            List<Factura> dbfacturas = dataBPedido.getFacturas(); // facturas en base de datos.
            List<Factura> facturas = new ArrayList<>();        // facturas en el dataBPedido a actualizar.
            List<Factura> facturasExistentes = new ArrayList<>(); // facturas comparadas, productos iguales pueden aumentar cantidad.
            List<Factura> facturasNuevas = new ArrayList<>(); // facturas nuevas para analizar si se pueden cambiar en la dataBPedido.
            List<Factura> facturaCambio = new ArrayList<>(); // facturas cambiadas

            facturasNuevas.addAll(facturas);
            facturaCambio.addAll(dbfacturas);

            for (int i = 0; i < cantidad.length; i++) {
                Boolean existeProducto = productoRepository.existsById(idProductos[i]);
                Producto producto = productoRepository.findById(idProductos[i]).orElse(null);
                Factura factura = new Factura();
                factura.setProducto(producto);
                factura.setCantidad(cantidad[i]);
                facturas.add(factura);
            }

            for (int i = 0; i < facturas.size(); i++) {
                for (int j = 0; j < dbfacturas.size(); j++) {
                    // compara los productos que vienen en el dataBPedido a actualizar con los productos del dataBPedido guardado en base de datos.
                    if (facturas.get(i).getProducto().getNombreProducto().equals
                            (dbfacturas.get(j).getProducto().getNombreProducto())) {
                        // Se agregaran al dataBPedido si la cantidad es mayor o igual.
                        if (facturas.get(i).getCantidad() >= dbfacturas.get(j).getCantidad()) {
                            facturasExistentes.add(facturas.get(i));
                            facturasNuevas.remove(facturas.get(i));
                        } else {
                            model.put(facturas.get(i).getProducto().getNombreProducto(), "A este producto no se le " +
                                    "puede cambiar la cantidad");
                            return model;
                        }
                    } else {
                        facturaCambio.remove(dbfacturas.get(i)); // deja las facturas que estaban en la base de datos y se van a cambiar
                    }
                }
            }

            // Guarda el precio menor de los productos a cambiar por el cliente.
            Double precioMayor = 0.0;
            for (int i = 0; i < facturaCambio.size(); i++) {
                if (facturaCambio.get(i).getProducto().getPrecio() > precioMayor) {
                    precioMayor = facturaCambio.get(i).getProducto().getPrecio();
                }
            }
            Double precioMenor = precioMayor;
            for (int i = 0; i < facturaCambio.size(); i++) {
                if (facturaCambio.get(i).getProducto().getPrecio() < precioMenor) {
                    precioMenor = facturaCambio.get(i).getProducto().getPrecio();
                }
            }

            // Revisa los productos nuevos de los pedidos para dejarlos cambiar.
            Integer contador = 0;
            for (Factura facturasNueva : facturasNuevas) {
                if (precioMenor < facturasNueva.getProducto().getPrecio()) {
                    model.put(facturasNueva.getProducto().getNombreProducto(), "Este producto no se puede cambiar");
                    contador++;
                }
            }
            if (contador > 0) {
                return model;
            }

            model.put("Empresa", dataBPedido.NOMBRE_EMPRESA);
            model.put("fecha", dataBPedido.getFechaPedido());
            model.put("nombre del cliente", dataBPedido.getCliente().getNombre() + " " + dataBPedido.getCliente().getNombre());
            model.put("cedula", dataBPedido.getCliente().getCedula());
            model.put("dirección", dataBPedido.getCliente().getDireccion());

            facturasExistentes.addAll(facturasNuevas);

            facturaRepository.saveAll(facturasExistentes);
            dataBPedido.setFacturas(facturasExistentes);

            Double subTotal = 0.0;
            Double valorBasico = 0.0;
            Integer index = 1;
            for (Factura facturasExistente : facturasExistentes) {
                model.put("producto" + index++, facturasExistente.getProducto().getNombreProducto() +
                        " valor: " + facturasExistente.getProducto().getPrecio() + " cantidad: " +
                        facturasExistente.getCantidad());

                // precion con el iva
                Double precioConIva = facturasExistente.getProducto().getPrecio() +
                        (facturasExistente.getProducto().getPrecio() * facturasExistente.getProducto().getIva()) / 100;

                subTotal += precioConIva * facturasExistente.getCantidad();

                valorBasico += facturasExistente.getProducto().getPrecio() * facturasExistente.getCantidad();

                if (valorBasico <= 70000) {
                    dataBPedido.setCostoPedido(valorBasico);
                    dataBPedido.setServicioDomicilio(0.0);
                } else if (subTotal > 70000 && subTotal <= 100000) {
                    dataBPedido.setCostoPedido(subTotal + dataBPedido.VALOR_DOMICILIO);
                    dataBPedido.setServicioDomicilio(dataBPedido.VALOR_DOMICILIO);
                } else {
                    dataBPedido.setCostoPedido(subTotal);
                    dataBPedido.setServicioDomicilio(0.0);
                }
            }
        }catch (Exception e){
            model.put("mensaje", "Ha ocurrido un error");
            model.put("error", e.getMessage());
            return model;
        }

        return model;
    }

    /**
     * Consulta la lista de pedidos en la base de datos.
     * @return
     */
    @Override
    public List<Pedido> listaPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Map<String, Object> borrarPedidoPorId(Long id, Map<String, Object> model) {

        try {
            Pedido pedido = pedidoRepository.findById(id).orElse(null);
            Float hora = ChronoUnit.SECONDS.between(pedido.getFechaPedido(), LocalDateTime.now()) / 3600.0f;

            if (hora > 12f){
                // Calcula el 10% del valor del pedido cancelado despues de las 12 horas.
                Double excedentePedidoCancelado = (pedido.getCostoPedido() * 10) /100;

                model.put("mensaje", "El pedido se ha cancelado, el excedente a pagar es de " + excedentePedidoCancelado);

                pedidoRepository.deleteById(id);
                return model;
            }

            pedidoRepository.deleteById(id);

        }catch (DataAccessException e){
            model.put("mensaje", "Error al eliminar la factura en la base de datos");
            model.put("error", e.getMostSpecificCause().getMessage());
            return model;
        }

        model.put("mensaje", "El pedido se ha eliminado con exito");
        return model;
    }


 
    public Cliente obtenerClientePorCedulaYDireccion(String cedula, String direccion) {
        Cliente cliente = clienteRepository.findByCedulaAndDireccion(cedula, direccion);
        return cliente;
    }

}
