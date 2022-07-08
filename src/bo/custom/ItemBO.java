package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;

import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean addItem(ItemDTO dto);
    boolean updateItem(ItemDTO dto);
    boolean deleteItem(String id);
    String getAllItemIds();
    ArrayList<ItemDTO> getAllItems();
}
