/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.DisciplinaDAO;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;

/**
 *
 * @author alexandre
 */
@ManagedBean(name = "controleDisciplina")
@SessionScoped
public class ControleDisciplina implements Serializable{
    private DisciplinaDAO dao;
    private Disciplina objeto;

    public ControleDisciplina() {
        dao = new DisciplinaDAO();
    }
    
    public String listar(){
        return "/privado/disciplina/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Disciplina();
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
    
    public DisciplinaDAO getDao() {
        return dao;
    }

    public void setDao(DisciplinaDAO dao) {
        this.dao = dao;
    }

    public Disciplina getObjeto() {
        return objeto;
    }

    public void setObjeto(Disciplina objeto) {
        this.objeto = objeto;
    }
    
}
