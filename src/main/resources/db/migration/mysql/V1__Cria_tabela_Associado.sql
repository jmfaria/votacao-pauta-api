
CREATE TABLE IF NOT EXISTS `votacao_pauta`.`associado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cpf` VARCHAR(45) NULL,
  `nome` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

insert into votacao_pauta.associado(cpf, nome) values('56592423080', 'Associado 01');
insert into votacao_pauta.associado(cpf, nome) values('92392401012', 'Associado 02');
insert into votacao_pauta.associado(cpf, nome) values('62718782021', 'Associado 03');
insert into votacao_pauta.associado(cpf, nome) values('62370466073', 'Associado 04');
insert into votacao_pauta.associado(cpf, nome) values('63654896090', 'Associado 05');
insert into votacao_pauta.associado(cpf, nome) values('44158921082', 'Associado 06');
