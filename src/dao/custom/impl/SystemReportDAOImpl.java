package dao.custom.impl;

import dao.custom.SystemReportDAO;
import dto.SystemReportDTO;
import entity.SystemReport;
//import model.OrderDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemReportDAOImpl implements SystemReportDAO {

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM Customer");
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public ArrayList<String> getYears() throws SQLException, ClassNotFoundException {
        ArrayList<String> year = new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT YEAR(orderDate) FROM Orders");
        while (rst.next()) {
            if(isYearExists(rst.getString(1),year)){

            }else {
                year.add(rst.getString(1));
            }
        }
        return year;
    }

    @Override
    public ArrayList<String> getMonth() throws SQLException, ClassNotFoundException {
        ArrayList<String>month= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT MONTH(orderDate) FROM Orders");

        while (rst.next()) {
            if(isYearExists(rst.getString(1),month)){

            }else {
                month.add(rst.getString(1));
            }
        }
        return month;
    }

    @Override
    public ArrayList<String> getDates() throws SQLException, ClassNotFoundException {
        ArrayList<String>dates= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT DATE(orderDate) FROM Orders");

        while (rst.next()) {
            if(isYearExists(rst.getString(1),dates)){

            }else {
                dates.add(rst.getString(1));
            }
        }
        return dates;
    }

    @Override
    public boolean isYearExists(String string, ArrayList<String> year) {
        for(String y : year){
            if(y.equals(string)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<SystemReportDTO> getYearlyDetails(String year) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> yearlyDetails = new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM Orders WHERE YEAR(orderDate) = ?",year);

        while (rst.next()) {
            SystemReportDTO s = new SystemReportDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    Double.parseDouble(rst.getString(5))
            );
            yearlyDetails.add(s);
        }
        return yearlyDetails;
    }

    @Override
    public ArrayList<SystemReportDTO> getMonthlyDetails(String month) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> MonthlyDetails= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM Orders WHERE MONTH(orderDate) = ?",month);

        while (rst.next()) {
            SystemReportDTO s = new SystemReportDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    Double.parseDouble(rst.getString(5))
            ) {
            };
            MonthlyDetails.add(s);
        }
        return MonthlyDetails;
    }

    @Override
    public ArrayList<SystemReportDTO> getDailyDetails(String date) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> DailyDetails= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM Orders WHERE DATE(orderDate) = ? ",date);
        while (rst.next()) {
            SystemReportDTO s = new SystemReportDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    Double.parseDouble(rst.getString(5))
            );
            DailyDetails.add(s);
        }
        return DailyDetails;
    }

    @Override
    public ArrayList<SystemReportDTO> getCustomerIncome(String id) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> CustomerWiseIncomeDetails= new ArrayList<>();
        ResultSet rst= CrudUtil.executeQuery("SELECT * FROM Orders WHERE custId = ? ",id);

        while (rst.next()) {
            SystemReportDTO s = new SystemReportDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    Double.parseDouble(rst.getString(5))
            );
            CustomerWiseIncomeDetails.add(s);
        }
        return CustomerWiseIncomeDetails;
    }

    @Override
    public String getMost(String iCode) throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.executeQuery("SELECT itemCode,COUNT(itemCode) FROM OrderDetail GROUP BY itemCode ORDER BY itemCode DESC LIMIT 1");
        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public String getLeast(String iCode) throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.executeQuery("SELECT itemCode,COUNT(itemCode) FROM OrderDetail GROUP BY itemCode ORDER BY itemCode ASC LIMIT 1");
        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public boolean add(SystemReport systemReport) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(SystemReport systemReport) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public SystemReport get(String id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<SystemReport> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }
}
