package com.anmed.storage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by adementiev on 21/09/2017.
 */
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
