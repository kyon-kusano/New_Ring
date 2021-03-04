package com.example.demo.repository;

import com.example.demo.model.entity.Employee;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {
    final
    EntityManager manager;

    public EmployeeRepositoryCustomImpl(EntityManager manager) {
        this.manager = manager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Employee> searchDepartment(int startIndex, int pageSize, String department, String column) {

        String[] colSort = column.split(",", 0);
        boolean departmentFlg = false;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT g FROM Employee g ");

        if (!"未選択".equals(department)) {
            departmentFlg = true;
            sql.append(" JOIN Department d ON g.department = d.id ");
            sql.append(" WHERE d.name LIKE :department ");
            if (column.equals("default")) {
                sql.append(" ORDER BY join_Date DESC ");
            } else if ("ASC".equals(colSort[1])) {
                if ("join_Date".equals(colSort[0]))
                    sql.append(" ORDER BY join_Date ASC ");
                if ("username".equals(colSort[0]))
                    sql.append(" ORDER BY username ASC ");
            } else if ("DESC".equals(colSort[1])) {
                if ("join_Date".equals(colSort[0]))
                    sql.append(" ORDER BY join_Date DESC ");
                if ("username".equals(colSort[0]))
                    sql.append(" ORDER BY username DESC ");
            }
        } else {
            if ("ASC".equals(colSort[1])) {
                if ("join_Date".equals(colSort[0]))
                    sql.append(" ORDER BY join_Date ASC ");
                if ("username".equals(colSort[0]))
                    sql.append(" ORDER BY username ASC ");
            }
            if ("DESC".equals(colSort[1])) {
                if ("join_Date".equals(colSort[0]))
                    sql.append(" ORDER BY join_Date DESC ");
                if ("username".equals(colSort[0]))
                    sql.append(" ORDER BY username DESC ");
            }
        }
        Query query = manager.createQuery(sql.toString());
        if (departmentFlg) {
            query.setParameter("department", department);
        }
        return (List<Employee>) query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList();

    }

    @Override
    public int findAllCnt() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM Employee g ");
        Query query = manager.createQuery(sql.toString());
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public int findAllCnt(String keyword) {
        boolean keywordFlg = false;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM Employee g ");
        if (!"".equals(keyword) && keyword != null) {
            sql.append(" WHERE g.username LIKE :keyword ");
            keywordFlg = true;
        }
        Query query = manager.createQuery(sql.toString());
        if (keywordFlg)
            query.setParameter("keyword", "%" + keyword + "%");
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public int findDepartmentCnt(String department, String column) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM Employee g ");
        if (!"未選択".equals(department)) {
            sql.append(" JOIN Department d ON g.department = d.id ");
            sql.append(" WHERE d.name LIKE :department ");
        } else {
            Query query = manager.createQuery(sql.toString());
            return ((Number) query.getSingleResult()).intValue();
        }
        Query query = manager.createQuery(sql.toString());
        query.setParameter("department", department);
        return ((Number) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Employee> findListPaging(int startIndex, int pageSize) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT g FROM Employee g ");
        sql.append(" ORDER BY join_Date DESC ");
        Query query = manager.createQuery(sql.toString());
        return (List<Employee>) query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Employee> searchKeyword(int startIndex, int pageSize, String keyword) {
        boolean keywordFlg = false;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT g FROM Employee g ");
        if (!"".equals(keyword) && keyword != null) {
            sql.append(" WHERE g.username LIKE :keyword ");
            keywordFlg = true;
        }
        sql.append(" ORDER BY join_Date DESC ");
        Query query = manager.createQuery(sql.toString());
        if (keywordFlg)
            query.setParameter("keyword", "%" + keyword + "%");
        return (List<Employee>) query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
    }


}
