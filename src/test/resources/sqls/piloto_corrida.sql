INSERT INTO pais(id,name) VALUES(2, 'Brazil');

INSERT INTO equipe(id_quipe,nome_equipe) VALUES(11, 'Ferrari');
INSERT INTO equipe(id_quipe,nome_equipe) VALUES(20, 'Red Bull');

INSERT INTO piloto (id_piloto,nome_piloto, country_id, team_id_quipe) VALUES (10, 'Felipe Massa', 2, 11);
INSERT INTO piloto (id_piloto,nome_piloto, country_id, team_id_quipe) VALUES (11, 'Barrichelo', 2, 20);

INSERT INTO campeonato(codigo_campeonato, descricao, ano) VALUES(2, 'F1 Race' , 2023);
INSERT INTO pista (id_pista,nome_pista, tamanho_pista, country_id)VALUES (12,'Interlagos', 1000, 2);
INSERT INTO corrida (id_corrida,data_corrida, speedway_id_pista, championship_codigo_campeonato) VALUES (15,'2023-07-20T12:34:56Z', 12, 2);

INSERT INTO piloto_corrida (id,colocacao, pilot_id_piloto, race_id_corrida) VALUES (20,1, 10, 15);
INSERT INTO piloto_corrida (id,colocacao, pilot_id_piloto, race_id_corrida) VALUES (21,4, 11, 15);