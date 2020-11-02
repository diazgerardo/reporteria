package ar.com.tecnoaccion.reporteria.services;

import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;

public interface OrganizacionService {
    OrganizacionDTO getByCodigo(Integer codigo);
}
