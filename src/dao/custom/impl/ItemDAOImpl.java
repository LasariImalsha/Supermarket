package dao.custom.impl;

import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import entity.Customer;
import entity.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?,?,?)",item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand(),item.getDiscountPercentage());
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET description=?, packSize=?, unitPrice=? , qtyOnHand=? , discountPercentage=? WHERE itemCode=?",item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand(),item.getDiscountPercentage(),item.getItemCode());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE itemCode=?",id);
    }

    @Override
    public Item get(String id) throws SQLException, ClassNotFoundException {
        Item item = null;
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE itemCode=?", id);
        if (rst.next()) {
            item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            );
        }
        return item;
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        while (rst.next()) {
            items.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            ));
        }
        return items;
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1");
        if(resultSet.next()){
            int index=Integer.parseInt(resultSet.getString(1).split("-")[1]);
            if(index<9){
                return "I-000"+ ++index;
            }else if(index<99){
                return "I-00"+ ++index;
            }else if(index<999){
                return "I-0"+ ++index;
            }else{
                return "I-"+ ++index;
            }
        }else{
            return "I-0001";
        }
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> items = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item");
        while (rst.next()) {
            items.add(rst.getString(1));
        }
        return items;
    }
}
