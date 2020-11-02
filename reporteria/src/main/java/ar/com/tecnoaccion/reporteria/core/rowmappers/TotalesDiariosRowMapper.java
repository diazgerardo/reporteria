package ar.com.tecnoaccion.reporteria.core.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.com.tecnoaccion.reporteria.beans.TotalesDiarios;

public class TotalesDiariosRowMapper implements RowMapper<TotalesDiarios> {

    @Override
    public TotalesDiarios mapRow(ResultSet rs, int rowNum) throws SQLException {

        TotalesDiarios totalesDiarios = new TotalesDiarios();
        totalesDiarios.setImporte(rs.getDouble(1));
        return totalesDiarios;
    }

}