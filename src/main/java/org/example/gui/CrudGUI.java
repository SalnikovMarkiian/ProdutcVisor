package org.example.gui;

import org.example.dao.ProductDao;
import org.example.dao.impl.ProductDaoImpl;
import org.example.model.Product;

import javax.swing.*;

public class CrudGUI {
    private JFrame frame;
    private JTextField fieldName, fieldQuantity;
    private JButton addButton, deleteButton, editButton, returnButton;
    private ProductDao productDAO;
    private MainGUI mainGUI;

    public CrudGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        frame = new JFrame("CRUD Операції");
        productDAO = new ProductDaoImpl();

        fieldName = new JTextField(20);
        fieldQuantity = new JTextField(20);

        addButton = new JButton("Додати товар");
        deleteButton = new JButton("Видалити товар");
        editButton = new JButton("Редагувати товар");
        returnButton = new JButton("Повернутися на головне вікно");


        addButton.addActionListener(e -> {
            new AddProductWindow(productDAO);
            updateProductList();
        });

        deleteButton.addActionListener(e -> {
            new DeleteProductWindow(productDAO);
            updateProductList();
        });

        editButton.addActionListener(e -> {
            new EditProductWindow(productDAO);
            updateProductList();
        });

        // Додайте функціональність для deleteButton та editButton

        returnButton.addActionListener(e -> {
            closeGUI();
            mainGUI.showGUI(); // Повернення на головне вікно
        });

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(new JLabel("Назва:"));
        frame.add(fieldName);
        frame.add(new JLabel("Кількість:"));
        frame.add(fieldQuantity);
        frame.add(addButton);
        frame.add(deleteButton);
        frame.add(editButton);
        frame.add(returnButton);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
    }

    private void updateProductList() {
        mainGUI.updateProductList();
    }

    public void showGUI() {
        frame.setVisible(true);
    }

    public void closeGUI() {
        frame.dispose();
    }
}
