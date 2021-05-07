package com.tp1_jonathanlorfils.demo.model;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
public class PermisTest  extends Permis implements Serializable {

//    @Nullable
    private LocalDate datePermisTest;
//    @Nullable
    private LocalDate ExpirationPermisTest;

    public PermisTest(){
        setDatePermisTest(LocalDate.now());
        setExpirationPermisTest(LocalDate.now().plusDays(3));
    }
}
