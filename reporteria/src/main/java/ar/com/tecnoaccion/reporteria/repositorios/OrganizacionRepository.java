package ar.com.tecnoaccion.reporteria.repositorios;

import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;

import java.util.Optional;

public interface OrganizacionRepository {
    Optional<OrganizacionDTO> findByCodigo(Integer codigo);
}
