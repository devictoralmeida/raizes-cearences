CREATE TABLE "codigo_verificacao"
(
    "id"            UUID       NOT NULL,
    "codigo"        varchar(5) NOT NULL,
    "fl_validado"   boolean,
    "dat_expiracao" TIMESTAMP  NOT NULL,
    CONSTRAINT "pk_codigoverificacao" PRIMARY KEY ("id")
);

CREATE TABLE "usuario"
(
    "id"                 uuid PRIMARY KEY,
    "login"              varchar(14) NOT NULL,
    "tipo_perfil"        varchar(13) NOT NULL,
    "firebase_uid"       varchar(28) NOT NULL,
    "senha"              varchar(255) NULL DEFAULT NULL,
    "codigo_verificacao" UUID               DEFAULT NULL,
    "dat_criacao"        timestamp   NOT NULL DEFAULT (now()),
    "dat_atualizacao"    timestamp NOT NULL DEFAULT (now()),
    CONSTRAINT "usuario_codigo_verificacao" FOREIGN KEY ("codigo_verificacao") REFERENCES "codigo_verificacao" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "pessoa_perfil"
(
    "id"              uuid PRIMARY KEY,
    "tipo_usuario"    varchar(15) NOT NULL,
    "documento"       varchar(14) NOT NULL,
    "user_id"         uuid        NOT NULL,
    "contato_id"      uuid        NOT NULL,
    "endereco_id"     uuid        NOT NULL,
    "vinculo_id"      uuid        NOT NULL,
    "dados_pessoa_id" uuid        NOT NULL,
    "dat_criacao"     timestamp   NOT NULL DEFAULT (now()),
    "dat_atualizacao" timestamp   NOT NULL DEFAULT (now())
);

CREATE TABLE "dados_pessoa_fisica"
(
    "id"              uuid PRIMARY KEY,
    "rg"              varchar(20)  NOT NULL,
    "orgao_expeditor" varchar(15),
    "dat_expedicao"   date,
    "nome"            varchar(100) NOT NULL,
    "sobrenome"       varchar(100) NOT NULL,
    "dat_nascimento"  date         NOT NULL,
    "sexo"            varchar(9)   NOT NULL,
    "grau_instrucao"  varchar(18),
    "nome_mae"        varchar(150),
    "nome_pai"        varchar(150)
);

CREATE TABLE "dados_pessoa_juridica"
(
    "id"                        uuid PRIMARY KEY,
    "sigla"                     varchar(150),
    "razao_social"              varchar(100) NOT NULL,
    "nome_fantasia"             varchar(150),
    "inscricao_junta_comercial" varchar(50),
    "inscricao_estadual"        varchar(12),
    "dat_fundacao"              date
);

CREATE TABLE "anexo"
(
    "id"         uuid PRIMARY KEY,
    "nome_anexo" varchar(255) NOT NULL,
    "tipo_mime"  varchar(5)   NOT NULL
);

CREATE TABLE "pessoa_perfil_anexo"
(
    "id"               uuid PRIMARY KEY,
    "tipo_documento"   varchar NOT NULL,
    "id_pessoa_perfil" uuid    NOT NULL,
    "id_anexo"         uuid    NOT NULL
);

CREATE TABLE "contato"
(
    "id"                  uuid PRIMARY KEY,
    "preferencia_contato" varchar(8) NOT NULL,
    "numero_contato"      varchar(11),
    "fl_whatsapp"         boolean,
    "numero_whatsapp"     varchar(11),
    "email"               varchar(320),
    "fl_newsletter"       boolean,
    "dat_criacao"         timestamp  NOT NULL DEFAULT (now()),
    "dat_atualizacao"     timestamp  NOT NULL DEFAULT (now())
);

CREATE TABLE "endereco"
(
    "id"               uuid PRIMARY KEY,
    "cep"              varchar(8)   NOT NULL,
    "municipio"        varchar(100) NOT NULL,
    "distrito"         varchar(100) NOT NULL,
    "localidade"       varchar(100) NOT NULL,
    "logradouro"       varchar(100) NOT NULL,
    "numero"           varchar(10)  NOT NULL,
    "complemento"      varchar(50),
    "bairro"           varchar(50),
    "uf"               varchar(2)   NOT NULL,
    "ponto_referencia" varchar(50),
    "dat_criacao"      timestamp    NOT NULL DEFAULT (now()),
    "dat_atualizacao"  timestamp    NOT NULL DEFAULT (now())
);

CREATE TABLE "vinculo"
(
    "id"                     uuid PRIMARY KEY,
    "caf"                    varchar(11),
    "fl_caf_juridica"        boolean,
    "caf_juridica"           varchar(11),
    "dat_validade_caf"       date,
    "fl_cadastro_secaf"      boolean,
    "fl_servicos_ater"       boolean,
    "fl_oferta_ceasa"        boolean,
    "fl_registro_sanitario"  boolean,
    "fl_assistencia_tecnica" boolean,
    "registro_sanitario"     varchar(3),
    "dat_criacao"            timestamp NOT NULL DEFAULT (now()),
    "dat_atualizacao"        timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE "presidente"
(
    "id"                 uuid PRIMARY KEY,
    "documento"          varchar(14) NOT NULL,
    "contato_id"         uuid        NOT NULL,
    "endereco_id"        uuid        NOT NULL,
    "dados_pessoa_id"    uuid        NOT NULL,
    "pessoa_perfil_id"   uuid        NOT NULL,
    "dat_inicio_mandato" date        NOT NULL,
    "dat_final_mandato"  date        NOT NULL,
    "dat_criacao"        timestamp   NOT NULL DEFAULT (now()),
    "dat_atualizacao"    timestamp   NOT NULL DEFAULT (now())
);

CREATE TABLE "perfil"
(
    "id"   uuid PRIMARY KEY,
    "nome" varchar
);

CREATE TABLE "modulo"
(
    "id"   uuid PRIMARY KEY,
    "nome" varchar
);

CREATE TABLE "permissao"
(
    "id"        uuid PRIMARY KEY,
    "nome"      varchar,
    "modulo_id" uuid
);

CREATE TABLE "perfil_permissao"
(
    "perfil_id"    uuid,
    "permissao_id" uuid
);

ALTER TABLE "pessoa_perfil"
    ADD FOREIGN KEY ("user_id") REFERENCES "usuario" ("id");

ALTER TABLE "pessoa_perfil"
    ADD FOREIGN KEY ("contato_id") REFERENCES "contato" ("id");

ALTER TABLE "pessoa_perfil"
    ADD FOREIGN KEY ("endereco_id") REFERENCES "endereco" ("id");

ALTER TABLE "pessoa_perfil"
    ADD FOREIGN KEY ("vinculo_id") REFERENCES "vinculo" ("id");

ALTER TABLE "pessoa_perfil_anexo"
    ADD FOREIGN KEY ("id_pessoa_perfil") REFERENCES "pessoa_perfil" ("id");

ALTER TABLE "pessoa_perfil_anexo"
    ADD FOREIGN KEY ("id_anexo") REFERENCES "anexo" ("id");

ALTER TABLE "presidente"
    ADD FOREIGN KEY ("contato_id") REFERENCES "contato" ("id");

ALTER TABLE "presidente"
    ADD FOREIGN KEY ("endereco_id") REFERENCES "endereco" ("id");

ALTER TABLE "presidente"
    ADD FOREIGN KEY ("pessoa_perfil_id") REFERENCES "pessoa_perfil" ("id");

ALTER TABLE "presidente"
    ADD FOREIGN KEY ("dados_pessoa_id") REFERENCES "dados_pessoa_fisica" ("id");

ALTER TABLE "permissao"
    ADD FOREIGN KEY ("modulo_id") REFERENCES "modulo" ("id");

ALTER TABLE "perfil_permissao"
    ADD FOREIGN KEY ("perfil_id") REFERENCES "perfil" ("id");

ALTER TABLE "perfil_permissao"
    ADD FOREIGN KEY ("permissao_id") REFERENCES "permissao" ("id");