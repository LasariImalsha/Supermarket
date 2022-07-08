package bo.custom.impl;

import bo.SuperBO;
import bo.custom.SystemReportBO;
import dao.DAOFactory;
import dao.custom.SystemReportDAO;
import dto.SystemReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemReportBOImpl implements SystemReportBO {
    private final SystemReportDAO systemReportDAO = (SystemReportDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SYSTEMREPORT);

    @Override
    public String getMost(String code) throws SQLException, ClassNotFoundException {
        return systemReportDAO.getMost(code);

    }

    @Override
    public String getLeast(String code) throws SQLException, ClassNotFoundException {
        return systemReportDAO.getLeast(code);
    }

    @Override
    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> all = systemReportDAO.getCustomerIds();
        return all;
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
    public ArrayList<String> getYears() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = systemReportDAO.getYears();
        return all;
    }

    @Override
    public ArrayList<String> getMonth() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = systemReportDAO.getMonth();
        return all;
    }

    @Override
    public ArrayList<String> getDate() throws SQLException, ClassNotFoundException {
        ArrayList<String> all = systemReportDAO.getDates();
        return all;
    }

    @Override
    public ArrayList<SystemReportDTO> getYearlyDetails(String year) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> alllYears = new ArrayList<>();
        ArrayList<SystemReportDTO> all = systemReportDAO.getYearlyDetails(year);
        for (SystemReportDTO i : all) {
            alllYears.add(new SystemReportDTO(i.getOrderId(),i.getOrderDate(),i.getCustomerId(),i.getOrderTime(),i.getOrderCost()));
        }
        return alllYears;
    }

    @Override
    public ArrayList<SystemReportDTO> getMonthlyDetails(String month) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> allMonths = new ArrayList<>();
        ArrayList<SystemReportDTO> all = systemReportDAO.getMonthlyDetails(month);
        for (SystemReportDTO i : all) {
            allMonths.add(new SystemReportDTO(i.getOrderId(),i.getOrderDate(),i.getCustomerId(),i.getOrderTime(),i.getOrderCost()));
        }
        return allMonths;
    }

    @Override
    public ArrayList<SystemReportDTO> getDailyDetails(String Day) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> allDaily = new ArrayList<>();
        ArrayList<SystemReportDTO> all = systemReportDAO.getDailyDetails(Day);
        for (SystemReportDTO i : all) {
            allDaily.add(new SystemReportDTO(i.getOrderId(),i.getOrderDate(),i.getCustomerId(),i.getOrderTime(),i.getOrderCost()));
        }
        return allDaily;
    }

    @Override
    public ArrayList<SystemReportDTO> getCustomerIncome(String id) throws SQLException, ClassNotFoundException {
        ArrayList<SystemReportDTO> allIncome = new ArrayList<>();
        ArrayList<SystemReportDTO> all = systemReportDAO.getCustomerIncome(id);
        for (SystemReportDTO i : all) {
            allIncome.add(new SystemReportDTO(i.getOrderId(),i.getOrderDate(),i.getCustomerId(),i.getOrderTime(),i.getOrderCost()));
        }
        return allIncome;
    }
}
