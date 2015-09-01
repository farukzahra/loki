package br.com.loki.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "solicitacao")
public class Solicitacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario solicitante;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Column(length = 1)
    private Character status;

    @ManyToOne
    private Post post;

    @Transient
    public static final char CRIADO = 'C';

    @Transient
    public static final char APROVADO = 'A';

    @Transient
    public static final char NEGADO = 'N';

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isNovo(){
        return status == CRIADO;
    }
    
    public boolean isAprovado(){
        return status == APROVADO;
    }
    
    public String getStatusCompleto(){
        if(status == CRIADO){
            return "Criado";
        }else if(status == APROVADO){
            return "Aprovado";
        }else if(status == NEGADO){
            return "Negado";
        }
        return "";
    }
    
}
