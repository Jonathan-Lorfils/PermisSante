package com.tp1_jonathanlorfils.demo.model;


//import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Permis implements Serializable {

    @Id
    @GeneratedValue
    private Integer idPermis;

//    @NotNull
    private String typePermis;

    private byte[] codeQR;
}
