package bo;

import bo.custom.impl.*;
import dao.DAOFactory;
import dao.SuperDAO;
import dao.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance(){
        return boFactory==null? boFactory=new BOFactory() : boFactory;
    }

    public enum BOType{
        CUSTOMER,ITEM,ORDER,ORDERDETAIL,SYSTEMREPORT,MANAGEORDER
    }

    public SuperBO getBO(BOType boType){
        switch (boType) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new ManageOrderBOImpl();
            case ORDERDETAIL:
                return new PlaceOrderBOImpl();
            case SYSTEMREPORT:
                return new SystemReportBOImpl();
            case MANAGEORDER:
                return new ManageOrderBOImpl();
            default:
                return null;
        }
    }
}
