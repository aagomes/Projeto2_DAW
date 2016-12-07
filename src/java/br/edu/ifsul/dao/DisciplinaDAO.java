/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.util.Util;
import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Disciplina;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.OverridesAttribute;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author alexandre
 */
public class DisciplinaDAO {
    private Disciplina objetoSelecionado;
    private String mensagem = "";
    private EntityManager em;

    public DisciplinaDAO(){
        em = EntityManagerUtil.getEntityManager();
        }
    public boolean validaObjeto(Disciplina obj){
        Validator validador = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Disciplina>> erros = validador.validate(obj);
        if (erros.size() > 0){
            mensagem = "";
            mensagem += "Objeto com Erros!<br/>";
            for (ConstraintViolation<Disciplina> erro: erros){
                mensagem += "Erro: "+erro.getMessage()+"<br/>";
            }
            return false;
        } else {
            return true;
        }
    }
    
    public List<Disciplina> getLista(){
        return em.createQuery("from Disciplina order by nome").getResultList();
    }
    
    public boolean salvar(Disciplina obj){
        try {
            em.getTransaction().begin();
            if (obj.getId() == null){
                em.persist(obj);
            }else {
                em.merge(obj);
            }
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso! ";
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive() == false){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao persistir:  "+Util.getMensagemErro(e);
            return false;
        }
    }
    
    public boolean remover(Disciplina obj){
        try {
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
            mensagem = "Objeto removido com sucesso! ";
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive() == false){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao remover objeto:  "+Util.getMensagemErro(e);
            return false;
        }
    }
    
    public Disciplina localizar(Integer id){
        return em.find(Disciplina.class, id);
    }
    
    public Disciplina getObjetoSelecionado() {
        return objetoSelecionado;
    }

    public void setObjetoSelecionado(Disciplina objetoSelecionado) {
        this.objetoSelecionado = objetoSelecionado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
}
