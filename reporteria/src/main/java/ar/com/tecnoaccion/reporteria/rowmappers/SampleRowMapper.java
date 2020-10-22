package ar.com.tecnoaccion.reporteria.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.com.tecnoaccion.reporteria.beans.Sample;

public class SampleRowMapper implements RowMapper<Sample> {

    @Override
    public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {

        Sample sample = new Sample();

        sample.setData1(rs.getString(1));
        sample.setData2(rs.getString(2));
        sample.setData3(rs.getString(3));
        sample.setData4(rs.getString(4));

        return sample;
    }

}