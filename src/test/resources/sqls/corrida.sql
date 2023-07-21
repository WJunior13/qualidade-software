INSERT INTO campeonato(codigo_campeonato, descricao, ano) VALUES(2, 'F1 Race' , 2023);
INSERT INTO campeonato(codigo_campeonato, descricao, ano) VALUES(3, 'F2 Race' , 2020);

INSERT INTO pais(id,name) VALUES(2, 'Brazil');
INSERT INTO pais(id,name) VALUES(3, 'Japan');

INSERT INTO pista (id_pista,nome_pista, tamanho_pista, country_id)VALUES (12,'Interlagos', 1000, 2);
INSERT INTO pista (id_pista,nome_pista, tamanho_pista, country_id)VALUES (13,'Singapura', 2000,3);

INSERT INTO corrida (id_corrida,data_corrida, speedway_id_pista, championship_codigo_campeonato) VALUES (15,'2023-07-20T12:34:56Z', 12, 2);
INSERT INTO corrida (id_corrida,data_corrida, speedway_id_pista, championship_codigo_campeonato) VALUES (16,'2020-07-20T12:34:56Z', 13, 3);