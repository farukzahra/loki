package br.com.loki.util;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JSFHelper {
    
    public static Map<String, Object> getApplicationMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
    }

    public static HttpSession getSession() {
        HttpSession httpSession = (HttpSession) getExternalContext().getSession(true);
        return httpSession;
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) getExternalContext().getRequest();
        return request;
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse response = (HttpServletResponse) getExternalContext().getResponse();
        return response;
    }

    public static Application getApplication() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static UIViewRoot getUIViewRoot() {
        return FacesContext.getCurrentInstance().getViewRoot();
    }

    public static void initUIViewRoot() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }

    public static String getSistemaAtual() {
        return getExternalContext().getInitParameter("SISTEMA_ATUAL");
    }

    public static void redirect(String path) {
        try {
            getExternalContext().redirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRequestParameterMap(String param) {
        return getExternalContext().getRequestParameterMap().get(param);
    }

    public static ProjectStage getProjectStage() {
        return FacesContext.getCurrentInstance().getApplication().getProjectStage();
    }
}
