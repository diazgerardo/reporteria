package ar.com.tecnoaccion.reporteria.utils;

import org.springframework.http.HttpStatus;

public enum DefinedErrors {
    CREDENCIALES_INVALIDAS {
        @Override
        public Integer getErrorCode() {
            return HttpStatus.UNAUTHORIZED.value();
        }

        @Override
        public String getErrorMessage() {
            return "Credenciales invalidas";
        }
    },
    ERROR_GENERICO{
        @Override
        public Integer getErrorCode() {
            return 300;
        }

        @Override
        public String getErrorMessage() {
            return "Reintentar nuevamente";
        }
    },
    USUARIO_EN_USO {
        @Override
        public Integer getErrorCode() {
            return 101;
        }

        @Override
        public String getErrorMessage() {
            return "Correo o Documento ya existente";
        }
    },
    USUARIO_NO_ENCONTRADO {
        @Override
        public Integer getErrorCode() {
            return 102;
        }

        @Override
        public String getErrorMessage() {
            return "Usuario no encontrado";
        }
    },
    JWT_TOKEN_CADUCADO_INVALIDO {
        @Override
        public Integer getErrorCode() {
            return 103;
        }

        @Override
        public String getErrorMessage() {
            return "JWT Token caducado o invalido";
        }
    },
    ERROR_TOKENQR_USUARIO {
        @Override
        public Integer getErrorCode() {
            return 104;
        }

        @Override
        public String getErrorMessage() {
            return "No se puedo crear el nuevo tokenQR de usuario";
        }
    },
    FIELD_ERRORS{
        @Override
        public Integer getErrorCode() {
            return 105;
        }

        @Override
        public String getErrorMessage() {
            return "Campos inválidos";
        }
    },
    JSON_ERROR{
        @Override
        public Integer getErrorCode() {
            return HttpStatus.BAD_REQUEST.value();
        }

        @Override
        public String getErrorMessage() {
            return "Error al parsear JSON";
        }
    },
    ACCESO_DENEGADO{
        @Override
        public Integer getErrorCode() {
            return HttpStatus.FORBIDDEN.value();
        }

        @Override
        public String getErrorMessage() {
            return "Su sesión ha cerrado por inactividad";
        }
    },
    ACCESO_NO_AUTORIZADO{
        @Override
        public Integer getErrorCode() {
            return HttpStatus.FORBIDDEN.value();
        }

        @Override
        public String getErrorMessage() {
            return "Acceso denegado";
        }
    },
    ERROR_TOKENS {
        @Override
        public Integer getErrorCode() {
            return 106;
        }

        @Override
        public String getErrorMessage() {
            return "Datos invalidos";
        }
    },
    ERROR_GENERAR_TOKENS {
        @Override
        public Integer getErrorCode() {
            return 107;
        }

        @Override
        public String getErrorMessage() {
            return "Error al generar tokens";
        }
    },
    ERROR_PERSISTENCIA {
        @Override
        public Integer getErrorCode() {
            return 301;
        }

        @Override
        public String getErrorMessage() {
            return "Error al persistir datos";
        }
    },
    ERROR_DESCONOCIDO{
        @Override
        public Integer getErrorCode() {
            return 304;
        }

        @Override
        public String getErrorMessage() {
            return "Error desconocido";
        }
    },
    ERROR_UUID{
        @Override
        public Integer getErrorCode() {
            return 305;
        }

        @Override
        public String getErrorMessage() {
            return "Error conversion string -> UUID";
        }
    },
    TOKEN_USER_INVALID{
        @Override
        public Integer getErrorCode() {
            return 116;
        }

        @Override
        public String getErrorMessage() {
            return "Token de usuario invalido";
        }
    },
    TOKEN_USER_EXPIRED{
        @Override
        public Integer getErrorCode() {
            return 117;
        }

        @Override
        public String getErrorMessage() {
            return "Token de usuario expirado";
        }
    },
    ERROR_NOT_FOUND{
        @Override
        public Integer getErrorCode(){
            return 308;
        }

        @Override
        public String getErrorMessage(){
            return "Datos no encontrados";
        }
    },
    OPCION_INVALIDA{
        @Override
        public Integer getErrorCode(){
            return 309;
        }

        @Override
        public String getErrorMessage(){
            return "Opcion invalida";
        }
    },
    USUARIO_ACTIVO{
        @Override
        public Integer getErrorCode() {
            return 145;
        }

        @Override
        public String getErrorMessage() {
            return "El usuario ya se encuentra activo";
        }
    },
    ARGUMENTOS_INVALIDOS{
        @Override
        public Integer getErrorCode() {
            return 146;
        }

        @Override
        public String getErrorMessage() {
            return "Argumentos inválidos";
        }
    };

    public abstract Integer getErrorCode();
    public abstract String getErrorMessage();
}