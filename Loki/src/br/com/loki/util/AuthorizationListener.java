package br.com.loki.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

public class AuthorizationListener implements PhaseListener {
    private static final long serialVersionUID = -8237087853801435858L;

    private static Logger log = Logger.getLogger(AuthorizationListener.class);

    public void beforePhase(PhaseEvent event) {
//        HttpSession httpSession = JSFHelper.getSession();
//        SessionManaged sessionManaged = (SessionManaged) httpSession.getAttribute("sessionManaged");
//        HttpServletResponse response = JSFHelper.getResponse();
//        // Participante usuarioLogado = (Participante)JSFHelper.getSession().getAttribute("usuarioLogado");
//        String currentPage = FacesContext.getCurrentInstance().getViewRoot().getViewId();
//        // log.debug("Usuario Logado : " + (usuarioLogado != null ? usuarioLogado.getSpaChaApelido() : "null") +
//        // ". Acessando a tela: "+currentPage);
//        if (currentPage.contains("/")) {
//            String[] diretorios = currentPage.split("/");
//            currentPage = diretorios[diretorios.length - 1];
//        }
//        if (currentPage.contains("xhtml")) {
//            currentPage = currentPage.replaceAll("xhtml", "jsf");
//        }
//        Usuario usuarioLogado = sessionManaged != null ? sessionManaged.getUsuarioLogado() : null;
//        if (usuarioLogado == null) {
//            String strUsu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usu");
//            String pswUsu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pwd");
//            if (strUsu != null && pswUsu != null) {
//                try {
//                    usuarioLogado = new UsuarioBO().loginCrypt(strUsu, pswUsu);
//                    if (usuarioLogado != null) {
//                        sessionManaged = LumeManagedBean.createInstance();
//                        sessionManaged.setUsuarioLogado(usuarioLogado);
//                        sessionManaged.setEmpresaLogada(usuarioLogado.getEmpresa());
//                        sessionManaged.setEmpresaSelecionada(usuarioLogado.getEmpresa());
//                        sessionManaged.setIndiceConfiguracoes(new IndiceConfiguracaoBO().listAll());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        Objeto objetoAtual = isPageDenied(currentPage, usuarioLogado, sessionManaged);
//        // Tentou entrar em uma pagina deslogado || entrou em uma pagina logado mas que não esta autorizado
//        if ((!currentPage.contains("sobre") && !currentPage.contains("login") && usuarioLogado == null) || (!currentPage.contains("trocarSenha") && !currentPage.contains("sobre") && !currentPage.contains("login") && usuarioLogado != null && objetoAtual == null)) {
//            try {
//                log.debug("Redirecionou da pagina : " + currentPage + " UserLogado : " + usuarioLogado);
//                JSFHelper.getExternalContext().redirect("login.jsf");
//            } catch (IOException ex) {
//                log.error("Erro no beforePhase", ex);
//            }
//        }
//        if (usuarioLogado != null)
//            try {
//                String paginaAnterior = (String) JSFHelper.getSession().getAttribute("PAGINA_ANTERIOR");
//                if (!currentPage.equals(paginaAnterior)) {
//                    new LogAcessoBO().persist(new LogAcesso(usuarioLogado, currentPage, JSFHelper.getRequest().getRemoteAddr(), "VENDAS"));
//                    JSFHelper.getSession().setAttribute("PAGINA_ANTERIOR", currentPage);
//                }
//            } catch (Exception e) {
//                log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
//            }
//        response.setHeader("Expires", "-1");
//        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidade, proxy-revalidade, private, post-check=0, pre-check=0");
//        response.setHeader("Pragma", "no-cache");
    }

        @Override
    public void afterPhase(PhaseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
