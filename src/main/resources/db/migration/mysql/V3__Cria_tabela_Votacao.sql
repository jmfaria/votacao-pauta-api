CREATE TABLE IF NOT EXISTS `votacao_pauta`.`votacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pauta_id` INT NOT NULL,
  `associado_id` INT NOT NULL,
  `voto` VARCHAR(45) NULL,
  `votado_em` DATETIME NOT NULL,
  INDEX `fk_votacao_pauta_idx` (`pauta_id` ASC),
  INDEX `fk_votacao_associado1_idx` (`associado_id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_votacao_pauta`
    FOREIGN KEY (`pauta_id`)
    REFERENCES `votacao_pauta`.`pauta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_votacao_associado1`
    FOREIGN KEY (`associado_id`)
    REFERENCES `votacao_pauta`.`associado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
