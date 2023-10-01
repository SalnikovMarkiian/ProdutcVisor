package org.example.gui;

import org.example.dao.ProductDao;
import org.example.dao.impl.ProductDaoImpl;
import org.example.model.Product;

import javax.swing.*;

public class CrudGUI {
    private JFrame frame;
    private JTextField fieldName, fiedlQuantity;
    private JButton addButton, deleteButton, editButton;
    private ProductDao productDAO;
    private MainGUI mainGUI;

    public CrudGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        frame = new JFrame("CRUD Операції");
        productDAO = new ProductDaoImpl();

        fieldName = new JTextField(20);
        fiedlQuantity = new JTextField(20);

        addButton = new JButton("Додати товар");
        deleteButton = new JButton("Видалити товар");
        editButton = new JButton("Редагувати товар");

        addButton.addActionListener(e -> {
            Product product = new Product();
            product.setName(fieldName.getText());
            product.setQuantity(Integer.parseInt(fiedlQuantity.getText()));
            productDAO.create(product);
            fieldName.setText("");
            fiedlQuantity.setText("");
            updateProductList(); // Оновлення списку після додавання товару
        });


        addButton.addActionListener(e -> {
            Product product = new Product();
            product.setName(fieldName.getText());
            product.setQuantity(Integer.parseInt(fiedlQuantity.getText()));
            productDAO.create(product);
            fieldName.setText("");
            fiedlQuantity.setText("");
        });

        // Додайте функціональність для deleteButton та editButton

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(new JLabel("Назва:"));
        frame.add(fieldName);
        frame.add(new JLabel("Кількість:"));
        frame.add(fiedlQuantity);
        frame.add(addButton);
        frame.add(deleteButton);
        frame.add(editButton);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
    }

    private void updateProductList() {
        mainGUI.updateProductList();
    }

    public void showGUI() {
        frame.setVisible(true);
    }
}
