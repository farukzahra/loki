package br.com.loki.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;

public class Utils {
    private static Logger log = Logger.getLogger(Utils.class);

    public static void main(String[] args) {
        System.out.println(adicionaZeroEsquerda(14, 46000100));
    }

    public static String adicionaZeroEsquerda(int tamanhoTotal, Number campo) {
        int tamanho = tamanhoTotal - campo.toString().length();
        String result = "";
        for (int i = 0; i < tamanho; i++) {
            result = "0" + result;
        }
        result = result + campo.toString();
        return result;
    }

    public static String formataPorcentagem(Number valor) {
        NumberFormat nf = DecimalFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }

    public static String adicionaZeroEsquerda(String linha) {
        return String.format("%014d", Long.parseLong(linha));
    }

    public static String formataCNPJ(String linha) {
        return format("##.###.###/####-##", adicionaZeroEsquerda(linha));
    }

    public static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getValorParametro(String parametro) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro);
    }

    public static String removeZerosAesquerda(String linha) {
        if (linha != null)
            return linha.replaceFirst("0*", "");
        return null;
    }

    public static String informacoesExceptionConcat(Exception e, int concat) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackTraceElement = e.getStackTrace();
        for (StackTraceElement stack : stackTraceElement) {
            if (stack.getClassName().contains("br.")) {
                sb.append("Classe : " + stack.getFileName());
                sb.append(" | Linha : " + stack.getLineNumber());
                sb.append(" | Metodo : " + stack.getMethodName());
                sb.append("\r\n");
            }
        }
        return split(sb.toString(), concat);
    }


    public static Calendar zeraDatas(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static int deductDates(Date initialDate, Date finalDate) {
        if (initialDate == null || finalDate == null) {
            return 0;
        }
        int days = (int) ((finalDate.getTime() - initialDate.getTime()) / (24 * 60 * 60 * 1000));
        return (days > 0 ? days : 0);
    }

    public static int diferencaEmDias(Date dataInicial, Date dataFinal) {
        Long diferenca = dataFinal.getTime() - dataInicial.getTime();
        Double diferencaEmDias = (double) ((diferenca / 1000) / 60 / 60 / 24);
        return diferencaEmDias.intValue();
    }

    public static String split(String s, int t) {
        return s != null && s.length() > t ? s.substring(0, t) : s;
    }

    public static String dateToString(Date data, String pattern) {
        String dataStr = null;
        if (data != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            dataStr = sdf.format(data);
        }
        return dataStr;
    }

    public static Date stringToDate(String data, String pattern) {
        Date dataRet = null;
        if (data != null && !data.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                dataRet = sdf.parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dataRet;
    }

    public static String listToString(List<String> lista) {
        String out = "";
        for (String string : lista) {
            out += "," + string;
        }
        out = out.replaceFirst(",", "");
        return out;
    }
}
