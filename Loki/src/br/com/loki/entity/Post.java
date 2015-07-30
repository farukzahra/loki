package br.com.loki.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Lob
    private String descricao;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "post")
    private List<Solicitacao> solicitacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDescricaoCurta() {
        return descricao != null && descricao.length() >= 50 ? descricao.substring(0, 49) : descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Solicitacao> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(List<Solicitacao> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }
    
    public int getTotalSolicitacoes(){
        return solicitacoes != null ? solicitacoes.size() : 0;
    }
}
