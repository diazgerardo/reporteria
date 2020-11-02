package ar.com.tecnoaccion.reporteria.services.impl;

import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;
import ar.com.tecnoaccion.reporteria.exception.CustomException;
import ar.com.tecnoaccion.reporteria.repositorios.OrganizacionRepository;
import ar.com.tecnoaccion.reporteria.services.OrganizacionService;
import ar.com.tecnoaccion.reporteria.utils.DefinedErrors;
import org.springframework.stereotype.Service;

@Service("organizacionService")
public class OrganizacionServiceImpl implements OrganizacionService {
    private OrganizacionRepository organizacionRepository;

    public OrganizacionServiceImpl(OrganizacionRepository organizacionRepository) {
        this.organizacionRepository = organizacionRepository;
    }

    @Override
    public OrganizacionDTO getByCodigo(Integer codigo) {
        return organizacionRepository.findByCodigo(codigo)
                .orElseThrow(() -> new CustomException(DefinedErrors.ERROR_NOT_FOUND));
    }
}
