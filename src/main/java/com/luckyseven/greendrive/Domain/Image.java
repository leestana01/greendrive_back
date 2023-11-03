package com.luckyseven.greendrive.Domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] data;
}
