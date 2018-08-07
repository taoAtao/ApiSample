package com.gateway.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by daip on 2018/6/25.
 */
//@Component("user")
@Entity
@Table(name="user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Component
@Scope("prototype")
@Getter
@Setter
public class User implements Serializable{
    @Id
    @Column(name="ID",length = 10)
    private int id;

    @Column(name="name",length = 20)
    private String name;

    @Column(name="password",length = 20)
    private String password;

}
