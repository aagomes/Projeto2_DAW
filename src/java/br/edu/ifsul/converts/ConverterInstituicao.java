/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.converts;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Instituicao;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;


/**
 *
 * @author alexandre
 */
@FacesConverter(value = "converterInstituicao")
public class ConverterInstituicao implements Serializable, Converter {
    // método que converte da tela para o objeto
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.equals("Selecionar um registro")){
            return null;
        }
        try {
            return EntityManagerUtil.getEntityManager().find(Instituicao.class,
                    Integer.parseInt(string));
        } catch (Exception e) {
            return null;
        }
    }
    
    //método que converte do objeto para a tela
    @Override  
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null){
            return null;
        }
        Instituicao obj = (Instituicao) o;
        return obj.getId().toString();
    }
    
    
}
