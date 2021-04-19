###RUTAS API

## POST CLIENTES
# http://localhost:8084/api/pagos/clientes 
## GET CLIENTES
# http://localhost:8084/api/pagos/clientes 
## EDIT CLIENTES
# http://localhost:8084/api/pagos/clientes/{id}/edit 
## DELETE CLIENTES
# http://localhost:8084/api/pagos/clientes/{id}/delete

## GET FACTURAS
# http://localhost:8084/api/pagos/facturas

## GET PRODUCTOS
# http://localhost:8084/api/pagos/productos
## POST PRODUCTOS
# http://localhost:8084/api/pagos/productos

## POST PEDIDOS
# http://localhost:8084/api/pagos/pedidos 
## POST PEDIDOS
# http://localhost:8084/api/pagos/pedidos 
## EDIT PEDIDOS
# http://localhost:8084/api/pagos/pedidos
## DELETE PEDIDOS
# http://localhost:8084/api/pagos/pedidos/{id}

 
##mysql
insert into clientes(id, nombre, apellido, cedula, direccion, creado_en) values (1, 'Juan', 'Santacruz', '444', 'Calle 26', now());
insert into clientes(id, nombre, apellido, cedula, direccion, creado_en) values (2, 'Oscar', 'Mayor', '12345', '11# 14-08', now());



insert into pedidos(costo_pedido, fecha_pedido, servicio_domicilio, id_cliente) values (120000, '2021-04-18 18:12:38.000000', 25000, 2);
insert into pedidos(costo_pedido, fecha_pedido, servicio_domicilio, id_cliente) values (120000, '2021-04-18 06:12:38.000000', 25000, 1);


insert into productos(id, porcenta_iva, nombre_producto, precio) values (1, 8.0, 'Keyboard', 23000.0);
insert into productos(id, porcenta_iva, nombre_producto, precio) values (2, 10.0, 'Mouse', 23000.0);



insert into facturas(id, cantidad, id_producto, id_pedido) values (1, 1, 2, 1);
insert into facturas(id, cantidad, id_producto, id_pedido) values (2, 2, 1, 1);




