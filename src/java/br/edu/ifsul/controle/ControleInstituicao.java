package br.edu.ifsul.controle;

import br.edu.ifsul.dao.InstituicaoDAO;
import br.edu.ifsul.modelo.Instituicao;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;

/**
 *
 * @author alexandre
 */
@ManagedBean(name = "controleInstituicao")
@SessionScoped
public class ControleInstituicao implements Serializable{
    private InstituicaoDAO<Instituicao> dao;
    private Instituicao objeto;    

    public ControleInstituicao() {
        dao = new InstituicaoDAO<>();
    }
    
    public String listar(){
        return "/privado/instituicao/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Instituicao();
        return "formulario";
    }
    
    public String salvar(){
        boolean persistiu;
        if (objeto.getId() == null){
            persistiu = dao.persist(objeto);
        }else {
            persistiu = dao.merge(objeto);
        }       
        if (persistiu){
            UtilMensagens.mensagemInformacao(dao.getMensagem());
            return "listar";
        }else {
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
    
    public void remove(Integer id){
        objeto = dao.localizar(id);
        if(dao.remove(objeto)){
            UtilMensagens.mensagemInformacao(dao.getMensagem());
        }
        else{
            UtilMensagens.mensagemErro(dao.getMensagem());            
        }
    }
    
    public InstituicaoDAO getDao() {
        return dao;
    }

    public void setDao(InstituicaoDAO dao) {
        this.dao = dao;
    }

    public Instituicao getObjeto() {
        return objeto;
    }

    public void setObjeto(Instituicao objeto) {
        this.objeto = objeto;
    }

}
