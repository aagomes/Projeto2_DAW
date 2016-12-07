/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.EspecialidadeDAO;
import br.edu.ifsul.modelo.Especialidade;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;

/**
 *
 * @author alexandre
 */
@ManagedBean(name = "controleEspecialidade")
@SessionScoped
public class ControleEspecialidade implements Serializable{
    private EspecialidadeDAO dao;
    private Especialidade objeto;

    public ControleEspecialidade() {
        dao = new EspecialidadeDAO();
    }
    
    public String listar(){
        return "/privado/especialidade/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Especialidade();
        return "formulario";
    }
    
    public String salvar(){
        if(dao.salvar(objeto)){
            UtilMensagens.mensagemInformacao(dao.getMensagem());
            return "listar";
        }else{
            UtilMensagens.mensagemErro(dao.getMensagem());
            return "formulario";
        }        
    }
    
    public String cancelar(){
        objeto = null;
        return "listar";
    }
    
    public String editar(Integer id){
        objeto = dao.localizar(id);
        return "formulario";
    }
    
    public void remover(Integer id){
        objeto = dao.localizar(id);
        if(dao.remover(objeto)){
            UtilMensagens.mensagemInformacao(dao.getMensagem());
        }
        else{
            UtilMensagens.mensagemErro(dao.getMensagem());            
        }
    }
    
    public EspecialidadeDAO getDao() {
        return dao;
    }

    public void setDao(EspecialidadeDAO dao) {
        this.dao = dao;
    }

    public Especialidade getObjeto() {
        return objeto;
    }

    public void setObjeto(Especialidade objeto) {
        this.objeto = objeto;
    }
    
}
