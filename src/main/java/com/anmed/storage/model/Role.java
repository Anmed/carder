package com.anmed.storage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@Table(
        name = "role",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@EqualsAndHashCode
public class Role {

    @Id
    @GeneratedValue
    private Long roleId;
    private String name;


}
