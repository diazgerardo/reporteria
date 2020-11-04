insert into reportes.reportes(id,codigo,titulo,grupo_id,consulta_sql) values(1,1,'titulo',1,'select apellido,nombre,documento from billetera.usuarios where active=true and cast(:desde as varchar) is null or fecha_alta >= to_date(:desde,''YYYYMMDD'') and cast(:hasta as varchar) is null or fecha_alta <=  to_date(:hasta,''YYYYMMDD'') order by 1,2;');
insert into reportes.reporte_parametros(id,reporte_id,nombre,tipo,opcional) values (1,1,'desde','varchar',false);
insert into reportes.reporte_parametros(id,reporte_id,nombre,tipo,opcional) values (2,1,'hasta','varchar',false);


insert into reportes.salida(id,reporte_id,nombre,etiqueta,tam) values(1,1,'nombre','Nombre','33');
insert into reportes.salida(id,reporte_id,nombre,etiqueta,tam) values(2,1,'apellido','Apellido','33');
insert into reportes.salida(id,reporte_id,nombre,etiqueta,tam) values(3,1,'documento','Documento','33');
