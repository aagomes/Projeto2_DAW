/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author alexandre
 */
public class DAOGenerico<T> implements Serializable{
    
    private List<T> listaObjetos; //retorno da consulta paginada
    private List<T> listaTodos; // retorna todos os registros
    private EntityManager em;
    private Class classePersistente;
    private String mensagem = "";
    private String ordem = "id";
    private String filtro = "";
    private Integer maximoObjetos = 3;
    private Integer posicaoAtual = 0;
    private Integer totalObjetos = 0;
    
    public DAOGenerico(){
        em = EntityManagerUtil.getEntityManager();
    }
    
    

    public List<T> getListaObjetos() {
        String jpql = "from "+classePersistente.getSimpleName();
        String where = "";
        filtro = filtro.replaceAll("[';-]", "");//protegendo contra injeção de sql
        if (filtro.length() > 0){
            if (ordem.equals("id")){
                try {
                    Integer.parseInt(filtro);
                    where += " where " +ordem + " = '" + filtro + "'";
                } catch (Exception e) {
                }
                
            }else
            {
                where += " where upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
            }            
        }
        jpql += where;
        jpql += " order by " + ordem;
        totalObjetos = em.createQuery("select id from "+classePersistente.getSimpleName() +
                where + " order by "+ ordem).getResultList().size();
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).
                getResultList();
    }
    
    public void primeiro(){
        posicaoAtual = 0;
    }
    public void anterior(){
        posicaoAtual -= maximoObjetos;
        if (posicaoAtual < 0){
            posicaoAtual = 0;
        }
    }
    public void proximo(){
        if (posicaoAtual + maximoObjetos < totalObjetos){
            posicaoAtual += maximoObjetos;
        }
    }
    public void ultimo(){
        int resto = totalObjetos % maximoObjetos;
        if(resto > 0){
            posicaoAtual = totalObjetos - resto;
        }else {
            posicaoAtual = totalObjetos - maximoObjetos;
        }
    }
    public String getMensagemNavegacao(){
        int ate = posicaoAtual + maximoObjetos;
        if (ate > totalObjetos){
            ate = totalObjetos;
        }
        return "Listando de "+ (posicaoAtual + 1) + " até " + ate + " de " +
                totalObjetos + " registros. ";
    }
    
    public List<T> getListaTodos() {
        String jpql = "from "+classePersistente.getSimpleName();
        return em.createQuery(jpql).getResultList();
    }
    
    public boolean persist(T obj){
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso !";
            return true;
        } catch (Exception e) {
            if(em.getTransaction().isActive() == false){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao persistir objeto "+e.getMessage();
            return false;
        }
    }
    
        public boolean merge(T obj){
        try {
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
            mensagem = "Objeto alterado com sucesso !";
            return true;
        } catch (Exception e) {
            if(em.getTransaction().isActive() == false){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao alterar objeto "+e.getMessage();
            return false;
        }
    }
        
        public boolean remove(T obj){
        try {
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
            mensagem = "Objeto removido com sucesso !";
            return true;
        } catch (Exception e) {
            if(em.getTransaction().isActive() == false){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao remover objeto "+e.getMessage();
            return false;
        }
    }
        
        public T localizar(Integer id){
            return (T) em.find(classePersistente, id);
        }
    
    public void setListaObjetos(List<T> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }    

    public void setListaTodos(List<T> listaTodos) {
        this.listaTodos = listaTodos;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassePersistente() {
        return classePersistente;
    }

    public void setClassePersistente(Class classePersistente) {
        this.classePersistente = classePersistente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }
    
}
