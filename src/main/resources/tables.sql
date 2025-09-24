create DATABASE manutencao_industrial;

use manutencao_industrial;

-- Tabela de Máquinas
CREATE TABLE Maquina (
id INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
setor VARCHAR(50) NOT NULL,
status ENUM("EM_MANUTENCAO", "OPERACIONAL") NOT NULL -- OPERACIONAL / EM_MANUTENCAO
);
-- Tabela de Técnicos
CREATE TABLE Tecnico (
id INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
especialidade VARCHAR(50)
);

select count(nome) from Tecnico
where especialidade = 'ti'
and nome = 'hugo';

-- Tabela de Peças
CREATE TABLE Peca (
id INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100) NOT NULL,
estoque DOUBLE NOT NULL
);

select count(nome) from Peca
where nome = 'motor';

-- Tabela de Ordens de Manutenção
CREATE TABLE OrdemManutencao (
id INT PRIMARY KEY AUTO_INCREMENT,
idMaquina INT NOT NULL,
idTecnico INT NOT NULL,
dataSolicitacao DATE NOT NULL,
status ENUM("PENDENTE", "EXECUTADA", "CANCELADA") NOT NULL, -- PENDENTE / EXECUTADA / CANCELADA
FOREIGN KEY (idMaquina) REFERENCES Maquina(id),
FOREIGN KEY (idTecnico) REFERENCES Tecnico(id)
);
-- Tabela de Peças utilizadas em cada ordem
CREATE TABLE OrdemPeca (
idOrdem INT NOT NULL,
idPeca INT NOT NULL,
quantidade DOUBLE NOT NULL,
PRIMARY KEY (idOrdem, idPeca),
FOREIGN KEY (idOrdem) REFERENCES OrdemManutencao(id),
FOREIGN KEY (idPeca) REFERENCES Peca(id)
);

SELECT * FROM Maquina;
select * from Tecnico;
SELECT * FROM Peca;

                SELECT id, nome, setor, status
                FROM Maquina
                where status = "OPERACIONAL";

                                SELECT id, nome, especialidade
                FROM Tecnico;

select * from OrdemManutencao;
delete from OrdemManutencao
where id = 1;

describe OrdemManutencao;

-- ○ Listar todas as ordens com status: “PENDENTE”

select OrdemManutencao.id as manutencao_id,
idMaquina as maquina_id,
Maquina.nome as maquina_nome,
idTecnico as tecnico_id,
Tecnico.nome as tecnico_nome,
dataSolicitacao,
OrdemManutencao.status
from OrdemManutencao
LEFT JOIN Maquina ON OrdemManutencao.idMaquina = Maquina.id
LEFT JOIN Tecnico ON OrdemManutencao.idTecnico = Tecnico.id
WHERE OrdemManutencao.status = "PENDENTE";

select OrdemManutencao.id as manutencao_id,
Maquina.nome as maquina_nome,
Tecnico.nome as tecnico_nome,
dataSolicitacao,
OrdemManutencao.status
from OrdemManutencao
LEFT JOIN Maquina ON OrdemManutencao.idMaquina = Maquina.id
LEFT JOIN Tecnico ON OrdemManutencao.idTecnico = Tecnico.id
WHERE OrdemManutencao.status = "PENDENTE";