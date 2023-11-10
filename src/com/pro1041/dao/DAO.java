/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pro1041.dao;

import java.util.List;

/**
 *
 * @author HUNG
 */
public interface DAO<Entity>{
    List<Entity> getSelectAll();

    void insert(Entity entity);

    void delete(String id);

    void update(Entity entity);
}
