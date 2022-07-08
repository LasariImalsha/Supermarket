package dao;

import dao.custom.CustomerDAO;
import dao.custom.OrderDAO;
import dao.custom.impl.*;
import db.DbConnection;

import javax.xml.bind.annotation.XmlType;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance(){
        return daoFactory==null? daoFactory=new DAOFactory(): daoFactory;
    }

    public enum DAOType{
        CUSTOMER,ITEM,ORDER,ORDERDETAIL,QUERY,SYSTEMREPORT
    }

    public SuperDAO getDAO(DAOType daoType){
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case SYSTEMREPORT:
                return new SystemReportDAOImpl();
            default:
                return null;
        }
    }
}
