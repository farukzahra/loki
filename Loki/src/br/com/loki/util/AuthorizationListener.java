package br.com.loki.util;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.loki.entity.Usuario;
import br.com.loki.managed.SessionManaged;

public class AuthorizationListener implements PhaseListener {
    private static final long serialVersionUID = -8237087853801435858L;

    private static Logger log = Logger.getLogger(AuthorizationListener.class);

    private static final String[] PAGINAS_LIVRE_ACESSO = new String[] { "/login.xhtml" ,  "/cadastro.xhtml"  , "/index.xhtml" };

    public void beforePhase(PhaseEvent event) {
        HttpSession httpSession = JSFHelper.getSession();
        SessionManaged sessionManaged = (SessionManaged) httpSession.getAttribute("sessionManaged");
        HttpServletResponse response = JSFHelper.getResponse();
        Usuario usuarioLogado = sessionManaged != null ? sessionManaged.getUsuarioLogado() : null;
        String currentPage = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        if (!isPaginaLivreAcesso(currentPage)) {
            if (usuarioLogado == null) {
                JSFHelper.redirect("login.jsf");
            }
        }
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidade, proxy-revalidade, private, post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
    }

    private boolean isPaginaLivreAcesso(String paginaCorrente) {
        for (String pagPermitida : PAGINAS_LIVRE_ACESSO) {
            if (paginaCorrente.equals(pagPermitida))
                return true;
        }
        return false;
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
