SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: reportes; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA reportes;


ALTER SCHEMA reportes OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: grupos; Type: TABLE; Schema: reportes; Owner: postgres
--

CREATE TABLE reportes.grupos (
    id integer NOT NULL,
    grupo character varying
);


ALTER TABLE reportes.grupos OWNER TO postgres;

--
-- Name: grupos_id_seq; Type: SEQUENCE; Schema: reportes; Owner: postgres
--

CREATE SEQUENCE reportes.grupos_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reportes.grupos_id_seq OWNER TO postgres;

--
-- Name: grupos_id_seq; Type: SEQUENCE OWNED BY; Schema: reportes; Owner: postgres
--

ALTER SEQUENCE reportes.grupos_id_seq OWNED BY reportes.grupos.id;


--
-- Name: reporte_parametros; Type: TABLE; Schema: reportes; Owner: postgres
--

CREATE TABLE reportes.reporte_parametros (
    id integer NOT NULL,
    reporte_id numeric,
	nombre character varying,
    tipo character varying,
    opcional boolean
);


ALTER TABLE reportes.reporte_parametros OWNER TO postgres;

--
-- Name: reporte_parametros_id_seq; Type: SEQUENCE; Schema: reportes; Owner: postgres
--

CREATE SEQUENCE reportes.reporte_parametros_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reportes.reporte_parametros_id_seq OWNER TO postgres;

--
-- Name: reporte_parametros_id_seq; Type: SEQUENCE OWNED BY; Schema: reportes; Owner: postgres
--

ALTER SEQUENCE reportes.reporte_parametros_id_seq OWNED BY reportes.reporte_parametros.id;


--
-- Name: reportes; Type: TABLE; Schema: reportes; Owner: postgres
--

CREATE TABLE reportes.reportes (
    id integer NOT NULL,
    codigo character varying NOT NULL,
    titulo character varying DEFAULT ''::character varying NOT NULL,
	pie character varying DEFAULT ''::character varying NOT NULL,
    grupo_id numeric,
	consulta_sql character varying DEFAULT ''::character varying NOT NULL
);


ALTER TABLE reportes.reportes OWNER TO postgres;

--
-- Name: reportes_id_seq; Type: SEQUENCE; Schema: reportes; Owner: postgres
--

CREATE SEQUENCE reportes.reportes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reportes.reportes_id_seq OWNER TO postgres;

--
-- Name: reportes_id_seq; Type: SEQUENCE OWNED BY; Schema: reportes; Owner: postgres
--

ALTER SEQUENCE reportes.reportes_id_seq OWNED BY reportes.reportes.id;


--
-- Name: salida; Type: TABLE; Schema: reportes; Owner: postgres
--

CREATE TABLE reportes.salida (
    id integer NOT NULL,
    reporte_id numeric,
    nombre character varying,
	etiqueta character varying,
	tam decimal
);


ALTER TABLE reportes.salida OWNER TO postgres;

--
-- Name: salida_id_seq; Type: SEQUENCE; Schema: reportes; Owner: postgres
--

CREATE SEQUENCE reportes.salida_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reportes.salida_id_seq OWNER TO postgres;

--
-- Name: salida_id_seq; Type: SEQUENCE OWNED BY; Schema: reportes; Owner: postgres
--

ALTER SEQUENCE reportes.salida_id_seq OWNED BY reportes.salida.id;


--
-- Name: grupos id; Type: DEFAULT; Schema: reportes; Owner: postgres
--

ALTER TABLE ONLY reportes.grupos ALTER COLUMN id SET DEFAULT nextval('reportes.grupos_id_seq'::regclass);


--
-- Name: reporte_parametros id; Type: DEFAULT; Schema: reportes; Owner: postgres
--

ALTER TABLE ONLY reportes.reporte_parametros ALTER COLUMN id SET DEFAULT nextval('reportes.reporte_parametros_id_seq'::regclass);


--
-- Name: reportes id; Type: DEFAULT; Schema: reportes; Owner: postgres
--

ALTER TABLE ONLY reportes.reportes ALTER COLUMN id SET DEFAULT nextval('reportes.reportes_id_seq'::regclass);


--
-- Name: salida id; Type: DEFAULT; Schema: reportes; Owner: postgres
--

ALTER TABLE ONLY reportes.salida ALTER COLUMN id SET DEFAULT nextval('reportes.salida_id_seq'::regclass);

--
-- Name: grupos_id_seq; Type: SEQUENCE SET; Schema: reportes; Owner: postgres
--

SELECT pg_catalog.setval('reportes.grupos_id_seq', 1, false);


--
-- Name: reporte_parametros_id_seq; Type: SEQUENCE SET; Schema: reportes; Owner: postgres
--

SELECT pg_catalog.setval('reportes.reporte_parametros_id_seq', 1, false);


--
-- Name: reportes_id_seq; Type: SEQUENCE SET; Schema: reportes; Owner: postgres
--

SELECT pg_catalog.setval('reportes.reportes_id_seq', 1, false);


--
-- Name: salida_id_seq; Type: SEQUENCE SET; Schema: reportes; Owner: postgres
--

SELECT pg_catalog.setval('reportes.salida_id_seq', 1, false);
