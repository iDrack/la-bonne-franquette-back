package org.labonnefranquette.data.dto.impl;

import lombok.Data;
import org.labonnefranquette.data.model.PaiementType;
import org.labonnefranquette.data.model.entity.Article;

import java.sql.Date;
import java.util.Collection;

@Data
public class PaiementReadDTO  {
    private long id;
    private long commandeId;
    private Date date;
    private PaiementType type;
    private int prix;
    private Collection<Article> articles;
}