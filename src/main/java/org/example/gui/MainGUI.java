package org.example.gui;

import org.example.dao.ProductDao;
import org.example.dao.impl.ProductDaoImpl;
import org.example.model.Product;

import javax.swing.*;
import java.util.List;

public class MainGUI {
    private JFrame frame;
    private JList<String> productList;
    private DefaultListModel<String> productListModel;
    private ProductDao productDao;
    private CrudGUI crudGUI;

    public MainGUI() {
        frame = new JFrame("Список товарів");
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        productDao = new ProductDaoImpl();

        JButton calculateButton = new JButton("Обрахувати залишок");
        calculateButton.addActionListener(e -> {
            // Реалізуйте функціонал для обрахунку залишку товарів
        });

        JButton editButton = new JButton("Редагувати товари");
        editButton.addActionListener(e -> {
            frame.setVisible(false);
            crudGUI.showGUI();
        });

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(new JScrollPane(productList));
        frame.add(calculateButton);
        frame.add(editButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        updateProductList();
    }

    public void updateProductList() {
        productListModel.clear();
        List<Product> products = productDao.getAll();
        for (Product product : products) {
            productListModel.addElement(product.getName() + " - " + product.getQuantity());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.crudGUI = new CrudGUI(mainGUI);
        });
    }

    public void showGUI() {
        frame.setVisible(true);
        updateProductList();
    }

}
