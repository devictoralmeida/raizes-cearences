CREATE TABLE "codigo_verificacao"
(
  "id"            UUID       NOT NULL,
  "codigo"        VARCHAR(5) NOT NULL,
  "tipo_codigo" VARCHAR(10) NOT NULL,
  "fl_validado"   boolean,
  "dat_expiracao" TIMESTAMP  NOT NULL,
  CONSTRAINT "pk_codigoverificacao" PRIMARY KEY ("id")
);

CREATE TABLE "usuario"
(
  "id"                     UUID PRIMARY KEY,
  "login"                  VARCHAR(14) NOT NULL,
  "tipo_perfil"            VARCHAR(13) NOT NULL,
  "firebase_uid"           VARCHAR(28) NOT NULL,
  "senha" VARCHAR(255) NULL DEFAULT NULL,
  "codigo_verificacao"     UUID                 DEFAULT NULL,
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"            TIMESTAMP   NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP   NOT NULL DEFAULT (now()),
  CONSTRAINT "usuario_codigo_verificacao" FOREIGN KEY ("codigo_verificacao") REFERENCES "codigo_verificacao" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "usuario_aud"
(
  "id"           UUID        NOT NULL,
  "login"        VARCHAR(14) NOT NULL,
  "tipo_perfil"  VARCHAR(13) NOT NULL,
  "senha"        VARCHAR(255) NULL DEFAULT NULL,
  "cd_auditoria" BIGINT      NOT NULL,
  "tp_movimento" SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbusuarioaud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbusuarioaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "pessoa_perfil"
(
  "id"                     UUID PRIMARY KEY,
  "tipo_usuario"           VARCHAR(15) NOT NULL,
  "documento"              VARCHAR(14) NOT NULL,
  "user_id"                UUID        NOT NULL,
  "contato_id"             UUID        NOT NULL,
  "endereco_id"            UUID        NOT NULL,
  "vinculo_id"             UUID        NOT NULL,
  "dados_pessoa_id"        UUID        NOT NULL,
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"            TIMESTAMP   NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP   NOT NULL DEFAULT (now())
);

CREATE TABLE "pessoa_perfil_aud"
(
  "id"              UUID        NOT NULL,
  "tipo_usuario"    VARCHAR(15) NOT NULL,
  "documento"       VARCHAR(14) NOT NULL,
  "user_id"         UUID        NOT NULL,
  "contato_id"      UUID        NOT NULL,
  "endereco_id"     UUID        NOT NULL,
  "vinculo_id"      UUID        NOT NULL,
  "dados_pessoa_id" UUID        NOT NULL,
  "cd_auditoria"    BIGINT      NOT NULL,
  "tp_movimento"    SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbpessoa_perfilaud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbpessoa_perfilaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "dados_pessoa_fisica"
(
  "id"                     UUID PRIMARY KEY,
  "rg"                     VARCHAR(20)  NOT NULL,
  "orgao_expeditor"        VARCHAR(15),
  "dat_expedicao"          DATE,
  "nome"                   VARCHAR(100) NOT NULL,
  "sobrenome"              VARCHAR(100) NOT NULL,
  "dat_nascimento"         DATE         NOT NULL,
  "sexo"                   VARCHAR(9)   NOT NULL,
  "grau_instrucao"         VARCHAR(18),
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"            TIMESTAMP    NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP    NOT NULL DEFAULT (now())
);

CREATE TABLE "dados_pessoa_fisica_aud"
(
  "id"              UUID         NOT NULL,
  "rg"              VARCHAR(20)  NOT NULL,
  "orgao_expeditor" VARCHAR(15),
  "dat_expedicao"   DATE,
  "nome"            VARCHAR(100) NOT NULL,
  "sobrenome"       VARCHAR(100) NOT NULL,
  "dat_nascimento"  DATE         NOT NULL,
  "sexo"            VARCHAR(9)   NOT NULL,
  "grau_instrucao"  VARCHAR(18),
  "cd_auditoria"    BIGINT       NOT NULL,
  "tp_movimento"    SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbdados_pessoa_fisica_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbdados_pessoa_fisica_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "dados_pessoa_juridica"
(
  "id"                        UUID PRIMARY KEY,
  "razao_social"     VARCHAR(150) NOT NULL,
  "nome_fantasia"             VARCHAR(150),
  "inscricao_junta_comercial" VARCHAR(50),
  "inscricao_estadual"        VARCHAR(12),
  "dat_fundacao"              DATE,
  "caf"              VARCHAR(11),
  "dat_validade_caf" DATE,
  "nm_usuario_atualizacao"    VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"       VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"               TIMESTAMP    NOT NULL DEFAULT (now()),
  "dat_atualizacao"           TIMESTAMP    NOT NULL DEFAULT (now())
);

CREATE TABLE "dados_pessoa_juridica_aud"
(
  "id"                        UUID         NOT NULL,
  "razao_social"     VARCHAR(150) NOT NULL,
  "nome_fantasia"             VARCHAR(150),
  "inscricao_junta_comercial" VARCHAR(50),
  "inscricao_estadual"        VARCHAR(12),
  "dat_fundacao"              DATE,
  "caf"              VARCHAR(11),
  "dat_validade_caf" DATE,
  "cd_auditoria"              BIGINT       NOT NULL,
  "tp_movimento"              SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbdados_pessoa_juridica_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbdados_pessoa_juridica_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "anexo"
(
  "id"                     UUID PRIMARY KEY,
  "nome_anexo"             VARCHAR(255) NOT NULL,
  "tipo_mime"              VARCHAR(5)   NOT NULL,
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"            TIMESTAMP    NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP    NOT NULL DEFAULT (now())
);

CREATE TABLE "anexo_aud"
(
  "id"           UUID         NOT NULL,
  "nome_anexo"   VARCHAR(255) NOT NULL,
  "tipo_mime"    VARCHAR(5)   NOT NULL,
  "cd_auditoria" BIGINT       NOT NULL,
  "tp_movimento" SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbanexo_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbanexo_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "pessoa_perfil_anexo"
(
  "id"                     UUID PRIMARY KEY,
  "tipo_documento"         VARCHAR   NOT NULL,
  "id_pessoa_perfil"       UUID      NOT NULL,
  "id_anexo"               UUID      NOT NULL,
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"            TIMESTAMP NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP NOT NULL DEFAULT (now())
);

CREATE TABLE "pessoa_perfil_anexo_aud"
(
  "id"               UUID    NOT NULL,
  "tipo_documento"   VARCHAR NOT NULL,
  "id_pessoa_perfil" UUID    NOT NULL,
  "id_anexo"         UUID    NOT NULL,
  "cd_auditoria"     BIGINT  NOT NULL,
  "tp_movimento"     SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbpessoa_perfil_anexo_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbpessoa_perfil_anexo_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "contato"
(
  "id"                     UUID PRIMARY KEY,
  "preferencia_contato" VARCHAR(8),
  "numero_contato"         VARCHAR(11),
  "fl_whatsapp"            boolean,
  "numero_whatsapp"        VARCHAR(11),
  "email"                  VARCHAR(320),
  "fl_newsletter"          boolean,
  "dat_criacao"            TIMESTAMP  NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP  NOT NULL DEFAULT (now()),
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL
);

CREATE TABLE "contato_aud"
(
  "id"                  UUID       NOT NULL,
  "preferencia_contato" VARCHAR(8),
  "numero_contato"      VARCHAR(11),
  "fl_whatsapp"         boolean,
  "numero_whatsapp"     VARCHAR(11),
  "email"               VARCHAR(320),
  "fl_newsletter"       boolean,
  "cd_auditoria"        BIGINT     NOT NULL,
  "tp_movimento"        SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbcontato_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbcontato_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "endereco"
(
  "id"                     UUID PRIMARY KEY,
  "cep"                    VARCHAR(8)   NOT NULL,
  "municipio"              VARCHAR(100) NOT NULL,
  "localidade" VARCHAR(100),
  "logradouro" VARCHAR(100),
  "numero"     VARCHAR(10),
  "complemento"            VARCHAR(50),
  "bairro"                 VARCHAR(50),
  "uf"                     VARCHAR(2)   NOT NULL,
  "ponto_referencia"       VARCHAR(50),
  "dat_criacao"            TIMESTAMP    NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP    NOT NULL DEFAULT (now()),
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL
);

CREATE TABLE "endereco_aud"
(
  "id"               UUID         NOT NULL,
  "cep"              VARCHAR(8)   NOT NULL,
  "municipio"        VARCHAR(100) NOT NULL,
  "localidade" VARCHAR(100),
  "logradouro" VARCHAR(100),
  "numero"     VARCHAR(10),
  "complemento"      VARCHAR(50),
  "bairro"           VARCHAR(50),
  "uf"               VARCHAR(2)   NOT NULL,
  "ponto_referencia" VARCHAR(50),
  "cd_auditoria"     BIGINT       NOT NULL,
  "tp_movimento"     SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbendereco_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbendereco_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "vinculo"
(
  "id"                     UUID PRIMARY KEY,
  "fl_cadastro_secaf"      boolean,
  "fl_servicos_ater"       boolean,
  "fl_oferta_ceasa"        boolean,
  "dat_criacao"            TIMESTAMP NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP NOT NULL DEFAULT (now()),
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL
);

CREATE TABLE "vinculo_aud"
(
  "id"                     UUID   NOT NULL,
  "fl_cadastro_secaf"      boolean,
  "fl_servicos_ater"       boolean,
  "fl_oferta_ceasa"        boolean,
  "cd_auditoria"           BIGINT NOT NULL,
  "tp_movimento"           SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbvinculo_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbvinculo_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "presidente"
(
  "id"                     UUID PRIMARY KEY,
  "documento" VARCHAR(11) NOT NULL,
  "contato_id"             UUID        NOT NULL,
  "dados_pessoa_id"        UUID        NOT NULL,
  "pessoa_perfil_id"       UUID        NOT NULL,
  "dat_inicio_mandato"     DATE        NOT NULL,
  "dat_final_mandato"      DATE        NOT NULL,
  "dat_criacao"            TIMESTAMP   NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP   NOT NULL DEFAULT (now()),
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL
);

CREATE TABLE "presidente_aud"
(
  "id"                 UUID        NOT NULL,
  "documento" VARCHAR(11) NOT NULL,
  "contato_id"         UUID        NOT NULL,
  "dados_pessoa_id"    UUID        NOT NULL,
  "pessoa_perfil_id"   UUID        NOT NULL,
  "dat_inicio_mandato" DATE        NOT NULL,
  "dat_final_mandato"  DATE        NOT NULL,
  "cd_auditoria"       BIGINT      NOT NULL,
  "tp_movimento"       SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbpresidente_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbpresidente_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "perfil_acesso"
(
  "id"                     UUID PRIMARY KEY,
  "nome"                   VARCHAR   NOT NULL,
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"            TIMESTAMP NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP NOT NULL DEFAULT (now())
);

CREATE TABLE "perfil_acesso_aud"
(
  "id"           UUID    NOT NULL,
  "nome"         VARCHAR NOT NULL,
  "cd_auditoria" BIGINT  NOT NULL,
  "tp_movimento" SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbperfil_acesso_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbperfil_acesso_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "modulo"
(
  "id"                     UUID PRIMARY KEY,
  "nome"                   VARCHAR   NOT NULL,
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"            TIMESTAMP NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP NOT NULL DEFAULT (now())
);

CREATE TABLE "modulo_aud"
(
  "id"           UUID    NOT NULL,
  "nome"         VARCHAR NOT NULL,
  "cd_auditoria" BIGINT  NOT NULL,
  "tp_movimento" SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbmodulo_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbmodulo_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "permissao"
(
  "id"                     UUID PRIMARY KEY,
  "nome"                   VARCHAR   NOT NULL,
  "nm_usuario_atualizacao" VARCHAR(255) NULL DEFAULT NULL,
  "nm_usuario_cadastro"    VARCHAR(255) NULL DEFAULT NULL,
  "dat_criacao"            TIMESTAMP NOT NULL DEFAULT (now()),
  "dat_atualizacao"        TIMESTAMP NOT NULL DEFAULT (now())
);

CREATE TABLE "permissao_aud"
(
  "id"           UUID    NOT NULL,
  "nome"         VARCHAR NOT NULL,
  "cd_auditoria" BIGINT  NOT NULL,
  "tp_movimento" SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbpermissao_aud" PRIMARY KEY ("id", "cd_auditoria"),
  CONSTRAINT "fk_tbpermissao_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "perfil_permissao"
(
  "perfil_id"    UUID,
  "permissao_id" UUID,
  CONSTRAINT "fk_perfil_permissao_perfil" FOREIGN KEY ("perfil_id") REFERENCES "perfil_acesso" ("id"),
  CONSTRAINT "fk_perfil_permissao_permissao" FOREIGN KEY ("permissao_id") REFERENCES "permissao" ("id")
);

CREATE TABLE "perfil_permissao_aud"
(
  "perfil_id"    UUID   NOT NULL,
  "permissao_id" UUID   NOT NULL,
  "cd_auditoria" BIGINT NOT NULL,
  "tp_movimento" SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_perfil_permissao_aud" PRIMARY KEY ("perfil_id", "permissao_id", "cd_auditoria"),
  CONSTRAINT "fk_perfil_permissao_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "modulo_permissao"
(
  "modulo_id"    UUID NOT NULL,
  "permissao_id" UUID NOT NULL,
  CONSTRAINT "fk_modulo_permissao_modulo" FOREIGN KEY ("modulo_id") REFERENCES "modulo" ("id"),
  CONSTRAINT "fk_modulo_permissao_permissao" FOREIGN KEY ("permissao_id") REFERENCES "permissao" ("id")
);

CREATE TABLE "modulo_permissao_aud"
(
  "modulo_id"    UUID   NOT NULL,
  "permissao_id" UUID   NOT NULL,
  "cd_auditoria" BIGINT NOT NULL,
  "tp_movimento" SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_modulo_permissao_aud" PRIMARY KEY ("modulo_id", "permissao_id", "cd_auditoria"),
  CONSTRAINT "fk_modulo_permissao_aud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "usuario_perfil_acesso"
(
  "usuario_id"       UUID NOT NULL,
  "perfil_acesso_id" UUID NOT NULL,
  CONSTRAINT "fk_tbusuarioperfilacesso_tbusuario" FOREIGN KEY ("usuario_id") REFERENCES "usuario" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "fk_tbusuarioperfilacesso_tbperfilacesso" FOREIGN KEY ("perfil_acesso_id") REFERENCES "perfil_acesso" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE "usuario_perfil_acesso_aud"
(
  "cd_auditoria"     BIGINT NOT NULL,
  "usuario_id"       UUID   NOT NULL,
  "perfil_acesso_id" UUID   NOT NULL,
  "tp_movimento"     SMALLINT NULL DEFAULT NULL,
  CONSTRAINT "pk_tbusuarioperfilacessoaud" PRIMARY KEY ("cd_auditoria", "usuario_id", "perfil_acesso_id"),
  CONSTRAINT "fk_tbusuarioperfilacessoaud_tbauditoria" FOREIGN KEY ("cd_auditoria") REFERENCES "tb_auditoria" ("ci_auditoria") ON UPDATE NO ACTION ON DELETE NO ACTION
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
  ADD FOREIGN KEY ("pessoa_perfil_id") REFERENCES "pessoa_perfil" ("id");

ALTER TABLE "presidente"
  ADD FOREIGN KEY ("dados_pessoa_id") REFERENCES "dados_pessoa_fisica" ("id");