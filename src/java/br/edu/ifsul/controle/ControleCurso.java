package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CursoDAO;
import br.edu.ifsul.modelo.Curso;
import br.edu.ifsul.dao.InstituicaoDAO;
import br.edu.ifsul.modelo.Instituicao;
import br.edu.ifsul.modelo.Disciplina;
import br.edu.ifsul.dao.DisciplinaDAO;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
/**
 *
 * @author alexandre
 */
@ManagedBean(name = "controleCurso")
@SessionScoped
public class ControleCurso implements Serializable{
    
    private CursoDAO<Curso> dao;
    private Curso objeto;
    private InstituicaoDAO daoInstituicao;
    private DisciplinaDAO daoDisciplina;
    private Disciplina disciplina;
    private Boolean novaDisciplina;
    

    
    public void novaDisciplina(){
        disciplina = new Disciplina();
        novaDisciplina = true;
    }
     public void alterarDisciplina(int index){
        disciplina = objeto.getDisciplina(index);
        novaDisciplina = false;
    }    
    public void removerDisciplina(int index){
        objeto.removerDisciplina(index);
        UtilMensagens.mensagemInformacao("Disciplina removida com sucesso!");
    }
    
    public void salvarDisciplina(){
        if(novaDisciplina){
            objeto.adicionarDisciplina(disciplina);
        }
        UtilMensagens.mensagemInformacao("Operação executada com sucesso!");
    }
    
    public ControleCurso() {
        dao = new CursoDAO<>();
        daoInstituicao = new InstituicaoDAO();        
    }
    
    public String listar(){
        return "/privado/curso/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Curso();
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
    
    public CursoDAO getDao() {
        return dao;
    }

    public void setDao(CursoDAO dao) {
        this.dao = dao;
    }

    public Curso getObjeto() {
        return objeto;
    }

    public void setObjeto(Curso objeto) {
        this.objeto = objeto;
    }
    
    public InstituicaoDAO getDaoInstituicao() {
        return daoInstituicao;
    }

    public void setDaoInstituicao(InstituicaoDAO daoInstituicao) {
        this.daoInstituicao = daoInstituicao;
    }
    
}
