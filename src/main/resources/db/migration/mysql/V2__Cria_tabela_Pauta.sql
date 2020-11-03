
CREATE TABLE IF NOT EXISTS `votacao_pauta`.`pauta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL,
  `descricao` VARCHAR(45) NULL,
  `valida_ate` DATETIME NULL,
  `encerrada` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

insert into votacao_pauta.pauta(nome, descricao) 
values('Pauta01', 'Descricao Pauta01');
insert into votacao_pauta.pauta(nome, descricao) 
values('Pauta02', 'Descricao Pauta02');
insert into votacao_pauta.pauta(nome, descricao) 
values('Pauta03', 'Descricao Pauta03');
insert into votacao_pauta.pauta(nome, descricao) 
values('Pauta04', 'Descricao Pauta04');
insert into votacao_pauta.pauta(nome, descricao) 
values('Pauta05', 'Descricao Pauta05');