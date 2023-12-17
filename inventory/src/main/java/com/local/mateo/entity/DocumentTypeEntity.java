package com.local.mateo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "documentType")
@Getter
@Setter
@AllArgsConstructor
public class DocumentTypeEntity {
    @Id
    private Long id;
    private String name;
    private String shortName;


    public DocumentTypeEntity(){}
}
