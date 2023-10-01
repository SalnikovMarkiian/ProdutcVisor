package org.example.gui;

import org.example.dao.ProductDao;
import org.example.model.Product;

import javax.swing.*;
import java.awt.*;

public class AddProductWindow {
    private JTextField nameField;
    private JTextField quantityField;

    public AddProductWindow(ProductDao productDao) {
        JFrame frame = new JFrame("Додати товар");
        nameField = new JTextField(20);
        quantityField = new JTextField(20);
        JButton addButton = new JButton("Додати");

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            Product product = new Product();
            product.setName(name);
            product.setQuantity(quantity);
            productDao.create(product);
            // Вивід повідомлення про успішне додавання
            JOptionPane.showMessageDialog(null, "Товар додано успішно");
            // Закриття вікна після додавання
            frame.dispose();
        });

        frame.setLayout(new GridLayout(3, 2));
        frame.add(new JLabel("Назва:"));
        frame.add(nameField);
        frame.add(new JLabel("Кількість:"));
        frame.add(quantityField);
        frame.add(addButton);

        frame.pack();
        frame.setVisible(true);
    }
}
