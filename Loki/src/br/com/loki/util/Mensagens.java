package br.com.loki.util;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class Mensagens {
    public static final String REGISTRO_SALVO_COM_SUCESSO = "salvar.sucesso";
    public static final String REGISTRO_REMOVIDO_COM_SUCESSO = "remover.sucesso";
    public static final String ERRO_AO_SALVAR_REGISTRO = "salvar.erro";
    public static final String ERRO_AO_REMOVER_REGISTRO = "remover.erro";
    public static final String ERRO_AO_BUSCAR_REGISTROS = "buscar.erro";
    public static final String USUARIO_SENHA_INVALIDO = "login.user.senha.invalido";
    public static final String USUARIO_SEM_PERFIL = "login.user.sem.perfil";
    public static final String SENHA_EXPIRADA = "login.senha.expirada";
    public static final String LOGIN_ERRO_GENERICO = "login.erro.generico";
    public static final String SENHA_ATUAL_INVALIDA = "trocar.senha.senha.atual.invalida";     
    public static final String SENHA_CONFIRMACAO_INVALIDA = "trocar.senha.senha.confirmacao.invalida";    
    public static final String USUARIO_NAOENCONTRADO = "login.user.naoencontrado";    
    public static final String USUARIO_BLOQUEADO = "login.user.bloqueado";    
    public static final String USUARIO_DUPLICADO = "usuario.user.duplicado";    
    public static final String EMAIL_INVALIDO = "usuario.email.invalido";    
    public static final String RANGE_DATA_INVALIDO = "range.data.invalido";    


    public static String getMensagem(String key) {
        ResourceBundle resource = JSFHelper.getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "msg");
        //ResourceBundle resource = ResourceBundle.getBundle("br.com.lume.resources.tj");
        return resource.getString(key);
    }
    
    public static String[] getMensagemOffLineSplit(String key) {        
        return getMensagemOffLine(key).split(",");
    }
    
    public static String getMensagemOffLine(String key){
        ResourceBundle rb = ResourceBundle.getBundle("br.com.lume.isvendas.resources.isvendas");
        return rb.getString(key);
    }
}
