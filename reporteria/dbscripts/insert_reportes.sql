insert into reportes.reportes(id,codigo,titulo,grupo_id,consulta_sql) values(1,1,'titulo',1,'select apellido,nombre,documento,version from billetera.usuarios where active=true and (cast(:desde as varchar) is null or fecha_alta >= to_date(:desde,'YYYYMMDD')) and (cast(:hasta as varchar) is null or fecha_alta <=  to_date(:hasta,'YYYYMMDD')) and version = :version order by 1,2;');

insert into reportes.reporte_parametros(id,reporte_id,nombre,tipo,opcional) values (1,1,'desde',12,false);
insert into reportes.reporte_parametros(id,reporte_id,nombre,tipo,opcional) values (2,1,'hasta',12,false);
insert into reportes.reporte_parametros(id,reporte_id,nombre,tipo,opcional) values (3,1,'version',4,false);


insert into reportes.salida(id,reporte_id,nombre,etiqueta,tam) values(1,1,'nombre','Nombre','22');
insert into reportes.salida(id,reporte_id,nombre,etiqueta,tam) values(2,1,'apellido','Apellido','22');
insert into reportes.salida(id,reporte_id,nombre,etiqueta,tam) values(3,1,'documento','Documento','22');
insert into reportes.salida(id,reporte_id,nombre,etiqueta,tam) values(4,1,'version','Version','22');
