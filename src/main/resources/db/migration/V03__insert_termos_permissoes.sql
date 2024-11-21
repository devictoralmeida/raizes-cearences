INSERT INTO "termo_condicao" (id, versao, conteudo, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                              nm_usuario_atualizacao)
VALUES ('eb1f62bf-9d16-45c1-be45-bd52f97dffb2',
        '0.0.1',
        'Este é um termo de condição genérico para a versão 0.0.1.',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "modulo" (id, nome, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                      nm_usuario_atualizacao)
VALUES ('4deac62e-fec3-4db1-9702-9165f4014404',
        'Oferta',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "permissao" (id, nome, modulo_id, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                         nm_usuario_atualizacao)
VALUES ('bd7d6cfe-783b-4c64-96be-628b978759f8',
        'Cadastrar',
        '4deac62e-fec3-4db1-9702-9165f4014404',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "permissao" (id, nome, modulo_id, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                         nm_usuario_atualizacao)
VALUES ('b195034b-941b-402a-a17c-0b7e02645d0c',
        'Visualizar',
        '4deac62e-fec3-4db1-9702-9165f4014404',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "permissao" (id, nome, modulo_id, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                         nm_usuario_atualizacao)
VALUES ('b7449d0c-bd16-44e5-9b3f-2d5b696e3217',
        'Editar',
        '4deac62e-fec3-4db1-9702-9165f4014404',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "perfil_acesso" (id, nome, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                             nm_usuario_atualizacao)
VALUES ('634de656-0769-451e-a93a-5ff21ebf3d31',
        'Gerenciar Ofertas',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "perfil_permissao" (perfil_acesso_id, permissao_id)
VALUES ('634de656-0769-451e-a93a-5ff21ebf3d31',
        'bd7d6cfe-783b-4c64-96be-628b978759f8');

INSERT INTO "perfil_permissao" (perfil_acesso_id, permissao_id)
VALUES ('634de656-0769-451e-a93a-5ff21ebf3d31',
        'b195034b-941b-402a-a17c-0b7e02645d0c');

INSERT INTO "perfil_permissao" (perfil_acesso_id, permissao_id)
VALUES ('634de656-0769-451e-a93a-5ff21ebf3d31',
        'b7449d0c-bd16-44e5-9b3f-2d5b696e3217');

INSERT INTO "modulo" (id, nome, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                      nm_usuario_atualizacao)
VALUES ('f99ae0e2-66c6-4ede-ae11-9b5050b105de',
        'Plano Oferta',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "permissao" (id, nome, modulo_id, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                         nm_usuario_atualizacao)
VALUES ('65e0c899-cce1-48e9-b2fe-c26b6c300764',
        'Cadastrar',
        'f99ae0e2-66c6-4ede-ae11-9b5050b105de',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "permissao" (id, nome, modulo_id, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                         nm_usuario_atualizacao)
VALUES ('a7609cbf-22ba-4751-b699-71ec16f5b63d',
        'Visualizar',
        'f99ae0e2-66c6-4ede-ae11-9b5050b105de',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "permissao" (id, nome, modulo_id, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                         nm_usuario_atualizacao)
VALUES ('06a51b37-8cb2-4f61-b5fe-97ee65db268e',
        'Editar',
        'f99ae0e2-66c6-4ede-ae11-9b5050b105de',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "perfil_acesso" (id, nome, dat_criacao, dat_atualizacao, nm_usuario_cadastro,
                             nm_usuario_atualizacao)
VALUES ('7bf3b51e-40d7-4169-b61e-d022e430883c',
        'Gerenciar Plano de Ofertas',
        now(),
        now(),
        'Assistente Virtual',
        'Assistente Virtual');

INSERT INTO "perfil_permissao" (perfil_acesso_id, permissao_id)
VALUES ('7bf3b51e-40d7-4169-b61e-d022e430883c',
        '65e0c899-cce1-48e9-b2fe-c26b6c300764');

INSERT INTO "perfil_permissao" (perfil_acesso_id, permissao_id)
VALUES ('7bf3b51e-40d7-4169-b61e-d022e430883c',
        'a7609cbf-22ba-4751-b699-71ec16f5b63d');

INSERT INTO "perfil_permissao" (perfil_acesso_id, permissao_id)
VALUES ('7bf3b51e-40d7-4169-b61e-d022e430883c',
        '06a51b37-8cb2-4f61-b5fe-97ee65db268e');